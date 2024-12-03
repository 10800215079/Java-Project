package com.gov.Authmis.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.UploadSecurityAuditReportEntity;
import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;
import com.gov.Authmis.jpa.DeactivateSubauaForNotSubmittingAuditReporty;
import com.gov.Authmis.jpa.UploadSecurityAuditReportReposiroty;
import com.gov.Authmis.jpa.ViewshareLicenceKeyRepository;
import com.gov.Authmis.model.MailContentModal;
import com.gov.Authmis.service.SchedulerForSendingRemainderForAuditReportService;

@Service
@Transactional
public class SchedulerForSendingRemainderForAuditReportServiceImpl implements SchedulerForSendingRemainderForAuditReportService {
	@Autowired
	UploadSecurityAuditReportReposiroty uploadSecurityAuditReportReposiroty;
	@Autowired
	DeactivateSubauaForNotSubmittingAuditReporty deactivateSubauaForNotSubmittingAuditReporty;
	@Autowired
	private ViewshareLicenceKeyRepository viewshareLicenceKeyRepository;
	static Logger logger = LoggerFactory.getLogger(SchedulerForSendingRemainderForAuditReportServiceImpl.class);
	
	private JavaMailSender javaMailSender;
	public SchedulerForSendingRemainderForAuditReportServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	String subject;
	StringBuilder s1 = new StringBuilder();
	    
