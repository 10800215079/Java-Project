package com.gov.Authmis.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;
import com.gov.Authmis.jpa.SchedulerForSendingFluctuationReportInMailRepository;
import com.gov.Authmis.model.MailContentModal;
import com.gov.Authmis.model.SchedulerForSendingFluctuationReportInMailModel;
import com.gov.Authmis.service.SchedulerForSendingFluctuationReportInMailService;

@Service
public class SchedulerForSendingFluctuationReportInMailServiceImpl
		implements SchedulerForSendingFluctuationReportInMailService {

	static Logger logger = LoggerFactory.getLogger(SchedulerForSendingFluctuationReportInMailServiceImpl.class);
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private SchedulerForSendingFluctuationReportInMailRepository schedulerForSendingFluctuationReportInMailRepository;

	String subject;
	String content = null;
	String s = null;
	String status;
	String previousDateString;
	String currentDateString;
	String returnStr = null;
	private JavaMailSender javaMailSender;
	StringBuilder s1 = new StringBuilder();
	static boolean checkNotification = true;
	static boolean checkResumeNotification = true;
	@Transactional
	public void checkSuccessRate() throws MessagingException {
		
		// Get current time and calculate the time range for the last 5 minutes
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
		logger.info("checkSuccessRate method=======>>>>>  " + now + " " );
		
		// Format dates to match the required format in SQL
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
		String startTime = fiveMinutesAgo.format(formatter);
		String endTime = now.format(formatter);

		String sql = " SELECT "
				+ "    CASE "
				+ "        WHEN ((SUM(CASE WHEN authentication_status IN ('Y', 'y') THEN 1 ELSE 0 END) / COUNT(1)) * 100) < 10 THEN 1 "
				+ "        ELSE 0 "
				+ "    END AS is_below_50"
				+ " FROM "
				+ "    AADHAAR.aua_request "
				+ " WHERE "
				+ "    CREATED_DATE BETWEEN TO_DATE(?, 'DD/MM/YY HH24:MI:SS') "
				+ "    AND TO_DATE(?, 'DD/MM/YY HH24:MI:SS') ";

		// Execute the query
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, startTime);
		query.setParameter(2, endTime);

		// if success % is below 70% it will return 1.
		int result = ((Number) query.getSingleResult()).intValue();
		List<ViewShareLicenceKeyDetails> subauaDetails = sendFluctuationReportOnAllSubaua(startTime, endTime);
		// if 1, then check getting which error code is it in between ('931','932','933','934','935','936','937','938','939')
		if (result == 1  ) {
			if(checkNotification) {
				logger.info("success rate < 70 %=======>>>>>  " + now + " " );
				// Check error is in between
				// ('931','932','933','934','935','936','937','938','939')
				String checkUidaiError = "SELECT "
						+ "    MAX(CASE WHEN error_count > 5 THEN 1 ELSE 0 END) AS result"
						+ " FROM ("
						+ "    SELECT "
						+ "        TO_CHAR(CREATED_DATE, 'HH24MI') AS created_hour_minute,"
						+ "        COUNT(1) AS error_count,"
						+ "        ERROR"
						+ "    FROM "
						+ "        aadhaar.aua_request"
						+ "    WHERE "
						+ "        CREATED_DATE BETWEEN TO_DATE(?, 'DD/MM/YY HH24:MI:SS') "
						+ "        AND TO_DATE(?, 'DD/MM/YY HH24:MI:SS')"
						+ "        AND ERROR IN ('931', '932', '933', '934', '935', '936', '937', '938', '939')"
						+ "    GROUP BY "
						+ "        TO_CHAR(CREATED_DATE, 'HH24MI'),"
						+ "        ERROR"
						+ "    ORDER BY "
						+ "        TO_CHAR(CREATED_DATE, 'HH24MI') DESC"
						+ " )" ;

				Query queryForCheckError = entityManager.createNativeQuery(checkUidaiError);
				queryForCheckError.setParameter(1, startTime);
				queryForCheckError.setParameter(2, endTime);

				//int resultListforUidaiError = ((Number) queryForCheckError.getSingleResult()).intValue();			
				Number resultofQuery = (Number) queryForCheckError.getSingleResult();
				int resultListforUidaiError = (resultofQuery != null) ? resultofQuery.intValue() : 0;
				
				if (resultListforUidaiError == 1) {
					logger.info("Error between 931 to 939 =======>>>>>>>");
					// Create Excel 
					String excelData = createExcelForFluctuationReport(startTime, endTime);	
					
					// create mail format and attach excel
					sendFluctuationReportOnMail(excelData, startTime, endTime);
				}
				
				// Check error is in between
				// ('336','337','931','932','933','934','935','936','937','938','939')
				else {
					logger.info("check error in between 336 to 939=======>>>>>>>");
					
					String checkOurError = "SELECT MAX(CASE WHEN error_count > 200 THEN 1 ELSE 0 END) AS result "
							+ " FROM (SELECT TO_CHAR(CREATED_DATE, 'HH24MI') AS created_hour_minute,COUNT(1) AS error_count, "
							+ " ERROR FROM aadhaar.aua_request "
							+ " WHERE CREATED_DATE BETWEEN TO_DATE(?, 'DDMMYY HH24MISS') AND TO_DATE(?, 'DDMMYY HH24MISS') "
							+ " AND ERROR IN ('336','337','931','932','933','934','935','936','937','938','939') "
							+ " GROUP BY TO_CHAR(CREATED_DATE, 'HH24MI'), ERROR   ORDER BY "
							+ " TO_CHAR(CREATED_DATE, 'HH24MI') DESC ) ";

					Query queryForCheckOurError = entityManager.createNativeQuery(checkOurError);
					queryForCheckError.setParameter(1, startTime);
					queryForCheckError.setParameter(2, endTime);

					//int resultListforcheckOurError = ((Number) queryForCheckError.getSingleResult()).intValue();
					Number resultOfOwnIssue = (Number) queryForCheckError.getSingleResult();
					int resultListforcheckOurError = (resultOfOwnIssue != null) ? resultOfOwnIssue.intValue() : 0;
					
					// Create mail to subAUA
					

					if (resultListforcheckOurError != 0) {
						logger.info("Get error is between 336 to 939=======>>>>>>>");
					    for (ViewShareLicenceKeyDetails subauadata : subauaDetails) {
					        // Send mail to active subAUA one by one
					        if (subauadata.getActive() != 0) {
					        	logger.info("Notified all active subaua for error=======>>>>>>>");
					            sendFluctuationNotificationToSubaua(subauadata, startTime);
					        }
					    }
					} else {
					    for (ViewShareLicenceKeyDetails subauadata : subauaDetails) {
					    	logger.info("Notified all active subaua for error is resolve=======>>>>>>>");
					        sendResumptionNotificationToSubaua(subauadata, startTime);
					    }
					}

				}
			}
		
			checkNotification = false;
			
		} else {
			checkNotification = true;
			if(checkResumeNotification) {
				for (ViewShareLicenceKeyDetails subauadata : subauaDetails) {
			    	logger.info("Notified all active subaua for error is resolve=======>>>>>>>");
			        sendResumptionNotificationToSubaua(subauadata, startTime);
			    }
				 checkResumeNotification = false;
			}
		}
		
	}

	private void sendResumptionNotificationToSubaua(ViewShareLicenceKeyDetails subauadata, String startTime) {
		logger.info("inside sendResumptionNotificationToSubaua=======>>>>>>>");
		MailContentModal mailContent = new MailContentModal();
		 String emailRICS = subauadata.getEmail();
		 String[] to = emailRICS.split(",");
		 
		 String[] toCC = {"pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in" };
		 
		 String subject = "Notification: Resumption of Aadhaar services (DoIT&C)";
		 
		 s1.append("Dear Partners,<br><br>");
		 s1.append("This is to inform you that the following services are working fine.<br><br>");
		 
		 s1.append("<b><u>Business Management Communication</u></b><br><br>");
		 s1.append("<table border='1' cellpadding='10' cellspacing='0'>");
		 s1.append("<tr><td><b>Current Situation</b></td><td><b>Observing normal response in Aadhaar Services</b></td></tr>");
		 s1.append("<tr><td><b>Environment</b></td><td>Production</td></tr>");
		 s1.append("<tr><td><b>Start Date & Time</b></td><td> " + startTime + "</td></tr>");
		 s1.append("<tr><td><b>Estimated End Date & Time</b></td><td>To be determined</td></tr>");
		 s1.append("<tr><td><b>Contact Information</b></td><td>pm.uid@rajasthan.gov.in, doitc.aua@rajasthan.gov.in</td></tr>");
		 s1.append("<tr><td><b>Remarks</b></td><td>Issue has been fixed</td></tr>");
		 s1.append("</table><br>");
		 s1.append("We value and appreciate your time and patience. We regret for the inconvenience caused.");
		 
		 s1.append("This is a system-generated email, please do not reply to this email. For any kind of support or clarification, please send an email to DOITC.AUA@RAJASTHAN.GOV.IN<br><br>");
		s1.append("<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>")
		  .append("Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>")
		  .append("IP Ext - 21354<br>Ph: 0141-2921354</p>");
		s1.append("<br>");
		s1.append("======================================================================<br>");
		s1.append("<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or ")
		  .append("entity to which they are addressed. You shall not share this message or any of its attachments to anyone without proper authorization.</p>");
		s1.append("======================================================================");

	    // Output the email content
		System.out.println(s1.toString());
		String emailContent = s1.toString();
		
		mailContent.setTo(to);
		mailContent.setToCC(toCC);
		mailContent.setSubject(subject);
		mailContent.setMailBody(emailContent);
		
		 logger.info("sendResumptionNotificationToSubaua : Ready to Call sendHtmlMail");
        try {
			sendHtmlMail(mailContent);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 	
		
	}

	private List<ViewShareLicenceKeyDetails> sendFluctuationReportOnAllSubaua(String startTime, String endTime) {
		//Get Subaua Details
		logger.info("inside sendFluctuationReportOnAllSubaua=======>>>>>>>");
		return schedulerForSendingFluctuationReportInMailRepository.findByActive(1);		
	}

	private void sendFluctuationNotificationToSubaua(ViewShareLicenceKeyDetails subauadata, String startTime) {
		logger.info("inside sendFluctuationNotificationToSubaua method=======>>>>>>>");
		 MailContentModal mailContent = new MailContentModal();
		 String emailRICS = subauadata.getEmail();
		 String[] to = emailRICS.split(",");
		 
		 String[] toCC = {"pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in" };
		 
		 String subject = "Notification: Intermitted fluctuation in Aadhaar services (DoIT&C)";
		 
		 s1.append("Dear Partners,<br><br>");
		 s1.append("This is to inform you that the following services are impacted due to some issues at UIDAI end.<br><br>");
		 
		 s1.append("<b><u>Business Management Communication</u></b><br><br>");
		 s1.append("<table border='1' cellpadding='10' cellspacing='0'>");
		 s1.append("<tr><td><b>Current Situation</b></td><td><b>Intermittent fluctuation in production environment Aadhaar Services</b></td></tr>");
		 s1.append("<tr><td><b>Environment</b></td><td>Production</td></tr>");
		 s1.append("<tr><td><b>Start Date & Time</b></td><td> " + startTime + "</td></tr>");
		 s1.append("<tr><td><b>Estimated End Date & Time</b></td><td>To be determined</td></tr>");
		 s1.append("<tr><td><b>Contact Information</b></td><td>pm.uid@rajasthan.gov.in, doitc.aua@rajasthan.gov.in</td></tr>");
		 s1.append("<tr><td><b>Remarks</b></td><td>Our technical team is working to fix the issue.</td></tr>");
		 s1.append("</table><br>");
		 s1.append("We value and appreciate your time and patience. We regret for the inconvenience caused.");
		 
		 s1.append("This is a system-generated email, please do not reply to this email. For any kind of support or clarification, please send an email to DOITC.AUA@RAJASTHAN.GOV.IN<br><br>");
 		s1.append("<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>")
 		  .append("Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>")
 		  .append("IP Ext - 21354<br>Ph: 0141-2921354</p>");
 		s1.append("<br>");
 		s1.append("======================================================================<br>");
 		s1.append("<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or ")
 		  .append("entity to which they are addressed. You shall not share this message or any of its attachments to anyone without proper authorization.</p>");
 		s1.append("======================================================================");

 	    // Output the email content
 		System.out.println(s1.toString());
 		String emailContent = s1.toString();
 		
 		mailContent.setTo(to);
 		mailContent.setToCC(toCC);
 		mailContent.setSubject(subject);
 		mailContent.setMailBody(emailContent);
 		
 		 logger.info("sendFluctuationNotificationToSubaua : Ready to Call sendHtmlMail");
         try {
 			sendHtmlMail(mailContent);
 		} catch (MessagingException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		 	
	}

	
	private void sendFluctuationReportOnMail(String excelData, String startTime, String endTime) throws MessagingException {
		logger.info("inside sendFluctuationReportOnMail =======>>>>>>>");
		
		MailContentModal mailContent = new MailContentModal();
		String[] to = {"pankajjaldeep.doit@rajasthan.gov.in", "pm.uid@rajasthan.gov.in" };
		String[] toCC = {"SUPPORT.UID@rajasthan.gov.in" };

		mailContent.setTo(to);
		mailContent.setToCC(toCC);
		
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		
		String subject = "Getting Error on Production.";

		s1.append("Dear Sir/Ma'am,<br><br>");
		s1.append("We are getting Error On Production. Please find the attachment for fluctuation report below.<br><br>");
		s1.append("AUA Code : 0000440000 <br><br>");
		s1.append("Environment : Production <br><br>");
		
		s1.append("This is a system-generated email, please do not reply to this email. For any kind of support or clarification, please send an email to DOITC.AUA@RAJASTHAN.GOV.IN<br><br>");
		s1.append("<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>")
		  .append("Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>")
		  .append("IP Ext - 21354<br>Ph: 0141-2921354</p>");
		s1.append("<br>");
		s1.append("======================================================================<br>");
		s1.append("<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or ")
		  .append("entity to which they are addressed. You shall not share this message or any of its attachments to anyone without proper authorization.</p>");
		s1.append("======================================================================");

		// Output the email content
		System.out.println(s1.toString());
		
		String emailContent = s1.toString();

		mailContent.setTo(to);
		mailContent.setToCC(toCC);
		mailContent.setSubject(subject);
		mailContent.setMailBody(emailContent);

		logger.info("sendFluctuationReportOnMailWithExcel : Ready to Call sendHtmlMail");
		try {
			sendHtmlMail(mailContent);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void sendHtmlMail(MailContentModal mailContent) throws MessagingException {
		if (mailContent.getTo() != null && mailContent.getTo().length > 0) {
	          
			MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("donotreply.uidsupport@raj.gov.in");
            helper.setTo(mailContent.getTo());
            helper.setCc(mailContent.getToCC());
            helper.setSubject(mailContent.getSubject());
            helper.setText(mailContent.getMailBody(), true);

            javaMailSender.send(mail);
            logger.info("Mail send successfully=======>>>>>>>");
        } else {
            throw new MessagingException("Recipient list is empty.");
        }
	}

	private String createExcelForFluctuationReport(String startTime, String endTime) {
		logger.info("inside createExcelForFluctuationReport method =======>>>>>>>");
		// TODO Auto-generated method stub
		String detailsOfQuery = " Select CREATED_DATE , ERROR , REQUEST_TYPE , VER , TRANSACTION_ID , UID_RESPONSE_CODE "
				+ " from aadhaar.aua_request where "
				+ " CREATED_DATE BETWEEN TO_DATE (?, 'DDMMYY HH24MISS') AND TO_DATE(?, 'DDMMYY HH24MISS') "
				+ " AND ERROR in ('931','932','933','934','935','936','937','938','939') " + " order by 1 desc ";
		
		
		Query queryForCheckError = entityManager.createNativeQuery(detailsOfQuery);
		queryForCheckError.setParameter(1, startTime);
		queryForCheckError.setParameter(2, endTime);

		List<SchedulerForSendingFluctuationReportInMailModel> getresults = queryForCheckError.getResultList();

		String excelFilePath = "Fluctuation_Report.xlsx";

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Fluctuation Report");

			// Create header row
			Row headerRow = sheet.createRow(0);
			String[] headers = { "CREATED_DATE", "ERROR", "REQUEST_TYPE", "VER", "TRANSACTION_ID",
					"UID_RESPONSE_CODE" };
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Write data rows
			int rowNum = 1;
			for (SchedulerForSendingFluctuationReportInMailModel request : getresults) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(request.getCreatedDate().toString());
				row.createCell(1).setCellValue(request.getError());
				row.createCell(2).setCellValue(request.getRequestType());
				row.createCell(3).setCellValue(request.getVer());
				row.createCell(4).setCellValue(request.getTransactionId());
				row.createCell(5).setCellValue(request.getUidResponseCode());
			}

			// Write the workbook to a file
			try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
				workbook.write(fileOut);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Excel is ready =======>>>>>>>");
		return excelFilePath;

	}
}
