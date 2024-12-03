package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gov.Authmis.entity.NonLiveUnblockEntity;
import com.gov.Authmis.model.NonLiveUnblockModel;

public interface NonLiveUnblockService 
{
	//public List<Map<String, Object>> GetBatchIds() throws SQLException;

	public HashMap<String, Object> GetBatchIds(String fromdate, String enddate) throws SQLException;

	public HashMap<String, Object> GetSubauaCode(String fromdate, String enddate,String batchid);

	public HashMap<String, Object> GetFinalBatchList(NonLiveUnblockModel model,List<NonLiveUnblockEntity> finalList);

	public HashMap<String, Object> UnblockByBatchid(NonLiveUnblockModel model,NonLiveUnblockEntity finalList);

}
