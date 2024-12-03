package com.gov.Authmis.service;

import java.util.List;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.entity.MisUserDetails;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;

public interface SubauaAndUserService {

	List<SubAua> getSubauaList();
	List<AddLicencyForSubAuaSystem> getSubauaLicenceKey();
	List<MisUserDetails> getUsers(String ssoID);

}
