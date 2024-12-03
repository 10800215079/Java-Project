package com.gov.Authmis.util;


public class ErrorCodeAvgResp {
  private String TotalTrans;
  
  private String Error;
  
  private String AvergTime;
  
  private String Time;
  
  public String getTime() {
    return this.Time;
  }
  
  public void setTime(String time) {
    this.Time = time;
  }
  
  public String getTotalTrans() {
    return this.TotalTrans;
  }
  
  public void setTotalTrans(String totalTrans) {
    this.TotalTrans = totalTrans;
  }
  
  public String getError() {
    return this.Error;
  }
  
  public void setError(String error) {
    this.Error = error;
  }
  
  public String getAvergTime() {
    return this.AvergTime;
  }
  
  public void setAvergTime(String avergTime) {
    this.AvergTime = avergTime;
  }
}
