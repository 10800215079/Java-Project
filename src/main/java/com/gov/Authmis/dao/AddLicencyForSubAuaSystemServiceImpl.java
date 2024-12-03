package com.gov.Authmis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.service.AddLicencyForSubAuaSystemService;

@Service
public class AddLicencyForSubAuaSystemServiceImpl implements AddLicencyForSubAuaSystemService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Transactional
	public Object save(AddLicencyForSubAuaSystem addLicencyForSubAuaSystem) {
		String query = "INSERT INTO AADHAAR.SUBAUA_EKYC_LK ( SUB_AUA_CODE, SUB_AUA_NAME, KUA_LK, KUA_CODE, SUB_AUA_LK, EXPIRY_DATE,STATUS, "
				+ " KUA_LK_CODE) VALUES (:a, :b, :c, :d, :e, :f,:g,:t)";
		String r = addLicencyForSubAuaSystem.getKuaLk() + "----" + addLicencyForSubAuaSystem.getKuaCode();
		Object s = em.createNativeQuery(query).setParameter("a", addLicencyForSubAuaSystem.getSubAuaCode())
				.setParameter("b", addLicencyForSubAuaSystem.getSubAuaName())
				.setParameter("c", addLicencyForSubAuaSystem.getKuaLk())
				.setParameter("d", addLicencyForSubAuaSystem.getKuaCode())
				.setParameter("e", addLicencyForSubAuaSystem.getSubAuaLk())
				.setParameter("f", addLicencyForSubAuaSystem.getExpiryDate()).setParameter("g", "1")
				.setParameter("t", r)

				.executeUpdate();
		return s;
	}

	public List<String> getSubauaCode(String ORGNAME) {
		String sql = " select SUBAUA_CODE from AADHAAR.subaua   WHERE ORGNAME='" + ORGNAME + "' ";
		Query query = this.em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data = query.getResultList();
		return data;

	}

	@Override
	public Object getSubAuaNameCode(String orgname) {
		String sql = "select subaua_code from AADHAAR.SUBAUA where 	orgname='" + orgname + "'";
		Query query = em.createNativeQuery(sql);
		Object o = query.getResultList();
		return o;
	}

	@Override
	public String addUserForGenricFaceAuth(String subauaCode, String subAuaName,  String lk, Long status) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = "{ \"LK\": \"" + lk + "\", \"SUBAUA_CODE\": \"" + subauaCode + "\", \"ORG_NAME\": \"" + subAuaName + "\", \"IS_ACTIVE\": "+status+" }";

        System.out.println("responsBody ==============="+ requestBody);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.UPDATECREATE_URL, requestEntity,
				String.class);
		if (responseEntity.getStatusCode().is2xxSuccessful() ) {
			String responseBody = responseEntity.getBody();
			System.out.println("API Response: " + responseBody);
		} else {
			System.err.println("Error: " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody();
	}
	
	@Override
	public List<Map<String, Object>> getSubAuaData(String subAuaCode) {

		String sql = "Select A.SUBAUA_CODE, A.EMAIL, A.ORGNAME, B.EXPIRY_DATE  from AADHAAR.SUBAUA A join AADHAAR.SUBAUA_EKYC_LK B on A.subaua_code=B.SUB_AUA_CODE where A.SUBAUA_CODE='"
				+ subAuaCode + "'  and status ='1'";

		Query query = this.em.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		List<Map<String, Object>> listofdetails = new ArrayList<>();

		for (Object[] row : resultList) {
			Map<String, Object> rowMap = new HashMap<>();
			rowMap.put("SUBAUA_CODE", row[0]);
			rowMap.put("EMAIL", row[1]);
			rowMap.put("ORGNAME", row[2]);
			rowMap.put("EXPIRY_DATE", row[3]);

			listofdetails.add(rowMap);
		}

		return listofdetails;
	}
	
	
	@Override
	public List<Map<String, Object>> getSubAuaDetailsForEmail(String subAuaCode) {

		String sql = "Select A.SUBAUA_CODE, A.EMAIL, A.ORGNAME, B.EXPIRY_DATE, A.PHONE  from AADHAAR.SUBAUA A join AADHAAR.SUBAUA_EKYC_LK B on A.subaua_code=B.SUB_AUA_CODE where A.SUBAUA_CODE='"
				+ subAuaCode + "'  and status ='1'";

		Query query = this.em.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		List<Map<String, Object>> listofdetails = new ArrayList<>();

		for (Object[] row : resultList) {
			Map<String, Object> rowMap = new HashMap<>();
			rowMap.put("SUBAUA_CODE", row[0]);
			rowMap.put("EMAIL", row[1]);
			rowMap.put("ORGNAME", row[2]);
			rowMap.put("EXPIRY_DATE", row[3]);
			rowMap.put("PHONE", row[4]);

			listofdetails.add(rowMap);
		}

		return listofdetails;
	}
	
	@Override
	public String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            String domain = email.substring(atIndex);
            String maskedDomain = domain.replaceAll(".", "*");
            return email.substring(0, atIndex + 1) + maskedDomain;
        }
        return email;
    }
}
