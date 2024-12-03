package com.gov.Authmis.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.SubAua;

public interface RequestForIpWhitelistService {

	List<Map<String, Object>> GetSubauaCodeName();

	List<Map<String, Object>> GetServiceType();

	HashMap<String, Object> getAllRequestedIpWhitelist(List<String> subAuaList1);

	Object save(RequestForIpWhitelistEntity requestForIpWhitelist);

	boolean isRequestedIpIsDuplicate(String subAuaCode, String iPAddress);

	Optional<RequestForIpWhitelistEntity> getData(Long id);

	Optional<RequestForIpWhitelistEntity> getImgData(Long id);

	List<SubAua> getDepartment(String ssoid);


}
