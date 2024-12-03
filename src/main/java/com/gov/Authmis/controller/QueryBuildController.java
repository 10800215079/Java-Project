package com.gov.Authmis.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.OtpAuthenticationRequestforQueryBuilder;
import com.gov.Authmis.model.QueryBuilder;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.QueryBuilderService;
import com.gov.Authmis.service.SubAuaRegistrationService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class QueryBuildController {
	
	SSOLoginController ssoLoginController = new SSOLoginController();
	@Autowired
	private QueryBuilderService service;
	@Autowired
	SubAuaRegistrationService subAuaRegistrationService;
	
	private String SELECT = "SELECT";
	
	static Logger logger = LoggerFactory.getLogger(QueryBuildController.class);

	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@GetMapping("/getQuery")
	public String getForm(QueryBuilder student, Model model, HttpServletRequest request, HttpSession session)
			throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		List<UsersRoleMapping> userProfile = subAuaRegistrationService.getSsoProfile(ssoId);
		model.addAllAttributes(userProfile);
			
		String ipAddress = request.getRemoteHost();
		
		System.out.println("IP address from remote Host : " + ipAddress );
		
		
		int whiteListSSO = service.validateSSOWhiteList(ipAddress,ssoId);
		
		if(whiteListSSO == 0 ) {			
			return "RestrictQueryBuilder";
		}	
		model.addAttribute("getQuery", student);	
		return "ManageQueryBuilder";
	}

	@PostMapping({ "/otpAuthenticationForQueryBuilder" })
	public ResponseEntity<String> otpAuthenticationForQueryBuilder(HttpServletRequest request,
			@RequestBody OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder,Model model) throws Exception  {
		  	  
		javax.servlet.http.HttpSession session = request.getSession();
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		otpAuthenticationRequestforQueryBuilder.setSso(ssoid);
		String txn = (String) session.getAttribute("txn");
		otpAuthenticationRequestforQueryBuilder.setTxn(txn);
		System.out.println(txn);
		
		String ipAddress = request.getRemoteAddr();
		System.out.println("=======**************************>>>>>System Ip :::: " + ipAddress);
		
		// Print the values of the otpAuthenticationRequest object
	    System.out.println("otp: " + otpAuthenticationRequestforQueryBuilder.getOtp());
	    System.out.println("uuid: " + otpAuthenticationRequestforQueryBuilder.getUuid());
	    System.out.println("querys: " + otpAuthenticationRequestforQueryBuilder.getQuerys());
		
		
		AuthResponseEntity resp = subAuaRegistrationService.athentication(otpAuthenticationRequestforQueryBuilder);
		try {
		if (resp.getAuth().getStatus().equalsIgnoreCase("Y")) {
			System.out.println("=========>>>> Authenticate successfully");
			//Save log data
			service.SaveQueryBuilderLogData(otpAuthenticationRequestforQueryBuilder,ipAddress);
			//After Successfully Authenticate Execute the Query
			//service.queryInsertUpdateAlterRename(otpAuthenticationRequestforQueryBuilder);
			
			//new code added
			String errorMessage = service.queryInsertUpdateAlterRename(otpAuthenticationRequestforQueryBuilder);
			return ResponseEntity.ok("Query Executed Successfully");
			
		} else {
			//return "";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OTP is Not Valid.");
		}
		}catch (UnexpectedRollbackException ex) {
			 String errorMessage = "UnexpectedRollbackException: " + ex.getMessage();
			 //model.addAttribute("errorMessage", errorMessage);
			 //return "500";
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Query Not Executed Please Try Again. ");
	    } catch (Exception e) {
	    	String errorMessage = "Exception: " + e.getMessage();
			 //model.addAttribute("errorMessage", errorMessage);
			 //return "500";
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Query Not Executed Please Try Again.");
	    }

	}
	//Query builder log report
		@RequestMapping(value = "/getQueryBuilderLogReport", method = RequestMethod.GET)
		@ResponseBody
		public Object queryBuilderLogReport(HttpServletRequest request) throws Exception {
			
			List<Object> result = new ArrayList();
			result = service.getLogReport();
			return result;
		}


}
