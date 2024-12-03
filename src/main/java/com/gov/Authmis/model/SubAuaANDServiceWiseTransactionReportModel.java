package com.gov.Authmis.model;


public class SubAuaANDServiceWiseTransactionReportModel {
  private String servicetype;
  
  private String subAuaCode;
  
  private String frdate;
  
  private String todate;
  
  private String status1;
  
  private String fromdate;
  
  private String Enddate;
  
	/*
	 * private String AUTHONTICATION_STATUS;
	 * 
	 * 
	 * public String getAUTHONTICATION_STATUS() { return AUTHONTICATION_STATUS; }
	 * 
	 * public void setAUTHONTICATION_STATUS(String aUTHONTICATION_STATUS) {
	 * AUTHONTICATION_STATUS = aUTHONTICATION_STATUS; }
	 */

public String getFromdate() {
    return this.fromdate;
  }
  
  public void setFromdate(String fromdate) {
    this.fromdate = fromdate;
  }
  
  public String getEnddate() {
    return this.Enddate;
  }
  
  public void setEnddate(String enddate) {
    this.Enddate = enddate;
  }
  
  public String getStatus1() {
    return this.status1;
  }
  
  public void setStatus1(String status1) {
    this.status1 = status1;
  }
  
  public String getServicetype() {
    return this.servicetype;
  }
  
  public void setServicetype(String servicetype) {
    this.servicetype = servicetype;
  }
  
  public String getSubAuaCode() {
    return this.subAuaCode;
  }
  
  public void setSubAuaCode(String subAuaCode) {
    this.subAuaCode = subAuaCode;
  }
  
  public String getFrdate() {
    return this.frdate;
  }
  
  public void setFrdate(String frdate) {
    this.frdate = frdate;
  }
  
  public String getTodate() {
    return this.todate;
  }
  
  public void setTodate(String todate) {
    this.todate = todate;
  }
  
  public String toString() {
    return "SubAuaWiseTransactionReportModel [status1=" + this.status1 + ", servicetype=" + this.servicetype + ", subAuaCode=" + this.subAuaCode + ", frdate=" + this.frdate + ", todate=" + this.todate + "]";
  }
}
