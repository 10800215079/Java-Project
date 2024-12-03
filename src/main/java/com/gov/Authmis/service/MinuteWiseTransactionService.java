package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.MinuteWiseTransactionModel;

public interface MinuteWiseTransactionService {

	HashMap<String, Object> GetMinuteWiseTransactionData(MinuteWiseTransactionModel minutewisetransmodel, String ssoid1);

	

	//HashMap<String, Object> getMinuteWiseTransactionDetails(String subCode);



	List<Object> getMinuteWiseTransactionDetails(String subCode, String tRANSACTION_ID, String FROM_DATE, String END_DATE);

}
