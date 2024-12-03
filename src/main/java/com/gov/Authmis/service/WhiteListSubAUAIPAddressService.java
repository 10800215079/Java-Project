package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.WhiteListSubAUAIPAddress;

public interface WhiteListSubAUAIPAddressService {
	
	
	public Object save(WhiteListSubAUAEntity whiteListSubAUAIPAddress);

	List < WhiteListSubAUAEntity > getAllData();

	/* WhiteListSubAUAEntity getDataById(long productId); */

	WhiteListSubAUAEntity createProduct(WhiteListSubAUAEntity whiteListSubAUAEntity);

	List<WhiteListSubAUAIPAddress> findAll() throws SQLException;

	public void deleteById(Integer id);

	public void delete(Object user);
	
	public Object findById(Long id);
	
	public List<Map<String, Object>> GetSubauaCodeName();
	
	public List<Map<String, Object>> GetServiceType();

	public List<String> getSubauaCode(String oRGNAME);
	
	public Object updateWhiteListedIp(WhiteListSubAUAEntity whiteListSubAUAIPAddress);

	WhiteListSubAUAEntity updateStaus(Long id, HttpServletRequest request);

	public boolean isSubAuaCodeAndIPDuplicate(String subAuaCode, String iPAddress);

	public List<WhiteListSubAUAEntity> searchByStatusAndDepartment(Long status, String department);

	public List<WhiteListSubAUAEntity> getAll();

	public List<WhiteListSubAUAEntity> searchByDepartment(String department);

	public List<WhiteListSubAUAEntity> searchByStatus(Long status);




}
