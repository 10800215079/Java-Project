package com.gov.Authmis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.gov.Authmis.model.WhitelistForDataVaultModel;

public interface WhiteListRequestForDataVoultService {

	ResponseEntity<String> saveWhiteListRequest(List<WhitelistForDataVaultModel> dataList, HttpServletRequest request);

	List<WhitelistForDataVaultModel> getAllWhiteListRecords();

	WhitelistForDataVaultModel getWhiteListRecordById(Long id);

	 
}
