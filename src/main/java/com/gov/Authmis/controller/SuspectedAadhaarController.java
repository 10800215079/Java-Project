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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SuspectedAadhaar;
import com.gov.Authmis.service.SuspectedAadhaarService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;
import com.gov.Authmis.service.SubauaWiseTransactionService;

@Controller
public class SuspectedAadhaarController {
	@Autowired
	SuspectedAadhaarService suspectedAadhaarController;

	@Autowired
	SubauaWiseTransactionService transactionService;
	@PersistenceContext
	private EntityManager entityManager;
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(SuspectedAadhaarController.class);
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@GetMapping({ "/suspected_aadhaar" })
	public String suspected_aadhaar(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoId);
		// List<Map<String, Object>> subAuaList =
		// this.transactionService.GetSubauaCodeName(ssoid1);
		logger.info("SuspectedAadhaarController=================> subauaList=====>" + subAuaList);

		model.addAttribute("subAuaList", subAuaList);
		return "suspected_aadhaar";
	}

	@GetMapping({ "/SuspectedAadhaarMapping" })
	public String searchSuspectedAadhaar(Model model,
			@ModelAttribute("suspectedAadhaar") SuspectedAadhaar suspectedAadhaar, HttpSession session,
			HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		try {
			String ssoid1 = (String) request.getSession().getAttribute("SSOID");
			List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
			System.out.println("subauaList ...... " + subAuaList);
			model.addAttribute("subAuaList", subAuaList);
			DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
			Date frDate = format.parse(suspectedAadhaar.getFromdate());
			Date toDate = format.parse(suspectedAadhaar.getEnddate());
			logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>Date frDate : " + frDate);
			logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>Date toDate : " + toDate);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String frdate = formatter.format(frDate);
			String todate = formatter.format(toDate);
			logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>Date frDate : " + frDate);
			logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>Date toDate : " + toDate);
			suspectedAadhaar.setFromdate(frdate);
			suspectedAadhaar.setEnddate(todate);
			List<SuspectedAadhaar> searchResult = new ArrayList<>();
			try {
				searchResult = this.suspectedAadhaarController.GetResult(suspectedAadhaar);
				String FROM_DATE = suspectedAadhaar.getFromdate();
				System.out.println(" from date in model " + FROM_DATE);
				model.addAttribute("FROM_DATE", FROM_DATE);
				String End_DATE = suspectedAadhaar.getEnddate();
				model.addAttribute("End_DATE", End_DATE);
				System.out.println(" end date in model " + End_DATE);
				logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>from date in model "
						+ FROM_DATE);
				logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>end date in model "
						+ End_DATE);
				logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>searchResult "
						+ searchResult);
				logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>SUB_AUA_CODE "
						+ suspectedAadhaar.getSubAuaCode());

			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("result", searchResult);
			// model.addAttribute("SUB_AUA_CODE", suspectedAadhaar.getSubAuaCode());

			return "suspected_aadhaar";
		} catch (ParseException e1) {
			e1.printStackTrace();
			return "/suspected_aadhaar";
		}
	}

	@RequestMapping(value = "/SuspectedAadhaarMappingDetails", method = RequestMethod.GET)
	public @ResponseBody Object SuspectedAadhaarMappingDetails(@RequestParam("E_AADHAAR_ID") String E_AADHAAR_ID,
			@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE, @RequestParam("FROM_DATE") String FROM_DATE,
			@RequestParam("End_DATE") String End_DATE, Model model) // ,@RequestParam("SUB_AUA_CODE") String
																	// SUB_AUA_CODE
	{
		System.out.println("AADHAAR ID" + E_AADHAAR_ID + "SUB_AUA_CODE" + SUB_AUA_CODE);

		HashMap<String, Object> result = new HashMap<>();

		result = this.suspectedAadhaarController.getSuspectedAadhaarMappingDetails(E_AADHAAR_ID, SUB_AUA_CODE,
				FROM_DATE, End_DATE);// ,SUB_AUA_CODE
		JSONObject jsonobj = new JSONObject(result);

		logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>JsonObject >>>>>>>>>>>>>>>>>>>>> "
				+ jsonobj);
		logger.info("SuspectedAadhaarController============>searchSuspectedAadhaar===>Result>>>>>>>>>>>>>>>>>>>>> "
				+ result.get("data"));

		model.addAttribute("NonLiveUploadListModalData", result.get("data"));

		return result.get("data");
	}
}
