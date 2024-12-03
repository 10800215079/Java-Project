package com.gov.Authmis.service;

import java.util.HashMap;

import com.gov.Authmis.model.TransactionDeatilsByAadhaarModel;

public interface TransactionDeatilsByAadhaarService {
	
	HashMap<String, Object> getTransDetailsByAadhaarID(TransactionDeatilsByAadhaarModel transactionDeatilsByAadhaarModel);

}
