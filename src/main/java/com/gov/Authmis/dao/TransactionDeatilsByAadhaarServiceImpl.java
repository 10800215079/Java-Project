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
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.TransactionDeatilsByAadhaarModel;

@Service
public class TransactionDeatilsByAadhaarServiceImpl {
	
	@PersistenceContext
	  private EntityManager entityManager;
	  
	  public HashMap<String, Object> getDataByAadhaarID(TransactionDeatilsByAadhaarModel transactionDeatilsByAadhaarModel) {
	    HashMap<String, Object> memberData = new HashMap<>();
	    try {
	      StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_TRANS_DETAILS_BY_AADHAAR")
	    		  .registerStoredProcedureParameter("p_AADHAARID", String.class, ParameterMode.IN)
	    		  .registerStoredProcedureParameter("P_DATE", String.class, ParameterMode.IN)
	    		  .registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
	      query.setParameter("p_AADHAARID", transactionDeatilsByAadhaarModel.getAadhaarid())
	        .setParameter("P_DATE", transactionDeatilsByAadhaarModel.getDate());
	 
	      query.execute();
	      
	      ResultSet rs = (ResultSet)query.getOutputParameterValue("prc");
	      
	      List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
	      System.out.println("obj ..." + obj);
	      
	      
	      
	      if (obj.size() > 0) {
	        memberData.put("details", obj);
	        memberData.put("dataNull", "false");
	      } else {
	        memberData.put("dataNull", "true\t");
	      } 
	    } catch (Exception e) {
	      e.printStackTrace();
	      throw e;
	    } 
	    return memberData;
	  }
	  
	  
	  static String mask(String input) {
		    return input.replaceAll(".(?=.{4})", "X");
		}
	  	  
}
