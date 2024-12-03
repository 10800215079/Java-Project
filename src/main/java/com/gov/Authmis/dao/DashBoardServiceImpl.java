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
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.DashBoardService;
import com.gov.Authmis.util.DepartmentWiseTransaction;
import com.gov.Authmis.util.DeviceFailTransaction;
import com.gov.Authmis.util.DeviceWisePercentage;
import com.gov.Authmis.util.ErrorCodeAvgResp;
import com.gov.Authmis.util.HoursWiseTrans;
import com.gov.Authmis.util.UniqueSuccessFailGraph;


@Service
public class DashBoardServiceImpl implements DashBoardService {
	
  @PersistenceContext
  private EntityManager entityManager;
  
  ResultSet rs2;
  
  ResultSet rs3;
  
  ResultSet rs4;
  
  ResultSet rs5;
  
  ResultSet rs7;
  
  ResultSet rs8;
  
  ResultSet rs9;
  static Logger    logger = LoggerFactory.getLogger(DashBoardServiceImpl.class);

  public void callStoredProcedure(String ssoid, String role_id) {
		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Total_Mis_dashborad_new")
				.registerStoredProcedureParameter("P_RC2", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("P_RC3", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("P_RC4", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("P_RC5", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("P_RC8", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("P_RC9", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("role_id", String.class, ParameterMode.IN);
				query.setParameter("SSO_ID", ssoid).setParameter("role_id", role_id);
		this.rs2 = (ResultSet) query.getOutputParameterValue("P_RC2");
		this.rs3 = (ResultSet) query.getOutputParameterValue("P_RC3");
		this.rs4 = (ResultSet) query.getOutputParameterValue("P_RC4");
		this.rs5 = (ResultSet) query.getOutputParameterValue("P_RC5");
		this.rs8 = (ResultSet) query.getOutputParameterValue("P_RC8");
		this.rs9 = (ResultSet) query.getOutputParameterValue("P_RC9");
	}

	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> GetResult(String ssoid, String role_id) {
	    HashMap<String, Object> memberData = new HashMap<>();
	    Number num = 0;
	    List<Object> obj = new ArrayList<>();
	    try {
			String str = " ";		
			if ("1".equals(role_id) || "3".equals(role_id)) {
			   
				str = "select  count(1) TOT_CURR_DAY  from AADHAAR.aua_request";  
				Query query1 = entityManager.createNativeQuery(str);
				num = (Number) query1.getSingleResult();

				
				String str2 = "SELECT " +
		                "  NVL((SELECT SUM(auth_success + auth_failure) year_Transaction " +
		                "  FROM AADHAAR.MIS_AUTH_TRANSACTION " +
		                "  WHERE EXTRACT(YEAR FROM transaction_date) = EXTRACT(YEAR FROM SYSDATE) " +
		                "  ), 0 " +
		                "  ) + "+num+" AS CURRENT_YEAR_Tansaction, " +
		                "  NVL((SELECT SUM(auth_success + auth_failure) curr_month_Transaction " +
		                "       FROM AADHAAR.MIS_AUTH_TRANSACTION " +
		                "       WHERE EXTRACT(MONTH FROM transaction_date) = EXTRACT(MONTH FROM SYSDATE) " +
		                "         AND EXTRACT(YEAR FROM transaction_date) = EXTRACT(YEAR FROM SYSDATE) " +
		                "      ), 0 " +
		                "  ) + "+num+" AS Curent_MONTH_Transaction, " +
		                "  "+num+" AS Curent_DAY_Transaction " +
		                "FROM DUAL";
				
				Query query2 = entityManager.createNativeQuery(str2);
				obj = query2.getResultList();
				
			} else {
			    // Handle the case when role_id is not 1 or 3
				str = "SELECT s.subaua_code FROM AADHAAR.SUBAUA s JOIN AADHAAR.SSO_USER_ROLES_MAPPING sso ON s.SUBAUA_CODE = sso.sub_aua_code  WHERE LOWER(sso_id) = LOWER('"+ssoid+"') "
						+ "	AND role_id = "+role_id+"  AND active = 1 AND status = 1";
				Query query = entityManager.createNativeQuery(str);
				String subAuaCode = (String) query.getSingleResult();
				
				String str1 = "select  count(1) TOT_CURR_DAY  from AADHAAR.aua_request where sub_aua_key = '"+subAuaCode+"'";  
				Query query1 = entityManager.createNativeQuery(str1);
				num = (Number) query1.getSingleResult();
				
				String str2 = "SELECT " +
		                "  NVL((SELECT SUM(auth_success + auth_failure) year_Transaction " +
		                "  FROM AADHAAR.MIS_AUTH_TRANSACTION " +
		                "  WHERE EXTRACT(YEAR FROM transaction_date) = EXTRACT(YEAR FROM SYSDATE) and sub_aua_key ='"+subAuaCode+"'  ), 0" +
		                "  ) + "+num+" AS CURRENT_YEAR_Tansaction, " +
		                "  NVL((SELECT SUM(auth_success + auth_failure) curr_month_Transaction " +
		                "       FROM AADHAAR.MIS_AUTH_TRANSACTION " +
		                "       WHERE EXTRACT(MONTH FROM transaction_date) = EXTRACT(MONTH FROM SYSDATE) " +
		                "         AND EXTRACT(YEAR FROM transaction_date) = EXTRACT(YEAR FROM SYSDATE) and  sub_aua_key ='"+subAuaCode+"' " +
		                "      ), 0 " +
		                "  ) + "+num+" AS Curent_MONTH_Transaction, " +
		                "  "+num+" AS Curent_DAY_Transaction " +
		                "FROM DUAL";
				
				Query query2 = entityManager.createNativeQuery(str2);
				obj = query2.getResultList();

			}
			
			//namesList = query.getResultList();
		} catch (Exception e) {
	        e.printStackTrace();
	        throw e;
		}
        
	    memberData.put("Details", obj.get(0));
	    
	    return memberData;
	}
	@SuppressWarnings("unchecked")
	public List<String> getsubauadata(String ssoid, String role_id) {
		List<String> namesList = new ArrayList<>();
		try {
			String str = " ";		
			if ("1".equals(role_id) || "3".equals(role_id)) {
			    // Handle the case when role_id is 1 or 3
				str = "SELECT COUNT(1) FROM AADHAAR.SubAua s WHERE active = 1";    
			} else {
			    // Handle the case when role_id is not 1 or 3
				str = "SELECT s.orgname FROM AADHAAR.SUBAUA s JOIN AADHAAR.SSO_USER_ROLES_MAPPING sso ON s.SUBAUA_CODE = sso.sub_aua_code  WHERE LOWER(sso_id) = LOWER('"+ssoid+"') "
						+ "	AND role_id = "+role_id+" "
						+ "	AND active = 1 "
						+ "AND status = 1";
			}
			
			Query query = entityManager.createNativeQuery(str);
			namesList = query.getResultList();
		} catch (Exception e) {
	        e.printStackTrace();
	        throw e;
		}
		
//	    try {
//	        StoredProcedureQuery query = this.entityManager
//	            .createStoredProcedureQuery("AADHAAR.GET_MIS_SUB_AND_NAME_count")
//	            .registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR)
//	            .registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
//	            .registerStoredProcedureParameter("role_id", Integer.class, ParameterMode.IN);
//	        
//	        query.setParameter("SSO_ID", ssoid)
//	             .setParameter("role_id", Integer.parseInt(role_id));
//
//	        ResultSet rs1 = (ResultSet) query.getOutputParameterValue("prc");
//
//	        List<Map<String, Object>> obj1 = ResultSetToListConverter.getListFromResultSet(rs1);
//
//			/*
//			 * for (Map<String, Object> entry : obj1) { String name = (String)
//			 * entry.get("NAME"); namesList.add(name); }
//			 */
//	        for (Map<String, Object> entry : obj1) {
//	            if (entry.containsKey("NAME")) {
//	                String name = (String) entry.get("NAME");
//	                namesList.add(name);
//	            } else if (entry.containsKey("COUNT_STR")) {
//	                String countStr = (String) entry.get("COUNT_STR");
//	                namesList.add(countStr); // Do something with the countStr if needed
//	            }
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        throw e;
//	    }
	    return namesList;
	}
  public List<DepartmentWiseTransaction> GetDashBoardResult() {
    List<DepartmentWiseTransaction> deptList = new ArrayList<>();
    try {
      while (this.rs2.next()) {
        DepartmentWiseTransaction obj = new DepartmentWiseTransaction();
        obj.setOrgName(this.rs2.getString(1));
        obj.setCurentOrgMonth(this.rs2.getString(2));
        obj.setCurentOrgDay(this.rs2.getString(3));
        deptList.add(obj);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return deptList;
  }
  
  public List<HoursWiseTrans> GetHoursWiseTransResult() {
    List<HoursWiseTrans> hoursWiseTrans = new ArrayList<>();
    try {
      while (this.rs3.next()) {
        HoursWiseTrans obj = new HoursWiseTrans();
        obj.setHourReecod(this.rs3.getString(1));
        obj.setCountHourRecord(this.rs3.getString(2));
        obj.setCountPrevdayRecord(this.rs3.getString(3));
        hoursWiseTrans.add(obj);
      } 
      logger.info("hour wise data...." + hoursWiseTrans);
      
    } catch (SQLException e) {
      e.printStackTrace();
      logger.info("exceptio in hour wise data...." + e);
    } 
    return hoursWiseTrans;
  }
  
  public List<DeviceWisePercentage> GetDeviceWisePercentResult() {
    List<DeviceWisePercentage> deviceWiseResult = new ArrayList<>();
    try {
      while (this.rs4.next()) {
        DeviceWisePercentage obj3 = new DeviceWisePercentage();
        obj3.setReqtDescDevice(this.rs4.getString(1));
        obj3.setTotalReqtDevice(this.rs4.getString(2));
        obj3.setPerceReqtDevice(this.rs4.getString(3));
        deviceWiseResult.add(obj3);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return deviceWiseResult;
  }
  
  public List<UniqueSuccessFailGraph> GetUniqueSuccessFailGraphResult() {
    List<UniqueSuccessFailGraph> uniqueSuccessFailGraph = new ArrayList<>();
    try {
      while (this.rs5.next()) {
        UniqueSuccessFailGraph obj = new UniqueSuccessFailGraph();
        obj.setOrgNameGra(this.rs5.getString(1));
        obj.setUniSuccOrgGra(this.rs5.getString(2));
        obj.setUniFailOrgGra(this.rs5.getString(3));
        uniqueSuccessFailGraph.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return uniqueSuccessFailGraph;
  }
  
  public List<DeviceFailTransaction> GetDeviceFailResult() {
    List<DeviceFailTransaction> devicefailList = new ArrayList<>();
    try {
      while (this.rs7.next()) {
        DeviceFailTransaction obj = new DeviceFailTransaction();
        obj.setOrgNameFail(this.rs7.getString(1));
        obj.setTodTransAvgFail(this.rs7.getString(2));
        devicefailList.add(obj);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return devicefailList;
  }
  
  public List<ErrorCodeAvgResp> GetErrorCodeAvgRespResult() {
    List<ErrorCodeAvgResp> ErrorCodeAvgRespList = new ArrayList<>();
    try {
      while (this.rs8.next()) {
        ErrorCodeAvgResp obj = new ErrorCodeAvgResp();
        obj.setTotalTrans(this.rs8.getString(2));
        obj.setError(this.rs8.getString(3));
        ErrorCodeAvgRespList.add(obj);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return ErrorCodeAvgRespList;
  }
  
  public List<ErrorCodeAvgResp> GetAvgRespResult() {
    List<ErrorCodeAvgResp> ErrorCodeAvgRespList = new ArrayList<>();
    try {
      while (this.rs9.next()) {
        ErrorCodeAvgResp obj = new ErrorCodeAvgResp();
        obj.setTime(this.rs9.getString(1));
        obj.setAvergTime(this.rs9.getString(2));
        ErrorCodeAvgRespList.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return ErrorCodeAvgRespList;
  }
  
}
