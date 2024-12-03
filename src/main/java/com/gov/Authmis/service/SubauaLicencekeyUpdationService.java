package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.SubauaLicencekeyUpdation;

@Service
public interface SubauaLicencekeyUpdationService {

	List<SubauaLicencekeyUpdation> findSubAuaAll() throws SQLException;

	/* List<Map<String, Object>> findBySubAuaId(Long tid); */

	SubauaLicencekeyUpdation findById(String tID);

	SubauaLicencekeyUpdation save(SubauaLicencekeyUpdation _tutorial);

	public List<String> getSubauaCode(String ORGNAME);

	public List<Map<String, Object>> GetSubauaCodeName();

}
