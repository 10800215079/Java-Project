package com.gov.Authmis.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.MenuBindingService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class MenuBindingController {
	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	MenuBindingService menuBindingService;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@GetMapping("/menu_binding_root")
	public String menubindingroot(HttpServletRequest request, Model model, @ModelAttribute("menubinding") ModuleMaster moduleMaster) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<Map<String, Object>> subAuaList = this.menuBindingService.GetRoles();
		model.addAttribute("subAuaList", subAuaList);
		model.addAttribute("menubinding", new ModuleMaster());
		return "menu_binding_root";

	}

	@GetMapping("/menubinding")
	public String menubinding(Model model, @ModelAttribute("menubinding") ModuleMaster moduleMaster,
			HttpSession session, HttpServletRequest request) {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		try {
			
			model.addAttribute("role_id", moduleMaster.getRole_id());
			List<Map<String, Object>> subAuaList = this.menuBindingService.GetRoles();

			model.addAttribute("subAuaList", subAuaList);
			

			List<ModuleMaster> status1 = new ArrayList<>();
			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.User_role_module_status")
					.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("p_role_id", moduleMaster.getRole_id());
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("P_RECORDSET");

			while (rs.next()) {
				ModuleMaster listResult = new ModuleMaster();
				listResult.setSub_menu_id(rs.getString(1));
				listResult.setStatus(rs.getString(2));
				status1.add(listResult);
			}
			model.addAttribute("status1", status1);	
			List<ModuleMaster> role_id2 = menuBindingService.Getmenubindingservice(moduleMaster);
			model.addAttribute("listofmainmenu1", role_id2);
			String getid = moduleMaster.getRole_id();
			HttpSession setid = request.getSession();

			if ("1".equals(getid)) {
				setid.setAttribute("role_id_ja", 1);
			} else if ("2".equals(getid)) {
				setid.setAttribute("role_id_ja", 2);
			} else if ("3".equals(getid)) {
				setid.setAttribute("role_id_ja", 3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/menu_binding_root";
	}

	
	@RequestMapping("/getsubmenuname1")
	public @ResponseBody Object submenuname1(@RequestParam("menu_id") String menu_id,@RequestParam("role_id") String role_id, HttpServletRequest request,
			Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
	

		List<String> data = new ArrayList<>();
		List<String> data1 = new ArrayList<>();
		StoredProcedureQuery sql = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_SUBMENU_ACCESS")
				.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("p_menuid", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
		sql.setParameter("p_role_id", role_id).setParameter("p_menuid", menu_id);
		sql.execute();

		ResultSet DetailsObj = (ResultSet) sql.getOutputParameterValue("P_RECORDSET");
		ModuleMaster listResult = new ModuleMaster();
		

		Map<String, Map<String, String>> submenuMap = new LinkedHashMap<>(); // UseLinkedHashMap to maintain insertion
																				// // order
		while (DetailsObj.next()) {
			String submenuid = DetailsObj.getString(1);
			String submenuName = DetailsObj.getString(2);
			String status = DetailsObj.getString(5);
			Map<String, String> submenuDetails = new LinkedHashMap<>();
			submenuDetails.put("id", submenuid);
			submenuDetails.put("name", submenuName);
			submenuDetails.put("url", DetailsObj.getString(3));
			submenuDetails.put("status", status);
			submenuMap.put(submenuid, submenuDetails);
		}

	
		model.addAttribute("listofsubmenu", submenuMap);
		
		System.out.println(submenuMap + "hhjhjjjgfhjdfhjdhjgdgjgj");
	

		return submenuMap;

	}

	@RequestMapping(path = { "/updatestaus" }, method = RequestMethod.POST)
	public String updateTutorialOfSubAua(@RequestParam("roleId") Long roleId, 
			@RequestParam("subMenuId") Long subMenuId,@RequestParam("status") boolean status, @ModelAttribute("menubinding") ModuleMaster menubinding,
			Model m, BindingResult bb, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		menubinding.setStatus(menubinding.getStatus());
		Object dd = menuBindingService.updateStatus(roleId, subMenuId, status, ssoid1);
		m.addAttribute("updateStatus", dd);
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "redirect:/menu_binding_root";
	}

}
