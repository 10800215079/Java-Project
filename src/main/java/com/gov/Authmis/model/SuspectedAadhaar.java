package com.gov.Authmis.model;

public class SuspectedAadhaar {
 
  
  private String frdate;
  
  private String todate;
  
  private String orgName;
  
  private String aadhaarId;
  
  private String subAuaCode;
  
  private String noOfTran;
  
  private String authSuccess;
  
  private String fromdate;
  
  private String Enddate;
  
  private String transacationDate;

public String getFrdate() {
	return frdate;
}

public String getTodate() {
	return todate;
}

public String getOrgName() {
	return orgName;
}

public String getAadhaarId() {
	return aadhaarId;
}

public String getSubAuaCode() {
	return subAuaCode;
}

public String getNoOfTran() {
	return noOfTran;
}

public String getAuthSuccess() {
	return authSuccess;
}

public String getFromdate() {
	return fromdate;
}

public String getEnddate() {
	return Enddate;
}

public String getTransacationDate() {
	return transacationDate;
}

public void setFrdate(String frdate) {
	this.frdate = frdate;
}

public void setTodate(String todate) {
	this.todate = todate;
}

public void setOrgName(String orgName) {
	this.orgName = orgName;
}

public void setAadhaarId(String aadhaarId) {
	this.aadhaarId = aadhaarId;
}

public void setSubAuaCode(String subAuaCode) {
	this.subAuaCode = subAuaCode;
}

public void setNoOfTran(String noOfTran) {
	this.noOfTran = noOfTran;
}

public void setAuthSuccess(String authSuccess) {
	this.authSuccess = authSuccess;
}

public void setFromdate(String fromdate) {
	this.fromdate = fromdate;
}

public void setEnddate(String enddate) {
	Enddate = enddate;
}

public void setTransacationDate(String transacationDate) {
	this.transacationDate = transacationDate;
}

public SuspectedAadhaar() {
	super();
}

public SuspectedAadhaar(String frdate, String todate, String orgName, String aadhaarId, String subAuaCode,
		String noOfTran, String authSuccess, String fromdate, String enddate, String transacationDate) {
	super();
	this.frdate = frdate;
	this.todate = todate;
	this.orgName = orgName;
	this.aadhaarId = aadhaarId;
	this.subAuaCode = subAuaCode;
	this.noOfTran = noOfTran;
	this.authSuccess = authSuccess;
	this.fromdate = fromdate;
	Enddate = enddate;
	this.transacationDate = transacationDate;
}

@Override
public String toString() {
	return "SuspectedAadhaar [frdate=" + frdate + ", todate=" + todate + ", orgName=" + orgName + ", aadhaarId="
			+ aadhaarId + ", subAuaCode=" + subAuaCode + ", noOfTran=" + noOfTran + ", authSuccess=" + authSuccess
			+ ", fromdate=" + fromdate + ", Enddate=" + Enddate + ", transacationDate=" + transacationDate + "]";
}

  
}
