package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;

import com.gov.Authmis.model.AddLicencyForSubAuaSystem;

public interface AddLicencyForSubAuaSystemService {

	public Object save(AddLicencyForSubAuaSystem addLicencyForSubAuaSystem); 
	public List<String> getSubauaCode(String ORGNAME);
	Object getSubAuaNameCode(String orgname);
	String addUserForGenricFaceAuth(String subauaCode ,String subAuaName, String subuaLk, Long status);
	public List<Map<String, Object>> getSubAuaData(String subAuaCode);
	List<Map<String, Object>> getSubAuaDetailsForEmail(String subAuaCode);
	String maskEmail(String email);
	

}
