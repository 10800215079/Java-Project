package com.gov.Authmis.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.bouncycastle.crypto.InvalidCipherTextException;
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

import com.google.gson.Gson;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.controller.LicencyForSubAuaSystemViewUpdationController;
import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.AuthrequestEkyc;
import com.gov.Authmis.entity.OtpAuthenticationRequest;
import com.gov.Authmis.entity.OtpAuthenticationRequestforQueryBuilder;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;
import com.gov.Authmis.jpa.OtpAuthenticationRequestRepository;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.AddLicencyForSubAuaSystemService;
import com.gov.Authmis.service.SubAuaRegistrationService;
import com.itextpdf.text.DocumentException;

@Service
public class SubAuaRegistrationServiceImpl implements SubAuaRegistrationService {

	@Autowired
	private OtpAuthenticationRequestRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	public SubAuaWiseSendMail subAuaWiseSendMail;
	
	@Autowired
	private AddLicencyForSubAuaSystemService addLicencyForSubAuaSystemservice;
	
	Logger logger = LoggerFactory.getLogger(LicencyForSubAuaSystemViewUpdationController.class);

	@Override
	public List<UsersRoleMapping> getSsoProfile(String ssoId) {
		CloseableHttpClient client1;
		StringEntity inputStr;
		List<UsersRoleMapping> userProfile = new ArrayList<UsersRoleMapping>();
		UsersRoleMapping ssoUserProfile;

		try {
			client1 = HttpClients.custom()
					.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

			HttpPost post1 = new HttpPost(Constant.SSO_GET_USER_PROFILE);
			/*
			 * HttpGet get1 = new HttpGet(
			 * "https://sso.rajasthan.gov.in:4443/SSOREST/GetTokenDetailNewJson/S3loeTFsMmdqYy85WG91YUVXb2FGaysybHZuaWVYSzljd28zNEQ3ZzNFK0xIYWxUWTJhVkpnaWRZR2p0b3V2cW5nTFpGYThBVHBiVEFnTWtRRDBzeW9HNVpBRWJBWk1DNDAzVnhUdXdxYWVSbDM4K2dGVmFMNUJTYkYzQUhObyttU05Ebko4TjQzZXFrcmpxMGZ3TXlDSjhmRnFPTmsxTk1vUU16TU1PWWsyOEYvanJDcWVWTzdtUldDRE1RUCt5"
			 * );
			 */
			inputStr = new StringEntity(
					"{ \"SSOID\":\"" + ssoId + "\",\"WSUSERNAME\":\"UMS.TEST\",\"WSPASSWORD\":\"UmFqYXN0aGFuQDE5\" }");
			System.out.println("inputStr===>>" + inputStr);

			post1.setEntity(inputStr);
			post1.setHeader("Content-Type", "application/json");
			System.out.println("Going to call service.....1");
			CloseableHttpResponse resp1 = client1.execute(post1);
			BufferedReader rd1 = new BufferedReader(new InputStreamReader(resp1.getEntity().getContent()));
			System.out.println("Going to call service.....2resp1===>" + resp1);
			String line1 = "";
			StringBuilder res1 = new StringBuilder();
			while ((line1 = rd1.readLine()) != null) {
				res1.append(line1);
			}
			System.out.println("res1==========>>" + res1);

			Gson g = new Gson();
			ssoUserProfile = g.fromJson(res1.toString(), UsersRoleMapping.class);

			System.out.println("SsoUserProfile==>>>>1" + ssoUserProfile);
			System.out.println("SsoUserProfile==>>>>1" + ssoUserProfile.getAadhaarId());
			userProfile.add(ssoUserProfile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return userProfile;
	}

	@Override
	public List<OtpRespose> getOtp(String UuId) {

		List<OtpRespose> otpRes = new ArrayList<OtpRespose>();

		try {		

			String licenceKey = Constant.LICENCEKEY; // preprod LK new
			AuthrequestEkyc authrequest = new AuthrequestEkyc();
			authrequest.setUid("609237480396");// 204846991706,
			// 368913088381, 304117523334, 526163615289 634678973487
			authrequest.setType("A");
			authrequest.setVer("2.5");// 368228
			// authrequest.setSubaua("0000440000");//711592060075
			authrequest.setSubaua(Constant.SUBAUA);
			authrequest.setIp("NA");

			authrequest.setFdc("NA");
			authrequest.setIdc("NA");
			authrequest.setBt("otp");
			// authrequest.setUdc("test123");

			authrequest.setMacadd("78-2B-CB-8D-93-F1");
			authrequest.setLot("P");
			authrequest.setLov("110002");
			authrequest.setLk(licenceKey);
			authrequest.setRc("Y");
			AuthrequestEkyc.Otp otp = new AuthrequestEkyc.Otp();
			authrequest.setOtp(otp);
			System.out.println("authrequest=====>>" + jaxbObjectToXML(authrequest));

			String authResponse = restTemplate.postForObject(Constant.REQUESTURLOTP, authrequest, String.class);
			System.out.println("authresponse=====>>" + authResponse);
			JAXBContext context = JAXBContext.newInstance(AuthResponseEntity.class);

			// Create an unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// Unmarshal the XML data into an AuthResponse object
			StringReader stringReader = new StringReader(authResponse);
			AuthResponseEntity authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);

			System.out.println("authResponse=====>>" + authResponse);


			OtpRespose otpResponse = new OtpRespose();
			otpResponse.setAsaType(authResponseEntity.getAsaType());
			otpResponse.setTxn(authResponseEntity.getAuth().getTxn());
			otpResponse.setStatus(authResponseEntity.getAuth().getStatus());
			otpResponse.setUUID(authResponseEntity.getUUID());
			otpResponse.setResponseStatus(authResponseEntity.getStatus());
			otpRes.add(otpResponse);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return otpRes;
	}

	@Override
	@Transactional
	public OtpRespose OtpAuthentication(OtpAuthenticationRequest otpAuthenticationRequest) {
		OtpRespose resp = new OtpRespose();

		JSONObject jsonobject = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStampStr = dateFormat.format(new Date()).replace(" ", "T");
		String CI = "";
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/assets/uidai_auth_prod.cer");
		Encrypter encrypter = new Encrypter(is);
		try {
			String PIDVER = "<Pid ts=\"" + timeStampStr + "\" ver=\"2.0\" wadh=\"\"><Pv otp =\""
					+ otpAuthenticationRequest.getOtp() + "\"/></Pid>";
			byte[] pidXmlBytes = PIDVER.getBytes("UTF-8");
			byte[] session_key = encrypter.generateSessionKey();
			byte[] encrypted_skey = encrypter.encryptUsingPublicKey(session_key);
			CI = encrypter.getCertificateIdentifier();
			byte[] encPidXmlBytes = encrypter.encryptUsingSessionKey(session_key, pidXmlBytes, timeStampStr);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hmac = digest.digest(pidXmlBytes);

			byte[] encryptedHmacByytes = encrypter.encryptUsingSessionKeyNoAdd(session_key, hmac, timeStampStr);
			System.out.println("PIDVER:=" + PIDVER);
			String encryptedSesssion_Key = Base64.getEncoder().encodeToString(encrypted_skey);
			String encryptedPID_Data = Base64.getEncoder().encodeToString(encPidXmlBytes);
			String encrypted_HMac = Base64.getEncoder().encodeToString(encryptedHmacByytes);
			System.out.println("encryptedPID_Data:=" + encryptedPID_Data);
			ResponseEntity<String> otpAuthResp = makeOtpAuthReq(encryptedSesssion_Key, encryptedPID_Data,
					encrypted_HMac, otpAuthenticationRequest, CI);
			jsonobject = XML.toJSONObject(otpAuthResp.getBody());

			JAXBContext context;
			AuthResponseEntity authResponseEntity = new AuthResponseEntity();
			try {
				context = JAXBContext.newInstance(AuthResponseEntity.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				StringReader stringReader = new StringReader(otpAuthResp.getBody());
				authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			System.out.println("authResponse=====>>" + authResponseEntity);

			String subAuaCode = "";

			otpAuthenticationRequest.setTxn(authResponseEntity.getAuth().getTxn());

			resp.setStatus(authResponseEntity.getAuth().getStatus());
			resp.setError(authResponseEntity.getMessage());
			LocalDate currentDate = LocalDate.now();
			String currentDateStr = currentDate.toString();
			otpAuthenticationRequest.setCreatedDate(currentDateStr);
			otpAuthenticationRequest.setUpdatedDate(currentDateStr);
			otpAuthenticationRequest.setActive(1L);

			String base64FileData = Base64.getEncoder().encodeToString(otpAuthenticationRequest.getApprovalDoc());
			byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);

			String uniqueFileName = "ApprovalDoc" + "_" + new Date().getTime();

			String uploadDir = Constant.FILE_PATH_APPROVALDOC + uniqueFileName + ".pdf";
			otpAuthenticationRequest.setUrl(uploadDir);
			// Create the file on the server
			File serverFile = new File(uploadDir);

			if (resp.getStatus().toUpperCase().equals("Y")) {
				if (otpAuthenticationRequest.getSubAuaCode() == null
						|| otpAuthenticationRequest.getSubAuaCode().isEmpty()) {
					subAuaCode = extractInitialLetters(otpAuthenticationRequest.getOrgName());
					String randomNuber = generateRandom(10 - subAuaCode.length() - 1);
					otpAuthenticationRequest.setSubAuaCode("P" + subAuaCode + randomNuber);
				}

				
				
				OtpAuthenticationRequest authenticationRequest =	repository.save(otpAuthenticationRequest);
				if(authenticationRequest != null ) {
					if(otpAuthenticationRequest.getIsRegWithUid() == 0) {
						String subauaLk = generateRandomString();
						LocalDate date = genrateExpiryDate();
						Date expiryDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
						
						List<String> kuaLk = getKuaLk();
						
						List<String> kuaCode = getKuaCode();
						
								
						AddLicencyForSubAuaSystem addLicencyForSubAuaSystem = new AddLicencyForSubAuaSystem();
						addLicencyForSubAuaSystem.setSubAuaCode(otpAuthenticationRequest.getSubAuaCode());
						addLicencyForSubAuaSystem.setKuaLk(kuaLk.get(0));
						java.sql.Date sqlExpiryDate = new java.sql.Date(expiryDate.getTime());
						addLicencyForSubAuaSystem.setExpiryDate(sqlExpiryDate);
						addLicencyForSubAuaSystem.setSubAuaLk(subauaLk);
						addLicencyForSubAuaSystem.setKuaCode(kuaCode.get(0));
						addLicencyForSubAuaSystem.setSubAuaName(otpAuthenticationRequest.getOrgName());
						addLicencyForSubAuaSystemservice.save(addLicencyForSubAuaSystem);
						//SubAuaWiseSendMailServiceImpl sendEmailDao = new SubAuaWiseSendMailServiceImpl();
						
						//List<Map<String, Object>> listOfData = sendEmailDao.getSubAuaData(otpAuthenticationRequest.getSubAuaCode());
//						Map<String, Object> row = new HashMap<String, Object>();
//						row.put("EMAIL", otpAuthenticationRequest.getEmail());
//						row.put("ORGNAME", otpAuthenticationRequest.getOrgName());
//						row.put("EXPIRY_DATE", addLicencyForSubAuaSystem.getExpiryDate());
//						List<Map<String, Object>> listOfData = new ArrayList<Map<String, Object>>();
//						listOfData.add(row);
						
						try {
							String response = addUserForGenricFaceAuth(otpAuthenticationRequest.getSubAuaCode(), otpAuthenticationRequest.getOrgName(),
									addLicencyForSubAuaSystem.getSubAuaLk());
							JSONObject jsonResponse = new JSONObject(response.substring(0, response.indexOf("}") + 1));
						    boolean isSuccess = jsonResponse.getBoolean("IsSuccess");
						    if (!isSuccess) {
						        throw new RuntimeException("External Server Error: Unable to Register Subaua");
						    }
						} catch (Exception e) {
						    // Rollback the transaction
						    throw new RuntimeException("Transaction rollback", e);
						}
						
//						subAuaWiseSendMail.SendEmailToSubAua(listOfData, otpAuthenticationRequest.getSubAuaCode(),
//								subauaLk, expiryDate);
					}
				} else {
					resp.setStatus("n");
					resp.setError("Enternal Server Error Unable to Register Subaua");
				} 
				
				
				

				try (FileOutputStream stream = new FileOutputStream(serverFile)) {
					stream.write(decodedFileData);
				}
				
			

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return resp;

	}

	public String extractInitialLetters(String input) {
		if (input == null) {
			return "";
		}

		String[] words = input.split(" ");
		StringBuilder initials = new StringBuilder();

		for (String word : words) {
			if (!word.isEmpty() && !word.equalsIgnoreCase("and") && !word.equalsIgnoreCase("of")) {
				initials.append(word.charAt(0));
				if (initials.length() == 4) {
					break;
				}
			}
		}

		return initials.toString();
	}

	public String generateRandom(int number) {
		Random random = new Random();
		int min = (int) Math.pow(10, number - 1);
		int max = (int) Math.pow(10, number) - 1;
		int randomNumber = min + random.nextInt(max - min + 1);
		return Integer.toString(randomNumber);
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

	@Override
	public boolean isRegistered(OtpAuthenticationRequest otpAuthenticationRequest) {

		String str = "select subaua_code from AADHAAR.subaua where upper(subaua_code)=upper('"
				+ otpAuthenticationRequest.getSubAuaCode() + "') and status=1";
		Query query = entityManager.createNativeQuery(str);

		@SuppressWarnings("unchecked")
		List<String> subAua = query.getResultList();
		if (subAua.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public ResponseEntity<String> makeOtpAuthReq(String encryptedSesssion_Key, String encryptedPID_Data,
			String encrypted_HMac, OtpAuthenticationRequest otpAuthenticationRequest, String CI) {

		String authXML = String
				.format("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><authrequest uid=\""
						+ "609237480396" + "\" tid=\"\" ver=\"2.5\" subaua=\"" + "0000440000"
						+ "\" bt=\"OTP\" ip=\"NA\" fdc=\"NA\" rc=\"Y\" lr=\"Y\" pfr=\"Y\" mec=\"Y\" macadd=\"NA\" rdsID=\"\" lot=\"P\" txn=\""
						+ otpAuthenticationRequest.getTxn()
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

	@SuppressWarnings("unused")
	@Override
	public AuthResponseEntity athentication(OtpAuthenticationRequest otpAuthenticationRequest) {
		OtpRespose resp = new OtpRespose();
		JSONObject jsonobject = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStampStr = dateFormat.format(new Date()).replace(" ", "T");

		String CI = "";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/assets/uidai_auth_prod.cer");
		Encrypter encrypter = new Encrypter(is);
		AuthResponseEntity authResponseEntity = new AuthResponseEntity();

		try {
			String PIDVER = "<Pid ts=\"" + timeStampStr + "\" ver=\"2.0\" wadh=\"\"><Pv otp =\""
					+ otpAuthenticationRequest.getOtp() + "\"/></Pid>";
			byte[] pidXmlBytes = PIDVER.getBytes("UTF-8");
			byte[] session_key = encrypter.generateSessionKey();
			byte[] encrypted_skey = encrypter.encryptUsingPublicKey(session_key);
			CI = encrypter.getCertificateIdentifier();
			byte[] encPidXmlBytes = encrypter.encryptUsingSessionKey(session_key, pidXmlBytes, timeStampStr);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hmac = digest.digest(pidXmlBytes);

			byte[] encryptedHmacByytes = encrypter.encryptUsingSessionKeyNoAdd(session_key, hmac, timeStampStr);
			System.out.println("PIDVER:=" + PIDVER);

			String encryptedSesssion_Key = Base64.getEncoder().encodeToString(encrypted_skey);
			String encryptedPID_Data = Base64.getEncoder().encodeToString(encPidXmlBytes);
			String encrypted_HMac = Base64.getEncoder().encodeToString(encryptedHmacByytes);

			System.out.println("encryptedPID_Data:=" + encryptedPID_Data);

			ResponseEntity<String> otpAuthResp = makeOtpAuthReq(encryptedSesssion_Key, encryptedPID_Data,
					encrypted_HMac, otpAuthenticationRequest, CI);
			jsonobject = XML.toJSONObject(otpAuthResp.getBody());

			JAXBContext context;

			try {
				context = JAXBContext.newInstance(AuthResponseEntity.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				StringReader stringReader = new StringReader(otpAuthResp.getBody());
				authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			System.out.println("authResponse=====>>" + authResponseEntity);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}

		return authResponseEntity;

	}

	// For Query Builder
	@SuppressWarnings("unused")
	@Override
	public AuthResponseEntity athentication(
			OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder) {
		OtpRespose resp = new OtpRespose();
		JSONObject jsonobject = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStampStr = dateFormat.format(new Date()).replace(" ", "T");

		String CI = "";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/assets/uidai_auth_prod.cer");
		Encrypter encrypter = new Encrypter(is);
		AuthResponseEntity authResponseEntity = new AuthResponseEntity();

		try {
			String PIDVER = "<Pid ts=\"" + timeStampStr + "\" ver=\"2.0\" wadh=\"\"><Pv otp =\""
					+ otpAuthenticationRequestforQueryBuilder.getOtp() + "\"/></Pid>";
			byte[] pidXmlBytes = PIDVER.getBytes("UTF-8");
			byte[] session_key = encrypter.generateSessionKey();
			byte[] encrypted_skey = encrypter.encryptUsingPublicKey(session_key);
			CI = encrypter.getCertificateIdentifier();
			byte[] encPidXmlBytes = encrypter.encryptUsingSessionKey(session_key, pidXmlBytes, timeStampStr);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hmac = digest.digest(pidXmlBytes);

			byte[] encryptedHmacByytes = encrypter.encryptUsingSessionKeyNoAdd(session_key, hmac, timeStampStr);
			System.out.println("PIDVER:=" + PIDVER);

			String encryptedSesssion_Key = Base64.getEncoder().encodeToString(encrypted_skey);
			String encryptedPID_Data = Base64.getEncoder().encodeToString(encPidXmlBytes);
			String encrypted_HMac = Base64.getEncoder().encodeToString(encryptedHmacByytes);

			System.out.println("encryptedPID_Data:=" + encryptedPID_Data);

			ResponseEntity<String> otpAuthResp = makeOtpAuthReqForQueryBuilder(encryptedSesssion_Key, encryptedPID_Data,
					encrypted_HMac, otpAuthenticationRequestforQueryBuilder, CI);
			jsonobject = XML.toJSONObject(otpAuthResp.getBody());

			JAXBContext context;

			try {
				context = JAXBContext.newInstance(AuthResponseEntity.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				StringReader stringReader = new StringReader(otpAuthResp.getBody());
				authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			System.out.println("authResponse=====>>" + authResponseEntity);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}

		return authResponseEntity;

	}

	// For Query Builder
	public ResponseEntity<String> makeOtpAuthReqForQueryBuilder(String encryptedSesssion_Key, String encryptedPID_Data,
			String encrypted_HMac, OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder,
			String CI) {
		String authXML = String
				.format("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><authrequest uid=\""
						+ otpAuthenticationRequestforQueryBuilder.getUuid() + "\" tid=\"\" ver=\"2.5\" subaua=\"" + "0000440000"
						+ "\" bt=\"OTP\" ip=\"NA\" fdc=\"NA\" rc=\"Y\" lr=\"Y\" pfr=\"Y\" mec=\"Y\" macadd=\"NA\" rdsID=\"\" lot=\"P\" txn=\""
						+ otpAuthenticationRequestforQueryBuilder.getTxn()
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

	// For White listing sso
	@SuppressWarnings("unused")
	@Override
	public AuthResponseEntity athenticationForWhiteListSSO(
			WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity) {
		OtpRespose resp = new OtpRespose();
		JSONObject jsonobject = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStampStr = dateFormat.format(new Date()).replace(" ", "T");

		String CI = "";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/assets/uidai_auth_prod.cer");
		Encrypter encrypter = new Encrypter(is);
		AuthResponseEntity authResponseEntity = new AuthResponseEntity();

		try {
			String PIDVER = "<Pid ts=\"" + timeStampStr + "\" ver=\"2.0\" wadh=\"\"><Pv otp =\""
					+ whiteListSubauaForQueryBuilderEntity.getOtp() + "\"/></Pid>";
			byte[] pidXmlBytes = PIDVER.getBytes("UTF-8");
			byte[] session_key = encrypter.generateSessionKey();
			byte[] encrypted_skey = encrypter.encryptUsingPublicKey(session_key);
			CI = encrypter.getCertificateIdentifier();
			byte[] encPidXmlBytes = encrypter.encryptUsingSessionKey(session_key, pidXmlBytes, timeStampStr);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hmac = digest.digest(pidXmlBytes);

			byte[] encryptedHmacByytes = encrypter.encryptUsingSessionKeyNoAdd(session_key, hmac, timeStampStr);
			System.out.println("PIDVER:=" + PIDVER);

			String encryptedSesssion_Key = Base64.getEncoder().encodeToString(encrypted_skey);
			String encryptedPID_Data = Base64.getEncoder().encodeToString(encPidXmlBytes);
			String encrypted_HMac = Base64.getEncoder().encodeToString(encryptedHmacByytes);

			System.out.println("encryptedPID_Data:=" + encryptedPID_Data);

			ResponseEntity<String> otpAuthResp = makeOtpAuthReqForWhitelistingSSO(encryptedSesssion_Key, encryptedPID_Data,
					encrypted_HMac, whiteListSubauaForQueryBuilderEntity, CI);
			jsonobject = XML.toJSONObject(otpAuthResp.getBody());

			JAXBContext context;

			try {
				context = JAXBContext.newInstance(AuthResponseEntity.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				StringReader stringReader = new StringReader(otpAuthResp.getBody());
				authResponseEntity = (AuthResponseEntity) unmarshaller.unmarshal(stringReader);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			System.out.println("authResponse=====>>" + authResponseEntity);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}

		return authResponseEntity;
	}
	
	// For Query Builder
	public ResponseEntity<String> makeOtpAuthReqForWhitelistingSSO(String encryptedSesssion_Key, String encryptedPID_Data,
			String encrypted_HMac, WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity,
			String CI) {
		String authXML = String
				.format("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><authrequest uid=\""
						+ whiteListSubauaForQueryBuilderEntity.getAadhaarId() + "\" tid=\"\" ver=\"2.5\" subaua=\"" + "0000440000"
						+ "\" bt=\"OTP\" ip=\"NA\" fdc=\"NA\" rc=\"Y\" lr=\"Y\" pfr=\"Y\" mec=\"Y\" macadd=\"NA\" rdsID=\"\" lot=\"P\" txn=\""
						+ whiteListSubauaForQueryBuilderEntity.getTRANSACTION_ID()
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
	
	public String generateRandomString() {
        // Define characters allowed in the random string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        // Generate random characters of the specified length
        for (int i = 0; i < 52; i++) {
            // Get a random index to select a character from the characters string
            int index = random.nextInt(characters.length());
            // Append the selected character to the string
            sb.append(characters.charAt(index));
        }
        
        return sb.toString();
    }

	public LocalDate genrateExpiryDate() {
		
		LocalDate currentDate = LocalDate.now();
 
        LocalDate futureDate = currentDate.plusYears(2);
       
		return futureDate;
		
	}
	
	public List<String> getKuaLk(){
		String sql=" SELECT KUA_LK  FROM SUBAUA_EKYC_LK sel WHERE SUB_AUA_CODE  = 'STGDOITRAJ' and status =1 ";
		Query query = this.entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data =  query.getResultList();
		return data;
}
	
	@Override
	@Transactional
	public int updateSubaua(Long tid) {
	    String sql = "UPDATE AADHAAR.SUBAUA SET active = 1 WHERE TID = :tid";
	    return entityManager.createNativeQuery(sql)
	            .setParameter("tid", tid)
	            .executeUpdate();
	}

	
	public List<String> getKuaCode(){
		String sql=" SELECT KUA_CODE  FROM SUBAUA_EKYC_LK sel WHERE SUB_AUA_CODE  = 'STGDOITRAJ' and status =1 ";
		Query query = this.entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data =  query.getResultList();
		return data;
}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getSubAuaData(String subAuaCode) {
        List<Map<String,Object>> subAuaData = null;
        try {
            Query nativeQuery = entityManager.createNativeQuery(
                    "SELECT A.SUBAUA_CODE, A.EMAIL, A.ORGNAME  " +
                    "FROM AADHAAR.SUBAUA A  " +
                    "WHERE A.SUBAUA_CODE = :subAuaCode AND ACTIVE = '1'"
            );
            nativeQuery.setParameter("subAuaCode", subAuaCode);
            subAuaData = nativeQuery.getResultList();
        } catch (Exception e) {
            System.out.println("Error while executing native query: " + e);
        }
        return subAuaData;
    }
	
public String addUserForGenricFaceAuth( String subauaCode, String subAuaName,String lk ) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = "{ \"LK\": \"" + lk + "\", \"SUBAUA_CODE\": \"" + subauaCode + "\", \"ORG_NAME\": \"" + subAuaName + "\", \"IS_ACTIVE\": 1 }";
		logger.info("requestBody==================="+ requestBody);
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
}
