package com.gov.Authmis.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.MappingOfSubAuaService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class MappingOfSubAua {

	@Autowired
	private MappingOfSubAuaService subauaadminservice;

	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@RequestMapping({ "/mappingOfSubAua" })
	public String subauaadminpage(HttpServletRequest request, Model model,
			@ModelAttribute("UsersRoleMapping") UsersRoleMapping subAuaAdmin, RedirectAttributes redirectAttributes) throws SQLException, ParseException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("srno", subAuaAdmin.getSrno());
		model.addAttribute("UsersRoleMapping", new UsersRoleMapping());
		List<UsersRoleMapping> details = subauaadminservice.findAll();
		model.addAttribute("details", details);
		
		

		return "mappingOfSubAua";

	}
	
	
	
	@RequestMapping(path = { "/getsubauaCode" }, method = RequestMethod.GET)
	public String updateTutorialOfSubAua(@RequestParam("srno") int srno, @RequestParam("status") int status,
			@ModelAttribute("UsersRoleMapping") UsersRoleMapping usersRoleMapping, Model model, BindingResult bb, RedirectAttributes redirectAttributes,HttpServletRequest request) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");

		
		System.out.println("ssoid ------------------------------------"+ssoid1);
		usersRoleMapping.setStatus(usersRoleMapping.getStatus());
		
		Object dd = subauaadminservice.getSubauaCode(srno,status, ssoid1);
		model.addAttribute("subauaLicenseKeydata", dd);
	
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "redirect:/subauaLicenseKeydata";
	}

}
