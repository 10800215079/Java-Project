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

import com.gov.Authmis.model.NonLiveUploadListModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveUploadListService;

@Service
public class NonLiveUploadListServiceImpl implements NonLiveUploadListService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public HashMap<String, Object> getBatchIdsByDates(NonLiveUploadListModel nonLiveUploadListModel) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_BATCH_ID")
					.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("batchid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);
					
			query.setParameter("fromdate_var", nonLiveUploadListModel.getFromdate())
			.setParameter("enddate_var", nonLiveUploadListModel.getEnddate())
			.setParameter("batchid",null)
			.setParameter("status",1);

			query.execute();

			ResultSet rs = (ResultSet)query.getOutputParameterValue("p_rc");
					
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			
				
			memberData.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return memberData;
	}

	@Override
	public HashMap<String, Object> getDataByBatchID(String BatchID) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_BATCH_ID")
					.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("batchid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);
					
			query.setParameter("fromdate_var", null)
			.setParameter("enddate_var", null)
			.setParameter("batchid",BatchID)
			.setParameter("status",2);

			query.execute();

			ResultSet rs = (ResultSet)query.getOutputParameterValue("p_rc");
					
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			
				
			memberData.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return memberData;
	}

}
