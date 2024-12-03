package com.gov.Authmis.dao;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.controller.LicencyForSubAuaSystemViewUpdationController;
import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.AuthrequestEkyc;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.model.ExternalSMSApiInfo;
import com.gov.Authmis.model.OtpModel;
import com.gov.Authmis.service.AadhaarOTPValidationService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AadhaarOTPValidationServiceImpl implements AadhaarOTPValidationService{

	
	@Autowired
	RestTemplate restTemplate;
	
	@PersistenceContext
	EntityManager entityManager;
	
	Logger logger = LoggerFactory.getLogger(LicencyForSubAuaSystemViewUpdationController.class);
	
    @Override
    public OtpRespose getOtp(String uuid) {
    
    	OtpRespose otpResponse = new OtpRespose();
        try {
            String licenceKey = Constant.LICENCEKEY;
            AuthrequestEkyc authrequest = new AuthrequestEkyc();
            authrequest.setUid("609237480396");
            authrequest.setType("A");
            authrequest.setVer("2.5");
            authrequest.setSubaua(Constant.SUBAUA);
            authrequest.setIp("NA");
            authrequest.setFdc("NA");
            authrequest.setIdc("NA");
            authrequest.setBt("otp");
            authrequest.setMacadd("78-2B-CB-8D-93-F1");
            authrequest.setLot("P");
            authrequest.setLov("110002");
            authrequest.setLk(licenceKey);
            authrequest.setRc("Y");
            authrequest.setOtp(new AuthrequestEkyc.Otp());

            logger.info("authrequest=====>>" + jaxbObjectToXML(authrequest));

            String authResponse = restTemplate.postForObject(Constant.REQUESTURLOTP, authrequest, String.class);
            logger.info("authresponse=====>>" + authResponse);

            JAXBContext context = JAXBContext.newInstance(AuthResponseEntity.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader stringReader = new StringReader(authResponse);
            AuthResponseEntity authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);

            logger.info("authResponse=====>>" + authResponse);

            
            otpResponse.setAsaType(authResponseEntity.getAsaType());
            otpResponse.setTxn(authResponseEntity.getAuth().getTxn());
            otpResponse.setStatus(authResponseEntity.getAuth().getStatus());
            otpResponse.setUUID(authResponseEntity.getUUID());
            otpResponse.setResponseStatus(authResponseEntity.getStatus());

        } catch (JAXBException e) {
            logger.error("Error unmarshalling the response", e);
        } catch (Exception ex) {
            logger.error("Unexpected error", ex);
            
        }

        return otpResponse;
    }
    
    @Override
    public AuthResponseEntity authenticateOtp(OtpModel model ) {
        AuthResponseEntity authResponseEntity = null;
        try {
            // Generate timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStampStr = dateFormat.format(new Date()).replace(" ", "T");

            // Initialize encrypter
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/assets/uidai_auth_prod.cer");
            Encrypter encrypter = new Encrypter(is);
            String CI = encrypter.getCertificateIdentifier();

            // Create PID XML
            String PIDVER = "<Pid ts=\"" + timeStampStr + "\" ver=\"2.0\" wadh=\"\"><Pv otp =\""
                    + model.getOtp() + "\"/></Pid>";
            byte[] pidXmlBytes = PIDVER.getBytes("UTF-8");

            // Encrypt PID XML and generate session key
            byte[] sessionKey = encrypter.generateSessionKey();
            byte[] encryptedSkey = encrypter.encryptUsingPublicKey(sessionKey);
            byte[] encPidXmlBytes = encrypter.encryptUsingSessionKey(sessionKey, pidXmlBytes, timeStampStr);

            // Generate and encrypt HMAC
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hmac = digest.digest(pidXmlBytes);
            byte[] encryptedHmacBytes = encrypter.encryptUsingSessionKeyNoAdd(sessionKey, hmac, timeStampStr);

            // Encode values
            String encryptedSessionKey = Base64.getEncoder().encodeToString(encryptedSkey);
            String encryptedPidData = Base64.getEncoder().encodeToString(encPidXmlBytes);
            String encryptedHmac = Base64.getEncoder().encodeToString(encryptedHmacBytes);

            // Log generated values for debugging
            logger.info("PIDVER: {}", PIDVER);
            logger.info("Encrypted PID Data: {}", encryptedPidData);

            // Make OTP authentication request
            ResponseEntity<String> otpAuthResp = makeOtpAuthReq(encryptedSessionKey, encryptedPidData,
                    encryptedHmac, model, CI);
            JSONObject jsonobject = XML.toJSONObject(otpAuthResp.getBody());
            logger.info("OTP Auth Response: {}", jsonobject.toString());

            // Unmarshal the response into AuthResponseEntity
            JAXBContext context = JAXBContext.newInstance(AuthResponseEntity.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader stringReader = new StringReader(otpAuthResp.getBody());
            authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);

        } catch (JAXBException e) {
            logger.error("Error unmarshalling the response", e);
        } catch (Exception ex) {
            logger.error("Unexpected error", ex);
        }

        return authResponseEntity;
    }
	
	private String jaxbObjectToXML(AuthrequestEkyc authrequest) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(AuthrequestEkyc.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
			StringWriter sw = new StringWriter();
			m.marshal(authrequest, sw);
			xmlString = sw.toString();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xmlString;
	}
	
	public ResponseEntity<String> makeOtpAuthReq(String encryptedSesssion_Key, String encryptedPID_Data,
			String encrypted_HMac, OtpModel model, String CI) {

		String authXML = String
				.format("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><authrequest uid=\""
						+ "609237480396" + "\" tid=\"\" ver=\"2.5\" subaua=\"" + "0000440000"
						+ "\" bt=\"OTP\" ip=\"NA\" fdc=\"NA\" rc=\"Y\" lr=\"Y\" pfr=\"Y\" mec=\"Y\" macadd=\"NA\" rdsID=\"\" lot=\"P\" txn=\""
						+ model.getTxn()
						+ "\" dpID=\"\" rdsVer=\"\" dc=\"\" mi=\"\" mc=\"\" deviceSrNO=\"NA\" idc=\"NC\" lov=\"302005\" lk=\""
						+ Constant.LICENCEKEY
						+ "\"><deviceInfo fType=\"NA\" iCount=\"NA\" pCount=\"NA\" errCodeRDS=\"NA\" errInfoRDS=\"NA\" fCount=\"NA\" nmPoints=\"NA\" qScore=\"NA\" srno=\"NA\" deviceError=\"NA\"/><Skey ci=\""
						+ CI + "\">" + "%s" + "</Skey><Hmac>" + "%s" + "</Hmac><Data type=\"X\">" + "%s"
						+ "</Data></authrequest>", encryptedSesssion_Key, encrypted_HMac, encryptedPID_Data);



		System.out.println("ReQ :@@@" + authXML);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<String> httpEntity = new HttpEntity<String>(authXML, httpHeaders);

		// String

		ResponseEntity<String> responce = restTemplate.exchange(Constant.REQUESTURLOTPAUTH, HttpMethod.POST, httpEntity,
				String.class);
		System.out.println("Resp :@@@" + responce);

		return responce;

	}

	@Override
	public ResponseEntity<String> sendSms(String[] mobileno,String message) {
//		 String[] mobileno = { "7987085850" };
//		    String message = "Dear Sir/Madam, A document has been shared on your email- muneshlamba.doit@rajasthan.gov.in from Aadhaar Authentication Project, DoIT&C. Please use this Password to open the document- SRCIS22869@123. Please do not share the password with any unauthorized person to avoid misuse of the document. - DoIT&C (AUA), GoR";
	        ExternalSMSApiInfo inputParams = new ExternalSMSApiInfo();
	        inputParams.setUniqueID("UIDAI_SMS");
	        inputParams.setServiceName("aadhaar new portal");
	        inputParams.setLanguage("ENG");
	        inputParams.setMobileNo(mobileno);
	        inputParams.setTemplateId("1107171653411357142");
	        inputParams.setMessage(message);

	        ObjectMapper objectMapper = new ObjectMapper();
	        String json = null;
			try {
				json = objectMapper.writeValueAsString(inputParams);
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}

	        String url = "https://api.sewadwaar.rajasthan.gov.in/app/live/eSanchar/Prod/api/OBD/CreateSMS/Request?client_id=4555c739-a184-4f14-baf4-f456dd122622";

	        RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("username", "UidaiSms");
	        headers.set("password", "mS$89Udi__1#");
	        headers.set("Content-Type", "application/json");

	        HttpEntity<String> request = new HttpEntity<>(json, headers);

	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            System.out.println("Response: " + response.getBody());
	        } else {
	            System.out.println("Error: " + response.getStatusCode());
	        }

	        return response;
	    }
	
	@Override
	public  String encrypt(String data, String base64Key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] key = Base64.getDecoder().decode(base64Key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        // Generate IV
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        // Combine IV and encrypted data
        byte[] ivAndEncryptedData = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, ivAndEncryptedData, 0, iv.length);
        System.arraycopy(encryptedData, 0, ivAndEncryptedData, iv.length, encryptedData.length);

        return Base64.getEncoder().encodeToString(ivAndEncryptedData);
    }

    // Method to decrypt data using a given key
	@Override
    public  String decrypt(String encryptedData, String base64Key) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] key = Base64.getDecoder().decode(base64Key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        byte[] ivAndEncryptedData = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[ivAndEncryptedData.length - 16];

        System.arraycopy(ivAndEncryptedData, 0, iv, 0, iv.length);
        System.arraycopy(ivAndEncryptedData, iv.length, encryptedBytes, 0, encryptedBytes.length);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = cipher.doFinal(encryptedBytes);

        return new String(original);
    }
	
	@Override
	@Transactional
	public void update() {
		String sql = "update aadhaar.blocked_aadhaar set  aadhaar_id = null  where id  in('312','313','315', '311')";
				
		 entityManager.createNativeQuery(sql).executeUpdate();
		 
	}
	

}
