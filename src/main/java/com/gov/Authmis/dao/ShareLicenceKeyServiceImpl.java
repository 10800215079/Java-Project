package com.gov.Authmis.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.ShareLicenceKeyEntity;
import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;
import com.gov.Authmis.jpa.ShareLicenceKeyRepository;
import com.gov.Authmis.jpa.ViewshareLicenceKeyRepository;
import com.gov.Authmis.model.MailContentModal;
import com.gov.Authmis.service.AadhaarOTPValidationService;
import com.gov.Authmis.service.ShareLicenceKeyService;
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
public class ShareLicenceKeyServiceImpl implements ShareLicenceKeyService{
	 private JavaMailSender javaMailSender;
	    static Logger logger = LoggerFactory.getLogger(ShareLicenceKeyServiceImpl.class);

	    public ShareLicenceKeyServiceImpl(JavaMailSender javaMailSender) {
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
	    String passForpdf;
	
	@Autowired
	private ShareLicenceKeyRepository shareLicenceKeyRepository;
	@Autowired
	private ViewshareLicenceKeyRepository viewshareLicenceKeyRepository;
	@Autowired
	AadhaarOTPValidationService aadhaarOTPValidationService;
	
	
	@Override
	public ResponseEntity<String> shareLicense(ViewShareLicenceKeyDetails data) {
        Optional<ShareLicenceKeyEntity> results = shareLicenceKeyRepository.findBySubAuaCodeAndStatus(data.getSubAuaCode(), 1);

        if (!results.isPresent()) {
            return new ResponseEntity<>("No license key found for the given SubAUA code and status.", HttpStatus.NOT_FOUND);
        }

        try {
            MailContentModal mailContent = new MailContentModal();
            mailContent.setSubAuaCode(data.getSubAuaCode());
            mailContent.setSubAuaLk(results.get().getSubAuaLk());
            mailContent.setNewExpiryDate(results.get().getExpiryDate());
            mailContent.setSubauaName(data.getOrgName());
            mailContent.setOfficername(data.getOfficerName());

            String maskedPhone = maskPhoneNumber(data.getPhone());
            mailContent.setPhone(data.getPhone());

            InetAddress ip = InetAddress.getLocalHost();
            String ipAddress = ip.toString();

            Date oldExpiryDateOfLK = results.get().getExpiryDate();
            String oldexp = oldExpiryDateOfLK.toString();
            if (oldexp.length() > 10) {
                DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = inputFormatter.parse(oldexp);

                DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yy");
                String output = outputFormatter.format(date);
                mailContent.setOldExpirydate(output);
                logger.info("SubAuaWiseSendMail====>" + output);
            } else {
                mailContent.setOldExpirydate(oldexp);
            }

            String emailRICS = data.getEmail();
            String[] to = emailRICS.split(",");
            String subject = "Confidential- License Key of Sub_AUA " + mailContent.getSubauaName()+ " for "+Constant.ENVIROMENT+" Enviroment.";
            mailContent.setTo(to);

            String[] toCC = {"pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in" };
            mailContent.setToCC(toCC);

            StringBuilder s1 = new StringBuilder();
            s1.append("Dear Sir/Ma’am,<br><p>Please find the License Key for your Sub-AUA " + mailContent.getSubauaName() + " .");

            s1.append("<p>The License Key is embedded in the attached Password Protected Pdf file. The password to open PDF has been sent on mobile no of TPOC " + maskedPhone +  "  (" + mailContent.getOfficername() + ").</p>");
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
            String resp = sendHtmlMail(mailContent);

            if("success".equals(resp)) {
                try {
                    sendSMS(passForpdf, mailContent.getPhone(), mailContent.getTo()[0]);
                } catch (Exception e) {
                    logger.error("Error sending SMS", e);
                    return new ResponseEntity<>("Error sending SMS", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<>("License key has been shared successfully.", HttpStatus.OK);

        } catch (UnknownHostException e) {
            logger.error("Error retrieving local host address", e);
            return new ResponseEntity<>("Error retrieving local host address", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            logger.error("Error parsing date", e);
            return new ResponseEntity<>("Error parsing date", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error sending email", e);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	private void sendSMS(String passForpdf, String mobileNo, String mail) {

		String password = passForpdf;
		String[] mobileno = { mobileNo };
		String maskMail = maskEmail(mail);
		String message = "Dear Sir/Madam, A document has been shared on your email- "+maskMail+" from Aadhaar Authentication Project, DoIT&C. Please use this Password to open the document- "
				+ password
				+ ". Please do not share the password with any unauthorized person to avoid misuse of the document. - DoIT&C (AUA), GoR";
		// Send Password in SMS
		ResponseEntity<String> response = aadhaarOTPValidationService.sendSms(mobileno, message);
	}
	

	public String sendHtmlMail(MailContentModal contentModal)
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
	            return "success";
	        } else {
	            throw new MessagingException("Recipient list is empty.");
	        }
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
	    Paragraph description = new Paragraph("Sub-AUA Code and Licence key for Integrating Aadhaar Services: -", tableFont);
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
	    table.addCell(createCell("Sub-AUA Name", tableFont));
        table.addCell(createCell(contentModal.getSubauaName(), tableFont));
	    table.addCell(createCell("Sub-AUA Code", tableFont));
        table.addCell(createCell(contentModal.getSubAuaCode(), tableFont));
        table.addCell(createCell("Licence Key", tableFont));
        table.addCell(createCell(contentModal.getSubAuaLk(), tableFont));
        table.addCell(createCell("Expiry Date", tableFont));
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
    
   

	 private String generatePassword() {
		  passForpdf = RANDOM.ints(PASSWORD_LENGTH, 0, CHARACTERS.length())
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                .collect(Collectors.joining());
		  return passForpdf;
    }

	private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

	private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 4) {
            return "****";
        }
        return "****" + phone.substring(phone.length() - 4);
    }

	
	
	
	
	
	
	@Override
	public List<Map<String, Object>> getLicenceExpiryDates(List<Map<String, Object>> subAuaList) {
        List<String> subAuaCode = new ArrayList<>();
        for (Map<String, Object> subAua : subAuaList) {
        	subAuaCode.add((String) subAua.get("ID"));
        }

        List<Object[]> results = shareLicenceKeyRepository.findBySubAuaCode(subAuaCode);
        
        List<Map<String, Object>> licenceExpiryDates = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("srno", result[0]);
            map.put("subAuaCode", result[1]);
            map.put("subAuaName", result[2]);
            map.put("expiryDate", result[3]);
            licenceExpiryDates.add(map);
        }

     

        return licenceExpiryDates;
    }

	@Override
	public Optional<ViewShareLicenceKeyDetails> getSubauaDetails(String subauacode) {
		Optional<ViewShareLicenceKeyDetails> serverdata = viewshareLicenceKeyRepository.findBySubAuaCode(subauacode);
		return serverdata;
	}


	public String maskEmail(String email) {
	    int atIndex = email.indexOf('@');
	    if (atIndex > 0) {
	        String maskedDomain = "******";
	        return email.substring(0, atIndex + 1) + maskedDomain;
	    }
	    return email;
	}


}
