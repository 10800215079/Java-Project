package com.gov.Authmis.model;

public class OddHourTransaction {
  private String subCode;
  
  private String fromdate;
  
  private String Enddate;
  
  private String orgName;
  
  private String totCnt;
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getTotCnt() {
    return this.totCnt;
  }
  
  public void setTotCnt(String totCnt) {
    this.totCnt = totCnt;
  }
  
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