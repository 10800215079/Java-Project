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
import com.gov.Authmis.model.MinuteWiseTransactionModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.MinuteWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class MinuteWiseTransactionController {
	@Autowired
	MinuteWiseTransactionService service;
	@PersistenceContext
	private EntityManager entityManager;
	SSOLoginController ssoLoginController = new SSOLoginController();
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@GetMapping("/minute_wise_transaction_report")
	public String fluctuation_report(HttpServletRequest request, Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		return "minute_wise_transaction_report";
	}

	@GetMapping(value = "/MinuteWiseTransactionData")
	public String searchMinuteWiseTransData(Model model,
			@ModelAttribute("minuteWiseData") MinuteWiseTransactionModel minuteWiseTransactionModel,
			HttpSession session, HttpServletRequest request) throws SQLException {
		Map<String, Object> searchResult = null;
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		try {
			searchResult = service.GetMinuteWiseTransactionData(minuteWiseTransactionModel, ssoid1);
			System.out.println("from date is " + minuteWiseTransactionModel.getFromdate());
			System.out.println("END DATE is " + minuteWiseTransactionModel.getEnddate());

			model.addAttribute("FROM_DATE", minuteWiseTransactionModel.getFromdate());
			model.addAttribute("END_DATE", minuteWiseTransactionModel.getEnddate());
			model.addAttribute("SUB_CODE", minuteWiseTransactionModel.getSubCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", searchResult.get("details"));

		return "/minute_wise_transaction_report";
	}

	@RequestMapping(value = "/searchMinuteWiseTransDataDetails", method = RequestMethod.GET)
	public @ResponseBody Object searchMinuteWiseTransDataDetails(@RequestParam("subCode") String subCode,
			@RequestParam("TRANSACTION_ID") String TRANSACTION_ID, @RequestParam("FROM_DATE") String FROM_DATE,
			@RequestParam("END_DATE") String END_DATE, Model model, HttpServletRequest request) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<Object> result = new ArrayList();

		result = this.service.getMinuteWiseTransactionDetails(subCode, TRANSACTION_ID, FROM_DATE, END_DATE);
		JSONObject jsonobj = new JSONObject(result);
		System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);

		// System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));

		model.addAttribute("NonLiveUploadListModalData", result);

		return result;
	}

}
