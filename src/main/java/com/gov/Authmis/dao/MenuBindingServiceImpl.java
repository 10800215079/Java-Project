package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gov.Authmis.model.AverageResponseTimeModel;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.MenuBindingService;

@Service
public class MenuBindingServiceImpl implements MenuBindingService {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<ModuleMaster> Getmenubindingservice(ModuleMaster moduleMaster) throws Exception {
		Model m = null;
		List<ModuleMaster> data = new ArrayList<>();
		try {
		
			System.out.println(moduleMaster.getRole_id()+"role_id with selected data");

			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MAINMENU_ACCESS")
					.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("p_role_id", moduleMaster.getRole_id());
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("P_RECORDSET");

			while (rs.next()) {
				ModuleMaster listResult = new ModuleMaster();
				listResult.setMenu_id(rs.getString(1));
				listResult.setMenu_str(rs.getString(2));
				data.add(listResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;

	}

	@Override

	public List<Map<String, Object>> GetSubauaCodeName1() {

		StoredProcedureQuery query1 = this.entityManager
				.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")

				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);

		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("prc");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);

		System.out.println(subauaList + "subauaList=================");
		return subauaList;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> GetRoles() {
		// TODO Auto-generated method stub

		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_ROLES_AND_NAME")

				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);

		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("prc");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);

		return subauaList;
	}

	@Override
	@Transactional
	public Object updateStatus(Long roleId, Long subMenuId, boolean status, String ssoid) {

		Object s = null;
		if (status) {
			String sql = "update aadhaar.User_role_module set  status=1,updated_by='" + ssoid + "'  where role_id=" + roleId
					+ " and submenuid=" + subMenuId + " ";
			s = entityManager.createNativeQuery(sql).executeUpdate();
		} else {

			String sql = "update aadhaar.User_role_module set  status=0,updated_by='" + ssoid + "'  where role_id=" + roleId
					+ " and submenuid=" + subMenuId + " ";
			s = entityManager.createNativeQuery(sql).executeUpdate();
		}
		return s;
	}
}
