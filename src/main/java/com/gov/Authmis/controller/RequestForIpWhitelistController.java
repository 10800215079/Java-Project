package com.gov.Authmis.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.service.RequestForIpWhitelistService;
import com.gov.Authmis.service.SubAuaANDServiceWiseTransactionReportService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class RequestForIpWhitelistController {

	@Autowired
	SubAuaANDServiceWiseTransactionReportService subAuaWiseTransactionReportService;

	@Autowired
	SubauaWiseTransactionService transactionService;

	@Autowired
	RequestForIpWhitelistService requestForIpWhitelistService;

	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(WhiteListSubAUAIPAddressController.class);
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(path = "/requestForIpWhitelist", headers = "Accept=application/x-www-form-urlencoded")
	public String requestForIpWhitelist(Model model, HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		logger.info("WhiteListSubAUAIPAddressController=================> Inside the method2");

		RequestForIpWhitelistEntity w = new RequestForIpWhitelistEntity();

		//List<Map<String, Object>> subAuaList = this.requestForIpWhitelistService.GetSubauaCodeName();
		

		List<Map<String, Object>> serviceTypeList = this.requestForIpWhitelistService.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);
		String ssoid = (String) request.getSession().getAttribute("SSOID");	
		
		//get department
		 List<SubAua> detlist = requestForIpWhitelistService.getDepartment(ssoid);	
		 String departmentid = null;
		 List<String> subAuaList1  = new ArrayList<>();
		 List<Map<String, Object>> subAuaList = new ArrayList<>();
		
		 for (SubAua subAua : detlist) {
			 subAuaList1.add(subAua.getID());
			 Map<String, Object> subAuaMap = new HashMap<>();
			 subAuaMap.put("ID",subAua.getID());
			 subAuaMap.put("NAME", subAua.getNAME());
			 subAuaList.add(subAuaMap);
			 departmentid += subAua.getID()+","; 			   
			}
		System.out.println(" ------->>>>" + departmentid);
		
		model.addAttribute("subAuaList",subAuaList );
		/*
		// get all data in table
		List<RequestForIpWhitelistEntity> result = requestForIpWhitelistService.getAllRequestedIpWhitelist(subAuaList1);
		model.addAttribute("details", result);
		
		*/
		/** get all data in table **/
		HashMap<String, Object> result = new HashMap<>();
		result = requestForIpWhitelistService.getAllRequestedIpWhitelist(subAuaList1);
		model.addAttribute("details", result.get("details"));
		return "RequestForIpWhitelist";

	}

	@RequestMapping(path = "/addRequestForIpWhitelist", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public String addRequestForIpWhitelist(@RequestParam("subAuaCode") String subAuaCode,
			@RequestParam("ipAddress") String ipAddress, @RequestParam("subAuaName") String subAuaName,
			@RequestParam("urlType") String urlType, @RequestParam("servicetype") String servicetype,
			 @RequestParam("purposefor") String purposefor,
			@RequestParam("appName") String appName, @RequestParam("applicatonUrl") String applicatonUrl,
			@RequestParam("schemeName") String schemeName, @RequestParam("isDocPublished") String isDocPublished,
			@RequestParam("ipBelongsTo") String ipBelongsTo, @RequestParam("fileInput") MultipartFile fileInput,
			@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("auditDoc") MultipartFile auditDoc,
			@RequestParam("NochangeCert") MultipartFile nochangeCert,
			Model model, HttpServletRequest request)
			throws SQLException, IOException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		// Create an instance of WhiteListSubAUAEntity and set its properties
		RequestForIpWhitelistEntity requestForIpWhitelist = new RequestForIpWhitelistEntity();
		requestForIpWhitelist.setSubAUACode(subAuaCode); 
		requestForIpWhitelist.setSubAUAName(subAuaName);
		requestForIpWhitelist.setIpAddress(ipAddress);
		requestForIpWhitelist.setRequestType(servicetype);
		requestForIpWhitelist.setAppName(appName);
		requestForIpWhitelist.setApplicationUrl(applicatonUrl);
		requestForIpWhitelist.setSchemeName(schemeName);
		requestForIpWhitelist.setIsDocPublished(Integer.parseInt(isDocPublished));
		requestForIpWhitelist.setIpBelongsTo(ipBelongsTo);
		
		requestForIpWhitelist.setServiceUrlType(urlType);
		requestForIpWhitelist.setIsDocPublished(Integer.parseInt(isDocPublished));
		requestForIpWhitelist.setIpBelongsTo(ipBelongsTo);
		requestForIpWhitelist.setACTOFAADHAAR(purposefor);

		logger.info("WhiteListSubAUAIPAddressController=================>" + requestForIpWhitelist.toString());
		
		
		requestForIpWhitelist.setCREATERIP(request.getRemoteHost());
		// Handle the file
		if (!fileInput.isEmpty()) {
			// Set the file content or perform necessary operations
			requestForIpWhitelist.setPublishedDoc(fileInput.getBytes());
		}
		// Handle the file
		if (!imageFile.isEmpty()) {
			// Set the file content or perform necessary operations
			requestForIpWhitelist.setConsentDocument(imageFile.getBytes());
		}
		
		if (!auditDoc.isEmpty()) {
			// Set the file content or perform necessary operations
			requestForIpWhitelist.setAuditDocument(auditDoc.getBytes());
		}
		
		/*
		 * if (!applicationVAPTReport.isEmpty()) { // Set the file content or perform
		 * necessary operations
		 * requestForIpWhitelist.setApplicationVAPTReport(applicationVAPTReport.getBytes
		 * ()); }
		 * 
		 * if (!sourceCodeReview.isEmpty()) { // Set the file content or perform
		 * necessary operations
		 * requestForIpWhitelist.setSourceCodeReview(sourceCodeReview.getBytes()); }
		 */

		if (!nochangeCert.isEmpty()) {
			// Set the file content or perform necessary operations
			requestForIpWhitelist.setNochangeCert(nochangeCert.getBytes());
		}


		String ssoid = (String) request.getSession().getAttribute("SSOID");
		requestForIpWhitelist.setCreatedBy(ssoid);

		Object user = requestForIpWhitelistService.save(requestForIpWhitelist);

		if (user == null) {
			throw new ServerException(null, null);
		} else {
			return "redirect:/requestForIpWhitelist";
		}
	}

	// Check Duplicate
	@PostMapping("/checkDuplicateRequestForIP")
	public ResponseEntity<Map<String, String>> checkDuplicateRequestForIP(@RequestParam String subAuaCode,
			@RequestParam String iPAddress) {
		boolean isDuplicate = requestForIpWhitelistService.isRequestedIpIsDuplicate(subAuaCode, iPAddress);

		if (isDuplicate) {
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("errorCode", "CONFLICT");
			responseBody.put("errorMessage", "Duplicate IP address: " + iPAddress);

			return ResponseEntity.ok(responseBody);
		} else {
			Map<String, String> responseBody = Collections.singletonMap("message", "IP address is unique");
			return ResponseEntity.ok(responseBody);
		}
	}
	
	@GetMapping("/downloadPublishedDocument/{id}")
	 public  ResponseEntity<RequestForIpWhitelistEntity> downloadPublishedDoc(@PathVariable Long id) {

		 Optional<RequestForIpWhitelistEntity> downloaddata = requestForIpWhitelistService.getData(id);
		  return ResponseEntity.ok(downloaddata.get());
	 }
	
	
	@GetMapping("/downloadPublishedImages/{id}")
	 public  ResponseEntity<RequestForIpWhitelistEntity> downloadPublishedImages(@PathVariable Long id) {

		 Optional<RequestForIpWhitelistEntity> downloaddata = requestForIpWhitelistService.getImgData(id);
		  
		    if (downloaddata.isPresent()) {
		        return ResponseEntity.ok(downloaddata.get());
		    } else {
		        return ResponseEntity.ok(null); 
		    }
	 }
	
	@GetMapping("/downloadAuditDocument/{id}")
	 public  ResponseEntity<RequestForIpWhitelistEntity> downloadAuditDocument(@PathVariable Long id) {

		 Optional<RequestForIpWhitelistEntity> downloaddata = requestForIpWhitelistService.getData(id);
		    if (downloaddata.isPresent()) {
		        return ResponseEntity.ok(downloaddata.get());
		    } else {
		        return ResponseEntity.ok(null); 
		    }
	 }

}
