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
import com.gov.Authmis.model.OddHourTransaction;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.DashBoardService;
import com.gov.Authmis.service.OddHourTransactionService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class OddHourTransactionController {
	@Autowired
	OddHourTransactionService oddHourTransactionController;

	@Autowired
	SubauaWiseTransactionService transactionService;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger    logger = LoggerFactory.getLogger(OddHourTransactionController.class);
	@GetMapping({"/ODD_Hour_transaction"})
	public String ODD_Hour_transaction(Model model,HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		logger.info("OddHourTransactionController====>ODD_Hour_transaction===>Inside the method1");
		
			List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
		model.addAttribute("subAuaList", subAuaList);
		return "ODD_Hour_transaction";
	}

	@GetMapping({"/OddHourTransactionMapping"})
	public String searchOddHourTransaction(Model model, @ModelAttribute("oddHourTransaction") OddHourTransaction oddHourTransaction, HttpSession session,HttpServletRequest request) throws SQLException {
		try {
			if(request.getSession().getAttribute("SSOID") == null) {
				return "redirect:/BackToSSO";
			}
			String ssoId = (String) request.getSession().getAttribute("SSOID");
			List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoId);
			//List<Map<String, Object>> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
			model.addAttribute("subAuaList", subAuaList);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date frDate = format.parse(oddHourTransaction.getFromdate());
			Date toDate = format.parse(oddHourTransaction.getEnddate());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String frdate = formatter.format(frDate);
			String todate = formatter.format(toDate);
			oddHourTransaction.setFromdate(frdate);
			oddHourTransaction.setEnddate(todate);
			Map<String, Object> searchResult = null;
			try {
				searchResult = this.oddHourTransactionController.GetResult(oddHourTransaction);
				model.addAttribute("FROM_DATE", oddHourTransaction.getFromdate());
				model.addAttribute("END_DATE", oddHourTransaction.getEnddate());
				model.addAttribute("SUB_AUA_CODE", oddHourTransaction.getSubCode());
				logger.info("OddHourTransactionController====>searchOddHourTransaction===>Inside the method2"+oddHourTransaction.getFromdate());
				logger.info("OddHourTransactionController====>searchOddHourTransaction===>Inside the method2"+oddHourTransaction.getEnddate());
				logger.info("OddHourTransactionController====>searchOddHourTransaction===>Inside the method2"+oddHourTransaction.getSubCode());
				
				//System.out.print("searchResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + searchResult);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			model.addAttribute("result", searchResult.get("details"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
		return "/ODD_Hour_transaction";
	}
	@RequestMapping(value="/oddhourtransactionsdetails", method = RequestMethod.GET)
	public @ResponseBody Object oddhourtransactionsdetails(@RequestParam("SUB_AUA_CODE") String SUB_AUA_CODE,@RequestParam("FROM_DATE") String FROM_DATE,@RequestParam("END_DATE") String END_DATE,Model model,HttpServletRequest request) throws SQLException 
	{
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		HashMap<String, Object> result = new HashMap<>();
		
		result = this.oddHourTransactionController.getoddhourtransactionsdetails(SUB_AUA_CODE,FROM_DATE,END_DATE);
		JSONObject jsonobj = new JSONObject(result);
		logger.info("OddHourTransactionController====>searchOddHourTransaction===>JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);
		logger.info("OddHourTransactionController====>searchOddHourTransaction===>Result>>>>>>>>>>>>>>>>>>>>> " + result.get("data"));

		model.addAttribute("oddhourtransactionsdetails", result.get("data"));
				
	    return result.get("data");
	}
}