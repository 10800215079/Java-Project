package com.gov.Authmis.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gov.Authmis.model.MailContentModal;
import com.gov.Authmis.model.sendMailModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SubAuaWiseSendMail {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    MailProperties mailProperties;

    private JavaMailSender javaMailSender;
    static Logger logger = LoggerFactory.getLogger(SubAuaWiseSendMail.class);

    public SubAuaWiseSendMail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    static String auaCode;
    String auaLk;
    String emailID;
    List<Map<String, Object>> subAUAData = null;
    String ipAddress;
    String emailRICS;
    Date newexpiryDate;
    static String passAuaCode;
    String s1 = null;
    String orgName;
    Object oldExpiryDateOfLK;
    String output;
    String userPassword;

    SubAuaWiseSendMailServiceImpl subAuaWiseSendMailServiceImpl = new SubAuaWiseSendMailServiceImpl();

    public ResponseEntity<MailContentModal> SendEmailToSubAua(List<Map<String, Object>> listOfEmail, String subAuaCode, String subAuaLk,
            Date newExpiryDate) {
        try {
            MailContentModal mailContent = new MailContentModal();
            mailContent.setSubAuaCode(subAuaCode);
            mailContent.setSubAuaLk(subAuaLk);
            mailContent.setNewExpiryDate(newExpiryDate);
            mailContent.setSubauaName((String) listOfEmail.get(0).get("ORGNAME"));

            String maskedPhone = maskPhoneNumber((String) listOfEmail.get(0).get("PHONE"));
            mailContent.setPhone((String) listOfEmail.get(0).get("PHONE"));

            InetAddress ip = InetAddress.getLocalHost();
            ipAddress = ip.toString();

            oldExpiryDateOfLK = listOfEmail.get(0).get("EXPIRY_DATE");

            String oldexp = oldExpiryDateOfLK.toString();
            if (oldexp.length() > 10) {
                DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = inputFormatter.parse(oldexp);

                DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yy");
                output = outputFormatter.format(date);
                mailContent.setOldExpirydate(output);
                logger.info("SubAuaWiseSendMail====>" + output);
            } else {
                mailContent.setOldExpirydate(oldexp);
            }
            
            emailRICS = (String) listOfEmail.get(0).get("EMAIL");
            String[] to = (emailRICS).split(",");
            String subject = "Confidential- License Key of Sub_AUA " + mailContent.getSubauaName() + "";
            mailContent.setTo(to);

            String[] toCC = { "SUPPORT.UID@rajasthan.gov.in"};
            mailContent.setToCC(toCC);

            StringBuilder s1 = new StringBuilder();
            s1.append("Dear Sir/Ma’am,<br><p>Please find new License Key for your Sub-AUA " + mailContent.getSubauaName() + " ")
              .append(". As old License key will expire on " + mailContent.getOldExpirydate() + " ");
            s1.append("<p>The License Key is embedded in the attached Password Protected Pdf file. The password to open PDF has been sent on mobile no of TPOC " + maskedPhone + ".</p>");
            s1.append("<p>The contents of this email are strictly confidential and should not be shared with any unauthorized person,")
              .append(" if shared, the concerned Sub-AUA will be responsible for any misuse of the License Key.</p>");
            s1.append("<p>Please do not reply to this email. For any kind of support or clarification, please send an email to PM.UID@RAJASTHAN.GOV.IN</p>");
            s1.append("<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,<br>")
              .append("Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>")
              .append("IP Ext - 21354<br>Ph: 0141-2921354</p>");
            s1.append("<br>");
            s1.append("======================================================================");
            s1.append("<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or ")
              .append("entity to which they are addressed. You shall not share this message or any of its attachments to anyone without proper authorization.</p>");
            s1.append("======================================================================");

            String emailContent = s1.toString();
            mailContent.setSubject(subject);
            mailContent.setMailBody(emailContent);

            logger.info("SubAuaWiseSendMail====>Calling sendHtmlMail");
            sendHtmlMail(mailContent);
            mailContent.setPassword(userPassword);
            return new ResponseEntity<>(mailContent, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error sending email", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void sendHtmlMail(MailContentModal contentModal)
            throws MessagingException, DocumentException, IOException {
        if (contentModal.getTo() != null && contentModal.getTo().length > 0) {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("donotreply.uidsupport@raj.gov.in");
            helper.setTo(contentModal.getTo());
            helper.setCc(contentModal.getToCC());
            helper.setSubject(contentModal.getSubject());
            helper.setText(contentModal.getMailBody(), true);

            byte[] b = this.insertPDFWithMail1(contentModal);
            DataSource dataSource = new ByteArrayDataSource(b, "application/pdf");
            helper.addAttachment("document.pdf", dataSource);
            javaMailSender.send(mail);
        } else {
            throw new MessagingException("Recipient list is empty.");
        }
    }

    public Integer sendHtmlMailTosewadwar(sendMailModel content )
			throws MessagingException, DocumentException, IOException {
		int status = 0;
		
		String[] emailArray = content.getToCC().split(",");
		for (int i = 0; i < emailArray.length; i++) {
		    emailArray[i] = emailArray[i].trim().toLowerCase();
		}
		if (content.getTo() != null && content.getTo().length() > 0) {
		
			MimeMessage mail = javaMailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			//helper.setFrom("donotreply.doitc-uid-support@raj.gov.in");
			helper.setFrom("donotreply.uidsupport@raj.gov.in");

			helper.setTo("support.uid@rajasthan.gov.in");
			logger.info("to====>" + content.getTo());
			//helper.setTo("apim.rsd@rajasthan.gov.in");
			logger.info("toCC====>" + emailArray);
			//helper.setCc(emailArray);
			helper.setCc("support.uid@rajasthan.gov.in");
			helper.setSubject(content.getSubject());
			helper.setText(content.getMailBody(), true);


			javaMailSender.send(mail);

			status = 1;
		} else {
			status = 0;
		}
		return status;
	}

    public byte[] insertPDFWithMail1(MailContentModal contentModal) throws DocumentException, IOException {
	    Document document = new Document();
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PdfWriter writer = PdfWriter.getInstance(document, outputStream);

	    userPassword = generatePassword();
	    String ownerPassword = "a@da33$ftq1qq";
	    writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING,
	            PdfWriter.ENCRYPTION_AES_128);

	    document.open();

	    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
	    Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
	    Font disclaimerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.RED);

	    // Adding title
	    Paragraph title = new Paragraph("DEPARTMENT OF INFORMATION TECHNOLOGY & COMMUNICATION\nGOVERNMENT OF RAJASTHAN", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);

	    document.add(Chunk.NEWLINE);

	    // Adding subtitle
	    Paragraph subtitle = new Paragraph("Aadhaar Authentication Ecosystem Project:", titleFont);
	    subtitle.setAlignment(Element.ALIGN_CENTER);
	    document.add(subtitle);

	    document.add(Chunk.NEWLINE);

	    // Adding descriptive text
	    Paragraph description = new Paragraph("Subauacode and Licence key for Integrating Aadhaar Services: -", tableFont);
	    document.add(description);

	    document.add(Chunk.NEWLINE);

	    // Creating the table
	    PdfPTable table = new PdfPTable(2);
	    table.setWidthPercentage(100);
	    table.setSpacingBefore(10f);
	    table.setSpacingAfter(10f);

	    // Table column widths
	    float[] columnWidths = {1f, 2f};
	    table.setWidths(columnWidths);

	    // Adding table headers
	    table.addCell(createCell("SubAua Name", tableFont));
        table.addCell(createCell(contentModal.getSubauaName(), tableFont));
	    table.addCell(createCell("Sub_AUA_Code", tableFont));
        table.addCell(createCell(contentModal.getSubAuaCode(), tableFont));
        table.addCell(createCell("Licence Key", tableFont));
        table.addCell(createCell(contentModal.getSubAuaLk(), tableFont));
        table.addCell(createCell("Expiry Date of LK", tableFont));
        table.addCell(createCell(contentModal.getNewExpiryDate().toString(), tableFont));

	    document.add(table);

	    // Adding disclaimer
	    Paragraph disclaimer = new Paragraph("Information contained in this document is to be treated as confidential and should not be shared with any unauthorized person in any condition to avoid misuse of confidential information.", disclaimerFont);
	    disclaimer.setAlignment(Element.ALIGN_CENTER);
	    document.add(disclaimer);

	    document.add(Chunk.NEWLINE);

	    // Adding watermark
	    Font watermarkFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 52, BaseColor.LIGHT_GRAY);
	    Phrase watermark = new Phrase("CONFIDENTIAL", watermarkFont);
	    PdfContentByte canvas = writer.getDirectContentUnder();
	    ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermark, 297.5f, 421, 45);

	    document.close();

	    byte[] pdfBytes = outputStream.toByteArray();
	    logger.info("SubAuaWiseSendMail====> Pdf is attached successfully" + pdfBytes);
	    return pdfBytes;
	}
    
   

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private String generatePassword() {
        return RANDOM.ints(PASSWORD_LENGTH, 0, CHARACTERS.length())
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                .collect(Collectors.joining());
    }


    private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 4) {
            return "****";
        }
        return "****" + phone.substring(phone.length() - 4);
    }
    
	public String getMailContent( List<sendMailModel> selectedItems  
			) throws SQLException, DocumentException, IOException, ParseException {

		String subject = "IP Whitelist Request for Aadhaar Production Services";

		//String[] toCC = { "ranveersingh.doit@rajasthan.gov.in", "pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in", "SUPPORT.UID@rajasthan.gov.in" };

		
		String[] to = {  "SUPPORT.UID@rajasthan.gov.in"};

        // Start building the email content
        StringBuilder emailContentBuilder = new StringBuilder();
        emailContentBuilder.append("Dear Team,<br><p>Please whitelist below mentioned IP address for production Aaadhaar services:-" );
        
        
        int count = 0;
        emailContentBuilder.append("<br><table border='1' style='width: 100%;'><thead><tr><th style='border:1px solid #ccc;padding: 15px;'>Sr. No</th><th style='border:1px solid #ccc; padding: 15px;'>App Name</th><th style='border:1px solid #ccc; padding: 15px;'>IP Address</th></tr></thead>");
        for (sendMailModel rowData : selectedItems) {
            emailContentBuilder.append("<tr>");
                emailContentBuilder.append("<td style='border:1px solid #ccc; padding: 7px;'>").append(++count).append("</td>");
                emailContentBuilder.append("<td style='border:1px solid #ccc; padding: 7px;'>").append(rowData.getAppName()).append("</td>");
                emailContentBuilder.append("<td style='border:1px solid #ccc; padding: 7px;'>").append(rowData.getIpAddress()).append("</td>");
            emailContentBuilder.append("</tr>");
        }
        emailContentBuilder.append("</table>");
        emailContentBuilder.append("<p>Please do not reply on this email. For any kind of clarification, please send email on pm.uid@rajasthan.gov.in and pankajjaldeep.doit@rajasthan.gov.in  </p>");

        // Add the disclaimer
        emailContentBuilder.append("<p style='font-weight: bold; color: black;'>Regards,<br>Aadhaar Authentication (AUA/ASA) Project Support,"
        		+ "<br>Department of Information Technology & Communication<br>I.T. Building, Yojana Bhawan, Jaipur (Rajasthan)<br>IP Ext - 21354<br>Ph: 0141-2921354</p>" +
                "======================================================================");
        emailContentBuilder.append("<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>");

        String emailContent = emailContentBuilder.toString();
        return emailContent;
	}
	
	public String getEmails(List<sendMailModel> mailModel){
		List<String> subauaCodes = mailModel.stream().map(sendMail -> sendMail.getSubAuaCode()).collect(Collectors.toList());
		String emails = "";
				if (!subauaCodes.isEmpty()) {
					String inClause = subauaCodes.stream().map(code -> "'" + code + "'").collect(Collectors.joining(", "));
					String q = "SELECT email FROM aadhaar.subaua WHERE subaua_code IN (" + inClause + ")";
					Query query = entityManager.createNativeQuery(q);

					@SuppressWarnings("unchecked")
					List<Object> resultList = query.getResultList();
					StringBuilder sb = new StringBuilder();
				    for (Object result : resultList) {
				        if (sb.length() > 0) {
				            sb.append(", ");
				        }
				        sb.append((String) result);
				    }
				    emails = sb.toString();
				}
				return emails;

	}
	

    
}
