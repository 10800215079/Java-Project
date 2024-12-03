package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import com.gov.Authmis.model.FluctuationReportModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.FluctuationReportService;

@Service
public class FlucationReportServiceImpl implements FluctuationReportService {
	@PersistenceContext
	private EntityManager entityManager;

	public HashMap<String, Object> GetFlucationReportData(FluctuationReportModel flucationReportModel, String ssoid1) {
		HashMap<String, Object> flucationData = new HashMap<>();
		try {
			String str="select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('"+ssoid1+"') and status=1";
			Query query1 = entityManager.createNativeQuery(str);
			@SuppressWarnings("unchecked")
			List<String> role_id1 = query1.getResultList();
			
			String str1=role_id1.toString();
			
			String str2=str1.replaceAll("[\\[\\]]", "");
			System.out.println("FROM_DATE..." + str2);
			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.GET_MIS_FLUCTUATION_REPORT")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("ROLE_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("FROM_DATE", flucationReportModel.getFromdate())
					.setParameter("End_DATE", flucationReportModel.getEnddate()).setParameter("SSO_ID", ssoid1).setParameter("ROLE_ID", str2);
			System.out.println("FROM_DATE..." + flucationReportModel.getFromdate());
			System.out.println("End_DATE..." + flucationReportModel.getEnddate());
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("P_RECORDSET");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("OBJ ......." + obj);
			if (obj.size() > 0) {
				flucationData.put("details", obj);
			} else {
				flucationData.put("dataNull", "true\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flucationData;
	}

}
