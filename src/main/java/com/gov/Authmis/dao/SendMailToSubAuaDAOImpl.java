package com.gov.Authmis.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import com.gov.Authmis.model.SendEmailToSubAuaModel;
import com.itextpdf.text.DocumentException;

@Service
public class SendMailToSubAuaDAOImpl {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	MailProperties mailProperties;

	@Autowired
	SendMailExceptionhandleServiceImpl sendMailExceptionhandleServiceImpl;

	private JavaMailSender javaMailSender;
	// static Logger logger = LoggerFactory.getLogger(SubAuaWiseSendMail.class);

	public SendMailToSubAuaDAOImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	SubAuaWiseSendMailServiceImpl subAuaWiseSendMailServiceImpl = new SubAuaWiseSendMailServiceImpl();

	public void SendComposedEmail(Object srno, String to, String cc, String subject, String content, String[] FileName,
			String[] FileType, byte[] FileData, List<Map<String, byte[]>> listOfData)
			throws DocumentException, IOException, SQLException {

		List<Map<String, byte[]>> convertedList = listOfData;

		String mailCC = cc;

		String output1 = to.replace(",", " ");
		String output2 = output1.replaceAll("\\s+", " ");
		String output3 = output2.replace(" ", ",");

		String[] outputArray = output3.split(",");
		System.out.println(Arrays.toString(outputArray));

	
		// add new code for multiple attachment
		String[] fileNames = FileName;
		String[] fileTypes = FileType;
		byte[] singleFileData = FileData;
		// Convert singleFileData into a byte[][] array
		byte[][] fileDataInArray = new byte[1][];
		fileDataInArray[0] = singleFileData;

		

		try {
			System.out.println("=================>Calling sendHtmlMail");
			sendHtmlMail(srno, outputArray, mailCC, subject, content, FileName, FileType, fileDataInArray,
					convertedList);

		} catch (MessagingException e) {
			System.out.println("==>>>>>>>>>>>>>>>>>>>>>>>> From here !!!!!!!!!! ");
			e.printStackTrace();
			// logger.error("SubAuaWiseSendMail====>"+e);
		}

	}

	@Transactional
	public Integer sendHtmlMail(Object srno, String[] toEmail, String mailCC, String subject, String content,
			String[] fileNames, String[] fileTypes, byte[][] fileData, List<Map<String, byte[]>> convertedList)
			throws MessagingException, SQLException {

		System.out.println("+++++++++++++++++++++++++>>>>>>>>>>>> calling sendHtmlMail Method within ");
		int status = 0;
		if (toEmail != null && toEmail.length > 0) {
			MimeMessage mail = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			//helper.setFrom("donotreply.doitc-uid-support@raj.gov.in");
			//helper.setFrom("doitc.aua@rajasthan.gov.in");
			helper.setFrom("donotreply.uidsupport@raj.gov.in");
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(content, true);

			
			if (convertedList != null && !convertedList.isEmpty()) {
				System.out.println("============>>>>>>>>>>>>>>> size of the convertedList : " + convertedList.size());
				for (Map<String, byte[]> attachment : convertedList) {

					for (Map.Entry<String, byte[]> entry : attachment.entrySet()) {
						// String fileName = StringUtils.cleanPath(entry.getKey());
						String fileName = entry.getKey();
						byte[] fileBytes = entry.getValue();
						String fileType = getFileTypeByFileName(fileName);
						DataSource dataSource = new ByteArrayDataSource(fileBytes, fileType);
						helper.addAttachment(fileName, dataSource);
						System.out.println("=====================>>>>>>>>>>>>>> fileName : " + fileName);
					}
				}
			}

			String toCc = "";
			if (!mailCC.isEmpty()) {
				toCc = (mailCC).replace(' ', ',');
				String[] CC = (toCc).split(",");
				System.out.println("=============>>>>>>> toCC" + mailCC);
				helper.setCc(CC);
			}
			javaMailSender.send(mail);
		

		}
		return status;
	}

	private String getFileTypeByFileName(String fileName) {
		String fileType = "";
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			fileType = fileName.substring(dotIndex + 1).toLowerCase();
		}

		// Map the file extension to the corresponding Content-Type
		switch (fileType) {
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "pdf":
			return "application/pdf";
		case "html":
			return "text/html";
		case "txt":
		case "text":
			return "text/plain";
		case "png":
			return "image/png";
		case "jpg":
		case "jpeg":
			return "image/jpeg";
		case "rtf":
			return "application/rtf";
		case "xls":
			return "application/vnd.ms-excel";
		// Add more cases for other file types if needed
		default:
			// Return a generic Content-Type for unknown file types
			return "application/octet-stream";
		}
	}

	private void detectInvalidAddress(MailSendException ex) {

		// TODO Auto-generated method stub
		Exception[] messageExceptions = ex.getMessageExceptions();
		if (messageExceptions.length > 0) {
			Exception messageException = messageExceptions[0];
			if (messageException instanceof SendFailedException) {
				SendFailedException sfe = (SendFailedException) messageException;
				Address[] invalidAddresses = sfe.getInvalidAddresses();
				StringBuilder addressStr = new StringBuilder();
				for (Address address : invalidAddresses) {
					addressStr.append(address.toString()).append("; ");
				}

				// logger.error("invalid address(es)：{}", addressStr);
				System.out.println("++++++++++++++++>>>>" + addressStr);
				return;
			}
		}

		// logger.error("exception while sending mail.", ex);
		System.out.println("++++++++++++++++>>>>" + ex);
	}

}
