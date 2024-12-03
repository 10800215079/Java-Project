package com.gov.Authmis.util;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.controller.SSOLoginController;
@Service
public class ValidateSSO {
	
	SSOLoginController ssoLoginController = new SSOLoginController();
	
	public String getValidateSSO(HttpServletRequest request) {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
	return "redirect:" + Constant.SSO_URL;
	}
	
}
