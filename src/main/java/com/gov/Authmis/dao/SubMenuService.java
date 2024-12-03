package com.gov.Authmis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.transaction.annotation.Transactional;
import com.gov.Authmis.entity.AddSubMenuUpdateEntity;
import com.gov.Authmis.jpa.AddSubMenuUpdateRepository;
import com.gov.Authmis.model.Menu;
import com.gov.Authmis.model.ResultSetToListConverter;


@Service
public class SubMenuService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AddSubMenuUpdateRepository addSubMenuUpdateRepository;

	
	@Transactional
	public Object save(Menu menu, String ssoid) throws ParseException {
	    System.out.println("menu id==================" + menu.getMENUID());
	    System.out.println("menu id==================" + menu.getRoles());
	    Object s = null;

	    // Insert a new record into TBLMENUMASTER
	    String insertSubMenuQuery = "INSERT INTO AADHAAR.tblSubMenuMaster(SUBMENUNAME,SUBMENUDESC,URL_REPO,PRIORITY_TYPE,MENUID,STATUS,CREATIONDATE,CREATIONBY,ISNEWTAB) "
	            + " VALUES (:J, :K, :L, :M, :N, :O, :P,:Q,:R) ";

	    s = entityManager.createNativeQuery(insertSubMenuQuery)
	            .setParameter("J", menu.getSUBMENUNAME())
	            .setParameter("K", menu.getSUBMENUDESC())
	            .setParameter("L", menu.getURL_REPO_submenu())
	            .setParameter("M", 1)
	            .setParameter("N", menu.getMENUID()) // Use the retrieved menuid
	            .setParameter("O", 1)
	            .setParameter("P", java.time.LocalDate.now())
	            .setParameter("Q", ssoid)
	            .setParameter("R", 1)
	            .executeUpdate();

	    if (menu.getRoles() != null) {
	        String[] rolesArray = menu.getRoles().split(","); // Split the comma-separated roles into an array

	        for (String role : rolesArray) {
	            // Insert each role individually
	        	
	        	 BigDecimal generatedMenuId2 = (BigDecimal) entityManager.createNativeQuery("SELECT my_sequence_submenu.currval FROM dual").getSingleResult();
	     	    String sql11 = " select submenuid from AADHAAR.tblSubMenuMaster where submenuid_seq= " + generatedMenuId2;
	     	    Query query111 = entityManager.createNativeQuery(sql11);

	     	    @SuppressWarnings("unchecked")
	     	    java.util.List<String> role_id5 = query111.getResultList();
	     	    String str7 = role_id5.toString().replaceAll("[\\[\\]]", "");
	     	    // Print the retrieved menuid
	     	    System.out.println("Generated menuid: " + generatedMenuId2 + " " + str7);
	            String insertRoleQuery = "INSERT INTO AADHAAR.user_role_module(ROLE_ID, MENUID, SUBMENUID, UPDATED_DATE, UPDATED_BY, STATUS) "
	                    + " VALUES (:S, :N, :M, :P, :Q, :R) ";

	            entityManager.createNativeQuery(insertRoleQuery)
	                    .setParameter("S", role.trim()) // Trim to remove leading/trailing spaces
	                    .setParameter("N", menu.getMENUID())
	                    .setParameter("M", str7)
	                    .setParameter("P", java.time.LocalDate.now())
	                    .setParameter("Q", ssoid)
	                    .setParameter("R", 1)
	                    .executeUpdate();
	        }
	    }

	    return s;
	}


	public HashMap<String, Object> getAllSubMenu() {
		
		HashMap<String, Object> getData = new HashMap<>();
		try {
			String sql = "";			
			//sql = "SELECT SUBMENUID, SUBMENUNAME, SUBMENUDESC, URL_REPO, PRIORITY_TYPE,MENUID,STATUS,CREATIONDATE,CREATIONBY,ISNEWTAB,SUBMENUID_VAR,ISVISIABLE,SUBMENUID_SEQ FROM AADHAAR.TBLSUBMENUMASTER";
			sql = "SELECT b.MENUNAME,a.SUBMENUID, a.SUBMENUNAME, a.SUBMENUDESC, a.URL_REPO, a.PRIORITY_TYPE,a.MENUID,a.STATUS,a.CREATIONDATE,a.CREATIONBY,a.ISNEWTAB,a.SUBMENUID_VAR,a.ISVISIABLE,a.SUBMENUID_SEQ "
					+ "FROM AADHAAR.TBLSUBMENUMASTER a join  aadhaar.tblMenuMaster b on a.menuid = b.menuid";
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
				map.put("MENUNAME", array[0]);
				map.put("SUBMENUID", array[1]);
				map.put("SUBMENUNAME", array[2]);
				map.put("SUBMENUDESC", array[3]);
				map.put("URL_REPO", array[4]);
				map.put("PRIORITY_TYPE", array[5]);
				map.put("MENUID", array[6]);
				map.put("STATUS", array[7]);	
				map.put("CREATIONDATE", array[8]);	
				map.put("CREATIONBY", array[9]);	
				map.put("ISNEWTAB", array[10]);
				map.put("SUBMENUID_VAR", array[11]);	
				map.put("ISVISIABLE", array[12]);
				map.put("SUBMENUID_SEQ", array[13]);	
				
				mapList.add(map);
			}
			if (mapList.size() > 0) {
				getData.put("details", mapList);
				System.out.println("");
				getData.put("dataIsNull", "false");
			} else {
				getData.put("dataIsNull", "true\t");
			}
			System.out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return getData;		
	}


	public AddSubMenuUpdateEntity findBySUBMENUID(Long SUBMENUID) {
		
		return addSubMenuUpdateRepository.findById(SUBMENUID).get();
	}


	public AddSubMenuUpdateEntity save(AddSubMenuUpdateEntity addSubMenuUpdate) {
		// TODO Auto-generated method stub
		return addSubMenuUpdateRepository.save(addSubMenuUpdate);
	}


	public List<Map<String, Object>> getMenuNames() {
		StoredProcedureQuery query3 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_MENU_AND_NAME")
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs3 = (ResultSet) query3.getOutputParameterValue("prc");
		List<Map<String, Object>> subauaList1 = ResultSetToListConverter.getListFromResultSet(rs3);
		return subauaList1;
		
		
	}


	public List<Map<String, Object>> getRoles() {
		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_ROLES_AND_NAME")
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs2 = (ResultSet) query1.getOutputParameterValue("prc");
		List<Map<String, Object>> roles = ResultSetToListConverter.getListFromResultSet(rs2);
		return roles;
	}


	public List<Map<String, Object>> findRoleByMenuAndSubmenuId(Long menuid, Long submenuid) {
	/**	String sql = "SELECT ROLE_ID, STATUS from aadhaar.user_role_module where menuid=" + menuid + " and submenuid= " + submenuid + " ";
		Query query = this.entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> data = query.getResultList();
		System.out.println("roles details=========>>>>" + data.toString());
		return data; **/
		
		String sql = "SELECT ROLE_ID, STATUS FROM aadhaar.user_role_module WHERE menuid = :menuid AND submenuid = :submenuid";
		Query query = this.entityManager.createNativeQuery(sql);
		query.setParameter("menuid", menuid);
		query.setParameter("submenuid", submenuid);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		List<Map<String, Object>> data = new ArrayList<>();

		for (Object[] result : resultList) {
		    Map<String, Object> row = new HashMap<>();
		    row.put("ROLE_ID", result[0]);
		    row.put("STATUS", result[1]);
		    data.add(row);
		}

		System.out.println("roles details=========>>>>" + data.toString());
		return data;

		
	}

	@Transactional
	public void saveroles(Long menuid, Long submenuid, List<String> roles) {
	
		
		String sql1 = "UPDATE user_role_module SET status = 0 WHERE menuid = :menuid AND submenuid = :submenuid";	    
	    Query query1 = this.entityManager.createNativeQuery(sql1);	  
	    query1.setParameter("menuid", menuid);
	    query1.setParameter("submenuid", submenuid);
	    int updatedRows1 = query1.executeUpdate();	    
	    // Optional: Print the number of updated rows
	    System.out.println("Updated rows: " + updatedRows1);
			
	    
	    if(roles != null) {
		String sql2 = "UPDATE user_role_module SET status = 1 WHERE role_id IN (:roles) AND menuid = :menuid AND submenuid = :submenuid";	    
	    Query query2 = this.entityManager.createNativeQuery(sql2);
	    query2.setParameter("roles", roles);
	    query2.setParameter("menuid", menuid);
	    query2.setParameter("submenuid", submenuid);
	    int updatedRows2 = query2.executeUpdate();	    
	    // Optional: Print the number of updated rows
	    System.out.println("Updated rows: " + updatedRows2);
	    
	    }		
	}

}
