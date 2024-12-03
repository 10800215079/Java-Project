package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;

import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SuspectedAadhaar;
import com.gov.Authmis.service.SuspectedAadhaarService;

@Service
public class SuspectedAadhaarServiceImpl implements SuspectedAadhaarService {
  @PersistenceContext
  private EntityManager entityManager;
  
  public List<SuspectedAadhaar> GetResult(SuspectedAadhaar suspectedAadhaar) {
    List<SuspectedAadhaar> data = new ArrayList<>();
    try {
      StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_mis_suspect_aadhaar")
    		  .registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
    		  .registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
    		  .registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
    		  .registerStoredProcedureParameter("E_AADHAAR_ID", String.class, ParameterMode.IN)
    		  .registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
    		  .registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR)
    		  .setParameter("FROM_DATE", suspectedAadhaar.getFromdate())
    		  .setParameter("End_DATE", suspectedAadhaar.getEnddate())
    		  .setParameter("SUB_AUA_CODE", suspectedAadhaar.getSubAuaCode())
      .setParameter("E_AADHAAR_ID", null)
      .setParameter("status",1);
      query.execute();
      ResultSet DetailsObj = (ResultSet)query.getOutputParameterValue("p_rc");
      while (DetailsObj.next()) {
        SuspectedAadhaar listResult = new SuspectedAadhaar();
        listResult.setOrgName(DetailsObj.getString(1));
        listResult.setAadhaarId(DetailsObj.getString(2));
        listResult.setSubAuaCode(DetailsObj.getString(3));
        listResult.setNoOfTran(DetailsObj.getString(4));
        listResult.setAuthSuccess(DetailsObj.getString(5));
        data.add(listResult);
      } 
      return data;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }


@Override
public HashMap<String, Object> getSuspectedAadhaarMappingDetails(String E_AADHAAR_ID, String sUB_AUA_CODE, String fROM_DATE, String end_DATE) {//,String SUB_AUA_CODE
	HashMap<String, Object> memberData = new HashMap<>();

	
	try {
		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_mis_suspect_aadhaar")
				.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("E_AADHAAR_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);
		System.out.println("E_AADHAAR_ID+++++++++++++++++++++"+query.toString());
				
		query.setParameter("FROM_DATE", fROM_DATE)
		.setParameter("End_DATE", end_DATE)
		.setParameter("SUB_AUA_CODE",sUB_AUA_CODE)
		.setParameter("E_AADHAAR_ID",E_AADHAAR_ID)
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

