package com.gov.Authmis.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.gov.Authmis.model.FluctuationReportModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.FluctuationReportService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class FluctuationReportController {

	@Autowired
	FluctuationReportService service;

	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@GetMapping("/fluctuation_report")
	public String fluctuation_report(HttpServletRequest request,Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		return "fluctuation_report";
	}

	@GetMapping(value = "/FluctuationData")
	public String searchFluctuationData(Model model,
			@ModelAttribute("fluctuationdata") FluctuationReportModel fluctuationModel, HttpSession session,HttpServletRequest request) throws SQLException {
		Map<String, Object> searchResult = null;
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		try {
			searchResult = service.GetFlucationReportData(fluctuationModel,ssoid1);
			System.out.print("Fluctuation Report Data ...." + searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", searchResult.get("details"));

		return "/fluctuation_report";
	}

}
