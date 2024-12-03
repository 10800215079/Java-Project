package com.gov.Authmis.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.WhiteListSubAUAIPAddress;
import com.gov.Authmis.service.WhiteListRepository;
import com.gov.Authmis.service.WhiteListSubAUAIPAddressService;
import org.springframework.web.client.RestTemplate;
@Service
public class WhiteListSubAUAIPAddresserviceImpl implements WhiteListSubAUAIPAddressService {
	
@PersistenceContext
private EntityManager em;
@Autowired
private WhiteListRepository whiteListRepository;
@Autowired
private RestTemplate restTemplate;

	@Override
	@Transactional
	public Object save(WhiteListSubAUAEntity whiteListSubAUAIPAddress) {
		
	//	whiteListRepository.save(whiteListSubAUAIPAddress);
		
		
		//whiteListRepository.save(whiteListSubAUAIPAddress);
		// Convert LocalDate to java.sql.Date
		
		 ZoneId indiaTimeZone = ZoneId.of("Asia/Kolkata");
	     LocalDateTime currentDateTimeInIndia = LocalDateTime.now(indiaTimeZone);
	     whiteListSubAUAIPAddress.setStatus(1L);
	     //SenDataForFaceGenericAuth(whiteListSubAUAIPAddress);
		
		  String query =
		  "INSERT INTO AADHAAR.subaua_ip_whitelist (SUB_AUA_CODE, SUB_AUA_NAME, REQUEST_TYPE, IP_ADDRESS, INSERT_DATE, STATUS, APP_NAME, IPBELONGSTO, APPLICATONURL, SCHEMENAME, PUBLISHEDDOC, ISDOCPUBLISHED, UPDATEDBY, CREATEDBY, UPDATEDDATE)"
		  + " VALUES (:a, :b, :c, :d, :e, :f, :g, :h, :i, :j, :k, :l, :m, :n, :o)";
		  
		  Object o = em.createNativeQuery(query) .setParameter("a",
		  whiteListSubAUAIPAddress.getSubAuaCode()) .setParameter("b",
		  whiteListSubAUAIPAddress.getSubAuaName()) .setParameter("c",
		  whiteListSubAUAIPAddress.getServicetype()) .setParameter("d",
		  whiteListSubAUAIPAddress.getIpAddress()) .setParameter("e",
		  Date.valueOf(LocalDate.now())) .setParameter("f", 1) // Assuming STATUS is  a number
		  .setParameter("g", whiteListSubAUAIPAddress.getAppName())
		  .setParameter("h", whiteListSubAUAIPAddress.getIpBelongsTo())
		  .setParameter("i", whiteListSubAUAIPAddress.getApplicatonUrl())
		  .setParameter("j", whiteListSubAUAIPAddress.getSchemeName())
		  .setParameter("k", whiteListSubAUAIPAddress.getPublishedDoc()) // Assuming this is a BLOB
		  .setParameter("l", whiteListSubAUAIPAddress.getIsDocPublished()) .setParameter("m",
		  whiteListSubAUAIPAddress.getUpdatedBy()) .setParameter("n",
		  whiteListSubAUAIPAddress.getCreatedBy()) .setParameter("o", currentDateTimeInIndia)
		  .executeUpdate();
		  
		 return 1;
	}
	
