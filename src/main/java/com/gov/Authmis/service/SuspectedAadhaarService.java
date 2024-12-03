package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.SuspectedAadhaar;

public interface SuspectedAadhaarService {

	List<SuspectedAadhaar> GetResult(SuspectedAadhaar suspectedAadhaar);

	//HashMap<String, Object> getSuspectedAadhaarMappingDetails(String e_AADHAAR_ID);

	HashMap<String, Object> getSuspectedAadhaarMappingDetails(String E_AADHAAR_ID, String sUB_AUA_CODE, String fROM_DATE, String end_DATE);  //, String sUB_AUA_CODE



}
