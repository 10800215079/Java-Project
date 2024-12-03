package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gov.Authmis.model.FraudTransactionModel;

public interface FraudTransactionService 
{
	//public List<Map<String, Object>> GetFraudTranscationData();

	HashMap<String, Object> GetFraudTranscationData(FraudTransactionModel fraudTransactionModel);

}
