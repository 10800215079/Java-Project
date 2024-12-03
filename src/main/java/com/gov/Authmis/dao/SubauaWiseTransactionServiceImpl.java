package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SubAuaWiseErrorDetailsReportModel;
import com.gov.Authmis.model.SubauaWiseTransactionModel;
import com.gov.Authmis.service.SubauaWiseTransactionService;

@Service
public class SubauaWiseTransactionServiceImpl implements SubauaWiseTransactionService {
	@PersistenceContext
	private EntityManager entityManager;

	// New Code
	@Override
	public HashMap<String, Object> getDataByFilter(
			SubAuaWiseErrorDetailsReportModel subAuaWiseErrorDetailsReportModel) {
		String vWhereClause = "";
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = "";
		String from_Date = "";
		try {
			String dateTimeFromStr = subAuaWiseErrorDetailsReportModel.getFromdate();
			String dateTimeToStr = subAuaWiseErrorDetailsReportModel.getEnddate();
			// Parse the input date string into a Date object

			Date inputDateFromDate = inputDateFormat.parse(dateTimeFromStr);
			Date inputDateToDate = inputDateFormat.parse(dateTimeToStr);
			// Set the time portion to 23:59:59
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

		HashMap<String, Object> todaysData = new HashMap<>();
		if (subAuaWiseErrorDetailsReportModel.getFromdate() != null
				&& subAuaWiseErrorDetailsReportModel.getEnddate() != null) {
			vWhereClause += "AR.CREATED_DATE BETWEEN TO_DATE('" + from_Date + "', 'DD/mm/yy HH24:MI:SS') AND TO_DATE('"
					+ toDate + "', 'DD/mm/yy HH24:MI:SS')";
		}

		if (!"ALL".equals(subAuaWiseErrorDetailsReportModel.getSubAuaCode())) {
			vWhereClause += " AND S.SUBAUA_CODE =  '" + subAuaWiseErrorDetailsReportModel.getSubAuaCode() + "' ";
			System.out.println("=========>>>>>>>>>>> " + subAuaWiseErrorDetailsReportModel.getSubAuaCode());
		}

		vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";

		try {
			String sql = "";
			sql = "SELECT SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, ERROR, RESPONSE_MESSAGE, DP_ID, SUM(COUNT) AS COUNT, device, MI, AUTHENTICATION_STATUS FROM ("
					+ " SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, AR.MI, count(AR.AUTHENTICATION_STATUS) AS COUNT, count(distinct mac_address) device, "
					+ " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS "
					+ " FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
					+ " WHERE " + vWhereClause + " and AR.AUTHENTICATION_STATUS is not null "
					+ " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, UPPER(AR.AUTHENTICATION_STATUS), AR.MI "
					+ " UNION "
					+ " SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, AR.MI, "
					+ " count(AR.AUTHENTICATION_STATUS) AS COUNT,  count(distinct mac_address) device, "
					+ " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS FROM AADHAAR.AUA_REQUEST_BKP AR "
					+ " INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE " + "  WHERE " + vWhereClause + " "
					+ " and AR.AUTHENTICATION_STATUS is not null "
					+ " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, UPPER(AR.AUTHENTICATION_STATUS),  AR.MI "
					+ " ORDER BY SUB_AUA_CODE ) "
					+ " GROUP BY SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, ERROR, RESPONSE_MESSAGE, DP_ID, AUTHENTICATION_STATUS, device, MI "
					+ " ORDER BY SUB_AUA_CODE ";
			
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
				map.put("sub_aua_key", array[0]);
				map.put("orgname", array[1]);
				map.put("Request_type", array[2]);
				map.put("error", array[3]);
				map.put("response_message", array[4]);
				map.put("dp_id", array[5]);
				map.put("count_conditionally", array[6]);	
				map.put("count_of_devices", array[7]);	//device count
				map.put("MI", array[8]);	
				map.put("authentication_status", array[9]);
				
				mapList.add(map);
			}
			if (mapList.size() > 0) {
				todaysData.put("details", mapList);
				System.out.println("");
				todaysData.put("dataIsNull", "false");
			} else {
				todaysData.put("dataIsNull", "true\t");
			}
			System.out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return todaysData;

	}

	// New Code
	@Override
	public List<Object> getDataByErrorCode(HttpSession session, String sUB_AUA_CODE, String rEQUEST_TYPE,
			String eRRORCODE, String rESPONSE_MESSAGE, String dP_ID, String mI) {

		String from_date = (String) session.getAttribute("fromdate");
		String end_date = (String) session.getAttribute("enddate");

		HashMap<String, Object> memberData = new HashMap<>();
		String vWhereClause = "";
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = "";
		String from_Date = "";
		try {
			String dateTimeFromStr = from_date;
			String dateTimeToStr = end_date;
			// Parse the input date string into a Date object

			Date inputDateFromDate = inputDateFormat.parse(dateTimeFromStr);
			Date inputDateToDate = inputDateFormat.parse(dateTimeToStr);
			// Set the time portion to 23:59:59
			inputDateToDate.setHours(23);
			inputDateToDate.setMinutes(59);
			inputDateToDate.setSeconds(59);

			// Format the modified date into the desired output format
			toDate = outputDateFormat.format(inputDateToDate);
			from_Date = outputDateFormat.format(inputDateFromDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		HashMap<String, Object> todaysData = new HashMap<>();
		if (from_date != null && end_date != null) {
			vWhereClause += "AR.CREATED_DATE BETWEEN TO_DATE('" + from_Date + "', 'DD/mm/yy HH24:MI:SS') AND TO_DATE('"
					+ toDate + "', 'DD/mm/yy HH24:MI:SS')";
		}
		vWhereClause += " AND S.SUBAUA_CODE =  '" + sUB_AUA_CODE + "' ";
		vWhereClause += " AND AR.REQUEST_TYPE = '" + rEQUEST_TYPE + "' ";

		vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";

		if (!eRRORCODE.isEmpty()) {
			vWhereClause += " AND AR.ERROR = '" + eRRORCODE + "' ";
		}

		if (!rESPONSE_MESSAGE.isEmpty()) {
			vWhereClause += " AND AR.RESPONSE_MESSAGE = '" + rESPONSE_MESSAGE + "' ";
		}
		if(!mI.isEmpty()) {
			vWhereClause += " AND AR.MI= '" + mI + "' ";
		}
		if(!dP_ID.isEmpty()) {
			vWhereClause += " AND AR.dP_ID = '" + dP_ID + "' ";
		}

		try {
			String sql = "";

			sql = "select  S.SUBAUA_CODE as SUB_AUA_CODE ,S.orgname as ORG_NAME, AR.request_type as REQUEST_TYPE,concat(substr(AR.aadhaar_id,1,4),'XXXXXXXX') as AADHAAR_ID,mac_address, "
					+ " appname,authentication_status, created_date, error,response_message, transaction_id, uid_response_code, dc, dp_id, mi, bt_aua, mac as IP "
					+ " FROM aadhaar.aua_request AR  inner join aadhaar.subaua S on AR.sub_aua_key=s.subaua_code  where "
					+ vWhereClause + " " + " union "
					+ " select  S.SUBAUA_CODE as SUB_AUA_CODE ,S.orgname as ORG_NAME, AR.request_type as REQUEST_TYPE,concat(substr(AR.aadhaar_id,1,4),'XXXXXXXX') as AADHAAR_ID,mac_address, "
					+ " ar.appname,ar.authentication_status, ar.created_date, ar.error,ar.response_message, ar.transaction_id, ar.uid_response_code, ar.dc, ar.dp_id, ar.mi, ar.bt_aua, mac as IP "
					+ " FROM aadhaar.aua_request_bkp AR  inner join aadhaar.subaua S on AR.sub_aua_key=s.subaua_code  where "
					+ vWhereClause + " ";

			Query query = this.entityManager.createNativeQuery(sql);

			@SuppressWarnings("unchecked")
			List<Object> obj = query.getResultList();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	
	
	
	
	
	

	public HashMap<String, Object> getFilterByDate(SubauaWiseTransactionModel transactionDetails) {
		HashMap<String, Object> memberData = new HashMap<>();
		try {
			StoredProcedureQuery query = this.entityManager
					.createStoredProcedureQuery("AADHAAR.Get_mis_subaua_trans_count")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR)
					.registerStoredProcedureParameter("prc1", ResultSet.class, ParameterMode.REF_CURSOR);
			query.setParameter("FROM_DATE", transactionDetails.getFrdate())
					.setParameter("End_DATE", transactionDetails.getTodate())
					.setParameter("SUB_AUA_CODE", transactionDetails.getSubAuaCode());
			query.execute();
			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
			ResultSet rs1 = (ResultSet) query.getOutputParameterValue("prc1");
			StoredProcedureQuery queryResult = this.entityManager
					.createStoredProcedureQuery("AADHAAR.Get_mis_subaua_tcount_resp")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			queryResult.setParameter("FROM_DATE", transactionDetails.getFrdate())
					.setParameter("End_DATE", transactionDetails.getTodate())
					.setParameter("SUB_AUA_CODE", transactionDetails.getSubAuaCode());
			queryResult.execute();
			ResultSet rs2 = (ResultSet) queryResult.getOutputParameterValue("prc");
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			List<Map<String, Object>> obj1 = ResultSetToListConverter.getListFromResultSet(rs1);
			List<Map<String, Object>> obj2 = ResultSetToListConverter.getListFromResultSet(rs2);

			memberData.put("details", obj);
			memberData.put("details1", obj1);
			memberData.put("details2", obj2);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return memberData;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SubAua> GetSubauaCodeName(String SSO_ID) {
		// SSOUserRolesMapping ssoUserRolesMapping =
		// ssoUserRoleMappingRepository.findBySsoIdAndStatus(SSO_ID.toLowerCase(),1);
		// List<Map<String, Object>> outputList = new ArrayList<>();
		String str = "select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('" + SSO_ID
				+ "') and status=1";
		Query query = entityManager.createNativeQuery(str);

		@SuppressWarnings("unchecked")
		List<String> role_id1 = query.getResultList();
		String str1 = role_id1.toString();

		String roleId = str1.replaceAll("[\\[\\]]", "");
		System.out.println(roleId + "ghgdfhghdfs");

		@SuppressWarnings("unchecked")
		List<SubAua> subAuaList = null;
		String queryString = "";
		Query query1;
		try {

			if ("1".equals(roleId) || "3".equals(roleId)) {
				queryString = "SELECT SUBAUA_CODE as ID, ORGNAME as NAME FROM AADHAAR.SUBAUA WHERE ACTIVE = 1 ORDER BY 1";
				query1 = entityManager.createNativeQuery(queryString, SubAua.class);
			} else {
				queryString = "SELECT s.SUBAUA_CODE as ID, s.ORGNAME as NAME FROM AADHAAR.SUBAUA s "
						+ "JOIN AADHAAR.SSO_USER_ROLES_MAPPING sso ON s.SUBAUA_CODE = sso.sub_aua_code "
						+ "WHERE sso_id = :ssoId AND ACTIVE = 1 AND status = 1 AND role_id = :roleId ORDER BY 1";
				query1 = entityManager.createNativeQuery(queryString, SubAua.class);
				query1.setParameter("ssoId", SSO_ID.toLowerCase());
				query1.setParameter("roleId", roleId);
			}

			subAuaList = query1.getResultList();
			System.out.println(subAuaList);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return subAuaList;
	}

	public List<Map<String, Object>> GetServiceType() {
		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_REQUEST_TYPE")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> servieTypeList = ResultSetToListConverter.getListFromResultSet(rs1);
		return servieTypeList;
	}

	@Override
	public List<Object> getDataBySubaAua(String SUB_AUA_CODE, String ERROR, String FROM_DATE, String End_DATE) {
		String sql = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
		LocalDate now = LocalDate.now();
		String str = dtf.format(now);
		try {
			if (((FROM_DATE).contains(str)) && ((End_DATE).contains(str))) {

				sql = " SELECT  T.subaua_name as SUB_AUA_NAME ,RESPONSE_MESSAGE as RESPONSE_MESSAGE ,CREATED_DATE as TRANSACTION_DATE from aadhaar.aua_request A "
						+ " INNER JOIN aadhaar.tbl_subaua_master T ON A.SUB_AUA_KEY =T.SUBAUA_CODE  "
						+ " WHERE   A.SUB_AUA_KEY='" + SUB_AUA_CODE + "'  AND " + "A.ERROR='" + ERROR + "' and "
						+ " CREATED_DATE BETWEEN TO_DATE('" + FROM_DATE + "' ,'dd/MM/yy HH24:MI:SS') AND  "
						+ " TO_DATE('" + End_DATE + "','dd/MM/yy HH24:MI:SS') ";
			} else {
				sql = " SELECT  T.subaua_name as SUB_AUA_NAME,error_desc as RESPONSE_MESSAGE ,TRANSACTION_DATE as TRANSACTION_DATE FROM MIS_AUTH_TRANSACTION  m  "
						+ " INNER JOIN aadhaar.tbl_subaua_master T ON m.SUB_AUA_KEY=T.SUBAUA_CODE "
						+ " INNER JOIN AADHAAR.ERROR_CODE e ON m.ERROR_TYPE=e.ERROR_CODE " + " WHERE m.SUB_AUA_KEY='"
						+ SUB_AUA_CODE + "' " + " AND m.ERROR_TYPE='" + ERROR + "'  " + " AND TRANSACTION_DATE "
						+ " BETWEEN TO_DATE('" + FROM_DATE + "','dd/MM/yy HH24:MI:SS')  " + " AND TO_DATE('" + End_DATE
						+ "','dd/MM/yy HH24:MI:SS') ";

			}
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			System.out.println(data.toString() + "++++++++++++++++++++++++++++++++++details  of sub aua wise ");

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// return data;
	}

	@Override
	public Object getSubAuaNameCode(String orgname) {
		String sql = "select subaua_code from AADHAAR.subaua where 	orgname='" + orgname + "'";
		Query query = entityManager.createNativeQuery(sql);
		Object o = query.getResultList();
		return o;
	}

	

}
