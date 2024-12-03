package com.gov.Authmis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gov.Authmis.controller.AddLicencyForSubAuaSystemController;
import com.gov.Authmis.entity.MenuMaster;
import com.gov.Authmis.entity.SubMenu;
import com.gov.Authmis.service.MenuAndSubMenuService;

@Service
public class MenuAndSubMenuServiceImpl implements MenuAndSubMenuService{

	static Logger logger = LoggerFactory.getLogger(AddLicencyForSubAuaSystemController.class);
	
	
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubMenu> getSubMenu(HttpServletRequest request) {
		
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		String queryString = "SELECT role_id FROM AADHAAR.SSO_USER_ROLES_MAPPING WHERE lower(sso_id) = lower(:ssoId) AND status = 1";
		Query query = entityManager.createNativeQuery(queryString)
		        .setParameter("ssoId", ssoId);

		 Object roleIdObject = null;
		    try {
		        roleIdObject = query.getSingleResult();
		    } catch (NoResultException e) {
		        logger.warn("No result found for the query: " + queryString, e);
		    }

		    // Check the actual type of roleIdObject and convert accordingly
		    Integer roleId = null;
		    if (roleIdObject instanceof Number) {
		        roleId = ((Number) roleIdObject).intValue();
		    } else if (roleIdObject instanceof String) {
		        try {
		            roleId = Integer.parseInt((String) roleIdObject);
		        } catch (NumberFormatException ex) {
		            // Handle the case where the String is not a valid integer
		            logger.warn("Invalid integer format for role_id: " + roleIdObject, ex);
		        }
		    }
		    String quryStr = "select distinct tbs.submenuid, tbs.submenuname,tbm.menuid , tbs.url_repo,urm.status from AADHAAR.tblMenuMaster tbm  join  AADHAAR.User_role_module urm on "
		    		+ " tbm.menuid=urm.menuid inner join AADHAAR.tblSubMenuMaster tbs "
		    		+ " on tbs.submenuid=urm.submenuid and tbs.menuid=tbm.menuid where urm.role_id="+roleId+" AND tbs.STATUS  =1 AND urm.STATUS =1 order by tbs.submenuname asc";
		    Query qry = entityManager.createNativeQuery(quryStr, SubMenu.class);
		    List<SubMenu> subMenuMaster = qry.getResultList();
		    //return getSubMenuRepository.findAllByStatus(1);
		    return subMenuMaster;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuMaster> getMenu(HttpServletRequest request) {
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		String queryString = "SELECT role_id FROM AADHAAR.SSO_USER_ROLES_MAPPING WHERE lower(sso_id) = lower(:ssoId) AND status = 1";
		Query query = entityManager.createNativeQuery(queryString)
		        .setParameter("ssoId", ssoId);

		 Object roleIdObject = null;
		    try {
		        roleIdObject = query.getSingleResult();
		    } catch (NoResultException e) {
		        logger.warn("No result found for the query: " + queryString, e);
		    }

		    // Check the actual type of roleIdObject and convert accordingly
		    Integer roleId = null;
		    if (roleIdObject instanceof Number) {
		        roleId = ((Number) roleIdObject).intValue();
		    } else if (roleIdObject instanceof String) {
		        try {
		            roleId = Integer.parseInt((String) roleIdObject);
		        } catch (NumberFormatException ex) {
		            // Handle the case where the String is not a valid integer
		            logger.warn("Invalid integer format for role_id: " + roleIdObject, ex);
		        }
		    }
		    
//		    String q = "select DISTINCT tbm.MENUID menuId,tbm.MENUNAME menuName from aadhaar.tblMenuMaster tbm  "
//		    		+ " left join  aadhaar.User_role_module urm on  tbm.menuid=urm.menuid "
//		    		+ "where urm.role_id  = "+roleId+" and urm.status=1 order by tbm.menuname ";
		    String quryStr = "select DISTINCT tbm.MENUID ,tbm.MENUNAME, tbm.MENUDESC  from aadhaar.tblMenuMaster tbm  "
		    		+ " Left join  aadhaar.User_role_module urm on  tbm.menuid=urm.menuid  "
		    		+ " where urm.role_id  = "+roleId+" and urm.status=1 order by tbm.menuname ";
		    		Query qry = entityManager.createNativeQuery(quryStr, MenuMaster.class);
					List<MenuMaster> menuMaster = qry.getResultList();	
					return menuMaster;
			}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubMenu> getSubMenuByRoleId(Long  roleId) {

		    String quryStr = "select distinct tbs.submenuid, tbs.submenuname,tbm.menuid , tbs.url_repo,urm.status from AADHAAR.tblMenuMaster tbm  join  AADHAAR.User_role_module urm on "
		    		+ " tbm.menuid=urm.menuid inner join AADHAAR.tblSubMenuMaster tbs "
		    		+ " on tbs.submenuid=urm.submenuid and tbs.menuid=tbm.menuid where urm.role_id="+roleId+" AND tbs.STATUS  =1  order by tbs.submenuname asc";
		    Query qry = entityManager.createNativeQuery(quryStr, SubMenu.class);
		    
			List<SubMenu> subMenuMaster = qry.getResultList();
		    //return getSubMenuRepository.findAllByStatus(1);
		    return subMenuMaster;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuMaster> getMenuByRole(Long roleId) {

		    String quryStr = "select DISTINCT tbm.MENUID ,tbm.MENUNAME, tbm.MENUDESC  from aadhaar.tblMenuMaster tbm  "
		    		+ " Left join  aadhaar.User_role_module urm on  tbm.menuid=urm.menuid  "
		    		+ " where urm.role_id  = "+roleId+"  order by tbm.menuname ";
		    		Query qry = entityManager.createNativeQuery(quryStr, MenuMaster.class);
					List<MenuMaster> menuMaster = qry.getResultList();	
					return menuMaster;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Boolean hasRole(String roleId, String urlRepo) {
				// Updated query string
				String queryStr = "SELECT 1 FROM aadhaar.TBLSUBMENUMASTER t "
						+ "INNER JOIN aadhaar.USER_ROLE_MODULE urm ON t.SUBMENUID = urm.SUBMENUID "
						+ "WHERE urm.ROLE_ID = :roleId AND t.URL_REPO = :urlRepo AND urm.STATUS =1 AND t.STATUS  =1";

				// Create query
				Query query = entityManager.createNativeQuery(queryStr);
				query.setParameter("roleId", roleId);
				query.setParameter("urlRepo", urlRepo);

				// Execute query
				List<Object> result = query.getResultList();

				// Return true if result is not empty, otherwise false
				return !result.isEmpty();
			}
}
