package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gov.Authmis.model.ModuleMaster;

public interface MenuBindingService {

	List<ModuleMaster> Getmenubindingservice(ModuleMaster moduleMaster) throws Exception;
	public List<Map<String, Object>> GetSubauaCodeName1();
	List<Map<String, Object>> GetRoles();
	Object updateStatus(Long roleId, Long subMenuId, boolean status, String ssoid1);
	

}
