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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.FraudTransactionModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.FraudTransactionService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class FraudTransactionController {
	@Autowired
	FraudTransactionService service;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	SubauaWiseTransactionService transactionService;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(FraudTransactionController.class);

	@GetMapping({ "/fraud_transations_report" })
	public String ODD_Hour_transaction(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
		model.addAttribute("subAuaList", subAuaList);
		return "fraud_transations_report";
	}

	@GetMapping({ "/FraudTransactionData" })
	public String searchFraudTransaction(Model model,
			@ModelAttribute("fraudTransactions") FraudTransactionModel fraudTransactionModel, HttpSession session,
			HttpServletRequest request) throws SQLException {
		
		if (request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		try {
			String ssoid1 = (String) request.getSession().getAttribute("SSOID");

			List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
			model.addAttribute("subAuaList", subAuaList);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date frDate = format.parse(fraudTransactionModel.getFromdate());
			Date toDate = format.parse(fraudTransactionModel.getEnddate());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String frdate = formatter.format(frDate);
			String todate = formatter.format(toDate);
			fraudTransactionModel.setFromdate(frdate);
			fraudTransactionModel.setEnddate(todate);
			Map<String, Object> searchResult = null;
			try {
				searchResult = this.service.GetFraudTranscationData(fraudTransactionModel);
				logger.info("FraudTransactionController==>Inside the method2" + searchResult);

			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("result", searchResult.get("details"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return "/fraud_transations_report";
	}
}
