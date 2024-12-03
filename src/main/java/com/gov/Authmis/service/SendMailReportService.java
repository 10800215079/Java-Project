package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.SendMailReportModel;

public interface SendMailReportService {

	List<SendMailReportModel> GetResult(SendMailReportModel sendMailReportModel) throws SQLException;

	HashMap<String, Object> getTransactionIdMappingDetails(String tRANSACTION_ID, String fROM_DATE, String end_DATE);

	HashMap<String, Object> TransactionIdMappingDetailsForAttchment(String tRANSACTION_ID);


}
