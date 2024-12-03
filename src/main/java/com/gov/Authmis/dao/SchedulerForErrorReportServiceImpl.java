package com.gov.Authmis.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.gov.Authmis.service.SchedulerForErrorReportService;

@Service
@Transactional
public class SchedulerForErrorReportServiceImpl implements SchedulerForErrorReportService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MailProperties mailProperties;

	private JavaMailSender javaMailSender;
	@Autowired
	private SubauaWiseTransactionServiceImpl subauaWiseTransactionServiceImpl;
	
	public SchedulerForErrorReportServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	static Logger logger = LoggerFactory.getLogger(SchedulerForErrorReportServiceImpl.class);
	
	List<Map<String, Object>> resultOfDays;
	String auaCode;
	String auaName;
	String subject;
	String s1 = null;
	String status;
	String[] to;
	List<Map<String, Object>> subaualistdataforRICS;
	List<Map<String, Object>> subAUAData = null;
	String previousDateString;
	String currentDateString;
	

	public void createConnection() throws UnknownHostException {
		// get subaua data
		subAUAData = getsubauadetails();
		System.out.println("Sub aua data: ..." + subAUAData);

		subAUAData.forEach(detail -> {			
			String subaua = (String) detail.get("SUB_AUA_CODE");
			// get subaua wise error list
			List<Map<String, Object>> gettransdata = getTransDataOfSubaua(subaua);
			System.out.println("---------------->>>>>subaua " + detail.get("SUB_AUA_CODE") + "--------->>>>>>data "+ gettransdata);
			System.out.println("EMAIL: " + detail.get("EMAIL"));
			System.out.println("ORGNAME: " + detail.get("ORGNAME"));
			if (!gettransdata.isEmpty()) {
				if (detail.get("EMAIL") != null) {
					try {
						checkConditions(detail, gettransdata);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} 
					
				} else {
					try {
						saveLogs(auaCode, "", "Their is no Email Id.", currentDateString, previousDateString);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}

		});

	}

	public void checkConditions(Map<String, Object> detail, List<Map<String, Object>> gettransdata) throws UnknownHostException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		currentDateString = dtf.format(currentDate);
		System.out.println("Current Date: " + currentDateString);

		// Get the previous date
		LocalDate previousDate = currentDate.minusDays(1);
		previousDateString = dtf.format(previousDate);
		System.out.println("Previous Date: " + previousDateString);

		if (detail.size() != 0) {

			auaCode = (String) (detail.get("SUB_AUA_CODE"));
			auaName = (String) (detail.get("ORGNAME"));
			String emailID = (String) (detail.get("EMAIL"));

			subject = "Daily Aadhaar Authentication Error Code Report - " + auaName + ", Date : " + previousDateString + " ";
			to = (emailID).split(",");
			System.out.println("to-------------->>>>>>>>> " + to);

			try {
				s1 = setEmailTemplateforDaysDataforOthers(auaCode, auaName, previousDateString, gettransdata);
				status = sendHtmlMail(to, subject, s1);
			} catch (MessagingException e) {
				e.printStackTrace();
				// saveLogs();
				saveLogs(auaCode, String.join(",", to), "Failed", currentDateString, previousDateString);
			}

			if (status == "success") {
				saveLogs(auaCode, String.join(",", to), status, currentDateString, previousDateString);
				logger.info("Mail Send Successfully .....");
			      
			} else {
				saveLogs(auaCode, String.join(",", to), "Failed", currentDateString, previousDateString);
				logger.info("Failed to Send Mail .....");
			}

		}
	}


	public void saveLogs(String subaua, String to, String status, String currentDateString, String previousDateString) throws UnknownHostException {
	    try {
	        InetAddress Ip = InetAddress.getLocalHost();
	        String ip = Ip.toString();

	        String query = "INSERT INTO AADHAAR.SUBAUA_ERROR_LOG_DETAIL(SUBAUA_CODE, EMAIL_ID_TO, IP, STATUS, TRANS_REPORT_DATE, MAIL_DATE) "
	                     + "VALUES (?, ?, ?, ?, TO_DATE(?, 'DD-MM-YYYY'), TO_DATE(?, 'DD-MM-YYYY'))";

	        int rowsAffected = entityManager.createNativeQuery(query)
	                .setParameter(1, subaua)
	                .setParameter(2, to)
	                .setParameter(3, ip)
	                .setParameter(4, status)
	                .setParameter(5, previousDateString)
	                .setParameter(6, currentDateString)
	                .executeUpdate();

	        System.out.println("Successfully saved data. Rows affected: " + rowsAffected);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public String setEmailTemplateforDaysDataforOthers(String auaCode, String auaName, String previousDateString,
			List<Map<String, Object>> gettransdata) {
		s1 = "Dear Sir/Ma'am,<br/><br/><br/>Please find Error Code wise Aadhaar Authentication Transaction Report for your Sub-Aua "
				+ auaName + " as below.<br/>";
		s1 = s1 + "<br/>For more details,May kindly login AADHAAR MIS portal on SSO. ";
		s1 = s1 + "<br/><table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
		s1 = s1 + "<br/><tr id = 'dateRow'>";
		s1 = s1 + "<td colspan='6' style='text-align:center; padding: 5px; border: 2px solid;'><b>Error Code List : Date: "
				+ previousDateString + " , SUBAUA Name: " + auaName + "</b></td>";
		s1 = s1 + "</tr>";
		s1 = s1 + "<tr>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Sub-Aua</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Request Type</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Dp Id</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Model Id</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>No of Device</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Transaction Count</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Error Code</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Response Message</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Authentication Status</td>";
		s1 = s1 + "</tr>";

		for (int j = 0; j < gettransdata.size(); j++) {
			s1 = s1 + "<tr>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("orgname")+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("Request_type")+ "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("dp_id") + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("MI") + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("count_of_devices")+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("count_conditionally") + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("error") + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("response_message")+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + gettransdata.get(j).get("authentication_status") + "</td>";
		}

		s1 = s1 + "</table>";
		s1 = s1 + "</br>";	
		s1 = s1 + "<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>IP Ext - 21354<br>Ph: 0141-2921354</p>";

		//s1 = s1 + "============================================================================================================================================================================";
		s1 = s1 + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
		//s1 = s1 + "============================================================================================================================================================================";
		s1 = s1 + "<br/><p>Kindly Revert on : <b> doitc.aua@rajasthan.gov.in </b></p>";
		
		return s1;
	}

	public String sendHtmlMail(String[] to, String subject, String content) throws MessagingException {
		String returnStr;
		
		if (to != null && to.length > 0) {
			
			//String[] toCC = { "ranveersingh.doit@rajasthan.gov.in", "pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in", "SUPPORT.UID@rajasthan.gov.in" };
			//String[] toCC = {"pm.uid@rajasthan.gov.in","SUPPORT.UID@rajasthan.gov.in"};
			String[] toCC = {"doitc.aua@rajasthan.gov.in","SUPPORT.UID@rajasthan.gov.in","pm.uid@rajasthan.gov.in"};

			MimeMessage mail = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
						
			helper.setFrom("donotreply.uidsupport@raj.gov.in");
			helper.setTo(to);
			helper.setCc(toCC);
			helper.setSubject(subject);
			helper.setText(content, true);

			//send mail
			javaMailSender.send(mail);

			returnStr = "success";
		} else {
			returnStr = "failed";
		}
		return returnStr;
	}

	public List<Map<String, Object>> getsubauadetails() {

		//For Production
		String sql = "select SUBAUA_CODE, ORGNAME, EMAIL from AADHAAR.SUBAUA where Active='1' and Email is not null";
		//String sql = "select SUBAUA_CODE, ORGNAME, EMAIL from AADHAAR.SUBAUA where subaua_code in  ('0000440000') ";
		//String sql = "select SUBAUA_CODE, ORGNAME, EMAIL from AADHAAR.SUBAUA where subaua_code in  ('STGDOITRAJ') ";
		
		Query query = this.entityManager.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		List<Map<String, Object>> listofdetails = new ArrayList<>();

		for (Object[] row : resultList) {
			Map<String, Object> rowMap = new HashMap<>();
			rowMap.put("SUB_AUA_CODE", row[0]);
			rowMap.put("ORGNAME", row[1]);
			rowMap.put("EMAIL", row[2]);

			listofdetails.add(rowMap);
		}

		return listofdetails;
	}

	public List<Map<String, Object>> getTransDataOfSubaua(String subaua) {
		String vWhereClause = "";
		List<Map<String, Object>> mapList = new ArrayList<>();
		Date[] dateRange = getDateRange();
		
		String fromDateString = formatDate(dateRange[0]);
		String toDateString = formatDate(dateRange[1]);
		 
		List<Map<String, Object>> todaysData = new ArrayList<>();
		
		
		// for testing purpose 
		
		// Assuming that fromDateString and toDateString are Strings
		/*
		 * String fromDateString = "03/01/2024 00:00:00"; // Start of the day String
		 * toDateString = "03/01/2024 23:59:59"; // End of the day
		 */
		/*
		 * if (fromDateString != null && toDateString != null) { vWhereClause +=
		 * "AR.CREATED_DATE >= TO_DATE('" + fromDateString +
		 * "', 'DD/mm/yy HH24:MI:SS') " + "AND AR.CREATED_DATE <= TO_DATE('" +
		 * toDateString + "', 'DD/mm/yy HH24:MI:SS')"; }
		 */
		
					
		if (fromDateString != null && toDateString != null) {
			vWhereClause += "AR.CREATED_DATE >= TO_DATE('" + fromDateString + "', 'DD/mm/yy HH24:MI:SS') "
					+ "AND AR.CREATED_DATE < TO_DATE('" + toDateString + "', 'DD/mm/yy HH24:MI:SS')";
		}
		 
		if (!"ALL".equals(subaua)) {
			vWhereClause += " AND S.SUBAUA_CODE =  '" + subaua + "' ";
		}

		vWhereClause += " AND AR.ERROR NOT IN ('336', '337', '338', '930', '931', '932', '933', '934', '935', '936', '937', '938', '939') ";
		
		vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";

		try {
			String sql = "";					
			sql = "SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, "
					+ "  COUNT(AR.AUTHENTICATION_STATUS) AS COUNT, UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS "
					+ "  FROM AADHAAR.AUA_REQUEST_BKP AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ "  WHERE " + vWhereClause + " GROUP BY "
					+ "  S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, UPPER(AR.AUTHENTICATION_STATUS) "
					+ "  ORDER BY S.SUBAUA_CODE, COUNT DESC ";
			
						
			
			// for testing  purpose
			/**
			 * sql = "SELECT S.SUBAUA_CODE AS
			 * SUB_AUA_CODE,S.ORGNAME,AR.REQUEST_TYPE,AR.ERROR,AR.RESPONSE_MESSAGE,AR.DP_ID,AR.MI,
			 * " + " COUNT(AR.AUTHENTICATION_STATUS) AS COUNT, COUNT(DISTINCT mac_address)
			 * AS device, " + " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS " +
			 * " FROM AADHAAR.AUA_REQUEST AR " + " INNER JOIN AADHAAR.SUBAUA S ON
			 * AR.SUB_AUA_KEY = S.SUBAUA_CODE " + " WHERE " + vWhereClause + " AND
			 * AR.AUTHENTICATION_STATUS IS NOT NULL " + " GROUP BY " + "
			 * S.SUBAUA_CODE,S.ORGNAME,AR.REQUEST_TYPE,AR.ERROR,AR.RESPONSE_MESSAGE,AR.DP_ID,UPPER(AR.AUTHENTICATION_STATUS),AR.MI
			 * " + " ORDER BY " + " SUB_AUA_CODE ";
			 **/
						
			/*
			 * 
			 * sql =
			 * "SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, AR.MI, "
			 * +
			 * " count(AR.AUTHENTICATION_STATUS) AS COUNT,  count(distinct mac_address) device, "
			 * +
			 * " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS FROM AADHAAR.AUA_REQUEST_BKP AR "
			 * + " INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE  " +
			 * " WHERE  " + vWhereClause +
			 * " AND AR.AUTHENTICATION_STATUS is not null GROUP BY S.SUBAUA_CODE, S.ORGNAME, "
			 * +
			 * " AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, UPPER(AR.AUTHENTICATION_STATUS),AR.MI "
			 * ;
			 */
			
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object[]> data = query.getResultList();

			for (Object[] innerList : data) {

				Map<String, Object> map = new HashMap<>();
				map.put("sub_aua_key", innerList[0]);
				map.put("orgname", innerList[1]);
				map.put("Request_type", innerList[2]);
				map.put("error", innerList[3]);
				map.put("response_message", innerList[4]);
				//map.put("dp_id", innerList[5]);
				//map.put("MI", innerList[6]);
				map.put("count_conditionally", innerList[5]);
				//map.put("count_of_devices", innerList[8]); // device count
				map.put("authentication_status", innerList[6]);

				mapList.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mapList;
	}

	private Date[] getDateRange() {
		// Get current date
		Date currentDate = new Date();

		// Create a calendar instance and set it to the current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Set the time to the end of the current day
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		// The 'to' date is now set to the end of the current day
		Date toDate = calendar.getTime();

		// Move to the previous day
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		// Set the time to the start of the previous day
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// The 'from' date is now set to the start of the previous day
		Date fromDate = calendar.getTime();

		// Return the date range as an array
		return new Date[] { fromDate, toDate };
	}

	private String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(date);
	}

}
