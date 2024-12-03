package com.gov.Authmis.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.Authmis.constant.Constant;

import com.gov.Authmis.entity.MisNonLiveTrnLogsEntity;
//import com.gov.Authmis.jpa.AddExcelDataForNonLivenessRepository;
import com.gov.Authmis.model.DSMTokanize;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveExcelUploadService;

@Service
@Transactional
public class NonLiveExcelUploadServiceImpl implements NonLiveExcelUploadService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String getUuidFromTxn(String txn) {
		
		String str = "Select aadhaar_id from aadhaar.aua_request_bkp where transaction_id = '" + txn + "' ";
				
		Query query1 =  entityManager.createNativeQuery(str);
		@SuppressWarnings("unchecked")
		List<String> role_id1 = query1.getResultList();
		 String str1=role_id1.toString();
         
         String aadhaar_id=str1.replaceAll("[\\[\\]]", "");
         
		return aadhaar_id;
	}

	@Override
	public List<MisNonLiveTrnLogsEntity> NonLiveTxnUpload(List<MisNonLiveTrnLogsEntity> nonLiveUploadModels,String fromDate,String toDate,String batchId,String ssoid,String pdfPath,String emailDate) {
		
			try 
			{
				//addExcelDataForNonLivenessRepository.saveAll(nonLiveUploadModels);
				System.out.println("fromDate="+fromDate+",toDate"+toDate);
				StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.CopyDataFromExcelToLogs")
						.registerStoredProcedureParameter("FROM_DATE_IN", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("END_DATE_IN", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("BATCH_ID_IN", String.class, ParameterMode.IN)
						.setParameter("FROM_DATE_IN", fromDate)
						.setParameter("END_DATE_IN", toDate)
						.setParameter("BATCH_ID_IN",batchId )
						;
				query.execute();

			}	
			catch (Exception e)
			{
				e.printStackTrace();

			}
		

		return nonLiveUploadModels;
	}
	
	
	@Override
	public List<MisNonLiveTrnLogsEntity> BlockAadhaar(List<MisNonLiveTrnLogsEntity> nonLiveUploadModels,String fromDate,String toDate,String batchId,String ssoid) {

		List<MisNonLiveTrnLogsEntity> tempEntity = 	new ArrayList<MisNonLiveTrnLogsEntity>();	

		for(MisNonLiveTrnLogsEntity misNonLiveTrnLogsEntity :nonLiveUploadModels) {
			String status = null;
			try 
			{
				StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_INSERT_BLOCKED_AADHAAR")
						.registerStoredProcedureParameter("P_AADHAAR_ID", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("SUB_AUA_CODE", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("DC", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("SSO_ID", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("UID_RESPONSE_CODE", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("TXN_ID", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("BLOCK_UNBLOCK", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("batch_id", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("from_date", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("end_date", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("uuid", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("p_rc", ResultSet.class, ParameterMode.REF_CURSOR)
						.setParameter("P_AADHAAR_ID", misNonLiveTrnLogsEntity.getAadhaarId())
						.setParameter("SUB_AUA_CODE", misNonLiveTrnLogsEntity.getSa())
						.setParameter("DC", misNonLiveTrnLogsEntity.getDeviceCode())
						.setParameter("SSO_ID", ssoid)
						.setParameter("UID_RESPONSE_CODE", misNonLiveTrnLogsEntity.getAuthResponseCode())
						.setParameter("TXN_ID", misNonLiveTrnLogsEntity.getTxn())
						.setParameter("BLOCK_UNBLOCK", "BLOCK")
						.setParameter("batch_id", batchId)
						.setParameter("from_date", fromDate)
						.setParameter("end_date", toDate)
						.setParameter("uuid", misNonLiveTrnLogsEntity.getUuid())
						;
				query.execute();
				ResultSet rs = (ResultSet) query.getOutputParameterValue("p_rc");

//				if (rs.next()) {
//					//aadhaar_ID = rs.getString("AADHAAR_ID");
//					status = rs.getString(1);
//					System.out.println("Is Blocked ......."+status);
//				}else {
//					System.out.println("Result set is null");
//				}
				misNonLiveTrnLogsEntity.setIsBlocked("Y");
				
			}	
			catch (Exception e)
			{
				e.printStackTrace();

			}

		}

		return nonLiveUploadModels;
	}
	
	@Override
	public void saveExcelDataToTemp(List<MisNonLiveTrnLogsEntity> excelData) {
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR", "aadhaar123#$")){
//		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR", "AADHAAR123#")) {
		    String insertSql = "INSERT INTO ExcelTempData (ID, auth_code, sub_aua_code, enr_ref_id, device_provider_id, device_code, model_id, finger_match_score, pid_ts, req_datetime, dt, txn, batch_id, email_date, to_date, from_date,UPLOADBY_SSOID,PDF_PATH_SS) " +
		            "VALUES (ExcelTempDataSeq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

		    try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
		        int batchSize = 1000; // Adjust the batch size based on your database and memory capabilities
		        int batchCount = 0;

		        for (MisNonLiveTrnLogsEntity entity : excelData) {
		            preparedStatement.setString(1, entity.getAuthResponseCode());
		            preparedStatement.setString(2, entity.getSa());
		            preparedStatement.setString(3, entity.getEnrRefId());
		            preparedStatement.setString(4, entity.getDeviceProviderId());
		            preparedStatement.setString(5, entity.getDeviceCode());
		            preparedStatement.setString(6, entity.getModelId());
		            preparedStatement.setString(7, entity.getFingerMatchScore());
		            preparedStatement.setString(8, entity.getPidTs());
		            preparedStatement.setString(9, entity.getReqDateTime());
		            preparedStatement.setString(10, entity.getDt());
		            preparedStatement.setString(11, entity.getTxn());
		            preparedStatement.setString(12, entity.getBatchId());
		            preparedStatement.setString(13, entity.getEmailDate());
		            preparedStatement.setString(14, entity.getToDate());
		            preparedStatement.setString(15, entity.getFromDate());
		            preparedStatement.setString(16, entity.getSsoId());
		            preparedStatement.setString(17, entity.getPdfPath());
		            
		            preparedStatement.addBatch();

		            batchCount++;

		            if (batchCount % batchSize == 0) {
		                // Execute the batch when it reaches the specified size
		                preparedStatement.executeBatch();
		                batchCount = 0;
		            }
		        }

		        // Execute any remaining statements in the batch
		        if (batchCount > 0) {
		            preparedStatement.executeBatch();
		        }

		        System.out.println("Data inserted into the database successfully!");
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}


	 
	}
}
