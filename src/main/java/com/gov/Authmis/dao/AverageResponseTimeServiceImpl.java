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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gov.Authmis.model.AverageResponseTimeModel;
import com.gov.Authmis.model.ErrorCodeModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.AverageResponseTimeService;

@Service
public class AverageResponseTimeServiceImpl implements AverageResponseTimeService {
	@PersistenceContext
	private EntityManager entityManager;
	
	Logger logger = LoggerFactory.getLogger(AverageResponseTimeServiceImpl.class);

	public List<AverageResponseTimeModel> GetAverageResponseTimeData(AverageResponseTimeModel averageResponseTimeModel, String ssoid1) throws Exception {
		List<AverageResponseTimeModel> data = new ArrayList<>();
		try {
			String str="select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('"+ssoid1+"') and status=1";
			Query query1 = entityManager.createNativeQuery(str);
			@SuppressWarnings("unchecked")
			List<String> role_id1 = query1.getResultList();
			
			String str1=role_id1.toString();
			
			String str2=str1.replaceAll("[\\[\\]]", "");
			System.out.println("FROM_DATE..." + str2);
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_AVG_RESPONSE_TIME")
					.registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("REQUEST_TYPE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("ROLE_ID", String.class, ParameterMode.IN)
					
					.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);

			query.setParameter("SSO_ID", ssoid1).setParameter("ROLE_ID", str2).setParameter("FROM_DATE", averageResponseTimeModel.getFromdate())
					.setParameter("End_DATE", averageResponseTimeModel.getEnddate())
					.setParameter("REQUEST_TYPE", null);
					

			query.execute();
		
			ResultSet DetailsObj = (ResultSet)query.getOutputParameterValue("PRC");
		      while (DetailsObj.next()) {
		    	  AverageResponseTimeModel listResult = new AverageResponseTimeModel();
		        listResult.setCREATED_DATE(DetailsObj.getString(1));
		        listResult.setREQUEST_TYPE(DetailsObj.getString(2));
		        listResult.setTOTAL_TRANSACTIONS(DetailsObj.getString(3));
		        listResult.setAVERAGE_TIME(DetailsObj.getString(4));
		      
		        data.add(listResult);
		      } 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	@Override
	public List<Object> getaverageResponseTimeDetails(String REQUEST_TYPE, String fROM_DATE, String eND_DATE) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			
			String sql="select REQUEST_TYPE, ASA_TYPE,APPNAME,AUA_CODE,AADHAAR_ID from aadhaar.aua_request a "
					
					+ "    WHERE    REQUEST_TYPE = '"+REQUEST_TYPE+"' AND a.created_date  between to_date('"+fROM_DATE+"','dd/MM/yy HH24:MI:SS') "
					+ "        and to_date('"+eND_DATE+"','dd/MM/yy HH24:MI:SS')  AND APPNAME IS NOT NULL order by REQUEST_TYPE ";
 			Query query = this.entityManager.createNativeQuery(sql); 
 		@SuppressWarnings("unchecked")
		List<Object> data=	query.getResultList();
 		logger.info("AverageResponseTimeServiceImpl==INFO==> getaverageResponseTimeDetails"+data);
 		logger.warn("AverageResponseTimeServiceImpl==WARN==> getaverageResponseTimeDetails"+data);
 		logger.error("AverageResponseTimeServiceImpl==ERROR==> getaverageResponseTimeDetails"+data);
 					
 		return data;
 		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	

	

}
