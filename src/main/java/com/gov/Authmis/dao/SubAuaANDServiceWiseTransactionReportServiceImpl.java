package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SubAuaANDServiceWiseTransactionReportModel;
import com.gov.Authmis.service.SubAuaANDServiceWiseTransactionReportService;

@Service
public class SubAuaANDServiceWiseTransactionReportServiceImpl implements SubAuaANDServiceWiseTransactionReportService {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("deprecation")
	public HashMap<String, Object> getFilterByDate(
			SubAuaANDServiceWiseTransactionReportModel subAuaWiseTransactionReportModel) {
		
		System.out.println("SUB AUA CODE is :====================== " + subAuaWiseTransactionReportModel.getEnddate());
		
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String toDate = "";
		String from_Date = "";
		 try {
			   String dateTimeFromStr = subAuaWiseTransactionReportModel.getFromdate() + "-01 00:00:00";
			   String dateTimeToStr = subAuaWiseTransactionReportModel.getEnddate() + "-01 00:00:00";
	            // Parse the input date string into a Date object
	           
	            Date inputDateFromDate = inputDateFormat.parse(dateTimeFromStr);
	            Date inputDateToDate = inputDateFormat.parse(dateTimeToStr);
	            // Set the time portion to 23:59:59
	            inputDateToDate.setDate(30);
	            inputDateToDate.setHours(23);
	            inputDateToDate.setMinutes(59);
	            inputDateToDate.setSeconds(59);

	            // Format the modified date into the desired output format
	             toDate = outputDateFormat.format(inputDateToDate);
	             from_Date = outputDateFormat.format(inputDateFromDate);

	            // Print the result
	            System.out.println("Formatted Date: " + toDate);
	            System.out.println("Formatted Date: " + from_Date);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
		
		HashMap<String, Object> memberData = new HashMap<>();
		String vWhereClause = "";

		if (subAuaWiseTransactionReportModel.getFromdate() != null && subAuaWiseTransactionReportModel.getEnddate() != null) {
			vWhereClause += "AR.CREATED_DATE BETWEEN TO_DATE('" + from_Date+ "', 'DD/mm/yy HH24:MI:SS') AND TO_DATE('" +toDate + "', 'DD/mm/yy HH24:MI:SS')";
		}

		if (!"ALL".equals(subAuaWiseTransactionReportModel.getSubAuaCode())) {
		    vWhereClause += " AND S.SUBAUA_CODE =  '" + subAuaWiseTransactionReportModel.getSubAuaCode() + "' ";
		}

		if (!"ALL".equals(subAuaWiseTransactionReportModel.getServicetype())) {
		    vWhereClause += " AND AR.REQUEST_TYPE = '" + subAuaWiseTransactionReportModel.getServicetype() + "' ";
		}

		if (!"ALL".equals(subAuaWiseTransactionReportModel.getStatus1())) {
		    if ("SUCCESS".equals(subAuaWiseTransactionReportModel.getStatus1())) {
		        vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'Y'";
		    } else if ("FAILURE".equals(subAuaWiseTransactionReportModel.getStatus1())) {
		        vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";
		    }
		}
		
		try {
			String query = "SELECT SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, SUM(COUNT) AS COUNT, AUTHENTICATION_STATUS FROM ("
					+ "SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, count(AR.AUTHENTICATION_STATUS) AS COUNT, "
					+ "UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS "
					+ "FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ "WHERE "+vWhereClause+" and AR.AUTHENTICATION_STATUS is not null "
					+ " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, UPPER(AR.AUTHENTICATION_STATUS) "
					+ " UNION  "
					+ " SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, "
					+ " count(AR.AUTHENTICATION_STATUS) AS COUNT,"
					+ " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS "
					+ " FROM AADHAAR.AUA_REQUEST_BKP AR "
					+ " INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ "  WHERE "+vWhereClause+" and AR.AUTHENTICATION_STATUS is not null" 
					+ " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, UPPER(AR.AUTHENTICATION_STATUS)"
					+ " ORDER BY SUB_AUA_CODE"
					+") "
					+ "GROUP BY SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, AUTHENTICATION_STATUS "
					+ "ORDER BY SUB_AUA_CODE ";
			Query query1 = this.entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object> data =  query1.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
	            map.put("SUB_AUA_CODE", array[0]);
	            map.put("ORGNAME", array[1]);
	            map.put("REQUEST_TYPE",array[2]);
	            map.put("COUNT", array[3]);
	            map.put("AUTHENTICATION_STATUS", array[4]);
	            mapList.add(map);

	        }
			if (mapList.size() > 0) {
				memberData.put("details", mapList);
				memberData.put("dataNull", "false");
			} else {
				memberData.put("dataNull", "true\t");
			}
			System.out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
//		try {
//			StoredProcedureQuery query = this.entityManager
//					.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUA_REQUESTTYPE")
//					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("REQUEST_TYPE1", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("STATUS1", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
//			query.setParameter("FROM_DATE", subAuaWiseTransactionReportModel.getFromdate())
//					.setParameter("End_DATE", subAuaWiseTransactionReportModel.getEnddate())
//					.setParameter("SUB_AUA_CODE", subAuaWiseTransactionReportModel.getSubAuaCode())
//					.setParameter("REQUEST_TYPE1", subAuaWiseTransactionReportModel.getServicetype())
//					.setParameter("STATUS1", subAuaWiseTransactionReportModel.getStatus1());
//			query.setHint("hibernate.proc.param_null_passing.FROM_DATE", "true");
//			query.setHint("hibernate.proc.param_null_passing.End_DATE", "true");
//			query.setHint("hibernate.proc.param_null_passing.SUB_AUA_CODE", "true");
//			query.setHint("hibernate.proc.param_null_passing.REQUEST_TYPE1", "true");
//			query.setHint("hibernate.proc.param_null_passing.STATUS1", "true");
//			query.execute();
//			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
//			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
//			System.out.println("obj ..." + obj);
//			if (obj.size() > 0) {
//				memberData.put("details", obj);
//				memberData.put("dataNull", "false");
//			} else {
//				memberData.put("dataNull", "true\t");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
		return memberData;
	}



	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getDataBySubaAua(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHENTICATION_STATUS,
			String from_date) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			System.out.println("SUB_AUA_CODE+++++++++++++++++++++++++++" + SUB_AUA_CODE);
			System.out.println("REQUEST_TYPE1+++++++++++++++++++++++++++" + REQUEST_TYPE1);
			
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String toDate = "";
			String fromDate = "";
			 try {
		            // Parse the input date string into a Date object
		            Date inputDateToDate = inputDateFormat.parse(from_date);
		            Date inputDateFromDate = inputDateFormat.parse(from_date);

		            // Set the time portion to 23:59:59
		          //  inputDateToDate.setHours(23);
		            inputDateToDate.setMinutes(59);
		            inputDateToDate.setSeconds(59);
		            
		          //  inputDateFromDate.setHours(00);
		            inputDateFromDate.setMinutes(00);
		            inputDateFromDate.setSeconds(00);


		            // Format the modified date into the desired output format
		             toDate = outputDateFormat.format(inputDateToDate);
		             fromDate = outputDateFormat.format(inputDateFromDate);

		            // Print the result
		            System.out.println("Formatted Date: " + toDate);
		            System.out.println("Formatted Date: " + fromDate);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
	
			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUA_REQ_DETAILS")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("REQUEST_TYPE1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("STATUS1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("FROM_DATE", fromDate)
					.setParameter("End_DATE", toDate)
					.setParameter("SUB_AUA_CODE", SUB_AUA_CODE)
					.setParameter("REQUEST_TYPE1", REQUEST_TYPE1)
					.setParameter("STATUS1", AUTHENTICATION_STATUS);
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
	
	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getDataBySubaAuaMonthWise(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHENTICATION_STATUS,
			String from_date, String end_date) {
		HashMap<String, Object> memberData = new HashMap<>();
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM");
		String toDate = "";
		String fromDate = "";
		 try {
	            // Parse the input date string into a Date object
	            Date inputDateToDate = inputDateFormat.parse(end_date);
	            Date inputDateFromDate = inputDateFormat.parse(from_date);

	            // Set the time portion to 23:59:59
	            inputDateToDate.setDate(30);
	            inputDateToDate.setHours(23);
	            inputDateToDate.setMinutes(59);
	            inputDateToDate.setSeconds(59);
	            
	         
	            inputDateFromDate.setHours(00);
	            inputDateFromDate.setMinutes(00);
	            inputDateFromDate.setSeconds(00);


	            // Format the modified date into the desired output format
	             toDate = outputDateFormat.format(inputDateToDate);
	             fromDate = outputDateFormat.format(inputDateFromDate);

	            // Print the result
	            System.out.println("Formatted Date: " + toDate);
	            System.out.println("Formatted Date: " + fromDate);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			try {
				String query = "SELECT  SUB_AUA_CODE, ORGNAME,  REQUEST_TYPE, MONTH, COUNT(AUTHENTICATION_STATUS) AS COUNT, UPPER(AUTHENTICATION_STATUS) AUTHENTICATION_STATUS "
						+ "FROM ( SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS, TO_CHAR(AR.CREATED_DATE, 'YYYY-MM') AS MONTH "
						+ "FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
						+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+fromDate+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS') AND "
						+ "sub_aua_key ='"+SUB_AUA_CODE+"'  and request_type= '"+REQUEST_TYPE1+"' "
						+ "	and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
						+ "UNION ALL "
						+ "SELECT "
						+ "S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS, TO_CHAR(AR.CREATED_DATE, 'YYYY-MM') AS MONTH "
						+ "FROM AADHAAR.AUA_REQUEST_BKP AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
						+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+fromDate+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS')  AND "
						+ "sub_aua_key ='"+SUB_AUA_CODE+"' and request_type= '"+REQUEST_TYPE1+"' "
						+ "and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
						+ ") "
						+ "GROUP BY SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, MONTH, UPPER(AUTHENTICATION_STATUS) "
						+ "ORDER BY SUB_AUA_CODE,MONTH";
				Query query1 = this.entityManager.createNativeQuery(query);
				@SuppressWarnings("unchecked")
				List<Object> data =  query1.getResultList();
				List<Map<String, Object>> mapList = new ArrayList<>();
				for (Object innerList : data) {
					Object[] array = (Object[]) innerList;
					Map<String, Object> map = new HashMap<>();
		            map.put("SUB_AUA_CODE", array[0]);
		            map.put("ORGNAME", array[1]);
		            map.put("REQUEST_TYPE",array[2]);
		            map.put("MONTH",array[3]);
		            map.put("COUNT", array[4]);
		            map.put("AUTHENTICATION_STATUS", array[5]);
		            mapList.add(map);

		        }
				if (mapList.size() > 0) {
					memberData.put("data", mapList);
					memberData.put("dataNull", "false");
				} else {
					memberData.put("dataNull", "true\t");
				}
				System.out.print(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			/*
			 * try { System.out.println("SUB_AUA_CODE+++++++++++++++++++++++++++" +
			 * SUB_AUA_CODE); System.out.println("REQUEST_TYPE1+++++++++++++++++++++++++++"
			 * + REQUEST_TYPE1);
			 * 
			 * StoredProcedureQuery query = this.entityManager
			 * .createStoredProcedureQuery("AADHAAR.GET_MONTHWISEREQDETAILS")
			 * .registerStoredProcedureParameter("FROM_DATE", String.class,
			 * ParameterMode.IN) .registerStoredProcedureParameter("End_DATE", String.class,
			 * ParameterMode.IN) .registerStoredProcedureParameter("SUB_AUA_CODE",
			 * String.class, ParameterMode.IN)
			 * .registerStoredProcedureParameter("REQUEST_TYPE1", String.class,
			 * ParameterMode.IN) .registerStoredProcedureParameter("STATUS1", String.class,
			 * ParameterMode.IN) .registerStoredProcedureParameter("prc", ResultSet.class,
			 * ParameterMode.REF_CURSOR); query.setParameter("FROM_DATE", fromDate)
			 * .setParameter("End_DATE", toDate) .setParameter("SUB_AUA_CODE", SUB_AUA_CODE)
			 * .setParameter("REQUEST_TYPE1", REQUEST_TYPE1) .setParameter("STATUS1",
			 * AUTHENTICATION_STATUS); query.execute();
			 * 
			 * ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
			 * 
			 * List<Map<String, Object>> obj =
			 * ResultSetToListConverter.getListFromResultSet(rs);
			 * 
			 * memberData.put("data", obj); } catch (Exception e) { e.printStackTrace();
			 * throw e; }
			 */
		return memberData;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getDataBySubaAuaDayWise(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHENTICATION_STATUS,
			String fromDate) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			System.out.println("SUB_AUA_CODE+++++++++++++++++++++++++++" + SUB_AUA_CODE);
			System.out.println("REQUEST_TYPE1+++++++++++++++++++++++++++" + REQUEST_TYPE1);
			
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String toDate = "";
			String from_Date = "";
			 try {
				   String dateTimeStr = fromDate + "-01 00:00:00";
		            // Parse the input date string into a Date object
		            Date inputDateToDate = inputDateFormat.parse(dateTimeStr);
		            Date inputDateFromDate = inputDateFormat.parse(dateTimeStr);

		            // Set the time portion to 23:59:59
		            inputDateToDate.setDate(30);
		            inputDateToDate.setHours(23);
		            inputDateToDate.setMinutes(59);
		            inputDateToDate.setSeconds(59);
	
		            // Format the modified date into the desired output format
		             toDate = outputDateFormat.format(inputDateToDate);
		             from_Date = outputDateFormat.format(inputDateFromDate);

		            // Print the result
		            System.out.println("Formatted Date: " + toDate);
		            System.out.println("Formatted Date: " + from_Date);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		
		String query = "SELECT SUB_AUA_CODE, ORGNAME,  REQUEST_TYPE, day, COUNT(AUTHENTICATION_STATUS) AS COUNT, UPPER(AUTHENTICATION_STATUS) AUTHENTICATION_STATUS \r\n"
				+ "FROM ( SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS, TO_CHAR(AR.CREATED_DATE, 'YYYY-MM-DD') AS day\r\n"
				+ "FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE\r\n"
				+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+from_Date+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS') AND "
				+  "sub_aua_key ='"+SUB_AUA_CODE+"'  and request_type= '"+REQUEST_TYPE1+"' "
				+ "and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
				+ "UNION ALL "
				+ "SELECT  "
				+ "S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS, TO_CHAR(AR.CREATED_DATE, 'YYYY-MM-DD') AS day\r\n"
				+ "FROM AADHAAR.AUA_REQUEST_BKP AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE \r\n"
				+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+from_Date+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS') AND "
				+"sub_aua_key ='"+SUB_AUA_CODE+"'  and request_type= '"+REQUEST_TYPE1+"' "
				+ "and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
				+ ") "
				+ "GROUP BY SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, day, UPPER(AUTHENTICATION_STATUS)\r\n"
				+ "ORDER BY SUB_AUA_CODE,day";
		Query query1 = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object> data =  query1.getResultList();
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (Object innerList : data) {
			Object[] array = (Object[]) innerList;
			Map<String, Object> map = new HashMap<>();
            map.put("SUB_AUA_CODE", array[0]);
            map.put("ORGNAME", array[1]);
            map.put("REQUEST_TYPE",array[2]);
            map.put("DAY", array[3]);
            map.put("COUNT", array[4]);
            map.put("AUTHENTICATION_STATUS", array[5]);
            mapList.add(map);

        }
		if (mapList.size() > 0) {
			memberData.put("data", mapList);
			memberData.put("dataNull", "false");
		} else {
			memberData.put("data", "true\t");
		}
		
	
/*			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.GET_DAYWISEREQDETAILS")
					.registerStoredProcedureParameter("FROMDATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("TODATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("REQUEST_TYPE1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("STATUS1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("FROMDATE", from_Date)
					.setParameter("TODATE", toDate)
					.setParameter("SUB_AUA_CODE", SUB_AUA_CODE)
					.setParameter("REQUEST_TYPE1", REQUEST_TYPE1)
					.setParameter("STATUS1", AUTHENTICATION_STATUS);
			query.execute();

			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");

			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);

			memberData.put("data", obj);
		} */ 
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return memberData;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getDataBySubaAuaHourWise(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHENTICATION_STATUS,
			String from_date) {
		HashMap<String, Object> memberData = new HashMap<>();
//		try {
//			System.out.println("SUB_AUA_CODE+++++++++++++++++++++++++++" + SUB_AUA_CODE);
//			System.out.println("REQUEST_TYPE1+++++++++++++++++++++++++++" + REQUEST_TYPE1);
//	
//			StoredProcedureQuery query = this.entityManager
//					.createStoredProcedureQuery("AADHAAR.GET_HOURWISEREQDETAILS")
//					.registerStoredProcedureParameter("FROMDATE", String.class, ParameterMode.IN)
////					.registerStoredProcedureParameter("TODATE", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("REQUEST_TYPE1", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("STATUS1", String.class, ParameterMode.IN)
//					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
//			query.setParameter("FROMDATE", from_date)
////					.setParameter("TODATE", end_date)
//					.setParameter("SUB_AUA_CODE", SUB_AUA_CODE)
//					.setParameter("REQUEST_TYPE1", REQUEST_TYPE1)
//					.setParameter("STATUS1", AUTHENTICATION_STATUS);
//			query.execute();
//
//			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
//
//			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
//
//			memberData.put("data", obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = "";
		String fromDate = "";
		 try {
	            // Parse the input date string into a Date object
	            Date inputDateToDate = inputDateFormat.parse(from_date);
	            Date inputDateFromDate = inputDateFormat.parse(from_date);

	            // Set the time portion to 23:59:59
	            inputDateToDate.setHours(23);
	            inputDateToDate.setMinutes(59);
	            inputDateToDate.setSeconds(59);
	            
	            inputDateFromDate.setHours(00);
	            inputDateFromDate.setMinutes(00);
	            inputDateFromDate.setSeconds(00);


	            // Format the modified date into the desired output format
	             toDate = outputDateFormat.format(inputDateToDate);
	             fromDate = outputDateFormat.format(inputDateFromDate);

	            // Print the result
	            System.out.println("Formatted Date: " + toDate);
	            System.out.println("Formatted Date: " + fromDate);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
		try {
			String query = "SELECT SUB_AUA_CODE, ORGNAME,  REQUEST_TYPE, HOUR,TO_CHAR(MAX(created_date), 'YYYY-MM-DD HH24:MI:SS') AS FULL_DATE_TIME, COUNT(AUTHENTICATION_STATUS) AS COUNT, UPPER(AUTHENTICATION_STATUS) AUTHENTICATION_STATUS "
					+ "FROM ( SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS,  TO_CHAR(AR.CREATED_DATE, 'HH24') AS hour, created_date "
					+ "FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+fromDate+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS') AND  "
					+ "sub_aua_key ='"+SUB_AUA_CODE+"'  and request_type= '"+REQUEST_TYPE1+"' "
					+ "and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
					+ "UNION all "
					+ "SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.AUTHENTICATION_STATUS, TO_CHAR(AR.CREATED_DATE, 'HH24') AS hour, created_date  "
					+ "FROM AADHAAR.AUA_REQUEST_BKP AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ "WHERE AR.CREATED_DATE BETWEEN TO_DATE ('"+fromDate+"' ,'DD/mm/yy HH24:MI:SS') AND TO_DATE('"+toDate+"' ,'DD/mm/yy HH24:MI:SS') AND "
					+ "	sub_aua_key ='"+SUB_AUA_CODE+"'  and request_type= '"+REQUEST_TYPE1+"' "
					+ "and UPPER(AR.authentication_status)=UPPER( '"+AUTHENTICATION_STATUS+"' ) AND AR.AUTHENTICATION_STATUS IS NOT NULL "
					+ ") "
					+ "GROUP BY SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, HOUR, UPPER(AUTHENTICATION_STATUS) "
					+ "ORDER BY MAX(created_date) DESC ";
			Query query1 = this.entityManager.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object> data =  query1.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
	            map.put("SUB_AUA_CODE", array[0]);
	            map.put("ORGNAME", array[1]);
	            map.put("REQUEST_TYPE",array[2]);
	            map.put("HOUR",array[3]);
	            map.put("DATE", array[4]);
	            map.put("COUNT", array[5]);
	            map.put("AUTHENTICATION_STATUS", array[6]);
	            mapList.add(map);

	        }
			if (mapList.size() > 0) {
				memberData.put("data", mapList);
				memberData.put("dataNull", "false");
			} else {
				memberData.put("dataNull", "true\t");
			}
			System.out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return memberData;
	}



	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getDetailedView(String SUB_AUA_CODE, String REQUEST_TYPE1,
			String AUTHENTICATION_STATUS, String from_date) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			System.out.println("SUB_AUA_CODE+++++++++++++++++++++++++++" + SUB_AUA_CODE);
			System.out.println("REQUEST_TYPE1+++++++++++++++++++++++++++" + REQUEST_TYPE1);
			
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String toDate = "";
			String fromDate = "";
			 try {
		            // Parse the input date string into a Date object
		            Date inputDateToDate = inputDateFormat.parse(from_date);
		            Date inputDateFromDate = inputDateFormat.parse(from_date);

		            // Set the time portion to 23:59:59
		            inputDateToDate.setHours(23);
		            inputDateToDate.setMinutes(59);
		            inputDateToDate.setSeconds(59);
		            
		            inputDateFromDate.setHours(00);
		            inputDateFromDate.setMinutes(00);
		            inputDateFromDate.setSeconds(00);


		            // Format the modified date into the desired output format
		             toDate = outputDateFormat.format(inputDateToDate);
		             fromDate = outputDateFormat.format(inputDateFromDate);

		            // Print the result
		            System.out.println("Formatted Date: " + toDate);
		            System.out.println("Formatted Date: " + fromDate);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
	
			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUA_REQ_DETAILS")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("REQUEST_TYPE1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("STATUS1", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("FROM_DATE", fromDate)
					.setParameter("End_DATE", toDate)
					.setParameter("SUB_AUA_CODE", SUB_AUA_CODE)
					.setParameter("REQUEST_TYPE1", REQUEST_TYPE1)
					.setParameter("STATUS1", AUTHENTICATION_STATUS);
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
