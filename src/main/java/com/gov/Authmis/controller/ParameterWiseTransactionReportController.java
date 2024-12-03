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
import com.gov.Authmis.model.ParameterWiseTransactionReportModel;
import com.gov.Authmis.service.ParameterWiseTransactionReportService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class ParameterWiseTransactionReportController {

	@Autowired
	SubauaWiseTransactionService subauaWiseTransactionService;
	@Autowired
	ParameterWiseTransactionReportService parameterWiseTransactionReportService;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	
	@GetMapping("/parameterWiseTransactionReport")
	public String parameterWiseTransactionReport(Model model, HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		
		List<SubAua> subAuaList = this.subauaWiseTransactionService.GetSubauaCodeName(ssoId);
		model.addAttribute("subAuaList", subAuaList);
		List<Map<String, Object>> serviceTypeList = this.subauaWiseTransactionService.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);
		model.addAttribute("parameterWiseTransactionReport");
		
		return "Parameter_Wise_Transation_Report";

	}

	// new code
	@GetMapping(path = "/getDataParameterWise", produces = MediaType.APPLICATION_JSON_VALUE)
	public String parameterWiseReport(
			@ModelAttribute("parameterWiseTransactionReport") ParameterWiseTransactionReportModel parameterWiseTransactionReportModel,
			HttpServletRequest request, Model model) throws ParseException, SQLException, IOException, JAXBException, KeyManagementException, NoSuchAlgorithmException {

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
		session.setAttribute("subAuaCode", parameterWiseTransactionReportModel.getSubAuaCode());
		session.setAttribute("servicetype", parameterWiseTransactionReportModel.getServicetype());
		session.setAttribute("status1", parameterWiseTransactionReportModel.getStatus());
		session.setAttribute("fromdate", parameterWiseTransactionReportModel.getFromdate());
		session.setAttribute("enddate", parameterWiseTransactionReportModel.getEnddate());
		session.setAttribute("paraeter", parameterWiseTransactionReportModel.getParaeter());
		session.setAttribute("paramValue", parameterWiseTransactionReportModel.getParamValue());
		
		System.out.println("getFromdate() ==========>>>>>>" + parameterWiseTransactionReportModel.getFromdate());
		System.out.println("getEnddate() ===========>>>>>>" + parameterWiseTransactionReportModel.getEnddate());

		HashMap<String, Object> result = new HashMap<>();
		result = parameterWiseTransactionReportService.getFilterByDate(parameterWiseTransactionReportModel);

		model.addAttribute("details", result.get("details"));
		return "Parameter_Wise_Transation_Report";
	}

	@RequestMapping(value = "/getCountWiseParameterReport", method = RequestMethod.GET)
	@ResponseBody
	public Object CountWiseParameterReport(HttpServletRequest request,
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,@RequestParam("ERRORCODE") String ERRORCODE,
			@RequestParam("DP_ID") String DP_ID,@RequestParam("RESPONSE_MESSAGE") String RESPONSE_MESSAGE,@RequestParam("MI") String MI) throws Exception {
		
		HttpSession session = request.getSession();
		List<Object> result = new ArrayList();
		result = parameterWiseTransactionReportService.getDataByParameter(session, SUB_AUA_CODE, REQUEST_TYPE,AUTHENTICATION_STATUS,ERRORCODE,DP_ID,RESPONSE_MESSAGE,MI);

		return result;
	}

}
