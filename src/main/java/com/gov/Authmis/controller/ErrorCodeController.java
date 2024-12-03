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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.model.AverageResponseTimeModel;
import com.gov.Authmis.model.ErrorCodeModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.ErrorCodeService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class ErrorCodeController {

	@Autowired
	private ErrorCodeService service;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(ErrorCodeController.class);
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@GetMapping("/ErrorCode")
	public String fluctuation_report(HttpServletRequest request, Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		return "ErrorCode";

	}

	@GetMapping(value = "/ErrorCodeReport")
	public String searchAvgResponseTimeData(Model model,
			@ModelAttribute("errorCodeModel") ErrorCodeModel errorCodeModel, HttpSession session,
			HttpServletRequest request) throws SQLException {
		List<ErrorCodeModel> searchResult = null;
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		try {
			searchResult = service.errorCodeService(errorCodeModel, ssoid1);
			logger.info("ErrorCodeController==>Error code Report Data ...." + searchResult);
			model.addAttribute("result", searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return "/ErrorCode";
	}
}
