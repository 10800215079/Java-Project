package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.NonLiveUnblockEntity;
import com.gov.Authmis.model.NonLiveUnblockByAadhaarIDModel;
import com.gov.Authmis.model.NonLiveUnblockModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveUnblockByAadhaarIDService;


@Service
public class NonLiveUnblockByAadhaarIDServiceImpl implements NonLiveUnblockByAadhaarIDService{

	@PersistenceContext
	private EntityManager entityManager;
	static Logger    logger = LoggerFactory.getLogger(NonLiveUnblockByAadhaarIDServiceImpl.class);
	@Override
	public HashMap<String, Object> GetDataBasedOnAadhaarID(NonLiveUnblockByAadhaarIDModel model) {	
		HashMap<String, Object> outputData = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_SELECT_UNBLOCK")
					.registerStoredProcedureParameter("fromdate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("enddate_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUBAUA", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("aadhaarid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("fromdate_var","")
					.setParameter("enddate_var","")
					.setParameter("STATUS", 4)
					.setParameter("BATCHID","")
					.setParameter("SUBAUA", "")
					.setParameter("aadhaarid",model.getAadhaarid());

			query.execute();

			ResultSet rs = (ResultSet)query.getOutputParameterValue("P_RC");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);

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
	public HashMap<String, Object> UnblockByAadhaarID(String batchid, String adhaarid, String subauacode, String fromdate, 
			String enddate, String dc, String uidresponsecode, String transactionid, String ssoid) 
	{	
		HashMap<String, Object> outputData = new HashMap<>();
	
		
		 logger.info("NonLiveUnblockByAadhaarIDServiceImpl=====>Inside UnblockByAadhaar ID DAO method.");
		
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_UNBLOCK_BY_TID")
					.registerStoredProcedureParameter("BATCHID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("aadhaarid_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("sub_aua_code", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("from_date", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("end_date", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("unblock", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("dc", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("uidresponsecode", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("transactionid_var", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("ssoid", String.class, ParameterMode.IN)					
					.registerStoredProcedureParameter("P_RC", ResultSet.class, ParameterMode.REF_CURSOR)
					.setParameter("BATCHID",batchid)
					.setParameter("aadhaarid_var",adhaarid)
					.setParameter("sub_aua_code", subauacode)
					.setParameter("from_date",fromdate )
					.setParameter("end_date",enddate )
					.setParameter("unblock", "UNBLOCK")
					.setParameter("dc", dc)
					.setParameter("uidresponsecode", uidresponsecode)
					.setParameter("transactionid_var",transactionid )
					.setParameter("ssoid",ssoid);
					

			query.execute();

			ResultSet rs = (ResultSet)query.getOutputParameterValue("P_RC");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("obj ..." + obj);
			if (obj.size() > 0) 
			{
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
