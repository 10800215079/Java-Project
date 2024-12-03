package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.SubAuaAdmin;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.SubAuaAdminService;

@Service
public class SubAuaAdminServiceImpl implements SubAuaAdminService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Map<String, Object>> GetSubauaCodeName() {
		StoredProcedureQuery query1 = this.entityManager
				.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);
		return subauaList;
	}

	public List<Map<String, Object>> getDataBySubaAua() {
		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_ROLE_MASTER")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);
		return subauaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object save(UsersRoleMapping subAuaAdmin,String ssoid) {
		System.out.println("sssosss id " + subAuaAdmin.getSSOID());
		

		String role_id = "select role_id  from aadhaar.roles_master where role_name ='" + subAuaAdmin.getUser_roles()
				+ "'   ";
		Query query1 = this.entityManager.createNativeQuery(role_id);
		@SuppressWarnings("unchecked")
		List<String> data = query1.getResultList();

		String subauacode = "SELECT subaua_code FROM aadhaar.subaua WHERE orgname = '" + subAuaAdmin.getSubauaname() + "' AND active = 1 FETCH FIRST 1 ROWS ONLY";
				;
		Query query2 = this.entityManager.createNativeQuery(subauacode);
		@SuppressWarnings("unchecked")
		List<String> data1 = query2.getResultList();
		System.out.println("mobile no  " + subAuaAdmin.getUser_roles());
		System.out.println("mobile no  " + subAuaAdmin.getSubauaname());
		System.out.println("subauacode  " + subAuaAdmin.getSubauacode());
		
		Object s = null;
		
//		for(String str : data1) {
//		if(str != null || ! str.isEmpty()) {
//			subAuaAdmin.setSubauacode(data1.get(0));
//			String response = addUserForGenricFaceAuth(subAuaAdmin.getSSOID(), subAuaAdmin.getSubauaname(),subAuaAdmin.getMailOfficial(),subAuaAdmin.getSubauacode(), 1L);
//			JSONObject jsonResponse = new JSONObject(response);
//			System.out.println(jsonResponse);
//		} 
//	}

			String query = "INSERT INTO AADHAAR.SSO_USER_ROLES_MAPPING(SSO_ID,NAME,AADHAAR_REF_NO,EMAIL_ID,MOBILE_NO,ROLE_ID,EMPLOYEE_NO,DESIGNATION,ROLE_NAME,SUB_AUA_NAME,CREATER_AUA_SSOID,CREATER_ROLES_ID,STATUS,CREATED_DATE,sub_aua_code)"
					+ " VALUES (:a, :b, :c, :d, :e, :f, :g, :h,:i, :j, :k, :l, :m, :n,:o)";

			s = entityManager.createNativeQuery(query).setParameter("a", subAuaAdmin.getSSOID())
					.setParameter("b", subAuaAdmin.getDisplayName()).setParameter("c", subAuaAdmin.getAadhaarId())
					.setParameter("d", subAuaAdmin.getMailOfficial()).setParameter("e", subAuaAdmin.getMobile())
					.setParameter("f", data).setParameter("g", subAuaAdmin.getEmployeeNumber())
					.setParameter("h", subAuaAdmin.getDesignation()).setParameter("i", subAuaAdmin.getUser_roles())
					.setParameter("j", subAuaAdmin.getSubauaname()).setParameter("k", ssoid).setParameter("l", data)
					.setParameter("m", "1").setParameter("n", java.time.LocalDate.now()).setParameter("o", data1)
					.executeUpdate();

			String query3 = "insert into aadhaar.MIS_USER_DETAILS(email,password, ssoid, role_id, org_id, status, created_date, updated_date) "
					+ "values(:p, :q, :r, :s, :t, :u, :v, :w)";
			s = entityManager.createNativeQuery(query3).setParameter("p", subAuaAdmin.getMailOfficial()).setParameter("q", subAuaAdmin.getSSOID())
					.setParameter("r", subAuaAdmin.getSSOID())
					.setParameter("s", data) // Convert to Integer
					.setParameter("t",1).setParameter("u", 1).setParameter("v", java.time.LocalDate.now())
					.setParameter("w", java.time.LocalDate.now()).executeUpdate();

			return s;
		
		

		
	}

//public String addUserForGenricFaceAuth( String ssoId ,String subAuaName , String email, String subauaCode, Long status) {
//    	
//		String sql = "SELECT SUB_AUA_LK  FROM SUBAUA_EKYC_LK sel WHERE SUB_AUA_CODE  = '"+subauaCode+"'  and status = 1 Fetch First rows only";
//		Query query = this.entityManager.createNativeQuery(sql);
//		@SuppressWarnings("unchecked")
//		List<String> data = query.getResultList();
//		String  lk = "null";
//		if(!data.isEmpty()) {
//			lk = data.get(0);
//		}
//		
//		if(ssoId.isEmpty()) {
//			ssoId = "null";
//		}
//		
//		if(email.isEmpty()) {
//			email = "null";
//		}
//
//			
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		String requestBody = "{ \"SSOID\": \"" + ssoId + "\", \"LK\": \"" + lk + "\", \"SUBAUA_CODE\": \"" + subauaCode
//                + "\", \"ORG_NAME\": \"" + subAuaName + "\", \"EMAIL\": \"" + email + "\", \"IS_ACTIVE\": "+status+" }";
//		System.out.println("API requestBody: " + requestBody);
//		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
//				Constant.UPDATECREATE_URL,
//				requestEntity, String.class);
//		if (responseEntity.getStatusCode().is2xxSuccessful()) {
//			String responseBody = responseEntity.getBody();
//			System.out.println("API Response: " + responseBody);
//		} else {
//			System.err.println("Error: " + responseEntity.getStatusCode());
//		}
//		return responseEntity.getBody();
//	}
}
