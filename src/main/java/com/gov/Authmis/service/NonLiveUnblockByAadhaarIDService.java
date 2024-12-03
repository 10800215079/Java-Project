package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.entity.NonLiveUnblockEntity;
import com.gov.Authmis.model.NonLiveUnblockByAadhaarIDModel;


public interface NonLiveUnblockByAadhaarIDService 
{

	HashMap<String, Object> GetDataBasedOnAadhaarID(NonLiveUnblockByAadhaarIDModel model);

	HashMap<String, Object> UnblockByAadhaarID(String batchid, String adhaarid, String subauacode, String fromdate, String enddate, String dc, String uidresponsecode, String transactionid, String ssoid);

	//HashMap<String, Object> GetDataBasedOnAadhaarID(String adhaarid);

	//HashMap<String, Object> GetDataBasedOnAadhaarID(String aadhaarid);

}
