package com.gov.Authmis.model;

import java.util.Date;

public class WhiteListSubauaForQueryBuilderModel {

	/**
	 * "SSO_ID" VARCHAR2(150 BYTE), 
    "IP" VARCHAR2(50 BYTE), 
    "CREATED_DATE" DATE, 
    "CREATED_BY" VARCHAR2(250 BYTE), 
	"AADHHAR_ID" VARCHAR2(100 BYTE), 
    "TRANSACTION_ID" VARCHAR2(200 BYTE),
	"STATUS" NUMBER(10,0) 	
	 **/

	private Long SRNO;
	private String SSO_ID;
	private String IP;
	
	
	
	public Long getSRNO() {
		return SRNO;
	}
	public void setSRNO(Long sRNO) {
		SRNO = sRNO;
	}
	public String getSSO_ID() {
		return SSO_ID;
	}
	public void setSSO_ID(String sSO_ID) {
		SSO_ID = sSO_ID;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public WhiteListSubauaForQueryBuilderModel(Long sRNO, String sSO_ID, String iP) {
		super();
		SRNO = sRNO;
		SSO_ID = sSO_ID;
		IP = iP;
	}
	@Override
	public String toString() {
		return "WhiteListSubauaForQueryBuilderModel [SRNO=" + SRNO + ", SSO_ID=" + SSO_ID + ", IP=" + IP + "]";
	}
	public WhiteListSubauaForQueryBuilderModel() {
		super();
		// TODO Auto-generated constructor stub
	}



	
}
