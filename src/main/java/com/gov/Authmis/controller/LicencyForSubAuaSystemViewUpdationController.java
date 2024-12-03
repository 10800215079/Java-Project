package com.gov.Authmis.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.dao.SubAuaWiseSendMail;
import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.service.AddLicencyForSubAuaSystemService;
import com.gov.Authmis.service.LicencyForSubAuaSystemViewUpdationService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;
import com.itextpdf.text.DocumentException;

@Controller
public class LicencyForSubAuaSystemViewUpdationController {
	@Autowired
	private LicencyForSubAuaSystemViewUpdationService service;
	
	@Autowired
	private AddLicencyForSubAuaSystemService addLicencyForSubAuaSystemservice;
	
	@Autowired
	SubauaWiseTransactionService transactionService;
	
	@Autowired
	public SubAuaWiseSendMail subAuaWiseSendMail;
	
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@PersistenceContext
	private EntityManager entityManager;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(LicencyForSubAuaSystemViewUpdationController.class);
	@GetMapping("/LicencyForSubAuaSystemViewUpdation")
	public String viewData(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws SQLException {	
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("LicencyForSubAuaSystemViewUpdation", new AddLicencyForSubAuaSystem());
		List<AddLicencyForSubAuaSystem> details = service.findAll();
		model.addAttribute("details", details);
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "LicencyForSubAuaSystemViewUpdation";
	}

	@GetMapping("/edit/{srno}")
	public String viewdata(Model model, @PathVariable("srno") Long srno,HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("details", service.findById(srno));
		return "AddLicencyForSubAuaSystemEdit";
	}

	@RequestMapping(path = "/{srno}", method = RequestMethod.GET)
	public String updateTutorial(@PathVariable("srno") Long srno,
			@ModelAttribute("details") AddLicencyForSubAuaSystem tutorial, Model model, BindingResult bb,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws SQLException, DocumentException, IOException, ParseException {
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		
		logger.info(
				"LicencyForSubAuaSystemViewUpdationController==>Inside the method2");
		AddLicencyForSubAuaSystem tutorialData = service.findById(srno);
		String oldSubauauaLk = tutorialData.getSubAuaLk();
		if(!tutorialData.getSubAuaLk().equalsIgnoreCase(tutorial.getSubAuaLk())) {
			String response = addLicencyForSubAuaSystemservice.addUserForGenricFaceAuth(tutorial.getSubAuaCode(),tutorial.getSubAuaName(), 
					tutorial.getSubAuaLk(), Long.parseLong(tutorial.getStatus()));
			
			JSONObject jsonResponse = new JSONObject(response);
			boolean isSuccess = jsonResponse.getBoolean("IsSuccess");
			if(!isSuccess) {
				redirectAttributes.addFlashAttribute("error", "Internal Server Error.");
				  return "redirect:/LicencyForSubAuaSystemViewUpdation";
		  }
		}
		tutorialData.setKuaLk(tutorial.getKuaLk());
		tutorialData.setKuaCode(tutorial.getKuaCode());
		tutorialData.setSubAuaLk(tutorial.getSubAuaLk());
		tutorialData.setKukLkCode(tutorial.getKuaLk() + "----" + tutorial.getKuaCode());
		tutorialData.setExpiryDate(tutorial.getExpiryDate());
		tutorialData.setStatus(tutorial.getStatus());
		
		
		AddLicencyForSubAuaSystem dd = service.save(tutorialData);
		/*
		 * if(!oldSubauauaLk.equals(tutorialData.getSubAuaLk())) { List<Map<String,
		 * Object>> listOfData =
		 * addLicencyForSubAuaSystemservice.getSubAuaData(tutorialData.getSubAuaCode());
		 * subAuaWiseSendMail.SendEmailToSubAua(listOfData,
		 * tutorialData.getSubAuaCode(), tutorialData.getSubAuaLk(),
		 * tutorialData.getExpiryDate());
		 * 
		 * }
		 */
		
		if(dd != null) {
		model.addAttribute("tutorials", dd);
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid1);
		model.addAttribute("subAuaList", subAuaList);
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "redirect:/LicencyForSubAuaSystemViewUpdation";
	} else {
		redirectAttributes.addFlashAttribute("error", "Internal Server Error.");
		return "redirect:/LicencyForSubAuaSystemViewUpdation";
	}
	  
	}
}
