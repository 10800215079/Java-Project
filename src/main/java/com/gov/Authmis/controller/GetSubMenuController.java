package com.gov.Authmis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gov.Authmis.model.SubMenuAndMenumaster;
import com.gov.Authmis.service.MenuAndSubMenuService;
import com.gov.Authmis.entity.MenuMaster;
import com.gov.Authmis.entity.SubMenu;

@RestController
@RequestMapping("/GetSubMenu")
public class GetSubMenuController {

	@Autowired
	private MenuAndSubMenuService service;
	
	@GetMapping("/")
	 public List<SubMenuAndMenumaster>  getSubMenuByRoleId(HttpServletRequest request) {
		
		List<SubMenu> subMenuList = service.getSubMenu(request);
		List<MenuMaster> menuMasterList = service.getMenu(request);

		List<SubMenuAndMenumaster> subMenuAndMenumasterList = new ArrayList<SubMenuAndMenumaster>();
		
		for (SubMenu subMenu : subMenuList) {
		    SubMenuAndMenumaster subMenuAndMenumaster = new SubMenuAndMenumaster();
		    subMenuAndMenumaster.setSubMenu(subMenu);

		    // Add a corresponding MenuMaster if available
		    if (!menuMasterList.isEmpty()) {
		        subMenuAndMenumaster.setMenuMaster(menuMasterList.remove(0));
		    }

		    subMenuAndMenumasterList.add(subMenuAndMenumaster);
		}

		// If there are remaining MenuMaster objects, create SubMenuAndMenumaster objects for them
		for (MenuMaster menuMaster : menuMasterList) {
		    SubMenuAndMenumaster subMenuAndMenumaster = new SubMenuAndMenumaster();
		    subMenuAndMenumaster.setMenuMaster(menuMaster);
		    subMenuAndMenumasterList.add(subMenuAndMenumaster);
		}

		
		return subMenuAndMenumasterList;	  
	 }	
	
	@GetMapping("/getMenuandSubmenu")
	 public List<SubMenuAndMenumaster>  getSubMenuForRoleMapping(@RequestParam Long roleId,  HttpServletRequest request) {
		List<SubMenu> subMenuList = service.getSubMenuByRoleId(roleId);
		List<MenuMaster> menuMasterList = service.getMenuByRole(roleId);

		List<SubMenuAndMenumaster> subMenuAndMenumasterList = new ArrayList<SubMenuAndMenumaster>();
		
		for (SubMenu subMenu : subMenuList) {
		    SubMenuAndMenumaster subMenuAndMenumaster = new SubMenuAndMenumaster();
		    subMenuAndMenumaster.setSubMenu(subMenu);

		    // Add a corresponding MenuMaster if available
		    if (!menuMasterList.isEmpty()) {
		        subMenuAndMenumaster.setMenuMaster(menuMasterList.remove(0));
		    }

		    subMenuAndMenumasterList.add(subMenuAndMenumaster);
		}

		// If there are remaining MenuMaster objects, create SubMenuAndMenumaster objects for them
		for (MenuMaster menuMaster : menuMasterList) {
		    SubMenuAndMenumaster subMenuAndMenumaster = new SubMenuAndMenumaster();
		    subMenuAndMenumaster.setMenuMaster(menuMaster);
		    subMenuAndMenumasterList.add(subMenuAndMenumaster);
		}

		
		return subMenuAndMenumasterList;	  
	 }	

}