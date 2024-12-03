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
import com.gov.Authmis.model.MinuteWiseTransactionModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.MinuteWiseTransactionService;

@Service
public class MinuteWiseTransactionServiceImpl implements MinuteWiseTransactionService {
  @PersistenceContext
  private EntityManager entityManager;
  
  public HashMap<String, Object> GetMinuteWiseTransactionData(MinuteWiseTransactionModel minutewisetransmodel, String ssoid1) {
    HashMap<String, Object> minuteWiseData = new HashMap<>();
    try {
    	String str="select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('"+ssoid1+"') and status=1";
		Query query1 = entityManager.createNativeQuery(str);
		@SuppressWarnings("unchecked")
		List<String> role_id1 = query1.getResultList();
		
		String str1=role_id1.toString();
		
		String str2=str1.replaceAll("[\\[\\]]", "");
		System.out.println("FROM_DATE..." + str2);
      StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_PERMINUTEWISE_TRANS").
    		  registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("subCode", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("ROLE_ID", String.class, ParameterMode.IN).
    		  registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
    		 
     
      query.setParameter("FROM_DATE", minutewisetransmodel.getFromdate())
		.setParameter("End_DATE", minutewisetransmodel.getEnddate())
		.setParameter("SSO_ID", ssoid1)
		.setParameter("ROLE_ID", str2)
		.setParameter("subCode",null)
		.setParameter("TRANSACTION_ID",null)
		.setParameter("status",1);
      query.execute();
      ResultSet rs = (ResultSet)query.getOutputParameterValue("P_RECORDSET");
      List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
      if (obj.size() > 0) {
        minuteWiseData.put("details", obj);
      } else {
        minuteWiseData.put("dataNull", "true");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return minuteWiseData;
  }


@Override
public List<Object> getMinuteWiseTransactionDetails(String subCode,String TRANSACTION_ID, String FROM_DATE, String END_DATE) {
	HashMap<String, Object> memberData = new HashMap<>();
	try {
		
		String sql="select ASA_TYPE,APPNAME,AUA_CODE,concat(substr(aadhaar_id,1,4),'XXXXXXXX') as "
				+ " AADHAAR_ID from aadhaar.aua_request a "
				+ "    WHERE    sub_aua_key = '"+subCode+"'  and TRANSACTION_ID='"+TRANSACTION_ID+"'  and a.CREATED_DATE BETWEEN TO_DATE('"+FROM_DATE+"','dd/mm/yy hh24:mi:SS') "
						+ "AND TO_DATE('"+END_DATE+"','dd/mm/yy hh24:mi:SS') ";
			Query query = this.entityManager.createNativeQuery(sql); 
		@SuppressWarnings("unchecked")
	List<Object> data=	query.getResultList();
		System.out.println(data.toString()+"++++++++++++++++++++++++++++++++++details  of sub aua wise ");
	
	return data;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} 
	

}
}
