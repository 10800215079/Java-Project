package com.gov.Authmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gov.Authmis.entity.MenuMaster;
import com.gov.Authmis.entity.SubMenu;

public interface MenuAndSubMenuService {

	List<MenuMaster> getMenu(HttpServletRequest request);

	List<SubMenu> getSubMenu(HttpServletRequest request);

	List<SubMenu> getSubMenuByRoleId(Long roleId);

	List<MenuMaster> getMenuByRole(Long roleId);

	Boolean hasRole(String roleId, String urlRepo);

}
