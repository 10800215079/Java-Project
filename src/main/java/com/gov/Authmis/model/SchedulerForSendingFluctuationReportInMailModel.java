package com.gov.Authmis.model;

import java.util.Date;

public class SchedulerForSendingFluctuationReportInMailModel {
	
	private Date createdDate;
	private String error;
	private String requestType;
	private String ver;
	private String transactionId;
	private String uidResponseCode;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUidResponseCode() {
		return uidResponseCode;
	}
	public void setUidResponseCode(String uidResponseCode) {
		this.uidResponseCode = uidResponseCode;
	}
	@Override
	public String toString() {
		return "SchedulerForSendingFluctuationReportInMailModel [createdDate=" + createdDate + ", error=" + error
				+ ", requestType=" + requestType + ", ver=" + ver + ", transactionId=" + transactionId
				+ ", uidResponseCode=" + uidResponseCode + "]";
	}
	public SchedulerForSendingFluctuationReportInMailModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
