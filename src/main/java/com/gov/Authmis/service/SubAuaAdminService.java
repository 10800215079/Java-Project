package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.SubAuaAdmin;
import com.gov.Authmis.model.UsersRoleMapping;
@Service
public interface SubAuaAdminService {

	List<Map<String, Object>> GetSubauaCodeName();



	List<Map<String, Object>> getDataBySubaAua();



	Object save(UsersRoleMapping ssoUserProfile, String ssoid1);



	/* Optional<UsersRoleMapping> getForObject(String SSOID); */

}
