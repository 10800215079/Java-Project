package com.gov.Authmis.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.WhiteListSubAUAIPAddress;
import com.gov.Authmis.service.SubAuaANDServiceWiseTransactionReportService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.service.WhiteListSubAUAIPAddressService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class WhiteListSubAUAIPAddressController {

	@Autowired
	SubAuaANDServiceWiseTransactionReportService subAuaWiseTransactionReportService;

	@Autowired
	SubauaWiseTransactionService transactionService;

	@Autowired
	WhiteListSubAUAIPAddressService whiteListSubAUAIPAddresservice;
	
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(WhiteListSubAUAIPAddressController.class);
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(path = "/white_List_SubAUA_IPAddress", headers = "Accept=application/x-www-form-urlencoded")
	public String white_List_SubAUA_IPAddress_saving(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		logger.info("WhiteListSubAUAIPAddressController=================> Inside the method2");
		model.addAttribute("addWhiteListIP", new WhiteListSubAUAIPAddress());
		//List<WhiteListSubAUAIPAddress> wsi = whiteListSubAUAIPAddresservice.findAll();
		List<WhiteListSubAUAEntity> wsi = whiteListSubAUAIPAddresservice.getAll();
		model.addAttribute("details", wsi);

		WhiteListSubAUAEntity w = new WhiteListSubAUAEntity();

		List<Map<String, Object>> subAuaList = this.whiteListSubAUAIPAddresservice.GetSubauaCodeName();
		model.addAttribute("subAuaList", subAuaList);
		List<Map<String, Object>> serviceTypeList = this.whiteListSubAUAIPAddresservice.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);
		return "whiteListSubAUAIPAddress";

	}

	@RequestMapping(path = "/addUser", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String addUser(@RequestParam("subAuaCode") String subAuaCode,@RequestParam("ipAddress") String ipAddress,
	        @RequestParam("subAuaName") String subAuaName,@RequestParam("servicetype") String servicetype,
	        @RequestParam("appName") String appName,@RequestParam("serverType") String serverType,@RequestParam("applicatonUrl") String applicatonUrl,
	        @RequestParam("schemeName") String schemeName,@RequestParam("isDocPublished") String isDocPublished,
	        @RequestParam("ipBelongsTo") String ipBelongsTo,@RequestParam("fileInput") MultipartFile fileInput,
	        Model model,
	        HttpServletRequest request) throws SQLException, IOException {

	    // Create an instance of WhiteListSubAUAEntity and set its properties
	    WhiteListSubAUAEntity whiteListSubAUAIPAddress = new WhiteListSubAUAEntity();
	    whiteListSubAUAIPAddress.setSubAuaCode(subAuaCode);
	    whiteListSubAUAIPAddress.setIpAddress(ipAddress);
	    whiteListSubAUAIPAddress.setSubAuaName(subAuaName);
	    whiteListSubAUAIPAddress.setServicetype(servicetype);
	    whiteListSubAUAIPAddress.setAppName(appName);
	    whiteListSubAUAIPAddress.setServicetype(servicetype);
	    whiteListSubAUAIPAddress.setApplicatonUrl(applicatonUrl);
	    whiteListSubAUAIPAddress.setSchemeName(schemeName);
	    whiteListSubAUAIPAddress.setIsDocPublished(Integer.parseInt(isDocPublished));  // Convert to integer
	    whiteListSubAUAIPAddress.setIpBelongsTo(ipBelongsTo);

	    logger.info("WhiteListSubAUAIPAddressController=================>" + whiteListSubAUAIPAddress.toString());
	    validateSSO.getValidateSSO(request);
	    // Main Menu
	    mainMenuUtil.getMainMenu(model, request);
	    // Set additional properties or process the entity as needed
	    
	    // Handle the file
	    if (!fileInput.isEmpty()) {
	        // Set the file content or perform necessary operations
	        whiteListSubAUAIPAddress.setPublishedDoc(fileInput.getBytes());
	    }

	    String ssoid = (String) request.getSession().getAttribute("SSOID");
	    whiteListSubAUAIPAddress.setCreatedBy(ssoid);

	    Object user = whiteListSubAUAIPAddresservice.save(whiteListSubAUAIPAddress);

	    if (user == null) {
	        throw new ServerException(null, null);
	    } else {
	        return "redirect:/white_List_SubAUA_IPAddress";
	    }
	}


	
	//Check Duplicate 
	 @PostMapping("/checkDuplicateSubauaIP")
	    public ResponseEntity<Map<String, String>> checkDuplicateSubauaIP(@RequestParam String subAuaCode ,@RequestParam String iPAddress) {
	        boolean isDuplicate = whiteListSubAUAIPAddresservice.isSubAuaCodeAndIPDuplicate(subAuaCode,iPAddress);
	        
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


	@GetMapping("/getAllData")
	public ResponseEntity<List<WhiteListSubAUAEntity>> getAllData(Model model, HttpServletRequest request)
			throws SQLException {
		validateSSO.getValidateSSO(request);
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		return ResponseEntity.ok().body(whiteListSubAUAIPAddresservice.getAllData());
	}

	
	@GetMapping("/subauaname/{ORGNAME}")
	public @ResponseBody List<String> getsubauaCode(@PathVariable(value = "ORGNAME") String ORGNAME, Model m) {
		List<String> str = whiteListSubAUAIPAddresservice.getSubauaCode(ORGNAME);
		logger.info("WhiteListSubAUAIPAddressController=================>subaua code" + str);
		m.addAttribute("ORGNAME", str);
		return str;

	}
	
	
	@RequestMapping(path = "/updateWhiteListedIPDetails", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String updateWhiteListedIPDetails(@ModelAttribute WhiteListSubAUAEntity whiteListSubAUAIPAddress , 
			@RequestParam("inputFile") MultipartFile fileInput, BindingResult result,
			Model model, HttpServletRequest request) throws SQLException, IOException {
			whiteListSubAUAIPAddress.setPublishedDoc(fileInput.getBytes());
			String ssoid = (String) request.getSession().getAttribute("SSOID");
			whiteListSubAUAIPAddress.setUpdatedBy(ssoid);
			Object o = whiteListSubAUAIPAddresservice.updateWhiteListedIp(whiteListSubAUAIPAddress);
			if (o == null) {
				throw new ServerException(null, null);
			} else {
				return "redirect:/white_List_SubAUA_IPAddress";
			}
		
	}
	
	
	@GetMapping("/updateStatus/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model, HttpServletRequest request) throws SQLException {

		validateSSO.getValidateSSO(request);
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		
		logger.info("WhiteListSubAUAIPAddressController=================>" + id);
		whiteListSubAUAIPAddresservice.updateStaus(id,request);
		return "redirect:/white_List_SubAUA_IPAddress";
	}
	
	
	
	@GetMapping("/filterrequest")
	public String getRequestByDeptAndStatus(@RequestParam Long status, @RequestParam String department,//@RequestParam String departmentName,
			HttpServletRequest request, Model model) throws SQLException {
	    if (request.getSession().getAttribute("SSOID") == null) {
	        return "redirect:/BackToSSO";
	    }
	    List<Map<String, Object>> subAuaList = this.whiteListSubAUAIPAddresservice.GetSubauaCodeName();
		model.addAttribute("subAuaList", subAuaList);
	    logger.info("getRequestByDeptAndStatus=================> Inside the method");
	    	
		
	    List<WhiteListSubAUAEntity> wsi;
	    if (department.equals("ALL") && status == 2) {
	        wsi = whiteListSubAUAIPAddresservice.getAll();
	    } else if (department.equals("ALL") && status != 2) {
	        wsi = whiteListSubAUAIPAddresservice.searchByStatus(status);
	    } else if (!department.equals("ALL") && status == 2) {
	    	wsi = whiteListSubAUAIPAddresservice.searchByDepartment(department);	        
	    } else {
	    	wsi = whiteListSubAUAIPAddresservice.searchByStatusAndDepartment(status, department);
	    }

	    model.addAttribute("details", wsi);
		return "whiteListSubAUAIPAddress";
	}	  

}
