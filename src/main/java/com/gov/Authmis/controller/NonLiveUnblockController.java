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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.dao.SendEmail;
import com.gov.Authmis.dao.SendEmailUnblock;
import com.gov.Authmis.entity.NonLiveUnblockEntity;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.NonLiveUnblockModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveUnblockService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class NonLiveUnblockController {
	String dataNull = "true";

	List<NonLiveUnblockEntity> finalList = new ArrayList<NonLiveUnblockEntity>();

	@Autowired
	NonLiveUnblockService service;

	@Autowired
	public SendEmailUnblock sendEmailUnblock;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(NonLiveUnblockController.class);
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/NonLiveUnBlock_file")
	public String NonLiveUnblockFile(Model model, NonLiveUnblockModel nonLiveUnblockModel, HttpServletRequest request)
			throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		return "NonLiveUnBlock_file";
	}

	@RequestMapping(value = "/batchIdList", method = RequestMethod.GET)
	public @ResponseBody Object getBatchIdList(@RequestParam("fromdate") String fromdate,
			@RequestParam("enddate") String enddate, Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		HashMap<String, Object> batchids = new HashMap<>();

		batchids = service.GetBatchIds(fromdate, enddate);

		// System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + batchids.get("data"));

		model.addAttribute("batchIdList", batchids.get("data"));

		return batchids.get("data");
	}

	@RequestMapping(value = "/subAuaList", method = RequestMethod.GET)
	public @ResponseBody Object getSubauaList(@RequestParam("fromdate") String fromdate,
			@RequestParam("enddate") String enddate, @RequestParam("batchid") String batchid, Model model)
			throws SQLException {
		HashMap<String, Object> subauaData = new HashMap<>();

		subauaData = service.GetSubauaCode(fromdate, enddate, batchid);

		logger.info("NonLiveUnblockController====>subaualist>>>>>>>>>>>>>>>>>>>>>" + subauaData.get("data"));

		model.addAttribute("subAuaList", subauaData);

		return subauaData.get("data");
	}

	@GetMapping(value = "/finalUnblockList")
	public String getUnblockList(Model model, NonLiveUnblockModel nonLiveUnblockModel, HttpServletRequest request)
			throws SQLException {

		logger.info("NonLiveUnblockController====>fromdate" + nonLiveUnblockModel.getFromdate());
		logger.info("NonLiveUnblockController====>enddate" + nonLiveUnblockModel.getEnddate());
		logger.info("NonLiveUnblockController====>BatchID" + nonLiveUnblockModel.getBatchid());
		logger.info("NonLiveUnblockController====>SubAuaCode" + nonLiveUnblockModel.getSubaua());
		validateSSO.getValidateSSO(request);
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		HashMap<String, Object> result = service.GetFinalBatchList(nonLiveUnblockModel, finalList);
		// System.out.println(" getUnblockList subAuaIds>>>>>>>>>>>>>>>>>>>>> " +
		// result.get("details"));
		// model.addAttribute("details",result.get("details"));

		this.dataNull = result.get("dataNull").toString();
		// System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("details"));
		// System.out.println("finalList>>>>>>>>>>>>>>>>>>>>> " + finalList);
		model.addAttribute("details", result.get("details"));
		model.addAttribute("dataNull", this.dataNull);

		// To add value to Instance variable.
		// finalList = (HashMap<String, NonLiveUnblockEntity>) result;

		return "/NonLiveUnBlock_file";
	}

	@GetMapping(value = "/UnblockAadhaar")
	public String UnblockByBatchidandSubaua(Model model, NonLiveUnblockModel nonLiveUnblockModel,
			RedirectAttributes redirAttrs, HttpServletRequest request) throws SQLException {
		try {
			logger.info("NonLiveUnblockController====>fromdate" + nonLiveUnblockModel.getFromdate());
			logger.info("NonLiveUnblockController====>enddate" + nonLiveUnblockModel.getEnddate());
			logger.info("NonLiveUnblockController====>BatchID" + nonLiveUnblockModel.getBatchid());
			logger.info("NonLiveUnblockController====>SubAuaCode" + nonLiveUnblockModel.getSubaua());
			validateSSO.getValidateSSO(request);
			// Main Menu
			mainMenuUtil.getMainMenu(model, request);
			HashMap<String, Object> result = null;
			logger.info("NonLiveUnblockController====>finalList==>" + finalList);
			for (NonLiveUnblockEntity nonLiveUnblockEntity : finalList) {
				logger.info("NonLiveUnblockController====>nonLiveUnblockEntity" + nonLiveUnblockEntity);
				result = service.UnblockByBatchid(nonLiveUnblockModel, nonLiveUnblockEntity);
			}

			// send email unblock
			// sendEmailUnblock.SendEmailUnblockbyBatchId(finalList,nonLiveUnblockModel.getFromdate(),nonLiveUnblockModel.getEnddate(),nonLiveUnblockModel.getBatchid(),nonLiveUnblockModel.getSubaua());
			logger.info("NonLiveUnblockController====>getUnblockList subAuaIds>>>>>>>>>>>>>>>>>>>>>"
					+ result.get("details"));
			// model.addAttribute("details",result.get("details"));

			// this.dataNull = result.get("dataNull").toString();
			// System.out.println("dataNull >>>>>>>>>>>>>>>>>>>>> " + this.dataNull);
			// System.out.println("Result>>>>>>>>>>>>>>>>>>>>> " + result.get("details"));
			// model.addAttribute("details", result.get("details"));
			// model.addAttribute("dataNull", this.dataNull);

			redirAttrs.addFlashAttribute("success", "All Aadhaar Has been Unblocked.");
		} catch (Exception e) {
			redirAttrs.addFlashAttribute("error", "Error In Unblocking Aadhaar.");
			e.printStackTrace();
		}

		return "redirect:/NonLiveUnBlock_file";
	}

}
