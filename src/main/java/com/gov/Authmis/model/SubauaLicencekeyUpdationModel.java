package com.gov.Authmis.model;

import java.sql.Date;

public class SubauaLicencekeyUpdationModel {

	private Long TID;
	private String SUBAUA_CODE;
	private String SUBAUA_LK;
	private String SUBAUA_CERFILE;
	private Date FROMDATE;
	private Date TODATE;
	private String ORGNAME;
	private String CERFILE;
	private String DEPARTMENT;
	private String OTHERDEPART;
	private String DEPARTKID;
	private String ACTIVE;
	private String OFFICERNAME;
	private String PHONE;
	private String EMAIL;
	private String APP_NAME;
	private String TYPEOFAPPLICATION;
	private String TEST_EMAIL;

	public SubauaLicencekeyUpdationModel(Long tID, String sUBAUA_CODE, String sUBAUA_LK, String sUBAUA_CERFILE,
			Date fROMDATE, Date tODATE, String oRGNAME, String cERFILE, String dEPARTMENT, String oTHERDEPART,
			String dEPARTKID, String aCTIVE, String oFFICERNAME, String pHONE, String eMAIL, String aPP_NAME,
			String tYPEOFAPPLICATION, String tEST_EMAIL) {
		super();
		TID = tID;
		SUBAUA_CODE = sUBAUA_CODE;
		SUBAUA_LK = sUBAUA_LK;
		SUBAUA_CERFILE = sUBAUA_CERFILE;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
		ORGNAME = oRGNAME;
		CERFILE = cERFILE;
		DEPARTMENT = dEPARTMENT;
		OTHERDEPART = oTHERDEPART;
		DEPARTKID = dEPARTKID;
		ACTIVE = aCTIVE;
		OFFICERNAME = oFFICERNAME;
		PHONE = pHONE;
		EMAIL = eMAIL;
		APP_NAME = aPP_NAME;
		TYPEOFAPPLICATION = tYPEOFAPPLICATION;
		TEST_EMAIL = tEST_EMAIL;
	}

	public SubauaLicencekeyUpdationModel() {
		super();
	}

	public Long getTID() {
		return TID;
	}

	public String getSUBAUA_CODE() {
		return SUBAUA_CODE;
	}

	public String getSUBAUA_LK() {
		return SUBAUA_LK;
	}

	public String getSUBAUA_CERFILE() {
		return SUBAUA_CERFILE;
	}

	public Date getFROMDATE() {
		return FROMDATE;
	}

	public Date getTODATE() {
		return TODATE;
	}

	public String getORGNAME() {
		return ORGNAME;
	}

	public String getCERFILE() {
		return CERFILE;
	}

	public String getDEPARTMENT() {
		return DEPARTMENT;
	}

	public String getOTHERDEPART() {
		return OTHERDEPART;
	}

	public String getDEPARTKID() {
		return DEPARTKID;
	}

	public String getACTIVE() {
		return ACTIVE;
	}

	public String getOFFICERNAME() {
		return OFFICERNAME;
	}

	public String getPHONE() {
		return PHONE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public String getTYPEOFAPPLICATION() {
		return TYPEOFAPPLICATION;
	}

	public String getTEST_EMAIL() {
		return TEST_EMAIL;
	}

	public void setTID(Long tID) {
		TID = tID;
	}

	public void setSUBAUA_CODE(String sUBAUA_CODE) {
		SUBAUA_CODE = sUBAUA_CODE;
	}

	public void setSUBAUA_LK(String sUBAUA_LK) {
		SUBAUA_LK = sUBAUA_LK;
	}

	public void setSUBAUA_CERFILE(String sUBAUA_CERFILE) {
		SUBAUA_CERFILE = sUBAUA_CERFILE;
	}

	public void setFROMDATE(Date fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public void setTODATE(Date tODATE) {
		TODATE = tODATE;
	}

	public void setORGNAME(String oRGNAME) {
		ORGNAME = oRGNAME;
	}

	public void setCERFILE(String cERFILE) {
		CERFILE = cERFILE;
	}

	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}

	public void setOTHERDEPART(String oTHERDEPART) {
		OTHERDEPART = oTHERDEPART;
	}

	public void setDEPARTKID(String dEPARTKID) {
		DEPARTKID = dEPARTKID;
	}

	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}

	public void setOFFICERNAME(String oFFICERNAME) {
		OFFICERNAME = oFFICERNAME;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}

	public void setTYPEOFAPPLICATION(String tYPEOFAPPLICATION) {
		TYPEOFAPPLICATION = tYPEOFAPPLICATION;
	}

	public void setTEST_EMAIL(String tEST_EMAIL) {
		TEST_EMAIL = tEST_EMAIL;
	}

	@Override
	public String toString() {
		return "SubauaLicencekeyUpdationModel [TID=" + TID + ", SUBAUA_CODE=" + SUBAUA_CODE + ", SUBAUA_LK=" + SUBAUA_LK
				+ ", SUBAUA_CERFILE=" + SUBAUA_CERFILE + ", FROMDATE=" + FROMDATE + ", TODATE=" + TODATE + ", ORGNAME="
				+ ORGNAME + ", CERFILE=" + CERFILE + ", DEPARTMENT=" + DEPARTMENT + ", OTHERDEPART=" + OTHERDEPART
				+ ", DEPARTKID=" + DEPARTKID + ", ACTIVE=" + ACTIVE + ", OFFICERNAME=" + OFFICERNAME + ", PHONE="
				+ PHONE + ", EMAIL=" + EMAIL + ", APP_NAME=" + APP_NAME + ", TYPEOFAPPLICATION=" + TYPEOFAPPLICATION
				+ ", TEST_EMAIL=" + TEST_EMAIL + "]";
	}

}
