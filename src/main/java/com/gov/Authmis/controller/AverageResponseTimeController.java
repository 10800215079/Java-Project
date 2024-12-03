package com.gov.Authmis.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.model.AverageResponseTimeModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.AverageResponseTimeService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class AverageResponseTimeController {
	@Autowired
	AverageResponseTimeService service;

	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	private EntityManager entityManager;
	Logger logger = LoggerFactory.getLogger(AverageResponseTimeController.class);
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@GetMapping("/average_responsetime_success_failure_count_report")
	public String fluctuation_report(HttpServletRequest request,Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		logger.info("AverageResponseTimeController==>  average_responsetime_success_failure_count_report   ");
		
		mainMenuUtil.getMainMenu(model, request);
		return "average_responsetime_success_failure_count_report";

	}

	@GetMapping(value = "/AverageResponseData")
	public String searchAvgResponseTimeData(Model model,
			@ModelAttribute("minuteWiseData") AverageResponseTimeModel averageResponseTimeModel, HttpSession session,HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<AverageResponseTimeModel> searchResult = null;
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		try {
			model.addAttribute("FROM_DATE", averageResponseTimeModel.getFromdate());
			model.addAttribute("END_DATE", averageResponseTimeModel.getEnddate());
			searchResult = service.GetAverageResponseTimeData(averageResponseTimeModel,ssoId);
			logger.info("AverageResponseTimeController==>  searchAvgResponseTimeData    " + searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", searchResult);

		return "/average_responsetime_success_failure_count_report";
	}

	@RequestMapping(value = "/averageResponseTimeDetails", method = RequestMethod.GET)
	public @ResponseBody Object averageResponseTimeDetails(@RequestParam("REQUEST_TYPE") String REQUEST_TYPE,
			@RequestParam("FROM_DATE") String FROM_DATE, @RequestParam("END_DATE") String END_DATE, Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		logger.info("AverageResponseTimeController==> averageResponseTimeDetails ==> REQUEST_TYPE  is :     "
				+ REQUEST_TYPE);
		List<Object> result = new ArrayList<>();

		result = this.service.getaverageResponseTimeDetails(REQUEST_TYPE, FROM_DATE, END_DATE);
		JSONObject jsonobj = new JSONObject(result);

		logger.info(
				"AverageResponseTimeController==> averageResponseTimeDetails ==> JsonObject >>>>>>>>>>>>>>>>>>>>>     "
						+ jsonobj);

		logger.info("AverageResponseTimeController==> averageResponseTimeDetails ==> Result>>>>>>>>>>>>>>>>>>>>>      "
				+ result);

		model.addAttribute("NonLiveUploadListModalData", result);

		return result;
	}

}
