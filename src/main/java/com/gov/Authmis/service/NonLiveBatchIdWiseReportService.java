package com.gov.Authmis.service;

import java.util.HashMap;

import com.gov.Authmis.model.NonLiveBatchIdWiseReportModel;



public interface NonLiveBatchIdWiseReportService {
	
	HashMap<String, Object> getBatchIdsByDates(NonLiveBatchIdWiseReportModel nonLiveUploadListModel);
	
	HashMap<String, Object> getDataByBatchID(String BatchID);

}
