package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.SubauaLicencekeyUpdation;

@Repository
public interface SubauaRepsitory extends JpaRepository<SubauaLicencekeyUpdation, String>{
	    List<SubauaLicencekeyUpdation> findByACTIVE(String active);
	
}
