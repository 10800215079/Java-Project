package com.gov.Authmis.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.SubAuaWiseErrorDetailsReportModel;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class SubauaWiseTransactionController {
	@Autowired
	SubauaWiseTransactionService subauaWiseTransactionService;
	
	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@GetMapping({ "/subaua_wise_transactions_detail" })
	public String transaction_details(Model model, HttpServletRequest request) throws SQLException {
		System.out.println("Inside the method1");
		// Validate SSO Login
		validateSSO.getValidateSSO(request);
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		List<SubAua> subAuaList = this.subauaWiseTransactionService.GetSubauaCodeName(ssoid1);
		System.out.println("subAuaList===>" + subAuaList);
		model.addAttribute("subAuaList", subAuaList);
		return "subaua_wise_transactions_detail";
	}

	// new code
	@GetMapping(path = "/subauaWiseData", produces = MediaType.APPLICATION_JSON_VALUE)
	public String parameterWiseReport(
			@ModelAttribute("subAuaWiseErrorDetailsReportModel") SubAuaWiseErrorDetailsReportModel subAuaWiseErrorDetailsReportModel,
			HttpServletRequest request, Model model) throws ParseException, SQLException, IOException, JAXBException,
			KeyManagementException, NoSuchAlgorithmException {

		model.addAttribute("parameterWiseTransactionReport");
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		String ssoId = (String) request.getSession().getAttribute("SSOID");

		List<SubAua> subAuaList = this.subauaWiseTransactionService.GetSubauaCodeName(ssoId);
		List<Map<String, Object>> serviceTypeList = this.subauaWiseTransactionService.GetServiceType();
		model.addAttribute("subAuaList", subAuaList);
		model.addAttribute("serviceTypeList", serviceTypeList);

		HttpSession session = request.getSession();
		// Set attributes in the session
		session.setAttribute("subAuaCode", subAuaWiseErrorDetailsReportModel.getSubAuaCode());
		session.setAttribute("servicetype", subAuaWiseErrorDetailsReportModel.getServicetype());
		session.setAttribute("fromdate", subAuaWiseErrorDetailsReportModel.getFromdate());
		session.setAttribute("enddate", subAuaWiseErrorDetailsReportModel.getEnddate());
	
		
		HashMap<String, Object> result = new HashMap<>();
		result = subauaWiseTransactionService.getDataByFilter(subAuaWiseErrorDetailsReportModel);

		model.addAttribute("details", result.get("details"));
		return "subaua_wise_transactions_detail";
	}
	
	//new code
	@RequestMapping(value = "/getCountWiseErrorReport", method = RequestMethod.GET)
	@ResponseBody
	public Object CountWiseErrorCodeReport(HttpServletRequest request,
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE,
			@RequestParam("ERRORCODE") String ERRORCODE,@RequestParam("DP_ID") String DP_ID,@RequestParam("MI") String MI,
			@RequestParam("RESPONSE_MESSAGE") String RESPONSE_MESSAGE) throws Exception {
		
		
		HttpSession session = request.getSession();
		List<Object> result = new ArrayList();
		result = subauaWiseTransactionService.getDataByErrorCode(session, SUB_AUA_CODE, REQUEST_TYPE,ERRORCODE,RESPONSE_MESSAGE,DP_ID,MI);

		return result;
	}
	
	
	
	
}
