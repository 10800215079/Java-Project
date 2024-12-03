package com.gov.Authmis.constant;

public class Constant {

	/*
	 * public static final String SSO_URL = "https://sso.rajasthan.gov.in"; public
	 * static final String SSO_URL_HTTP = "http://sso.rajasthan.gov.in";
	 */
	
	
//	  public static final String SSO_URL = "https://sso.rajasthan.gov.in"; 
//	  public static final String SSO_URL_HTTP = "http://sso.rajasthan.gov.in";	 	 		 

	// public static final String SSO_URL = "https://ssotest.rajasthan.gov.in";

	
	
	  public static final String SSO_URL = "https://SSOTEST.RAJASTHAN.GOV.IN";
	  public static final String SSO_URL_HTTP = "http://ssotest.rajasthan.gov.in";
	  public static final String ENVIROMENT = "Pre-production";

//		 production URL IP whiteListing	  
	//	 public static final String IPWHITELISTED_URL =  "https://aadhaarfaceauth.rajasthan.gov.in/AadhaarFaceAuthAPI/api/User/IPWhitelistSubAua";		
//		 production URL Update Create User and subaua	  
//		 public static final String UPDATECREATE_URL =  "https://aadhaarfaceauth.rajasthan.gov.in/AadhaarFaceAuthAPI/api/User/CreateOrUpdateSUBAUA";

//		 Pre production URL IP whiteListing
//		public static final String IPWHITELISTED_URL = "https://aadhaarfaceauthtest.rajasthan.gov.in/GenericFaceAuthApi/api/User/IPWhitelistSubAua";
		
//		 Pre production URL Update Create User and subaua
		public static final String UPDATECREATE_URL = "https://aadhaarfaceauthtest.rajasthan.gov.in/GenericFaceAuthApi/api/User/CreateOrUpdateSUBAUA";

	// https://ssotest.rajasthan.gov.in:4443/SSOREST/GetTokenDetailNewJson/1234

	public static final String FILE_PATH = "F:\\uploadfile\\";

	public static final String FILE_PATH_APPROVALDOC = "F:\\ApprovalDoc\\";

	public static final String BACK_TO_SSO = SSO_URL + "/sso";
	public static final String SSO_LOGOUT = SSO_URL + "/signout";

	public static final String SSO_GET_TOKEN_DETAILS_URL = SSO_URL + ":4443/SSOREST/GetTokenDetailNewJson/";
	// public static final String SSO_GET_TOKEN_DETAILS_URL = SSO_URL +
	// ":4443/SSOREST/GetTokenDetailNewJson/";
	// public static final String SSO_GET_TOKEN_DETAILS_URL = SSO_URL +
	// ":4443/SSOREST/GetTokenDetailNewJson/1234";

	public static final String PDF_PATH = "F:\\upload\\";
	// public static final String
	// ENC_DEC_URL="https://apitest.sewadwaar.rajasthan.gov.in/app/live/Aadhaar/Staging/detokenizeV2/doitAadhaar/encDec/demo/hsm/auth?client_id=5a33291f-14d3-459a-90cd-bd13382371ce";
	// public static final String ENC_DEC_URL =
	// "https://aadhaarauthapi.rajasthan.gov.in/doit-aadhaar-enc-dec/demo/hsm/auth/detokenizeV2";
	// public static final String ENC_DEC_URL_tokenize =
	// "https://aadhaarauthapi.rajasthan.gov.in/doit-aadhaar-enc-dec/demo/hsm/auth/tokenizeV2";
	public static final String SSO_GET_USER_PROFILE = "https://sso.rajasthan.gov.in:4443/SSOREST/GetUserDetailJSON";
	public static final String ENC_DEC_URL = "https://aadhaarauthapi.rajasthan.gov.in/doit-aadhaar-enc-dec/demo/hsm/auth/tokenizeV2";

//	//PRE-PRODUCTION PARAMS
//		public static String SUBAUA="STGDOITRAJ";  
//	    public static String ENV="Pre-Prod";
//	    public static String LICENCEKEY = "MF5hI5L1yC4Pc2c4-BJdT9IaxG_PsMzAnoNsCSwh9ciK_3ayEIk-1NE"; //Preprod 
//		public static String CERTIFICATE ="F:\\DOIT\\Certificate\\Aadhaar\\Certificate_2023\\uidai_auth_preprod.cer";

	// PRODUCTION PARAMS
	public static String SUBAUA = "0000440000";// "PSJED22863"; //PRSLD22858 0000440000
	public static String ENV = "Prod";
	public static String LICENCEKEY = "MGZc7nnfh2VT3oSlWxf7PstAndcSLG7fYTU_4XnAqeyfkOy9xeFy294"; // production LK till
																									// 23/01/2024
	// public static String CERTIFICATE =
	// "F:\\DOIT\\Certificate\\Aadhaar\\Certificate_2023\\uidai_auth_prod.cer";

	// productiion SEWADWAAR URL
	public static String REQUESTURLOTP = "https://aadhaarauthapi.rajasthan.gov.in/api/aua/otp/request/encr";
	public static String REQUESTURLOTPAUTH = "https://aadhaarauthapi.rajasthan.gov.in/api/aua/otp/auth/encr";

	// PRODUCTION SEWADWAAR URL
//		public static String REQUESTURLOTP="https://api.sewadwaar.rajasthan.gov.in/app/live/was/otp/request/prod?client_id=977c74a2-672b-4c2c-b8d3-4bbe1368d195";
//		public static String REQUESTURLOTPAUTH="https://api.sewadwaar.rajasthan.gov.in/app/live/was/otp/auth/prod?client_id=977c74a2-672b-4c2c-b8d3-4bbe1368d195";
//		public static String REQUESTURLOTPKYC ="https://api.sewadwaar.rajasthan.gov.in/app/live/was/kyc/otp/prod?client_id=977c74a2-672b-4c2c-b8d3-4bbe1368d195";
   public static final String key = "E10Q4Zzueue03rLaxSKGqHp9S0YVLJusLPfiIoqH+vA=";
}