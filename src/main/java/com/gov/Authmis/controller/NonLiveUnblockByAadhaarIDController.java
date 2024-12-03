package com.gov.Authmis.controller;

import java.net.UnknownHostException;
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
import com.gov.Authmis.dao.SendEmailUnblock;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.NonLiveUnblockByAadhaarIDModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveUnblockByAadhaarIDService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class NonLiveUnblockByAadhaarIDController {
	// List<NonLiveUnblockEntity> finalList = new ArrayList<NonLiveUnblockEntity>();

	@Autowired
	NonLiveUnblockByAadhaarIDService service;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	SendEmailUnblock sendEmailUnblock;

	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(NonLiveExcelUploadController.class);

	@GetMapping({ "/NonLiveUnblockByAadhaarID" })
	public String NonLiveUnblockBySearch(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		return "NonLiveUnblockByAadhaarID";
	}

	@GetMapping(value = "/SingleAadhaarUnblockData")
	public String getUnblockListForAadhaar(Model model, NonLiveUnblockByAadhaarIDModel nonLiveUnblockBySearchModel,
			HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		HashMap<String, Object> result = service.GetDataBasedOnAadhaarID(nonLiveUnblockBySearchModel);
		nonLiveUnblockBySearchModel.setAadhaarid(nonLiveUnblockBySearchModel.getAadhaarid());
		model.addAttribute("hffd");
		System.out.println("aaaddar : " + nonLiveUnblockBySearchModel.getAadhaarid());
		// model.addAttribute("aadhaar", nonLiveUnblockBySearchModel.getAadhaarid());
		model.addAttribute("aadhaarid", nonLiveUnblockBySearchModel.getAadhaarid());

		model.addAttribute("details", result.get("details"));

		return "/NonLiveUnblockByAadhaarID";
	}

	@RequestMapping(value = "/UnblockByAadhaarID", method = RequestMethod.GET)
	public @ResponseBody String UnblockByAadhaarID(@RequestParam("batchid") String batchid,
			@RequestParam("adhaarid") String adhaarid, @RequestParam("subauacode") String subauacode,
			@RequestParam("fromdate") String fromdate, @RequestParam("enddate") String enddate,
			@RequestParam("dc") String dc, @RequestParam("uidresponsecode") String uidresponsecode,
			@RequestParam("transactionid") String transactionid, @RequestParam("ssoid") String ssoid, Model model)
			throws SQLException {

		logger.info("NonLiveUnblockByAadhaarIDController====>Inside the method3" + batchid + adhaarid + subauacode
				+ fromdate + enddate + dc + uidresponsecode + transactionid + ssoid);
		HashMap<String, Object> result = null;

		result = service.UnblockByAadhaarID(batchid, adhaarid, subauacode, fromdate, enddate, dc, uidresponsecode,
				transactionid, ssoid);

		// send email unblock
		try {
			sendEmailUnblock.SendEmailUnblockbyAadhaarId(batchid, adhaarid, subauacode, fromdate, enddate, dc,
					uidresponsecode, transactionid, ssoid);
		} catch (UnknownHostException | SQLException e) {
			e.printStackTrace();
		}

		return "success";
	}
}
