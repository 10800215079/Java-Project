package com.gov.Authmis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.entity.MisUserDetails;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.jpa.AddLicencyForSubAuaSystemRepo;
import com.gov.Authmis.jpa.MisUserDetailsRepository;
import com.gov.Authmis.jpa.SubauaRepsitory;
import com.gov.Authmis.service.SubauaAndUserService;

@Service
public class SubauaAndUserServiceImpl implements SubauaAndUserService{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	AddLicencyForSubAuaSystemRepo addLicencyForSubAuaSystemRepo;
	
	@Autowired
	MisUserDetailsRepository misUserDetailsRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAua> getSubauaList() {
		
		List<SubAua> subAuaList = null;
		String queryString = "";
		Query query1;
		queryString = "SELECT SUBAUA_CODE as ID, ORGNAME as NAME FROM AADHAAR.SUBAUA WHERE ACTIVE = 1 ORDER BY 1";
		query1 = entityManager.createNativeQuery(queryString, SubAua.class);
		subAuaList = query1.getResultList();
		return subAuaList;
	}

	@Override
	public List<AddLicencyForSubAuaSystem> getSubauaLicenceKey() {
		
		return addLicencyForSubAuaSystemRepo.findByStatus("1");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MisUserDetails> getUsers(String ssoID) {
	
		return misUserDetailsRepository.findBySsoId(ssoID);
		
	}

}
