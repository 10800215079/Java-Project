package com.gov.Authmis.service;

import java.util.HashMap;

import com.gov.Authmis.model.NonLiveUploadListModel;


public interface NonLiveUploadListService {
	
	HashMap<String, Object> getBatchIdsByDates(NonLiveUploadListModel nonLiveUploadListModel);
	
	HashMap<String, Object> getDataByBatchID(String BatchID);

}
