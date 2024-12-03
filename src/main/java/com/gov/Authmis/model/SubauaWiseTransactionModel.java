package com.gov.Authmis.model;


public class SubauaWiseTransactionModel {
  private String subAuaCode;
  
  private String frdate;
  
  private String todate;
  
  private String fromdate;
  
  private String Enddate;
  
  private String errorCode;

public SubauaWiseTransactionModel(String subAuaCode, String frdate, String todate, String fromdate, String enddate,
		String errorCode) {
	super();
	this.subAuaCode = subAuaCode;
	this.frdate = frdate;
	this.todate = todate;
	this.fromdate = fromdate;
	Enddate = enddate;
	this.errorCode = errorCode;
}

public SubauaWiseTransactionModel() {
	super();
}

public String getSubAuaCode() {
	return subAuaCode;
}

public String getFrdate() {
	return frdate;
}

public String getTodate() {
	return todate;
}

public String getFromdate() {
	return fromdate;
}

public String getEnddate() {
	return Enddate;
}

public String getErrorCode() {
	return errorCode;
}

public void setSubAuaCode(String subAuaCode) {
	this.subAuaCode = subAuaCode;
}

public void setFrdate(String frdate) {
	this.frdate = frdate;
}

public void setTodate(String todate) {
	this.todate = todate;
}

public void setFromdate(String fromdate) {
	this.fromdate = fromdate;
}

public void setEnddate(String enddate) {
	Enddate = enddate;
}

public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
}

@Override
public String toString() {
	return "SubauaWiseTransactionModel [subAuaCode=" + subAuaCode + ", frdate=" + frdate + ", todate=" + todate
			+ ", fromdate=" + fromdate + ", Enddate=" + Enddate + ", errorCode=" + errorCode + "]";
}
  
  
}
