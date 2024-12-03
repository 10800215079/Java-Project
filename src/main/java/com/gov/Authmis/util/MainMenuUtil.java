package com.gov.Authmis.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;

@Service
public class MainMenuUtil {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public  Model getMainMenu(Model model,HttpServletRequest request) throws SQLException {
		
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");

		String str3 = "select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where lower(sso_id)=lower('" + ssoid1
				+ "') and status=1";
		Query query2 = entityManager.createNativeQuery(str3);

		@SuppressWarnings("unchecked")
		List<String> role_id3 = query2.getResultList();
		String str5 = role_id3.toString().replaceAll("[\\[\\]]", "");
		List<ModuleMaster> data = new ArrayList<>();
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("AADHAAR.GET_MAINMENU")
				.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
		query.setParameter("p_role_id", str5);
		query.execute();
		ResultSet rs = (ResultSet) query.getOutputParameterValue("P_RECORDSET");

		while (rs.next()) {
			ModuleMaster listResult = new ModuleMaster();
			listResult.setMenu_id(rs.getString(1));
			listResult.setMenu_str(rs.getString(2));
			data.add(listResult);
		}
		StoredProcedureQuery menuid = entityManager.createStoredProcedureQuery("AADHAAR.GET_MENU_ID")
				.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
		menuid.setParameter("p_role_id", str5);
		menuid.execute();
		ResultSet rs1 = (ResultSet) menuid.getOutputParameterValue("P_RECORDSET");
		List<Map<String, Object>> obj2 = ResultSetToListConverter.getListFromResultSet(rs1);
		List<Object> convertedList1 = new ArrayList<>();

		for (Map<String, Object> map : obj2) {
			for (Object value : map.values()) {
				convertedList1.add(value);
			}
		}
		String menuid6 = convertedList1.toString().replaceAll("[\\[\\]]", "");

		System.out.println(menuid6);
		model.addAttribute("menuid", menuid6);

		model.addAttribute("listofmainmenu", data);

		return model;
		
	}
}
