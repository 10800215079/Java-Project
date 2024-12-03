package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;




public interface LicencyForSubAuaSystemViewUpdationService {
	
	public List<AddLicencyForSubAuaSystem> findAll() throws SQLException;

	public AddLicencyForSubAuaSystem findById(Long srno);

	public AddLicencyForSubAuaSystem save(AddLicencyForSubAuaSystem comment);

	/*
	 * public List<Map<String, Object>> findSubAuaAll();
	 * 
	 * public List<Map<String, Object>> findBySubAuaId(Long tid);
	 * 
	 * public AddLicencyForSubAuaSystem getById(Long srno);
	 * 
	 * public Object update(Long srno);
	 */

	

}
