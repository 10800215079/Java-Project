package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.SubAuaANDServiceWiseTransactionReportModel;

public interface SubAuaANDServiceWiseTransactionReportService {

	HashMap<String, Object> getFilterByDate(SubAuaANDServiceWiseTransactionReportModel subAuaWiseTransactionReportModel);
	
	HashMap<String, Object> getDataBySubaAua(String SUB_AUA_CODE,String REQUEST_TYPE1,String AUTHENTICATION_STATUS, String from_date);//getDataBySubaAua(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHONTICATION_STATUS);
	
	HashMap<String, Object> getDataBySubaAuaMonthWise(String SUB_AUA_CODE, String REQUEST_TYPE1,
			String AUTHENTICATION_STATUS, String from_date, String end_date);

	HashMap<String, Object> getDataBySubaAuaDayWise(String SUB_AUA_CODE, String REQUEST_TYPE1,
			String AUTHENTICATION_STATUS, String from_date);

	HashMap<String, Object> getDataBySubaAuaHourWise(String SUB_AUA_CODE, String REQUEST_TYPE1,
			String AUTHENTICATION_STATUS, String fromDate);

	HashMap<String, Object> getDetailedView(String SUB_AUA_CODE, String REQUEST_TYPE1, String AUTHENTICATION_STATUS,
			String from_date);

//	HashMap<String, Object> getDataBySubaAuaHourWise(String SUB_AUA_CODE, String REQUEST_TYPE1,
//			String AUTHENTICATION_STATUS, String from_date);

}
