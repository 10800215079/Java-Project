package com.gov.Authmis.model;

import javax.persistence.StoredProcedureParameter;

public class SSOIDUserDetails {
	
	private String USER_ID;
	private String EMAIL;
	private String PASSWORD;
	private String SSOID;
	private String ROLE_ID;
	private String ROLE_NAME;
	private String ORG_ID;
	private String ORG_NAME;
	private String STATUS;
	private String CREATED_DATE;
	public String getUSER_ID() {
		return USER_ID;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public String getSSOID() {
		return SSOID;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public String getORG_ID() {
		return ORG_ID;
	}
	public String getORG_NAME() {
		return ORG_NAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public void setSSOID(String sSOID) {
		SSOID = sSOID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public void setORG_ID(String oRG_ID) {
		ORG_ID = oRG_ID;
	}
	public void setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public SSOIDUserDetails(String uSER_ID, String eMAIL, String pASSWORD, String sSOID, String rOLE_ID,
			String rOLE_NAME, String oRG_ID, String oRG_NAME, String sTATUS, String cREATED_DATE) {
		super();
		USER_ID = uSER_ID;
		EMAIL = eMAIL;
		PASSWORD = pASSWORD;
		SSOID = sSOID;
		ROLE_ID = rOLE_ID;
		ROLE_NAME = rOLE_NAME;
		ORG_ID = oRG_ID;
		ORG_NAME = oRG_NAME;
		STATUS = sTATUS;
		CREATED_DATE = cREATED_DATE;
	}
	public SSOIDUserDetails() {
		super();
	}
	@Override
	public String toString() {
		return "SSOIDUserDetails [USER_ID=" + USER_ID + ", EMAIL=" + EMAIL + ", PASSWORD=" + PASSWORD + ", SSOID="
				+ SSOID + ", ROLE_ID=" + ROLE_ID + ", ROLE_NAME=" + ROLE_NAME + ", ORG_ID=" + ORG_ID + ", ORG_NAME="
				+ ORG_NAME + ", STATUS=" + STATUS + ", CREATED_DATE=" + CREATED_DATE + "]";
	}
	
	

}
