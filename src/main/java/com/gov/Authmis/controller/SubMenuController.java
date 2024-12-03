package com.gov.Authmis.controller;

import java.rmi.ServerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gov.Authmis.dao.SubMenuService;
import com.gov.Authmis.entity.AddSubMenuUpdateEntity;
import com.gov.Authmis.model.Menu;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class SubMenuController {
	@Autowired
	private SubMenuService menuService;
	SSOLoginController ssoLoginController = new SSOLoginController();

	Logger logger = LoggerFactory.getLogger(MenuController.class);
	@PersistenceContext
	private EntityManager entityManager;
	Menu subMenu = new Menu();
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	
	
	@GetMapping({ "/SubMenu" })
	public String getAllSubmenu(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		logger.info("SubMenuController==> Sub Menu  ");
		//for role		
		List<Map<String, Object>> roles = menuService.getRoles();	
		model.addAttribute("subAuaList", roles);	
		//for menu
		List<Map<String, Object>> menulist = menuService.getMenuNames();		
		model.addAttribute("subAuaList1", menulist);
		model.addAttribute("subMenu", subMenu);
		
		/** get all data in table **/
		HashMap<String, Object> result = new HashMap<>();
		result = menuService.getAllSubMenu();
		model.addAttribute("details", result.get("details"));

		return "SubMenu";
	}

	@PostMapping("/addSubMenu")
	public String addMenu(@ModelAttribute("subMenu") Menu subMenu, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) throws ParseException, ServerException, SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		redirectAttributes.addFlashAttribute("success", " Submenu's Inserted Successfully ");
		Object menus = menuService.save(subMenu, ssoid);
		if (menus == null) {
			redirectAttributes.addFlashAttribute("error", "Inserted Failure.");
			throw new ServerException(null, null);
		} else {
			return "redirect:/SubMenu";
		}
	}
	
	
	
	@GetMapping("/editById/{SUBMENUID}")
	public String viewdataByMenuId(@PathVariable("SUBMENUID") Long SUBMENUID,HttpServletRequest request, Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId  = (String) request.getSession().getAttribute("SSOID");
		
		//for role		
		List<Map<String, Object>> roles = menuService.getRoles();	
		model.addAttribute("subAuaList", roles);
		System.out.println("roles======>>>>>" + roles.toString());
		
		//for sub menu
		List<Map<String, Object>> menulist = menuService.getMenuNames();		
		model.addAttribute("subAuaList1", menulist);
				
		AddSubMenuUpdateEntity addSubMenuUpdateEntity = menuService.findBySUBMENUID(SUBMENUID);
		model.addAttribute("menudata", addSubMenuUpdateEntity);
		
		Long menuid= addSubMenuUpdateEntity.getMENUID();
		Long submenuid= addSubMenuUpdateEntity.getSUBMENUID();
		
		//findRoles
		List<Map<String, Object>> roleslist = menuService.findRoleByMenuAndSubmenuId(menuid,submenuid);
		System.out.println("roles======>>>>>" + roleslist.toString());
					
		// Combine roles and roleslist into a new list
		List<Map<String, Object>> combinedRoles = new ArrayList<>();

		for (Map<String, Object> role : roles) {
		    Map<String, Object> combinedRole = new HashMap<>();
		    combinedRole.put("ID", role.get("ID"));
		    combinedRole.put("NAME", role.get("NAME"));

		    // Check if the role is present in roleslist and get its status
		    for (Map<String, Object> roleStatus : roleslist) {
		        if (role.get("ID").equals(roleStatus.get("ROLE_ID"))) {
		            combinedRole.put("STATUS", roleStatus.get("STATUS"));
		            break;
		        }
		    }
		    // If the role is not present in roleslist, set a default status (e.g., 0)
		    combinedRole.putIfAbsent("STATUS", 0);
		    combinedRoles.add(combinedRole);
		}

		model.addAttribute("combinedRoles", combinedRoles);
		
		//model.addAttribute("subAuaList", roleslist);				
		return "addsubmenuupdatedetails";
	}

	
	@RequestMapping(path = { "/updateaddsubmenu/{SUBMENUID}" }, method = RequestMethod.GET)
	public String updateDataByMenuId(@PathVariable("SUBMENUID") Long SUBMENUID,
			@ModelAttribute("menudata") AddSubMenuUpdateEntity addSubMenuUpdateEntity,
			@RequestParam(name = "roles", required = false) List<String> roles, 						
		    Model model,
			RedirectAttributes redirectAttributes,HttpServletRequest request) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}		
		//for role		
		List<Map<String, Object>> roless = menuService.getRoles();	
		model.addAttribute("subAuaList", roless);		
		List<Map<String, Object>> menulist = menuService.getMenuNames();
		model.addAttribute("subAuaList1", menulist);		
		String ssoid = (String) request.getSession().getAttribute("SSOID");	
		
		AddSubMenuUpdateEntity addSubMenuUpdate = menuService.findBySUBMENUID(SUBMENUID);
		
		addSubMenuUpdate.setSUBMENUNAME(addSubMenuUpdateEntity.getSUBMENUNAME());
		addSubMenuUpdate.setSUBMENUDESC(addSubMenuUpdateEntity.getSUBMENUDESC());
		addSubMenuUpdate.setCREATIONBY(addSubMenuUpdateEntity.getCREATIONBY());		
		addSubMenuUpdate.setURL_REPO(addSubMenuUpdateEntity.getURL_REPO());
		addSubMenuUpdate.setSTATUS(addSubMenuUpdateEntity.getSTATUS());
		addSubMenuUpdate.setMENUID(addSubMenuUpdateEntity.getMENUID());
		addSubMenuUpdate.setSUBMENUID(addSubMenuUpdateEntity.getSUBMENUID());
		//addMenuUpdate.setISNEWTAB(addMenuUpdateEntity.getISNEWTAB());
		//addMenuUpdate.setMTYPID(addMenuUpdateEntity.getMTYPID());
		//addMenuUpdate.setPRIORITY_TYPE(addMenuUpdateEntity.getPRIORITY_TYPE());

        LocalDateTime dateTime = LocalDateTime.parse(addSubMenuUpdateEntity.getCREATIONDATE(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yy").withLocale(java.util.Locale.ENGLISH));
    
        addSubMenuUpdate.setCREATIONDATE(formattedDate);				
        AddSubMenuUpdateEntity updatedData = menuService.save(addSubMenuUpdate);
        
       
        menuService.saveroles(addSubMenuUpdateEntity.getMENUID(),addSubMenuUpdateEntity.getSUBMENUID(),roles);
        
             
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "redirect:/SubMenu";
	}

}
