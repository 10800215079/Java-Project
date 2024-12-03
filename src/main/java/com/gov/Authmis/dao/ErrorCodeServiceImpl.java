package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.gov.Authmis.model.ErrorCodeModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SubAuaANDServiceWiseTransactionReportModel;
import com.gov.Authmis.model.SuspectedAadhaar;
import com.gov.Authmis.service.ErrorCodeService;

@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {
	@PersistenceContext
	private EntityManager em;

	

	public List<ErrorCodeModel> errorCodeService(ErrorCodeModel errorCodeModel,String ssoid1) throws Exception {
		 List<ErrorCodeModel> data = new ArrayList<>();
		try {
			String str="select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('"+ssoid1+"') and status=1";
			Query query1 = em.createNativeQuery(str);
			@SuppressWarnings("unchecked")
			List<String> role_id1 = query1.getResultList();
			
			String str1=role_id1.toString();
			
			String str2=str1.replaceAll("[\\[\\]]", "");
			System.out.println("FROM_DATE..." + str2);
			StoredProcedureQuery query = this.em.createStoredProcedureQuery("AADHAAR.Get_Error_Code_data")
					.registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("ROLE_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("SSO_ID", ssoid1).setParameter("ROLE_ID", str2).setParameter("FROM_DATE", errorCodeModel.getFromdate()).setParameter("End_DATE",
					errorCodeModel.getEnddate());

			query.setHint("hibernate.proc.param_null_passing.FROM_DATE", "true");
			query.setHint("hibernate.proc.param_null_passing.End_DATE", "true");
			query.execute();
			ResultSet DetailsObj = (ResultSet) query.getOutputParameterValue("prc");
			while (DetailsObj.next()) {
				ErrorCodeModel listResult = new ErrorCodeModel();
				listResult.setError_code(DetailsObj.getString(1));
				listResult.setError_message(DetailsObj.getString(2));
				listResult.setError_count(DetailsObj.getString(3));
				listResult.setError_percent(DetailsObj.getString(4));

				data.add(listResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}
	

}
