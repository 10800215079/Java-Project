package com.gov.Authmis.controller;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;
import com.gov.Authmis.model.Menu;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.SubAuaRegistrationService;
import com.gov.Authmis.service.WhiteListSubauaForQueryBuilderService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class WhiteListSubauaForQueryBuilderController {
	@Autowired
	private WhiteListSubauaForQueryBuilderService whiteListSubauaForQueryBuilderService;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(WhiteListSubauaForQueryBuilderController.class);
	Menu Menu = new Menu();
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@Autowired
	SubAuaRegistrationService subAuaRegistrationService;
	
	//show all the list in the same page 
	@GetMapping({ "/WhiteListSubAua" })
	public String getAllSSO(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("addSSOAndIP");
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		// For getting Aadhaar for SSO
		List<UsersRoleMapping> userProfile = subAuaRegistrationService.getSsoProfile(ssoId);
		model.addAllAttributes(userProfile);
		
		//get all data in table 
		HashMap<String, Object> result = new HashMap<>();
		result = whiteListSubauaForQueryBuilderService.getAllWhiteListSSO();
		model.addAttribute("details", result.get("details"));
		
		return "WhiteListSubauaForQueryBuilder";
	}

	
	@PostMapping({ "/otpAuthenticationForWhiteListSSO" })
	public ResponseEntity<String> otpAuthenticationForWhiteListSSO(HttpServletRequest request,
			@RequestBody WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity,Model model) throws Exception  {
		
		javax.servlet.http.HttpSession session = request.getSession();
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		whiteListSubauaForQueryBuilderEntity.setCREATED_BY(ssoid);
		String txn = (String) session.getAttribute("txn");
		whiteListSubauaForQueryBuilderEntity.setTRANSACTION_ID(txn);
		System.out.println(txn);
	
		AuthResponseEntity resp = subAuaRegistrationService.athenticationForWhiteListSSO(whiteListSubauaForQueryBuilderEntity);
				
		if (resp.getAuth().getStatus().equalsIgnoreCase("Y")) {
			System.out.println("=========>>>> Authenticate successfully");
			Object whiteList = whiteListSubauaForQueryBuilderService.save(whiteListSubauaForQueryBuilderEntity,ssoid);					
			return ResponseEntity.ok("SSO Id is Whitelisted Successfully");
			
		} else {			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OTP is Not Valid.");
		}
		
	}
	
	
	@GetMapping("/updateBySRNO/{SRNO}")
	public String updateBySSOId(@PathVariable("SRNO") Long SRNO, Model model, HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

	    whiteListSubauaForQueryBuilderService.updateStatus(SRNO);

	    return "redirect:/WhiteListSubAua";
	}

	 @PostMapping("/checkDuplicateIP")
	    public ResponseEntity<Map<String, String>> checkDuplicateIP(@RequestParam String IP) {
	        boolean isDuplicate = whiteListSubauaForQueryBuilderService.isIPDuplicate(IP);
	        
	        if (isDuplicate) {
	            Map<String, String> responseBody = new HashMap<>();
	            responseBody.put("errorCode", "CONFLICT");
	            responseBody.put("errorMessage", "Duplicate IP address: " + IP);
	            return ResponseEntity.ok(responseBody);
	        } else {
	            Map<String, String> responseBody = Collections.singletonMap("message", "IP address is unique");
	            return ResponseEntity.ok(responseBody);
	        }
	    }
	
	/**
	@GetMapping("/deleteBySRNO/{SRNO}")
	public String deleteBySSOId(@PathVariable("SRNO") Long SRNO, Model model, HttpServletRequest request) throws SQLException {

		validateSSO.getValidateSSO(request);
		mainMenuUtil.getMainMenu(model, request);
		
		whiteListSubauaForQueryBuilderService.delete(SRNO);
			
		return "redirect:/WhiteListSubAua";
	}

   **/
	
}


