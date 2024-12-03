package com.gov.Authmis.controller;

import java.rmi.ServerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gov.Authmis.dao.MenuService;
import com.gov.Authmis.entity.AddMenuUpdateEntity;
import com.gov.Authmis.model.Menu;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(MenuController.class);
	Menu Menu = new Menu();
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@PersistenceContext
	private EntityManager entityManager;
	

	@GetMapping({ "/Menu" })
	public String getAllEmployees(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		logger.info("MenuController==> Menu  ");
		List<Map<String, Object>> subauaList = menuService.GetRoleAndName();
		model.addAttribute("subAuaList", subauaList);
		model.addAttribute("Menu", Menu);
		
		//get all data in table 
		HashMap<String, Object> result = new HashMap<>();
		result = menuService.getAllMainMenu();
		model.addAttribute("details", result.get("details"));
		
		return "Menu";
	}

	@PostMapping("/addMenu")
	public String addMenu(@ModelAttribute("Menu") Menu menu, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) throws ParseException, ServerException, SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
			
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		redirectAttributes.addFlashAttribute("success", "Menu's Inserted Successfully ");
		Object menus = menuService.save(menu, ssoid);
		if (menus == null) {
			redirectAttributes.addFlashAttribute("error", "Inserted Failure.");
			throw new ServerException(null, null);
		} else {
			return "redirect:/Menu";
		}
	}
	
	
	@GetMapping("/editBy/{MENUID}")
	public String viewdataByMenuId(@PathVariable("MENUID") Long MENUID,HttpServletRequest request, Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId  = (String) request.getSession().getAttribute("SSOID");
		
		AddMenuUpdateEntity addMenuUpdateEntity = menuService.findByMENUID(MENUID);
		model.addAttribute("menudata", addMenuUpdateEntity);
		
		return "addmenuupdatedetails";
	}

	
	@RequestMapping(path = { "/updateaddmenu/{MENUID}" }, method = RequestMethod.GET)
	public String updateDataByMenuId(@PathVariable("MENUID") Long MENUID,
			@ModelAttribute("menudata") AddMenuUpdateEntity addMenuUpdateEntity,Model model,
			RedirectAttributes redirectAttributes,HttpServletRequest request) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
	
		AddMenuUpdateEntity addMenuUpdate = menuService.findByMENUID(MENUID);
		addMenuUpdate.setMENUNAME(addMenuUpdateEntity.getMENUNAME());
		addMenuUpdate.setMENUDESC(addMenuUpdateEntity.getMENUDESC());
		addMenuUpdate.setCREATIONBY(addMenuUpdateEntity.getCREATIONBY());		
		addMenuUpdate.setURL_REPO(addMenuUpdateEntity.getURL_REPO());
		addMenuUpdate.setSTATUS(addMenuUpdateEntity.getSTATUS());
		//addMenuUpdate.setISNEWTAB(addMenuUpdateEntity.getISNEWTAB());
		//addMenuUpdate.setMTYPID(addMenuUpdateEntity.getMTYPID());
		//addMenuUpdate.setPRIORITY_TYPE(addMenuUpdateEntity.getPRIORITY_TYPE());

        LocalDateTime dateTime = LocalDateTime.parse(addMenuUpdateEntity.getCREATIONDATE(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yy").withLocale(java.util.Locale.ENGLISH));
    
		addMenuUpdate.setCREATIONDATE(formattedDate);
				
		AddMenuUpdateEntity updatedData = menuService.save(addMenuUpdate);
		
		redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
		return "redirect:/Menu";
	}

	
}
