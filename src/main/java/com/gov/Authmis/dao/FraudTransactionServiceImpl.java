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

import com.gov.Authmis.model.FraudTransactionModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.FraudTransactionService;

@Service
public class FraudTransactionServiceImpl implements FraudTransactionService {
	@PersistenceContext
	private EntityManager entityManager;

	public HashMap<String, Object> GetFraudTranscationData(FraudTransactionModel fraudTransactionModel) {
		HashMap<String, Object> fraudTransactionData = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_FRAUD_LOG_REPORT")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUBAUACODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("FROM_DATE", fraudTransactionModel.getFromdate())
					.setParameter("End_DATE", fraudTransactionModel.getEnddate())
					.setParameter("SUBAUACODE", fraudTransactionModel.getSubCode());
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("PRC");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("OBJ ......." + obj);
			if (obj.size() > 0) {
				fraudTransactionData.put("details", obj);
			} else {
				fraudTransactionData.put("dataNull", "true\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fraudTransactionData;
	}
}
