package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.gov.Authmis.model.OddHourTransaction;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.OddHourTransactionService;

@Service
public class OddHourTransactionServiceImpl implements OddHourTransactionService {
	@PersistenceContext
	private EntityManager entityManager;

	public HashMap<String, Object> GetResult(OddHourTransaction oddHourTransaction) {
		HashMap<String, Object> data = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.GetMis_ODDhour_Trans")
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			System.out.println("FROM_DATE--------------------------------- " + oddHourTransaction.getFromdate());
			System.out.println("FROM_DATE--------------------------------- " + oddHourTransaction.getEnddate());
			System.out.println("SUB_AUA_CODE--------------------------------- " + oddHourTransaction.getSubCode());

			query.setParameter("FROM_DATE", oddHourTransaction.getFromdate())
					.setParameter("End_DATE", oddHourTransaction.getEnddate())
					.setParameter("SUB_AUA_CODE", oddHourTransaction.getSubCode()).setParameter("status", 1);
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);

			
			if (obj.size() > 0) {
				data.put("details", obj);
			} else {
				data.put("dataNull", "true\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public HashMap<String, Object> getoddhourtransactionsdetails(String SUB_AUA_CODE,String FROM_DATE,String END_DATE) {
		HashMap<String, Object> memberData = new HashMap<>();//GetMis_ODDhour_Trans
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.GetMis_ODDhour_Trans")
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);

			query.setParameter("FROM_DATE", FROM_DATE).setParameter("End_DATE", END_DATE)
					.setParameter("SUB_AUA_CODE", SUB_AUA_CODE).setParameter("status", 2);

			query.execute();

			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");

			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);

			memberData.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return memberData;
	}
}