package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.SubAuaWiseErrorDetailsReportModel;
import com.gov.Authmis.model.SubauaWiseTransactionModel;

public interface SubauaWiseTransactionService {

	HashMap<String, Object> getFilterByDate(SubauaWiseTransactionModel transactionDetails);
	
	public List<SubAua> GetSubauaCodeName(String ssoid1);
	
	public List<Map<String, Object>> GetServiceType();

	List<Object> getDataBySubaAua(String SUB_AUA_CODE, String ERROR, String fROM_DATE, String end_DATE);

	Object getSubAuaNameCode(String orgname);

	//new code
	HashMap<String, Object> getDataByFilter(SubAuaWiseErrorDetailsReportModel subAuaWiseErrorDetailsReportModel);
	//new code
	/*
	 * List<Object> getDataByErrorCode(HttpSession session, String sUB_AUA_CODE,
	 * String rEQUEST_TYPE, String eRRORCODE, String rESPONSE_MESSAGE);
	 */

	List<Object> getDataByErrorCode(HttpSession session, String sUB_AUA_CODE, String rEQUEST_TYPE, String eRRORCODE,
			String rESPONSE_MESSAGE, String dP_ID, String mI);

}
