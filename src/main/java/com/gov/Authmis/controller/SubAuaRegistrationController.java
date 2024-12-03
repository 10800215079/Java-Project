package com.gov.Authmis.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.DocumentFormatException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gov.Authmis.constant.Constant;

import com.gov.Authmis.entity.AuthrequestEkyc;
import com.gov.Authmis.entity.OtpAuthenticationRequest;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.SubAuaRegistrationService;
import com.gov.Authmis.util.ValidateSSO;
import com.itextpdf.text.DocumentException;

@Controller
public class SubAuaRegistrationController {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	SubAuaRegistrationService subAuaRegistrationService;
	
	@Autowired(required = true)
	ValidateSSO validateSSO;
	
	@GetMapping({"/Registration"})
	public String subAuaRegistration(HttpServletRequest request,  Model model) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId  = (String) request.getSession().getAttribute("SSOID");
		List<UsersRoleMapping> userProfile = subAuaRegistrationService.getSsoProfile(ssoId);
		model.addAllAttributes(userProfile);
		return "SubAuaRegistration";
	}
	
	@GetMapping({"/otpGeneration"})
	public String generateOtp(HttpServletRequest request,@RequestParam("UUID") String Uuid,  Model model)  {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<OtpRespose> response =  subAuaRegistrationService.getOtp(Uuid);
		
		javax.servlet.http.HttpSession session = request.getSession();
	    
	    // Set attributes in the session
	    session.setAttribute("txn", response.get(0).getTxn());
	   
		model.addAttribute(response);
		if(response.get(0).getStatus().equalsIgnoreCase("y")) {
			return "otpGeneration";
		}else {
			return " ";
		}

	}
	@ResponseBody
	@PostMapping({"/otpAuthentication"})
	public OtpRespose otpAuthentication(HttpServletRequest request,@RequestBody OtpAuthenticationRequest otpAuthenticationRequest  )  {
		javax.servlet.http.HttpSession session = request.getSession();
	    
	    String txn = (String) session.getAttribute("txn");
	    otpAuthenticationRequest.setTxn(txn);
	    System.out.println(txn);
	   // OtpRespose resp = subAuaRegistrationService.OtpAuthentication(otpAuthenticationRequest);
	    OtpRespose resp = subAuaRegistrationService.OtpAuthentication(otpAuthenticationRequest);
		   
		return resp;
		
	}
	
	
	@PostMapping("/activateSubaua")
	public ResponseEntity<String> createTutorial(@RequestParam("id") Long id, HttpServletRequest request) throws SQLException {
	    try {
	        // Check if session is valid
	        if (request.getSession().getAttribute("SSOID") == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired. Please log in again.");
	        }
	        
	        int result = subAuaRegistrationService.updateSubaua(id);

	        // Check the result of the update operation
	        if (result > 0) {
	            return ResponseEntity.ok("SubAUA activated successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to activate SubAUA. Please try again.");
	        }

	    }  catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
	    }
	}

	
	

}