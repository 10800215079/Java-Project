package com.gov.Authmis.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gov.Authmis.service.SchedulerForRemainderService;

@Transactional
@Service
public class SchedulerForRemainderServiceImpl implements SchedulerForRemainderService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MailProperties mailProperties;

	private JavaMailSender javaMailSender;

	public SchedulerForRemainderServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	static Logger logger = LoggerFactory.getLogger(SchedulerForErrorReportServiceImpl.class);

	String subject;
	String content = null;
	String s = null;
	String status;
	String[] to;
	String [] toCC;
	List<Map<String, Object>> remainderdetails = null;
	String previousDateString;
	String currentDateString;
	String returnStr = null;

	@Override
	public void setRemainder() {

		// get only todays remainder data
		remainderdetails = getRemainderdetails();
		System.out.println("Remainder data: ..." + remainderdetails);

		remainderdetails.forEach(detail -> {
			String mailto = (String) detail.get("MAIL_TO");
			String mailcc = (String) detail.get("MAIL_CC");
			
			String projectname = (String) detail.get("PROJECT_NAME");
			String task = (String) detail.get("PERFORM_ACTIVITY");
			Date remainderdate = (Date) detail.get("REMAINDER_DATE");
			
			BigDecimal getRemainderDay = (BigDecimal) detail.get("GET_REMAINDER");
			Long getRemainder = getRemainderDay.longValue();

			//Given Reminder Date
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
			String remainderDateStr = dateFormat.format(remainderdate);	
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
			LocalDate reDate = LocalDate.parse(remainderDateStr, formatter);
		
			
			// Calculate the exact date
			LocalDate exactDate = reDate.minusDays(getRemainder);
			

			LocalDate currentDate = LocalDate.now();
			boolean flag = false;
			if(currentDate.isEqual(exactDate) || currentDate.isAfter(exactDate)) {
				exactDate = currentDate;
				flag = true;
			}
			
			// Compare the exactDate with the current system date
			if (flag && (exactDate.isBefore(reDate) || exactDate.isEqual(reDate)  )) {
				try {
					  sendMailOnRemainderDate(projectname, remainderDateStr, task,mailto,mailcc);
											
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

		});

	}

	private void sendMailOnRemainderDate(String projectname, String remainderDateStr, String task, String mailto, String mailcc) throws MessagingException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate currentDate = LocalDate.now();
		currentDateString = dtf.format(currentDate);
	
		//Manage Body of the mail
		content = setEmailTemplateforRemainders(task,remainderDateStr);
		
		//String[] to = { "ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in", "pm.uid@rajasthan.gov.in" };
		//String[] toCC = { "doitc.aua@rajasthan.gov.in", "SUPPORT.UID@rajasthan.gov.in" };
		
		String [] to = (mailto).split(",");
		
		
		subject = "Activity Reminder : " + projectname + " ";

		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);

		helper.setFrom("donotreply.uidsupport@raj.gov.in");
		helper.setTo(to);
		
		if(mailcc != null) {
			toCC = (mailcc).split(",");
			helper.setCc(toCC);
			}
		
		helper.setSubject(subject);
		helper.setText(content, true);

		// send mail
		try {
		    javaMailSender.send(mail);
		    saveLogs(String.join(",", to),String.join(",", mailcc), "success", currentDateString);
			logger.info("Mail Send Successfully .....");
		} catch (MailException e) {
		    saveLogs(String.join(",", to),String.join(",", mailcc), "Failed", currentDateString);
			logger.info("Failed to Send Mail .....");
		    e.printStackTrace(); 
		}	
		
	}

	@Transactional
	private void saveLogs(String to, String toCC, String returnStr, String currentDateString) {
		try {
	        
	        String query = "INSERT INTO AADHAAR.REMAINDER_LOG_DETAIL (SEND_TO, SEND_CC, STATUS, MAIL_DATE) VALUES (?,?, ?, TO_DATE(?, 'DD-MM-YYYY'))";

	        int rowsAffected = entityManager.createNativeQuery(query)
	                .setParameter(1, to)
	                .setParameter(2, toCC)
	                .setParameter(3, returnStr)
	                .setParameter(4, currentDateString)
	                .executeUpdate();

	        System.out.println("Successfully saved data. Rows affected: " + rowsAffected);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	private String setEmailTemplateforRemainders(String task, String remainderDateStr) {
		 String s = "Dear User,<br/><br/><br/>This is a system generated reminder for the following activity : ";
			
			s = s + "<br/><table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
			s = s + "<br/><tr id = 'dateRow'>";

			s = s + "</tr>";
			s = s + "<tr>";
			s = s + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Activity Description</td>";
			s = s + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Reminder End Date</td>";
			s = s + "</tr>";

			
				s = s + "<tr>";
				s = s + "<td style='border: 2px solid; padding-left: 5px;'>" + task + "</td>";
				s = s + "<td style='border: 2px solid; padding-left: 5px;'>" + remainderDateStr + "</td>";
				

			s = s + "</table>";
			s = s + "<br/>For more details,May kindly login AADHAAR MIS portal on SSO. ";
			s = s + "</br>";	
			s = s + "<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>IP Ext - 21354<br>Ph: 0141-2921354</p>";

			s = s + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
			s = s + "<br/><p>Kindly Revert on : <b> doitc.aua@rajasthan.gov.in </b></p>";
			
			

		return s;
	}

	public List<Map<String, Object>> getRemainderdetails() {

		String sql = " SELECT PROJECT_NAME,PERFORM_ACTIVITY,REMAINDER_DATE,GET_REMAINDER,MAIL_TO,MAIL_CC FROM AADHAAR.REMAINDER_DETAILS WHERE STATUS = 1";

		Query query = this.entityManager.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		System.out.println("resultList--------------" + resultList);

		List<Map<String, Object>> listofremainderdetails = new ArrayList<>();

		for (Object[] row : resultList) {
			Map<String, Object> rowMap = new HashMap<>();
			rowMap.put("PROJECT_NAME", row[0]);
			rowMap.put("PERFORM_ACTIVITY", row[1]);
			rowMap.put("REMAINDER_DATE", row[2]);
			rowMap.put("GET_REMAINDER", row[3]);
			rowMap.put("MAIL_TO", row[4]);
			rowMap.put("MAIL_CC", row[5]);

			listofremainderdetails.add(rowMap);
		}

		return listofremainderdetails;
	}

}
