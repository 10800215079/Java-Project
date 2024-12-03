package com.gov.Authmis.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.jpa.SubauaRepsitory;
import com.gov.Authmis.jpa.WhitelistRequestRepository;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.RequestForIpWhitelistService;

@Service
public class RequestForIpWhitelistServiceImpl implements RequestForIpWhitelistService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	WhitelistRequestRepository repository;

	@Autowired
	SubauaRepsitory subauaRepsitory;

	@Override
	public List<Map<String, Object>> GetSubauaCodeName() {
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);

		return subauaList;
	}

	@Override
	public List<Map<String, Object>> GetServiceType() {
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.GET_MIS_REQUEST_TYPE")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> servieTypeList = ResultSetToListConverter.getListFromResultSet(rs1);
		return servieTypeList;
	}

	/*
	 * @Override public List<RequestForIpWhitelistEntity> getAllRequestedIpWhitelist(List<String> subAuaCode) {
	 * 
	 * List<RequestForIpWhitelistEntity> list = repository.findBySubAUACodeIn(subAuaCode); return list;
	 * 
	 * }
	 */
	
	@Override
	public HashMap<String, Object> getAllRequestedIpWhitelist(List<String> subAuaCode) {
	    HashMap<String, Object> getData = new HashMap<>();
	    List<Map<String, Object>> resultList = new ArrayList<>();
	    try {
	        String sql = "SELECT w.ID, w.SUB_AUA_CODE, w.SUB_AUA_NAME, w.REQUEST_TYPE, w.IP_ADDRESS, w.CREATED_DATE, w.STATUS, w.APP_NAME, "
	                + "w.IPBELONGSTO, w.APPLICATONURL, w.SCHEMENAME, w.ISDOCPUBLISHED, w.UPDATED_BY, w.CREATED_BY, "
	                + "w.UPDATED_DATE, w.ACTOFAADHAAR, w.REASON, w.SERVICE_URL_TYPE, CURRENTSTATUS "
	                + "FROM IPwhitlistRequest w "
	                + "WHERE w.SUB_AUA_CODE in( :subAuaCode )"
	                + "UNION "
	                + "SELECT w.ID, w.SUB_AUA_CODE, w.SUB_AUA_NAME, w.REQUEST_TYPE, w.IP_ADDRESS, w.INSERT_DATE, w.STATUS, w.APP_NAME, "
	                + "w.IPBELONGSTO, w.APPLICATONURL, w.SCHEMENAME, w.ISDOCPUBLISHED, w.UPDATEDBY AS UPDATED_BY, w.CREATEDBY AS CREATED_BY, "
	                + "w.UPDATEDDATE AS UPDATED_DATE, 'NA' AS ACTOFAADHAAR, 'NA' AS REASON, 'NA' SERVICE_URL_TYPE, w.STATUS CURRENTSTATUS "
	                + "FROM subaua_ip_whitelist w "
	                + "WHERE w.SUB_AUA_CODE in( :subAuaCode ) and IP_ADDRESS  NOT IN (SELECT IP_ADDRESS FROM IPwhitlistRequest )";

	        Query query = em.createNativeQuery(sql);
	        query.setParameter("subAuaCode", subAuaCode);

	        @SuppressWarnings("unchecked")
	        List<Object[]> data = query.getResultList();
	        for (Object[] array : data) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("ID", array[0]);
	            map.put("SUB_AUA_CODE", array[1]);
	            map.put("SUB_AUA_NAME", array[2]);
	            map.put("REQUEST_TYPE", array[3]);
	            map.put("IP_ADDRESS", array[4]);
	            map.put("CREATED_DATE", array[5]);
	            map.put("STATUS", array[6]);
	            map.put("APP_NAME", array[7]);
	            map.put("IPBELONGSTO", array[8]);
	            map.put("APPLICATONURL", array[9]);
	            map.put("SCHEMENAME", array[10]);
	            map.put("ISDOCPUBLISHED", array[11]);
	            map.put("UPDATED_BY", array[12]);
	            map.put("CREATED_BY", array[13]);
	            map.put("UPDATED_DATE", array[14]);
	            map.put("ACTOFAADHAAR", array[15]);
	            map.put("REASON", array[16]);
	            map.put("SERVICE_URL_TYPE", array[17]);
	            map.put("CURRENTSTATUS", array[18]);
	            
	            resultList.add(map);
	        }

	        if (!resultList.isEmpty()) {
	            getData.put("details", resultList);
	            getData.put("dataIsNull", false);
	        } else {
	            getData.put("dataIsNull", true);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	    return getData;
	}


	@Override
	@Transactional
	public Object save(RequestForIpWhitelistEntity requestForIpWhitelist) {

		// repository.save(requestForIpWhitelist);

		String query = "INSERT INTO AADHAAR.IPwhitlistRequest (SUB_AUA_CODE, SUB_AUA_NAME, REQUEST_TYPE, IP_ADDRESS, CREATED_DATE, STATUS, "
				+ " APP_NAME, IPBELONGSTO, APPLICATONURL, SCHEMENAME, PUBLISHEDDOC, ISDOCPUBLISHED, Created_By, CREATERIP, CONSENTDOCUMENT,"
				+ " SERVICE_URL_TYPE, ACTOFAADHAAR, AUDITDOCUMENT,  NOCHANGECERT)"
				+ " VALUES (:a, :b, :c, :d, :e, :f, :g, :h, :i, :j, :k, :l, :m, :n, :o, :p, :q, :r, :s)";

		Object o = em.createNativeQuery(query).setParameter("a", requestForIpWhitelist.getSubAUACode())
				.setParameter("b", requestForIpWhitelist.getSubAUAName())
				.setParameter("c", requestForIpWhitelist.getRequestType())
				.setParameter("d", requestForIpWhitelist.getIpAddress())
				.setParameter("e", Date.valueOf(LocalDate.now())).setParameter("f", 2) // status is 2 because request is
																						// pending
				.setParameter("g", requestForIpWhitelist.getAppName())
				.setParameter("h", requestForIpWhitelist.getIpBelongsTo())
				.setParameter("i", requestForIpWhitelist.getApplicationUrl())
				.setParameter("j", requestForIpWhitelist.getSchemeName())
				.setParameter("k", requestForIpWhitelist.getPublishedDoc()) // Assuming this is a BLOB
				.setParameter("l", requestForIpWhitelist.getIsDocPublished())
				.setParameter("m", requestForIpWhitelist.getCreatedBy())
				.setParameter("n", requestForIpWhitelist.getCREATERIP())
				.setParameter("o", requestForIpWhitelist.getConsentDocument()) // BLOB
				.setParameter("p", requestForIpWhitelist.getServiceUrlType())
				.setParameter("q", requestForIpWhitelist.getACTOFAADHAAR())
				.setParameter("r", requestForIpWhitelist.getAuditDocument())
				.setParameter("s", requestForIpWhitelist.getNochangeCert())
				.executeUpdate();

		return 1;
	}

	/*
	 * @Override public boolean isRequestedIpIsDuplicate(String subAuaCode, String
	 * iPAddress) {
	 * 
	 * 
	 * String sql =
	 * "SELECT COUNT(1) FROM AADHAAR.IPwhitlistRequest WHERE SUB_AUA_CODE = :subAuaCode AND IP_ADDRESS = :iPAddress AND STATUS = 2"
	 * ;
	 * 
	 * String sql =
	 * "SELECT 1 FROM IPwhitlistRequest a INNER JOIN SUBAUA_IP_WHITELIST b ON a.sub_aua_code = b.SUB_AUA_CODE "
	 * + " AND a.ip_address = b.IP_ADDRESS WHERE b.STATUS = 1 AND a.status = 2 ";
	 * 
	 * Query query = em.createNativeQuery(sql); query.setParameter("subAuaCode",
	 * subAuaCode); query.setParameter("iPAddress", iPAddress);
	 * 
	 * @SuppressWarnings("unchecked") List<Object> result = query.getResultList();
	 * 
	 * if (((Number) result.get(0)).intValue() == 1) { return true; } else { return
	 * false; }
	 * 
	 * 
	 * String sql =
	 * "SELECT COUNT(1) FROM IPwhitlistRequest a INNER JOIN SUBAUA_IP_WHITELIST b ON a.sub_aua_code = b.SUB_AUA_CODE "
	 * + "AND a.ip_address = b.IP_ADDRESS WHERE b.STATUS = 1 AND a.status = 2 ";
	 * 
	 * Query query = em.createNativeQuery(sql);
	 * 
	 * query.setParameter("subAuaCode", subAuaCode); query.setParameter("iPAddress",
	 * iPAddress);
	 * 
	 * @SuppressWarnings("unchecked") List<Object> result = query.getResultList();
	 * 
	 * if (((Number) result.get(0)).intValue() == 1) { return true; } else { return
	 * false; }
	 *

	}*/
	
	@Override
	public boolean isRequestedIpIsDuplicate(String subAuaCode, String iPAddress) {
	    String sql1 = "SELECT COUNT(1) FROM AADHAAR.SUBAUA_IP_WHITELIST WHERE SUB_AUA_CODE = :subAuaCode AND IP_ADDRESS = :iPAddress"
	    		+ " AND STATUS = 1";

	    Query query1 = em.createNativeQuery(sql1);

	    query1.setParameter("subAuaCode", subAuaCode);
	    query1.setParameter("iPAddress", iPAddress);

	    @SuppressWarnings("unchecked")
	    List<Object> result1 = query1.getResultList();

	    if (((Number) result1.get(0)).intValue() == 1) {
	    	return true;
	    }
	    else {
	    	 String sql2 = "SELECT COUNT(1) FROM AADHAAR.IPwhitlistRequest WHERE SUB_AUA_CODE = :subAuaCode AND IP_ADDRESS = :iPAddress "
	    	 		+ " AND STATUS = 2";

	 	    Query query = em.createNativeQuery(sql2);

	 	    query.setParameter("subAuaCode", subAuaCode);
	 	    query.setParameter("iPAddress", iPAddress);

	 	    @SuppressWarnings("unchecked")
	 	    List<Object> result = query.getResultList();
	 	    
	 	   if (((Number) result.get(0)).intValue() == 1) {
		    	return true;
		    }
	    	return false;
	    }
	}


	@Override
	public Optional<RequestForIpWhitelistEntity> getData(Long id) {
		Optional<RequestForIpWhitelistEntity> data = repository.findById(id);
		return data;
	}

	@Override
	public Optional<RequestForIpWhitelistEntity> getImgData(Long id) {
		Optional<RequestForIpWhitelistEntity> data = repository.findById(id);
		return data;
	}

	@Override
	public List<SubAua> getDepartment(String ssoid) {

		String str = "select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('" + ssoid
				+ "') and status=1";
		Query query = em.createNativeQuery(str);

		@SuppressWarnings("unchecked")
		List<String> role_id1 = query.getResultList();
		String str1 = role_id1.toString();

		String roleId = str1.replaceAll("[\\[\\]]", "");

		@SuppressWarnings("unchecked")
		List<SubAua> subAuaList = null;
		String queryString = "";
		Query query1;
		try {

			if ("1".equals(roleId) || "3".equals(roleId)) {
				queryString = "SELECT SUBAUA_CODE as ID, ORGNAME as NAME FROM AADHAAR.SUBAUA WHERE ACTIVE = 1 ORDER BY 1";
				query1 = em.createNativeQuery(queryString, SubAua.class);
			} else {
				queryString = "SELECT s.SUBAUA_CODE as ID, s.ORGNAME as NAME FROM AADHAAR.SUBAUA s "
						+ "JOIN AADHAAR.SSO_USER_ROLES_MAPPING sso ON s.SUBAUA_CODE = sso.sub_aua_code "
						+ "WHERE sso_id = :ssoId AND ACTIVE = 1 AND status = 1 AND role_id = :roleId ORDER BY 1";
				query1 = em.createNativeQuery(queryString, SubAua.class);
				query1.setParameter("ssoId", ssoid.toLowerCase());
				query1.setParameter("roleId", roleId);
			}

			subAuaList = query1.getResultList();
			System.out.println(subAuaList);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return subAuaList;
	}

	

}