	public void createConnection() throws UnknownHostException, ParseException {
		logger.info("createConnection method==========>");		
		//List<UploadSecurityAuditReportEntity> getAlldetails = uploadSecurityAuditReportReposiroty.findAll();
		List<UploadSecurityAuditReportEntity> getAlldetails = uploadSecurityAuditReportReposiroty.findByStatus(1);
		
		//get only due date		
        for (UploadSecurityAuditReportEntity report : getAlldetails) {
        	//if status is 1 that means it is active then process other hand if it is deactivate no needs to further process 
        	if(!report.getStatus().equals(0)) {
        		Timestamp dueDateTimestamp = report.getDueDate();
        		//Calculate Due date
        		int checkDateRange = compareDueDates(dueDateTimestamp);
        		//if date is within a range
        		if(checkDateRange != 0 ) {
        			logger.info("checkDateRange not equal to 0=======>>>>>");
					try {
							//get sub aua contact details	
							Optional<ViewShareLicenceKeyDetails> subauaContactDetails = getSubauaDetails(report.getSubauaCode());
							sendRemainderMail(subauaContactDetails,report.getAppName(),report.getDueDate(),checkDateRange);
						
					} catch (UnknownHostException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }
    }
	    

	private void suspendServicesForSubAua(String subAuaCode) {
		deactivateSubauaForNotSubmittingAuditReporty.updateStatus(subAuaCode,0L);			
		}


	private void sendRemainderMail(Optional<ViewShareLicenceKeyDetails> subauaContactDetails, String appName, Timestamp dueDate, int checkDateRange) throws UnknownHostException, ParseException {
		logger.info("sendRemainderMail method==========>to set mailContent");
		MailContentModal mailContent = new MailContentModal();
        mailContent.setSubAuaCode(subauaContactDetails.get().getSubAuaCode());      
        mailContent.setSubauaName(subauaContactDetails.get().getOrgName());      
      
        InetAddress ip = InetAddress.getLocalHost();
        String ipAddress = ip.toString();

        String changedDueDate = dueDate.toString();
        
        String outputDate;
		
		DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = inputFormatter.parse(changedDueDate);

		DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yy");
		outputDate = outputFormatter.format(date);
		mailContent.setOldExpirydate(outputDate);

        String emailRICS = subauaContactDetails.get().getEmail();
        String[] to = emailRICS.split(",");
        
        String[] toCC = {"pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in" };
        //String[] toCC = {"support.uid@rajasthan.gov.in"};
        mailContent.setToCC(toCC);
        
        

		//Date in Range
        if(checkDateRange == 1) {
        		subject = "Security Audit Report Pending- SubAUA " + mailContent.getSubauaName() + " .";
        		mailContent.setTo(to);

        		//StringBuilder s1 = new StringBuilder();
        		s1.append("Dear Sir/Ma'am,<br><br>");
        		s1.append("It is hereby informed that the status of submission of the half-yearly security audit report for the applications of " + mailContent.getSubauaName() + " is as below.<br><br>");
        		
        		// Start the table
        		s1.append("<table border='1' cellpadding='5' style='width: 100%;'"
        				+ "cellspacing='0'>");
        		s1.append("<tr style='background-color: green; color: white;'>");
        		s1.append("<th>S.No.</th>");
        		s1.append("<th>Sub-AUA Name</th>");
        		s1.append("<th>Application Name</th>");
        		s1.append("<th>Security Audit Report submission Due Date</th>");
        		s1.append("<th>Current Status</th>");
        		s1.append("</tr>");

        		// Add dynamic values to the table
        		s1.append("<tr>");
        		s1.append("<td>1</td>");
        		s1.append("<td>" + mailContent.getSubauaName() + "</td>");
        		s1.append("<td>" + appName + "</td>"); 
        		s1.append("<td>" + outputDate + "</td>");
        		s1.append("<td>Not Submitted</td>");
        		s1.append("</tr>");

        		// Close the table
        		s1.append("</table><br><br>");

        		s1.append("As per the SOP dated: 12-07-2014 issued by DOIT&C, it is mandatory to conduct application security audit of all Aadhaar integrated applications of Sub-AUA by a CERT-In empanelled auditor in every six months.<br><br>");
        		s1.append("Due dates for submission of security audit reports to DoIT&C are 31<sup>st</sup> January and 31<sup>st</sup> July for every year.<br><br>");       		
        		s1.append("Therefore, it is requested to kindly submit the application security reports for all Aadhaar integrated application as above before the due date without fail. Without the receipt of the security audit report, Aadhaar services may be suspended.<br><br>");        		
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
        		logger.info("sendRemainderMail====> Calling sendHtmlMail from 1");

        	}
        
        //Date id after 30 of Range
        else if(checkDateRange == 2) {
        	subject = "Security Audit Report Pending- SubAUA " + mailContent.getSubauaName()+ " ";
    		mailContent.setTo(to);

    		//StringBuilder s1 = new StringBuilder();
    		s1.append("Dear Sir/Ma'am,<br><br>");
    		s1.append("It is hereby informed that the status of submission of the half-yearly security audit report for the applications of " + mailContent.getSubauaName() + " is as below.<br><br>");
    		
    		// Start the table
    		s1.append("<table border='1' cellpadding='5' style='width: 100%;'"
    				+ "cellspacing='0'>");
    		s1.append("<tr style='background-color: green; color: white;'>");
    		s1.append("<th>S.No.</th>");
    		s1.append("<th>Sub-AUA Name</th>");
    		s1.append("<th>Application Name</th>");
    		s1.append("<th>Security Audit Report submission Due Date</th>");
    		s1.append("<th>Current Status</th>");
    		s1.append("</tr>");

    		// Add dynamic values to the table
    		s1.append("<tr>");
    		s1.append("<td>1</td>");
    		s1.append("<td>" + mailContent.getAppName() + "</td>");
    		s1.append("<td>" + appName + "</td>"); 
    		s1.append("<td>" + outputDate + "</td>");
    		s1.append("<td>Not Submitted</td>");
    		s1.append("</tr>");

    		// Close the table
    		s1.append("</table><br><br>");
    		
    		s1.append("As per the SOP dated: 12-07-2014 issued by DOIT&C, it is mandatory to conduct an application security audit of all Aadhaar integrated applications of Sub-AUA by a CERT-In empanelled auditor in every six months.<br><br>");
    		s1.append("Due dates for submission of security audit reports to DoIT&C are 31<sup>st</sup> January and 31<sup>st</sup> July for every year.<br><br>");       		
    		s1.append("Therefore, it is requested to kindly submit the application security reports for all Aadhaar integrated application as above before the due date without fail. Without the receipt of the security audit report, Aadhaar services may be suspended.<br><br>");        		
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
    		logger.info("sendRemainderMail====> Calling sendHtmlMail from 2");
  	
        	}
        
        //Date after 30+ means 31
        else if(checkDateRange == 3) {
        	//method to stop services making status 0 in subaua table 
			suspendServicesForSubAua(mailContent.getSubAuaCode());
        	
        	subject = "Security Audit Report Pending- SubAUA " + mailContent.getSubauaName()+ " Deactivated.";
    		mailContent.setTo(to);

    		//StringBuilder s1 = new StringBuilder();
    		s1.append("Dear Sir/Ma'am,<br><br>");
    		s1.append("It is hereby informed that the status of submission of the half-yearly security audit report for the applications of " + mailContent.getSubauaName() + " is as below.<br><br>");
    		
    		// Start the table
    		s1.append("<table border='1' cellpadding='5' style='width: 100%;'"
    				+ "cellspacing='0'>");
    		s1.append("<tr style='background-color: green; color: white;'>");
    		s1.append("<th>S.No.</th>");
    		s1.append("<th>Sub-AUA Name</th>");
    		s1.append("<th>Application Name</th>");
    		s1.append("<th>Security Audit Report submission Due Date</th>");
    		s1.append("<th>Current Status</th>");
    		s1.append("</tr>");

    		// Add dynamic values to the table
    		s1.append("<tr>");
    		s1.append("<td>1</td>");
    		s1.append("<td>" + mailContent.getAppName() + "</td>");
    		s1.append("<td>" + appName + "</td>");
    		s1.append("<td>" + outputDate + "</td>");
    		s1.append("<td>Not Submitted</td>");
    		s1.append("</tr>");

    		// Close the table
    		s1.append("</table><br><br>");
    		
    		s1.append("As per the SOP dated: 12-07-2014 issued by DOIT&C, it is mandatory to conduct application security audit of all Aadhaar integrated applications of Sub-AUA by a CERT-In empanelled auditor in every six months.<br><br>");
    		s1.append("Due dates for submission of security audit reports to DoIT&C are 31<sup>st</sup> January and 31<sup>st</sup> July for every year.<br><br>");       		
    		
    		s1.append("It is hereby informed that Sub-Aua Code of DoIT and C has been de-activated due to non-submission of half yearly security audit reports.Kindly submit security audit reports for re-activation of Sub_Aua Code.<br><br>");    
    		   		    		   		
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
  	}
              
        String emailContent = s1.toString();
        mailContent.setSubject(subject);
        mailContent.setMailBody(emailContent);

        logger.info("sendRemainderMail====> Calling sendHtmlMail from 3");
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
            logger.info(" Calling sendHtmlMail method====> Mail send successfully");
        } else {
            throw new MessagingException("Recipient list is empty.");
        }
	}

	//get sub aua contact details	
	 private Optional<ViewShareLicenceKeyDetails> getSubauaDetails(String subauacode) {
		 logger.info("getSubauaDetails method==========>get subaua data");
		 //Optional<ViewShareLicenceKeyDetails> serverdata = viewshareLicenceKeyRepository.findBySubAuaCode(subauacode);
		 Optional<ViewShareLicenceKeyDetails> serverdata = viewshareLicenceKeyRepository.findBySubAuaCodeAndActive(subauacode, 1);
			return serverdata;
	}

	// Calculate Due date
	private int compareDueDates(Timestamp dueDateTimestamp) {
		LocalDate dueDate = dueDateTimestamp.toLocalDateTime().toLocalDate();
		// int dayOfMonth = dueDate.getDayOfMonth();
		LocalDate now = LocalDate.now();
		int dayOfMonth = now.getDayOfMonth();
		// Calculate 30 days before and after the due date
		LocalDate before30Days = dueDate.minusDays(30);
		LocalDate after30Days = dueDate.plusDays(30);

		// If due date is equal to today's date
		if (dueDate.equals(now)) {
			logger.info("compareDueDates method==========>same as a due day");
			return 1;
		}

		/*
		 * if (dayOfMonth % 3 == 0) { // Check if the current date is within the range
		 * if (now.isAfter(before30Days) && now.isBefore(dueDate)) { return 1; } else if
		 * (now.isAfter(dueDate) && now.isBefore(after30Days)) { return 2; } else {
		 * return 3; } }
		 */

		// Check if the current date is within the range
		if (now.isAfter(before30Days) && now.isBefore(dueDate)) {
			if (dayOfMonth % 3 == 0) {
				logger.info("compareDueDates method==========>before 30 days");
				return 1;
			}
		} else if (now.isAfter(dueDate) && now.isBefore(after30Days)) {
			/*
			 * if (dayOfMonth % 3 == 0) {
			 * logger.info("compareDueDates method==========>after 30 days"); return 2; }
			 */
			logger.info("compareDueDates method==========>after 30 days");
			return 2;
		} else if (now.equals(after30Days)) {
			if (dayOfMonth % 3 == 0) {
				logger.info("compareDueDates method==========>last day");
				return 3;
			}
		}

		return 0;
	}


}