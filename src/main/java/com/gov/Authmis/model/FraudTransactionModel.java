package com.gov.Authmis.model;



public class FraudTransactionModel {
  private String subCode;
  
  private String fromdate;
  
  private String Enddate;
  
  public String getSubCode() {
    return this.subCode;
  }
  
  public void setSubCode(String subCode) {
    this.subCode = subCode;
  }
  
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
}