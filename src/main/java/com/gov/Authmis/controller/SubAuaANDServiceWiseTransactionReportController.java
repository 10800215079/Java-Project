package com.gov.Authmis.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.SubAuaANDServiceWiseTransactionReportModel;
import com.gov.Authmis.service.SubAuaANDServiceWiseTransactionReportService;
import com.gov.Authmis.service.SubauaWiseTransactionService;

@Controller
public class SubAuaANDServiceWiseTransactionReportController {
	String dataNull = "true";

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	SubAuaANDServiceWiseTransactionReportService subAuaWiseTransactionReportService;

	@Autowired
	SubauaWiseTransactionService transactionService;

	SSOLoginController ssoLoginController = new SSOLoginController();

	@GetMapping({ "/subaua_and_service_wise_transaction_report" })
	public String subaua_wise_transaction_report(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
		System.out.println("subAuaList===>" + subAuaList);
		model.addAttribute("subAuaList", subAuaList);
		List<Map<String, Object>> serviceTypeList = this.transactionService.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);
		return "subaua_and_service_wise_transaction_report";
	}

	@GetMapping(path = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
	public String SubauaANDServiceWiseTransaction(Model model, HttpSession session,
			SubAuaANDServiceWiseTransactionReportModel subAuaWiseTransactionReportModel, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
		model.addAttribute("subAuaList", subAuaList);
		List<Map<String, Object>> serviceTypeList = this.transactionService.GetServiceType();
		model.addAttribute("serviceTypeList", serviceTypeList);
		System.out.println(" SUBAUA  " + subAuaWiseTransactionReportModel.getSubAuaCode());
		System.out.println(" SERVICETYPE  " + subAuaWiseTransactionReportModel.getServicetype());
		System.out.println(" STATUS  " + subAuaWiseTransactionReportModel.getStatus1());
		System.out.println(" FROMDATE  " + subAuaWiseTransactionReportModel.getFromdate());
		System.out.println(" TODATE  " + subAuaWiseTransactionReportModel.getEnddate());
		HashMap<String, Object> result = new HashMap<>();
		result = this.subAuaWiseTransactionReportService.getFilterByDate(subAuaWiseTransactionReportModel);
		this.dataNull = result.get("dataNull").toString();
		System.out.println(" dataNull >>>>>>>>>>>>>>>>>>>>> " + this.dataNull);
		System.out.println(" SubauaANDServiceWiseTransaction Result>>>>>>>>>>>>>>>>>>>>> " + result.get("details"));
		String FROM_DATE = subAuaWiseTransactionReportModel.getFromdate();
		System.out.println(" from date in model " + FROM_DATE);
		model.addAttribute("FROM_DATE", FROM_DATE);
		String End_DATE = subAuaWiseTransactionReportModel.getEnddate();
		model.addAttribute("End_DATE", End_DATE);
		System.out.println(" end date in model " + End_DATE);
		model.addAttribute("details", result.get("details"));
		model.addAttribute("dataNull", this.dataNull);
        session.setAttribute("subAuaCode", subAuaWiseTransactionReportModel.getSubAuaCode());
        session.setAttribute("servicetype", subAuaWiseTransactionReportModel.getServicetype());
        session.setAttribute("status1", subAuaWiseTransactionReportModel.getStatus1());
        session.setAttribute("fromdate", subAuaWiseTransactionReportModel.getFromdate());
        session.setAttribute("enddate", subAuaWiseTransactionReportModel.getEnddate());

		return "subaua_and_service_wise_transaction_report";
	}

	@RequestMapping(value = "/SubauaANDServiceWiseTransaction", method = RequestMethod.GET)
	public @ResponseBody Object SubauaANDServiceWiseTransactionDetails(
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE1,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,
			@RequestParam("from_date") String from_date, Model model)
			throws ParseException {

		System.out.println("SUB AUA CODE is :====================== " + SUB_AUA_CODE);
		System.out.println("REQUEST_TYPE1 is:====================== " + REQUEST_TYPE1);
		System.out.println("AUTHENTICATION_STATUS:=================" + AUTHENTICATION_STATUS);

		HashMap<String, Object> result = new HashMap<>();

		result = this.subAuaWiseTransactionReportService.getDataBySubaAua(SUB_AUA_CODE, REQUEST_TYPE1,
				AUTHENTICATION_STATUS, from_date);
		JSONObject jsonobj = new JSONObject(result);
		System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);

		System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));

		model.addAttribute("oddhourtransactionsdetails", result.get("data"));

		return result.get("data");
	}
	
	@RequestMapping(value = "/monthWiseSubauaANDServiceWiseTransaction", method = RequestMethod.GET)
	public @ResponseBody Object monthWiseSubauaANDServiceWiseTransaction(
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE1,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,
			@RequestParam("from_date") String from_date, @RequestParam("end_date") String end_date, Model model)
			
		throws ParseException {

			HashMap<String, Object> result = new HashMap<>();
	
			result = this.subAuaWiseTransactionReportService.getDataBySubaAuaMonthWise(SUB_AUA_CODE, REQUEST_TYPE1,
					AUTHENTICATION_STATUS, from_date, end_date);
			JSONObject jsonobj = new JSONObject(result);
			System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);
	
			System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));
	
			model.addAttribute("oddhourtransactionsdetails", result.get("data"));

		return result.get("data");
	}
	
	@RequestMapping(value = "/daySubauaANDServiceWiseTransaction", method = RequestMethod.GET)
	public @ResponseBody Object dayWiseSubauaANDServiceWiseTransaction(
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE1,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,
			@RequestParam("fromDate") String fromDate,  Model model)
			
		throws ParseException {

			HashMap<String, Object> result = new HashMap<>();
	
			result = this.subAuaWiseTransactionReportService.getDataBySubaAuaDayWise(SUB_AUA_CODE, REQUEST_TYPE1,
					AUTHENTICATION_STATUS,fromDate );
			JSONObject jsonobj = new JSONObject(result);
			System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);
	
			System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));
	
			model.addAttribute("oddhourtransactionsdetails", result.get("data"));

		return result.get("data");
	}
	
	@RequestMapping(value = "/hourSubauaANDServiceWiseTransaction", method = RequestMethod.GET)
	public @ResponseBody Object hourWiseSubauaANDServiceWiseTransaction(
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE1,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,
			@RequestParam("fromDate") String fromDate,  Model model)
			
		throws ParseException {

			HashMap<String, Object> result = new HashMap<>();
	
			result = this.subAuaWiseTransactionReportService.getDataBySubaAuaHourWise(SUB_AUA_CODE, REQUEST_TYPE1,
					AUTHENTICATION_STATUS, fromDate);
			JSONObject jsonobj = new JSONObject(result);
			System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);
	
			System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));
	
			model.addAttribute("oddhourtransactionsdetails", result.get("data"));

		return result.get("data");
	}
	
	@RequestMapping(value = "/detailedView", method = RequestMethod.GET)
	public @ResponseBody Object detailedView(
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("REQUEST_TYPE1") String REQUEST_TYPE1,
			@RequestParam("AUTHENTICATION_STATUS") String AUTHENTICATION_STATUS,
			@RequestParam("from_date") String from_date, Model model)
			throws ParseException {

		HashMap<String, Object> result = new HashMap<>();

		result = this.subAuaWiseTransactionReportService.getDetailedView(SUB_AUA_CODE, REQUEST_TYPE1,
				AUTHENTICATION_STATUS, from_date);
		JSONObject jsonobj = new JSONObject(result);
		System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);

		System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));

		model.addAttribute("oddhourtransactionsdetails", result.get("data"));

		return result.get("data");
	}
}
