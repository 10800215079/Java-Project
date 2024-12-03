package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.MappingOfSubAuaService;

@Service
public class MappingOfSubAuaServiceImpl implements MappingOfSubAuaService {

	@PersistenceContext
	private EntityManager em;
	

	@Override
	@Transactional
	public List<UsersRoleMapping> findAll() throws SQLException, ParseException {

		List<UsersRoleMapping> data = new ArrayList<>();
		StoredProcedureQuery query = this.em.createStoredProcedureQuery("AADHAAR.Get_user_roles_mapping")

				.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR);

		query.execute();
		ResultSet l = (ResultSet) query.getOutputParameterValue("p_rc");
		while (l.next()) {
			UsersRoleMapping listResult = new UsersRoleMapping();

			listResult.setSrno(l.getInt(1));
			listResult.setSSOID(l.getString(2));
			listResult.setDisplayName(l.getString(3));
			listResult.setDesignation(l.getString(4));
			listResult.setUser_roles(l.getString(5));
			listResult.setSubauaname(l.getString(6));
			;
			listResult.setStatus(l.getString(8));
			listResult.setCreater_aua_ssoid(l.getString(9));
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			String sd = formatter.format(l.getDate(10));
			listResult.setCREATED_DATE(sd);
			data.add(listResult);

		}
		return data;
	}

	

	@Override
	@Transactional
	public Object getSubauaCode(int srno, int status, String ssoid1) {
	

		System.out.println(ssoid1 + "ssoid=========================");

		String role_id = "select role_id from aadhaar.MIS_USER_DETAILS  where ssoid ='" + ssoid1 + "'  and status=1  ";
		Query query1 = this.em.createNativeQuery(role_id);

		@SuppressWarnings("unchecked")
		List<Number> role_id1 = query1.getResultList();

		String str = role_id1.toString();

		String str1 = str.replaceAll("[\\[\\]]", "");
		System.out.println(str1 + "ghgdfhghdfs");

		
		System.out.println(status + "D+++++++++++" + srno);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String strDate = formatter.format(date);
		String sql = "update AADHAAR.SSO_USER_ROLES_MAPPING set  status=" + status + ",DEACTIVATION_DATE=to_date('" + strDate
				+ "','dd-MM-yy'),DEACTIVATED_BY_SSOID='" + ssoid1 + "',DEACTIVATED_ROLEID='" + str1 + "'  where srno="
				+ srno + " ";
		Object s = em.createNativeQuery(sql).executeUpdate();
		return s;
	}

	
}
