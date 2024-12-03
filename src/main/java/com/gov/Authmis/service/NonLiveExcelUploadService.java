package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;

import com.gov.Authmis.entity.MisNonLiveTrnLogsEntity;
import com.gov.Authmis.model.NonLiveUploadModel;

public interface NonLiveExcelUploadService {
	public String getUuidFromTxn(String txn);
	public List<MisNonLiveTrnLogsEntity> NonLiveTxnUpload(List<MisNonLiveTrnLogsEntity> nonLiveUploadModel,String fromDate,String toDate,String batchId,String ssoid,String pdfPath,String emailDate);
	
	public List<MisNonLiveTrnLogsEntity> BlockAadhaar(List<MisNonLiveTrnLogsEntity> nonLiveUploadModel,String fromDate,String toDate,String batchId,String ssoid);
	void saveExcelDataToTemp(List<MisNonLiveTrnLogsEntity> excelData);

}
