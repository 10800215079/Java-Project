package com.gov.Authmis.dao;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;
import com.gov.Authmis.model.ParameterWiseTransactionReportModel;
import com.gov.Authmis.service.ParameterWiseTransactionReportService;
import com.gov.Authmis.util.GenerateReferenceNo;

@Service
public class ParameterWiseTransactionReportServiceImpl implements ParameterWiseTransactionReportService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, Object> getFilterByDate(
			ParameterWiseTransactionReportModel parameterWiseTransactionReportModel)
			throws ParseException, IOException, JAXBException, KeyManagementException, NoSuchAlgorithmException {
		String vWhereClause = "";
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = "";
		String from_Date = "";
		try {
			String dateTimeFromStr = parameterWiseTransactionReportModel.getFromdate();
			String dateTimeToStr = parameterWiseTransactionReportModel.getEnddate();
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
		if (parameterWiseTransactionReportModel.getFromdate() != null
				&& parameterWiseTransactionReportModel.getEnddate() != null) {
			vWhereClause += "AR.CREATED_DATE BETWEEN TO_DATE('" + from_Date + "', 'DD/mm/yy HH24:MI:SS') AND TO_DATE('"
					+ toDate + "', 'DD/mm/yy HH24:MI:SS')";
		}

		if (!"ALL".equals(parameterWiseTransactionReportModel.getSubAuaCode())) {
			vWhereClause += " AND S.SUBAUA_CODE =  '" + parameterWiseTransactionReportModel.getSubAuaCode() + "' ";
		}

		if (!"ALL".equals(parameterWiseTransactionReportModel.getServicetype())) {
			vWhereClause += " AND AR.REQUEST_TYPE = '" + parameterWiseTransactionReportModel.getServicetype() + "' ";
		}

		if (!"ALL".equals(parameterWiseTransactionReportModel.getStatus())) {
			if ("SUCCESS".equals(parameterWiseTransactionReportModel.getStatus())) {
				vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'Y'";
			} else if ("FAILURE".equals(parameterWiseTransactionReportModel.getStatus())) {
				vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";
			}
		}

		if ("referenceNo".equals(parameterWiseTransactionReportModel.getParaeter())) {
			String refeno = GenerateReferenceNo.getReferenceNumber(parameterWiseTransactionReportModel.getParamValue());
			System.out.println("====>>>ParameterWiseTransactionReportServiceImpl==>>getFilterByDate :" + refeno);
			vWhereClause += " AND AADHAAR_ID = '" + refeno + "'";
		} else if ("deviceCode".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND dc = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		} else if ("dpId".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND DP_ID = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		} else if ("mi".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND MI = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		} else if ("responseCode".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND UID_RESPONSE_CODE = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		} else if ("errorCode".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND ERROR = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		}else if ("Ip".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND MAC = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		}else if ("transactionID".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND TRANSACTION_ID  = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		}else if ("macAddress".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND MAC_ADDRESS = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		}else if ("appName".equals(parameterWiseTransactionReportModel.getParaeter())) {
			vWhereClause += " AND APPNAME  = '" + parameterWiseTransactionReportModel.getParamValue() + "'";
		}


		try {
			String sql = "";

			sql = "SELECT SUB_AUA_CODE, ORGNAME, REQUEST_TYPE, ERROR, RESPONSE_MESSAGE, DP_ID, SUM(COUNT) AS COUNT, device, MI, AUTHENTICATION_STATUS FROM ("
				    + " SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, AR.MI, count(AR.AUTHENTICATION_STATUS) AS COUNT, count(distinct mac_address) device,"
				    + " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS "
				    + " FROM AADHAAR.AUA_REQUEST AR INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE "
				    + " WHERE " + vWhereClause + " and AR.AUTHENTICATION_STATUS is not null "
				    + " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, UPPER(AR.AUTHENTICATION_STATUS), AR.MI"
				    + " UNION  " 
				    + " SELECT S.SUBAUA_CODE AS SUB_AUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, AR.MI,"
				    + " count(AR.AUTHENTICATION_STATUS) AS COUNT,  count(distinct mac_address) device,"
				    + " UPPER(AR.AUTHENTICATION_STATUS) AS AUTHENTICATION_STATUS FROM AADHAAR.AUA_REQUEST_BKP AR "
				    + " INNER JOIN AADHAAR.SUBAUA S ON AR.SUB_AUA_KEY = S.SUBAUA_CODE " + "  WHERE " + vWhereClause
				    + " and AR.AUTHENTICATION_STATUS is not null"
				    + " GROUP BY S.SUBAUA_CODE, S.ORGNAME, AR.REQUEST_TYPE, AR.ERROR, AR.RESPONSE_MESSAGE, AR.DP_ID, UPPER(AR.AUTHENTICATION_STATUS),  AR.MI"
				    + " ORDER BY SUB_AUA_CODE" + ") "
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

	@Override
	public List<Object> getDataByParameter(HttpSession session, String sUB_AUA_CODE, String rEQUEST_TYPE,
			String aUTHENTICATION_STATUS,String eRRORCODE, String dP_ID, String rESPONSE_MESSAGE, String MI) throws IOException, JAXBException, KeyManagementException, NoSuchAlgorithmException {

		/*
		 * String SUB_AUA_CODE = (String) session.getAttribute("subAuaCode"); String
		 * REQUEST_TYPE = (String) session.getAttribute("servicetype"); String
		 * AUTHENTICATION_STATUS = (String) session.getAttribute("status1");
		 */
		String from_date = (String) session.getAttribute("fromdate");
		String end_date = (String) session.getAttribute("enddate");
		String paraeter = (String) session.getAttribute("paraeter");
		String paramValue = (String) session.getAttribute("paramValue");

		System.out.println("Under service impl method ");
		System.out.println("============>>>>SUB_AUA_CODE : " + sUB_AUA_CODE);
		System.out.println("============>>>>REQUEST_TYPE : " + rEQUEST_TYPE);
		System.out.println("============>>>>AUTHENTICATION_STATUS : " + aUTHENTICATION_STATUS);
		System.out.println("============>>>>from date : " + from_date);
		System.out.println("============>>>>end date : " + end_date);
		System.out.println("============>>>>paraeter : " + paraeter);
		System.out.println("============>>>>paramValue: " + paramValue);
		System.out.println("============>>>>eRRORCODE : " + eRRORCODE);
		System.out.println("============>>>>dP_ID : " + dP_ID);
		System.out.println("============>>>>rESPONSE_MESSAGE: " + rESPONSE_MESSAGE);

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

			// Print the result
			System.out.println("Formatted Date: " + toDate);
			System.out.println("Formatted Date: " + from_Date);
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
		
		//new filter
		if(!eRRORCODE.isEmpty()) {
			vWhereClause += " AND AR.ERROR = '" + eRRORCODE + "' ";
		}
		if(!dP_ID.isEmpty()) {
			vWhereClause += " AND AR.dP_ID = '" + dP_ID + "' ";
		}
		if(!rESPONSE_MESSAGE.isEmpty()) {
			vWhereClause += " AND AR.RESPONSE_MESSAGE = '" + rESPONSE_MESSAGE + "' ";
		}
		if(!MI.isEmpty()) {
			vWhereClause += " AND AR.MI= '" + MI + "' ";
		}
		

		if ("Y".equals(aUTHENTICATION_STATUS)) {
			vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'Y'";
		} else if ("N".equals(aUTHENTICATION_STATUS)) {
			vWhereClause += " AND UPPER(AR.AUTHENTICATION_STATUS) = 'N'";
		}		
		if ("referenceNo".equals(paraeter)) {
			String refeno = GenerateReferenceNo.getReferenceNumber(paramValue);
			System.out.println("====>>>ParameterWiseTransactionReportServiceImpl==>>getDataByParameter :" + refeno);
			vWhereClause += " AND AADHAAR_ID = '" + refeno + "'";
		} else if ("deviceCode".equals(paraeter)) {
			vWhereClause += " AND dc = '" + paramValue + "'";
		} else if ("dpId".equals(paraeter)) {
			vWhereClause += " AND DP_ID = '" + paramValue + "'";
		} else if ("mi".equals(paraeter)) {
			vWhereClause += " AND MI = '" + paramValue + "'";
		} else if ("responseCode".equals(paraeter)) {
			vWhereClause += " AND UID_RESPONSE_CODE = '" + paramValue + "'";
		} else if ("errorCode".equals(paraeter)) {
			vWhereClause += " AND ERROR = '" + paramValue + "'";
		}else if ("Ip".equals(paraeter)) {
			vWhereClause += " AND MAC = '" + paramValue + "'";
		}else if ("transactionID".equals(paraeter)) {
			vWhereClause += " AND TRANSACTION_ID  = '" + paramValue + "'";
		}else if ("macAddress".equals(paraeter)) {
			vWhereClause += " AND MAC_ADDRESS = '" + paramValue + "'";
		}else if ("appName".equals(paraeter)) {
			vWhereClause += " AND APPNAME = '" + paramValue + "'";
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

			// ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");

			// List<Map<String, Object>> obj =
			// ResultSetToListConverter.getListFromResultSet(rs);

			return obj; // memberData.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
