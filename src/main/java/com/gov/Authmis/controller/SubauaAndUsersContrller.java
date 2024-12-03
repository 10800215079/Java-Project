package com.gov.Authmis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.entity.MisUserDetails;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.service.SubauaAndUserService;

@RestController
public class SubauaAndUsersContrller {
	
	@Autowired
	private SubauaAndUserService subauaAndUserService;
	
	@RequestMapping(value = "/subaua",method = RequestMethod.POST)
	public List<SubAua> getSubaua( ) {
		return subauaAndUserService.getSubauaList();
	}
	
	@RequestMapping(value = "/subauaLicenceKey",method = RequestMethod.POST)
	public List<AddLicencyForSubAuaSystem> getSubauaLicenceKey( ) {
		return subauaAndUserService.getSubauaLicenceKey();
	}
	
	@RequestMapping(value = "/userAuth",method = RequestMethod.POST)
	public List<MisUserDetails> getUsers(@RequestBody Map<String, Object> requestData) {
		String ssoID = (String) requestData.get("ssoID");
		return subauaAndUserService.getUsers(ssoID);
	}
}
