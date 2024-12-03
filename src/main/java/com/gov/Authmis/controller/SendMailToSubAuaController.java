package com.gov.Authmis.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.ServerException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.dao.SendMailExceptionhandleServiceImpl;
import com.gov.Authmis.dao.SendMailToSubAuaDAOImpl;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SendEmailToSubAuaModel;
import com.gov.Authmis.service.SendMailToSubAuaService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;
import com.itextpdf.text.DocumentException;

@Controller
public class SendMailToSubAuaController {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	SendMailToSubAuaService sendMailToSubAuaService;

	@Autowired
	SendMailToSubAuaDAOImpl sendMailToSubAuaDAOImpl;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@Autowired
	SendMailExceptionhandleServiceImpl sendMailExceptionhandleServiceImpl;
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger    logger = LoggerFactory.getLogger(QueryBuildController.class);
	@PersistenceContext
	private EntityManager entityManager;
	@GetMapping(path = "/send_email_to_subaua", headers = "Accept=application/x-www-form-urlencoded")
	public String send_email_to_subaua_data(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("addEmail", new SendEmailToSubAuaModel());
		// List<SendEmailToSubAuaModel> wsi = sendMailToSubAuaService.findAll();

		// model.addAttribute("details", wsi);

		List<Map<String, Object>> subAuaList = this.sendMailToSubAuaService.GetSubauaCodeName();
		model.addAttribute("subAuaList", subAuaList);
		List<Map<String, Object>> serviceTypeList = this.sendMailToSubAuaService.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);

		List<Map<String, Object>> emailList = this.sendMailToSubAuaService.GetEmail();
		model.addAttribute("emailList", emailList);

		return "SendMailToSubAua";

	}

	
	@RequestMapping(path = "/addEmailDetails", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String addEmailDetails(@ModelAttribute SendEmailToSubAuaModel sendEmailToSubAuaModel,
			@RequestParam("files") MultipartFile[] files, BindingResult result, Model model, HttpServletRequest request,RedirectAttributes redirectAttributes)
			throws IOException, SerialException, SQLException, DocumentException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		HashMap<String, SendEmailToSubAuaModel> fileMap = new HashMap<>();
		System.out.println("------------------>>>> :" + files.length);

		try {

			if (files != null) {

				for (MultipartFile file : files) {

					if (file.isEmpty() || file.getSize() == 0) {
						continue; // Skip processing empty files
					}

					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					String fileType = StringUtils.cleanPath(file.getContentType());

					byte[] data = file.getBytes();

					SendEmailToSubAuaModel dbFile = new SendEmailToSubAuaModel();
					dbFile.setFileName(new String[] { fileName });
					// dbFile.setFileType(file.getContentType());
					dbFile.setFileType(new String[] { fileType });
					dbFile.setData(data);

					fileMap.put(fileName, dbFile);

					/*
					 * String fileDownloadUri =
					 * ServletUriComponentsBuilder.fromCurrentContextPath().path("/")
					 * .path(fileName).toUriString(); dbFile.setUri(new String[] { fileDownloadUri
					 * });
					 */
					Path path = Paths.get(Constant.FILE_PATH + fileName);

					try {
						Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/files/download/").path(fileName).toUriString();
					//dbFile.setUri(new String[] { fileDownloadUri });
					 dbFile.setUri(new String[] { path.toString() });
					System.out.println("filename===>" + fileDownloadUri);
					// return ResponseEntity.ok(fileDownloadUri);


					System.out.println("==============================>" + fileMap);
					System.out.println("======================>" + dbFile.getFileName());
					System.out.println("======================>" + fileDownloadUri);
					System.out.println("======================>" + dbFile.getFileType());
					System.out.println("======================>" + file.getSize());
					System.out.println("======================>" + dbFile.getData());

				}
			}
			Object user = sendMailToSubAuaService.save(sendEmailToSubAuaModel, new ArrayList<>(fileMap.values()));
		

			redirectAttributes.addFlashAttribute("success", "Mail Send successfully");
			
			if (fileMap.values().size() == 0) {
				
				String sql="SELECT SRNO FROM AADHAAR.SEND_MAILS_TO_SUBAUA_LOGS_DATA ORDER BY SRNO DESC FETCH FIRST 1 ROWS ONLY"; 
				Query q = em.createNativeQuery(sql);
				@SuppressWarnings("unchecked") 
				Object srno = q.getSingleResult();
				 
				System.out.println("++++++++ srno : " + srno );
				sendMailToSubAuaDAOImpl.SendComposedEmail(srno, sendEmailToSubAuaModel.getTo(),
						sendEmailToSubAuaModel.getCc(), sendEmailToSubAuaModel.getSubject(),
						sendEmailToSubAuaModel.getContent(), null, null, null,null);
			} 
			else {
			
				  System.out.println("************************************ Calling SendComposedEmail ");
				  String sql = "SELECT SRNO FROM AADHAAR.SEND_MAILS_TO_SUBAUA_LOGS_DATA ORDER BY SRNO DESC FETCH FIRST 1 ROWS ONLY"; 
				  Query q = em.createNativeQuery(sql);
				  @SuppressWarnings("unchecked") 
				  Object srno = q.getSingleResult();
				 
				  SendMailExceptionhandleServiceImpl getFileData = new SendMailExceptionhandleServiceImpl();
				  List<Map<String, byte[]>> listOfData = getFileData.getAllFileDataAccordingToSrno(srno);
				
				sendMailToSubAuaDAOImpl.SendComposedEmail(srno, sendEmailToSubAuaModel.getTo(),
						sendEmailToSubAuaModel.getCc(), sendEmailToSubAuaModel.getSubject(),
						sendEmailToSubAuaModel.getContent(),null,null,null, listOfData);
				
				
				
				
			}
			return "redirect:/send_email_to_subaua";
		} catch (MailSendException ex) {
			
			return "redirect:/send_email_to_subaua";
		}

	}

	@GetMapping("/subauaORGNAME1/{subauacode}")
	public @ResponseBody String[] getsubauaCode1(@PathVariable(value = "subauacode") List<String> subauacode, Model m) {
		String[] str = sendMailToSubAuaService.getSubauaCode1(subauacode);
		System.out.println(str + "subaua code");
		m.addAttribute("subauacode", str);
		return str;

	}
}