	@Override
	public List<WhiteListSubAUAIPAddress> findAll() throws SQLException {
		
		
		List<WhiteListSubAUAIPAddress> data = new ArrayList<>();
		StoredProcedureQuery query = this.em.createStoredProcedureQuery("AADHAAR.Get_SUBAUA_IP_WHITELIST")
	    		  
	    		  .registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
	    		  
		 query.execute();
		 ResultSet l = (ResultSet)query.getOutputParameterValue("prc");
		while (l.next()) {
			WhiteListSubAUAIPAddress listResult = new WhiteListSubAUAIPAddress();
			listResult.setSubAuaCode(l.getString(2));
			listResult.setSubAuaName(l.getString(3));
			listResult.setServicetype(l.getString(4));
			listResult.setiPAddress(l.getString(5));
			listResult.setInsertDate(l.getDate(6));
			listResult.setStatus(l.getString(7));
			listResult.setAppName(l.getString(8));
			listResult.setId(l.getLong(1));
			listResult.setIpBelongsTo(l.getString(9));
			listResult.setSchemeName(l.getString(10));
			listResult.setIsDocPublished(l.getLong(11));
			data.add(listResult);
			 
			
		}
		return data;
	}
	public List<Map<String, Object>> GetSubauaCodeName() {
		StoredProcedureQuery query1 = this.em
				.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);
		return subauaList;
	}

	public List<Map<String, Object>> GetServiceType() {
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.GET_MIS_REQUEST_TYPE")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> servieTypeList = ResultSetToListConverter.getListFromResultSet(rs1);
		return servieTypeList;
	}
	
	@Override
	public void deleteById(Integer id) {
		whiteListRepository.deleteById(id);
		
	}
 
	
	@Override
	public List<WhiteListSubAUAEntity> getAllData() {
		 return this.whiteListRepository.findAll();
		//return null;
	}


	@Override
	public WhiteListSubAUAEntity createProduct(WhiteListSubAUAEntity whiteListSubAUAEntity) {
		
		return whiteListRepository.save(whiteListSubAUAEntity);
	}

	@Override
	public void delete(Object user) {
		
		whiteListRepository.delete((WhiteListSubAUAEntity) user);
		
	}

	
	  @Override public WhiteListSubAUAEntity findById(Long id) { 
		  return whiteListRepository.findById(id); }

	@Override
	public List<String> getSubauaCode(String ORGNAME) {
		String sql=" select SUBAUA_CODE from AADHAAR.subaua   WHERE ORGNAME='"+ORGNAME+"' ";
		Query query = this.em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data =  query.getResultList();
		return data;
		
		
	}
	    
	
		@Transactional
	    @Override
	    public WhiteListSubAUAEntity updateStaus(Long id, HttpServletRequest request) {
	    	WhiteListSubAUAEntity whiteListSubAUAEntity = whiteListRepository.findById(id);
			if(whiteListSubAUAEntity.getStatus() == 0 || whiteListSubAUAEntity.getStatus() == null) {
				whiteListSubAUAEntity.setStatus(1L);
			} else {
				whiteListSubAUAEntity.setStatus(0L);
			}
			String ssoid = (String) request.getSession().getAttribute("SSOID");
			whiteListSubAUAEntity.setUpdatedBy(ssoid);
	    	Calendar calendar = Calendar.getInstance();
			java.util.Date currentDate = calendar.getTime();
			java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
			whiteListSubAUAEntity.setUpdatedDate(sqlDate);
			//SenDataForFaceGenericAuth(whiteListSubAUAEntity);
			updateRequestIPWhiteList(whiteListSubAUAEntity);
			whiteListRepository.save(whiteListSubAUAEntity);
			
	       
	       
	    	return whiteListSubAUAEntity;
	    }

	
		private Object updateRequestIPWhiteList(WhiteListSubAUAEntity whiteListSubAUAEntity) {
			String sql=" UPDATE AADHAAR.IPWHITLISTREQUEST SET CURRENTSTATUS = '"+whiteListSubAUAEntity.getStatus()+"' WHERE IP_ADDRESS  = '"+whiteListSubAUAEntity.getIpAddress()+"'  "
					+ "AND SUB_AUA_CODE  = '"+whiteListSubAUAEntity.getSubAuaCode()+"'";
			Query query = em.createNativeQuery(sql);
			Object data =  query.executeUpdate();
			return data;
		}

		@Override
		public Object updateWhiteListedIp(WhiteListSubAUAEntity whiteListSubAUAIPAddress) {
			WhiteListSubAUAEntity oldWhiteListSubAUAIPAddress = 	whiteListRepository.findById(whiteListSubAUAIPAddress.getId());
//			if(!whiteListSubAUAIPAddress.getIpAddress().equals(oldWhiteListSubAUAIPAddress.getIpAddress())) {
//				SenDataForFaceGenericAuth(oldWhiteListSubAUAIPAddress);
//			}
			
			if(!whiteListSubAUAIPAddress.getApplicatonUrl().isEmpty()) {
				oldWhiteListSubAUAIPAddress.setApplicatonUrl(whiteListSubAUAIPAddress.getApplicatonUrl());
			}
			if(!whiteListSubAUAIPAddress.getAppName().isEmpty()) {
				oldWhiteListSubAUAIPAddress.setAppName(whiteListSubAUAIPAddress.getAppName());
			}
			if(!whiteListSubAUAIPAddress.getIpAddress().isEmpty()) {
				oldWhiteListSubAUAIPAddress.setIpAddress(whiteListSubAUAIPAddress.getIpAddress());
			}
			if(whiteListSubAUAIPAddress.getIpBelongsTo() != null && !whiteListSubAUAIPAddress.getIpBelongsTo().isEmpty()) {
				oldWhiteListSubAUAIPAddress.setIpBelongsTo(whiteListSubAUAIPAddress.getIpBelongsTo());
			}
			if(!whiteListSubAUAIPAddress.getSchemeName().isEmpty()) {
				oldWhiteListSubAUAIPAddress.setSchemeName(whiteListSubAUAIPAddress.getSchemeName());
			}
			oldWhiteListSubAUAIPAddress.setIsDocPublished(whiteListSubAUAIPAddress.getIsDocPublished());
			if(whiteListSubAUAIPAddress.getPublishedDoc().length > 0) {
				oldWhiteListSubAUAIPAddress.setPublishedDoc(whiteListSubAUAIPAddress.getPublishedDoc());
			}
			Calendar calendar = Calendar.getInstance();
			java.util.Date currentDate = calendar.getTime();
			java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
			oldWhiteListSubAUAIPAddress.setUpdatedDate(sqlDate);
			oldWhiteListSubAUAIPAddress.setUpdatedBy(whiteListSubAUAIPAddress.getUpdatedBy());
			
			return whiteListRepository.save(oldWhiteListSubAUAIPAddress);
		}

		@Override
		public boolean isSubAuaCodeAndIPDuplicate(String subAuaCode, String iPAddress) {
			
			 return whiteListRepository.existsBySubAuaCodeAndIpAddress(subAuaCode, iPAddress);
		}
		
		@Override public List<WhiteListSubAUAEntity>searchByStatusAndDepartment(Long status, String department) { 
			return whiteListRepository.findByStatusAndSubAuaCode(status, department); }

		@Override
		public List<WhiteListSubAUAEntity> getAll() {
			return whiteListRepository.findAll();
		}

		@Override
		public List<WhiteListSubAUAEntity> searchByDepartment(String department) {			
			return whiteListRepository.findBySubAuaCode(department);
		}

		@Override
		public List<WhiteListSubAUAEntity> searchByStatus(Long status) {
			// TODO Auto-generated method stub
			return whiteListRepository.findByStatus(status);
		}
		 
		
	
	
}
