package com.gov.Authmis.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.dao.SubAuaWiseSendMail;
import com.gov.Authmis.dao.SubAuaWiseSendMailServiceImpl;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.MailContentModal;
import com.gov.Authmis.service.AadhaarOTPValidationService;
import com.gov.Authmis.service.AddLicencyForSubAuaSystemService;
import com.gov.Authmis.service.MenuAndSubMenuService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;
import com.itextpdf.text.DocumentException;

@Controller
public class AddLicencyForSubAuaSystemController {
	AddLicencyForSubAuaSystem addLicencyForSubAuaSystem = new AddLicencyForSubAuaSystem();
	@Autowired
	SubauaWiseTransactionService transactionService;
	@Autowired
	private AddLicencyForSubAuaSystemService addLicencyForSubAuaSystemservice;
	
	@Autowired
	AadhaarOTPValidationService aadhaarOTPValidationService;
	
	@Autowired
	private MenuAndSubMenuService service;

	static Logger logger = LoggerFactory.getLogger(AddLicencyForSubAuaSystemController.class);
	@Autowired
	public SubAuaWiseSendMail subAuaWiseSendMail;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	SSOLoginController ssoLoginController = new SSOLoginController();
	
	
	@GetMapping({ "/AddLicencyForSubAuaSystemView" })
	public String getAllEmployees(HttpServletRequest request,Model model) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
         
		String roleId = (String) request.getSession().getAttribute("roleId");
		Boolean b = service.hasRole(roleId, "AddLicencyForSubAuaSystemView");
		if(!b) {
			return "redirect:/BackToSSO";
		}
		
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoId);
	
		model.addAttribute("subAuaList", subAuaList);
		model.addAttribute("AddLicencyForSubAuaSystem", addLicencyForSubAuaSystem);
		return "AddLicencyForSubAuaSystemView";
	}

	@GetMapping("/Subauanamecode")
	public Object getSubauanamecode(@RequestParam("name") String name) {

		Object object = this.transactionService.getSubAuaNameCode(name);

		return object;
	}

	@PostMapping("/AddLicencyForSubAuaSystem")
	public ResponseEntity<String> createTutorial(
	    @RequestBody AddLicencyForSubAuaSystem addLicencyForSubAuaSystem,
	    BindingResult bb, Model model, HttpServletRequest request)
	    throws DocumentException, IOException, ParseException, SQLException {
	    
	    if (request.getSession().getAttribute("SSOID") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired. Please log in again.");
	    }
	    
	    String ssoId = (String) request.getSession().getAttribute("SSOID");
	    model.addAttribute("AddLicencyForSubAuaSystem", addLicencyForSubAuaSystem);

	    String response = addLicencyForSubAuaSystemservice.addUserForGenricFaceAuth(addLicencyForSubAuaSystem.getSubAuaCode(),
	            addLicencyForSubAuaSystem.getSubAuaName(), addLicencyForSubAuaSystem.getSubAuaLk(), 1L);
	    
	    if (response.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	    }
	    
	    JSONObject jsonResponse = new JSONObject(response);
	    boolean isSuccess = jsonResponse.getBoolean("IsSuccess");
	    
	    if (isSuccess) {
	        Object tutorial = addLicencyForSubAuaSystemservice.save(addLicencyForSubAuaSystem);

	        logger.info("AddLicencyForSubAuaSystemController====>" + addLicencyForSubAuaSystem.getSubAuaCode());
	        logger.info("AddLicencyForSubAuaSystemController====>" + addLicencyForSubAuaSystem.getSubAuaLk());

	        model.addAttribute("subAuaCode", addLicencyForSubAuaSystem.getSubAuaCode());
	        model.addAttribute("subAuaLk", addLicencyForSubAuaSystem.getSubAuaLk());
	        model.addAttribute("expiryDate", addLicencyForSubAuaSystem.getExpiryDate());

	        if (tutorial == null) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insertion Failure.");
	        } else {
	            return ResponseEntity.status(HttpStatus.OK).body("Inserted Successfully for " + addLicencyForSubAuaSystem.getSubAuaName() + " Subaua ");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	    }
	}




	@GetMapping("/subauaORGNAME/{ORGNAME}")
	public @ResponseBody List<String> getsubauaCode(@PathVariable(value = "ORGNAME") String ORGNAME, Model m) {
		List<String> str = addLicencyForSubAuaSystemservice.getSubauaCode(ORGNAME);
		System.out.println(str + "subaua code");
		m.addAttribute("ORGNAME", str);
		return str;

	}

}
