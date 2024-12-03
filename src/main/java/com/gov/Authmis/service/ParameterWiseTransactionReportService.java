package com.gov.Authmis.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import com.gov.Authmis.model.ParameterWiseTransactionReportModel;

public interface ParameterWiseTransactionReportService {

	HashMap<String, Object> getFilterByDate(ParameterWiseTransactionReportModel parameterWiseTransactionReportModel) throws ParseException, IOException, JAXBException, KeyManagementException, NoSuchAlgorithmException;

	/*
	 * HashMap<String, Object> getDataByParameter(String sUB_AUA_CODE, String
	 * rEQUEST_TYPE, String aUTHENTICATION_STATUS, String paramValue, String
	 * paraeter, String from_date, String end_date);
	 */

	List<Object> getDataByParameter(HttpSession session, String sUB_AUA_CODE, String rEQUEST_TYPE, String aUTHENTICATION_STATUS, String eRRORCODE, String dP_ID, String rESPONSE_MESSAGE, String mI) throws IOException, JAXBException, KeyManagementException, NoSuchAlgorithmException;

	

}
