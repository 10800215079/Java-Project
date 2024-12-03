package com.gov.Authmis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gov.Authmis.constant.Constant;

@Controller
public class BlockAadhaarNoController {
	
	SSOLoginController ssoLoginController = new SSOLoginController();

	@GetMapping("/block_aadhaar_No")
	public String block_aadhaar_No(HttpServletRequest request) 
	{
		//Validate SSO Login
		boolean sessionoutput = ssoLoginController.validateSSOSession(request);
		if(!sessionoutput) {
			request.getSession().invalidate();
			return "redirect:"+Constant.SSO_URL;
		}
		return "block_aadhaar_No";
	}	
}
