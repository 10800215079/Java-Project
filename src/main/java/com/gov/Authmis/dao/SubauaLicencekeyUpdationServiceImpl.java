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
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.jpa.SubauaLicencekeyUpdationModelRepository;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.SubauaLicencekeyUpdationService;

@Service
public class SubauaLicencekeyUpdationServiceImpl implements SubauaLicencekeyUpdationService {
	 @PersistenceContext
	  private EntityManager entityManager;
	@Autowired
	private SubauaLicencekeyUpdationModelRepository repo;

	public SubauaLicencekeyUpdationServiceImpl(SubauaLicencekeyUpdationModelRepository repo) {
		super();
		this.repo = repo;
	}

	/*
	 * @Override public List<SubauaLicencekeyUpdation> findSubAuaAll() { return
	 * repo.findAll(); }
	 */
	@Override
	public List<SubauaLicencekeyUpdation> findSubAuaAll()  throws SQLException {
		List<SubauaLicencekeyUpdation> data = new ArrayList<>();
		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_subaua_details")

				.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);

		query.execute();
		ResultSet l = (ResultSet) query.getOutputParameterValue("p_rc");
		while (l.next()) {
			SubauaLicencekeyUpdation listResult = new SubauaLicencekeyUpdation();
			/*
			 * System.out.println("sub aua data-----------------------------"+listResult);
			 */ 
			listResult.setTID(l.getString(1));
			listResult.setSUBAUA_CODE(l.getString(2));
			listResult.setORGNAME(l.getString(3));
			listResult.setACTIVE(l.getString(4));
			listResult.setPHONE(l.getString(5));
			 listResult.setEMAIL(l.getString(6)); 
			data.add(listResult);
			

		}
		return data;
	}
	@Transactional
	@Override
	public SubauaLicencekeyUpdation save(SubauaLicencekeyUpdation tutorial) {
		return repo.save(tutorial);
	}

	@Transactional
	@Override
	public SubauaLicencekeyUpdation findById(String tID) {
		 return repo.findByTid(tID);

	}
	public List<String> getSubauaCode(String ORGNAME){
		String sql=" select SUBAUA_CODE from AADHAAR.subaua   WHERE ORGNAME='"+ORGNAME+"' ";
		Query query = this.entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data =  query.getResultList();
		return data;
		
	}
	public List<Map<String, Object>> GetSubauaCodeName() {
		StoredProcedureQuery query1 = this.entityManager
				.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);
		return subauaList;
	}
	
}
