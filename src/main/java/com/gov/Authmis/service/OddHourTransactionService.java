package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.OddHourTransaction;


public interface OddHourTransactionService {
	
	public HashMap<String, Object>  GetResult(OddHourTransaction oddHourTransaction);

	public HashMap<String, Object> getoddhourtransactionsdetails(String sUB_AUA_CODE,String FROM_DATE,String END_DATE);

}