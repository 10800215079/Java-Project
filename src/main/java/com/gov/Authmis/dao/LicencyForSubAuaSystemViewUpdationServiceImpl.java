package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.jpa.AddLicencyForSubAuaSystemRepositotry;
import com.gov.Authmis.service.LicencyForSubAuaSystemViewUpdationService;

@Service
public class LicencyForSubAuaSystemViewUpdationServiceImpl implements LicencyForSubAuaSystemViewUpdationService {

	@PersistenceContext
	EntityManager em;
	@Autowired
	AddLicencyForSubAuaSystemRepositotry repository;

	AddLicencyForSubAuaSystem addLicencyForSubAuaSystem;

	@Override
	public List<AddLicencyForSubAuaSystem> findAll() throws SQLException {
		List<AddLicencyForSubAuaSystem> data = new ArrayList<>();
		StoredProcedureQuery query = this.em.createStoredProcedureQuery("AADHAAR.Get_License_key_for_subaua")

				.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);

		query.execute();
		ResultSet l = (ResultSet) query.getOutputParameterValue("p_rc");
		while (l.next()) {
			AddLicencyForSubAuaSystem listResult = new AddLicencyForSubAuaSystem();
			listResult.setSubAuaCode(l.getString(2));
			listResult.setSubAuaName(l.getString(3));
			listResult.setSubAuaLk(l.getString(4));
			listResult.setKuaLk(l.getString(5));
			listResult.setStatus(l.getString(6));
			listResult.setSrno(l.getLong(1));
			listResult.setExpiryDate(l.getDate(7));
			data.add(listResult);

		}
		return data;
	}

	@Override
	public AddLicencyForSubAuaSystem findById(Long srno) {
		return repository.findById(srno).get();
	}

	@Override
	public AddLicencyForSubAuaSystem save(AddLicencyForSubAuaSystem comment) {
		return repository.save(comment);
	}

}
