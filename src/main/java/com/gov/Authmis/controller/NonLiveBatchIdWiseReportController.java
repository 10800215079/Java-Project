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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.NonLiveBatchIdWiseReportModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveBatchIdWiseReportService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class NonLiveBatchIdWiseReportController {

	@Autowired
	NonLiveBatchIdWiseReportService service;

	SSOLoginController ssoLoginController = new SSOLoginController();
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	Logger logger = LoggerFactory.getLogger(NonLiveBatchIdWiseReportController.class);
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping({ "/NonLiveBatchIdWiseReport" })
	public String NonLiveUploadList(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		return "NonLiveBatchIdWiseReport";
	}

	@GetMapping({ "/NonLiveUploadListData" })
	public String NonLiveUploadListData(Model model, NonLiveBatchIdWiseReportModel nonLiveUploadListModel,
			HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		HashMap<String, Object> result = new HashMap<>();
		result = this.service.getBatchIdsByDates(nonLiveUploadListModel);
		JSONObject jsonobj = new JSONObject(result);
		logger.info("NonLiveBatchIdWiseReportController====>NonLiveUploadListData" + jsonobj);
		model.addAttribute("NonLiveUploadListData", result.get("data"));

		return "NonLiveBatchIdWiseReport";
	}

	@RequestMapping(value = "/NonLiveUploadListModalData", method = RequestMethod.GET)
	public @ResponseBody Object NonLiveUploadListModalData(@RequestParam("batchid") String batchid, Model model,
			HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		logger.info("NonLiveBatchIdWiseReportController====>batch id is : " + batchid);
		HashMap<String, Object> result = new HashMap<>();

		result = this.service.getDataByBatchID(batchid);
		JSONObject jsonobj = new JSONObject(result);
		logger.info("NonLiveBatchIdWiseReportController====>NonLiveUploadListModalData" + jsonobj);
		logger.info("NonLiveBatchIdWiseReportController====>Result>>>>>>>>>>>>>>>>>>>>>" + jsonobj);
		model.addAttribute("NonLiveUploadListModalData", result.get("data"));

		return result.get("data");
	}

}
