package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.NonLiveUnblockEntity;
import com.gov.Authmis.model.NonLiveUnblockModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveUnblockService;

@Service
public class NonLiveUnblockServiceImpl implements NonLiveUnblockService
{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public HashMap<String, Object> GetBatchIds(String fromdate, String enddate) throws SQLException {
		HashMap<String, Object> batchIdData = new HashMap<>();
		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_UNBLOCK")
				.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SUBAUA", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("aadhaarid", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
				.setParameter("fromdate_var",fromdate)
				.setParameter("enddate_var",enddate)
				.setParameter("STATUS", 1)
				.setParameter("BATCHID", "")
				.setParameter("SUBAUA", "")
				.setParameter("aadhaarid", "");

		query1.execute();

		ResultSet rs1 = (ResultSet)query1.getOutputParameterValue("P_RC");
		List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs1);
		System.out.println("list...:"+obj);
		batchIdData.put("data", obj);
		return batchIdData;
	}

	@Override
	public HashMap<String, Object> GetSubauaCode(String fromdate, String enddate, String batchid) {	
		HashMap<String, Object> subauaData = new HashMap<>();

		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_UNBLOCK")
				.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SUBAUA", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("aadhaarid", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
				.setParameter("fromdate_var",fromdate)
				.setParameter("enddate_var",enddate)
				.setParameter("STATUS", 2)
				.setParameter("BATCHID",batchid)
				.setParameter("SUBAUA", "")
				.setParameter("aadhaarid", "");

		query1.execute();

		ResultSet rs1 = (ResultSet)query1.getOutputParameterValue("P_RC");
		List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs1);
		subauaData.put("data", obj);
		return subauaData;		
	}

	@Override
	public HashMap<String, Object> GetFinalBatchList(NonLiveUnblockModel model,List<NonLiveUnblockEntity> finalList) {	
		HashMap<String, Object> outputData = new HashMap<>();
		NonLiveUnblockEntity nonLiveUnblock = new NonLiveUnblockEntity();
		//finalList = new ArrayList<NonLiveUnblockEntity>();

		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_UNBLOCK")
					.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUBAUA", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("aadhaarid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("fromdate_var",model.getFromdate())
					.setParameter("enddate_var",model.getEnddate())
					.setParameter("STATUS", 3)
					.setParameter("BATCHID", model.getBatchid())
					.setParameter("SUBAUA", model.getSubaua())
					.setParameter("aadhaarid", "");

			query.execute();

			//			ResultSet rs1 = (ResultSet)query1.getOutputParameterValue("P_RC");
			//			List<Map<String, Object>> batchIdsList = ResultSetToListConverter.getListFromResultSet(rs1);
			//			System.out.println("batchIdsList ......."+batchIdsList);

			ResultSet rs = (ResultSet)query.getOutputParameterValue("P_RC");
			/*
			 * List<Map<String, Object>> obj =
			 * ResultSetToListConverter.getNonLiveunblockFromResultSet(rs,finalList);
			 */
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("finalList===>"+finalList);
			System.out.println("obj ..." + obj);
			if (obj.size() > 0) {
				outputData.put("details", obj);
				outputData.put("dataNull", "false");
			} else {
				outputData.put("dataNull", "true\t");
			} 

			return outputData;
		}catch (Exception e)
		{
			e.printStackTrace();

		}
		return outputData;
	}
		
	
	@Override
	public HashMap<String, Object> UnblockByBatchid(NonLiveUnblockModel model,NonLiveUnblockEntity nonLiveUnblockEntity) {	
		HashMap<String, Object> outputData = new HashMap<>();
		//NonLiveUnblockEntity nonLiveUnblock = new NonLiveUnblockEntity();
		System.out.println("Inside UnblockByBatchid DAO method.");
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_UNBLOCK_AADHAAR")
					.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("aadhaar_id", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("sub_aua_code", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("from_date", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("end_date", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("unblock", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("dc", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("uidresponsecode", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("transactionid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("ssoid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("BATCHID",nonLiveUnblockEntity.getBatchId())
					.setParameter("aadhaar_id",nonLiveUnblockEntity.getAadhaarId())
					.setParameter("sub_aua_code", nonLiveUnblockEntity.getSubAuaCode()+"")
					.setParameter("from_date", nonLiveUnblockEntity.getFromDate())
					.setParameter("end_date", nonLiveUnblockEntity.getEndDate())
					.setParameter("unblock", "UNBLOCK")
					.setParameter("dc", nonLiveUnblockEntity.getDeviceCode())
					.setParameter("uidresponsecode", nonLiveUnblockEntity.getResponseCode())
					.setParameter("transactionid", nonLiveUnblockEntity.getTransactionId())
					.setParameter("ssoid", nonLiveUnblockEntity.getSsoId());

			query.execute();

			ResultSet rs = (ResultSet)query.getOutputParameterValue("P_RC");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("obj ..." + obj);
			if (obj.size() > 0) {
				outputData.put("details", obj);
				outputData.put("dataNull", "false");
			} else {
				outputData.put("dataNull", "true\t");
			} 

			return outputData;
		}catch (Exception e)
		{
			e.printStackTrace();

		}
		return outputData;
	}


		
}
