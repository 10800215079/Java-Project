package com.gov.Authmis.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import org.springframework.stereotype.Service;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.controller.NonLiveExcelUploadController;
import com.gov.Authmis.entity.MisNonLiveTrnLogsEntity;
import com.gov.Authmis.model.DSMTokanize;

@Service
@Transactional
public class OracleCon {
	
	@PersistenceContext
    private EntityManager entityManager;

	public void createConnection() {
		List<Object> mapData = getTxnAndRefId();
		
		List<MisNonLiveTrnLogsEntity> entityList = new ArrayList<>();
		
		for (Object listOfObject : mapData) {
            MisNonLiveTrnLogsEntity entity = new MisNonLiveTrnLogsEntity();
            entity.setUuid((String) listOfObject);  
            entityList.add(entity);
        }
		//batch processing
		
	    List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntity = getAadhaarFromUUID(entityList);
	
		//batch processing
	   updateAadhaar(misNonLiveTrnLogsEntity );

	}

	@SuppressWarnings("unchecked")
	public List<Object> getTxnAndRefId() {
		String query = "SELECT distinct uuid FROM aadhaar.blocked_aadhaar WHERE aadhaar_id is null and LENGTH(uuid) = 15 AND insert_date >= SYSTIMESTAMP - INTERVAL '20' HOUR AND insert_date <= SYSTIMESTAMP"; 
		Query query1 = entityManager.createNativeQuery(query);
		return query1.getResultList();
        
    }
	
	public List<MisNonLiveTrnLogsEntity> getAadhaarFromUUID(List<MisNonLiveTrnLogsEntity> nonLivetrnlogs){
		CloseableHttpClient client1;
		StringEntity inputStr;
		for(MisNonLiveTrnLogsEntity nonLivetrn:nonLivetrnlogs) {
			try {
				System.out.println("httpClient started");
				client1 = HttpClients.custom()
						.setSSLContext(new SSLContextBuilder()
								.loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
								.build())
						.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
						.build();

				//HttpPost post1 = new HttpPost("http://10.68.180.114:9081/doit-aadhaar-enc-dec/demo/hsm/auth/detokenizeV2");
				HttpPost post1 = new HttpPost(Constant.ENC_DEC_URL);

				inputStr = new StringEntity("<AuthRequest  UUID=\""+nonLivetrnlogs.get(0).getUuid()+"\" flagType=\" A\" subaua=\"0000440000\" ver=\"2.5\">\r\n</AuthRequest>");

				post1.setEntity((HttpEntity) inputStr);
				post1.setHeader("Content-Type", "application/xml");
				org.apache.http.HttpResponse resp1 = (org.apache.http.HttpResponse) client1.execute((HttpUriRequest) post1);
				BufferedReader rd1 = new BufferedReader(new InputStreamReader(((org.apache.http.HttpResponse) resp1).getEntity().getContent()));
				String line1 = "";
				StringBuilder res1 = new StringBuilder();
				while ((line1 = rd1.readLine()) != null) {
					res1.append(line1);
				}
				System.out.println("res1==========>>"+res1);

				String responseString = res1.toString();
				System.out.println("response UID==="+ responseString);

				JAXBContext jaxbContext = JAXBContext.newInstance(DSMTokanize.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(responseString);
				DSMTokanize dSMTokanize = (DSMTokanize) unmarshaller.unmarshal(reader);

			//	System.out.println("person.getAadhaarId()"+ dSMTokanize.getAadhaarNo());
			//	System.out.println("person.getAadhaarId()"+ dSMTokanize.getStatus());
				System.out.println("person.getAadhaarId()"+ dSMTokanize.getStatusCode());
				nonLivetrn.setAadhaarId(dSMTokanize.getRefNo());


			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 try {
	                Thread.sleep(1 * 1000); // Convert seconds to milliseconds
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
		}

		return nonLivetrnlogs;
	}
	
	private void updateAadhaar(List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntity) {
		if(!misNonLiveTrnLogsEntity.isEmpty()) {
			for(int i = 0; i<misNonLiveTrnLogsEntity.size(); i++) {	
			String query = "update blocked_aadhaar set aadhaar_id = '"+misNonLiveTrnLogsEntity.get(i).getAadhaarId()+"' where uuid = '"+misNonLiveTrnLogsEntity.get(i).getUuid()+"'"; 
			Query query1 = entityManager.createNativeQuery(query);
			query1.executeUpdate();
			
			 try {
	                Thread.sleep(1 * 1000); // Convert seconds to milliseconds
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
			
			}
		}
	}
}

