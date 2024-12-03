package com.gov.Authmis.entity;

public class NonLiveUnblockEntity {
	
	private String refId;
	private String subAuaCode;
	private String responseCode;
	private String deviceCode;
	private String transactionId;
	private String batchId;
	private String blockDate;
	private String aadhaarId;
	private String ssoId;
	private String fromDate;
	private String endDate;
	
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getSubAuaCode() {
		return subAuaCode;
	}
	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBlockDate() {
		return blockDate;
	}
	public void setBlockDate(String blockDate) {
		this.blockDate = blockDate;
	}
	public String getAadhaarId() {
		return aadhaarId;
	}
	public void setAadhaarId(String aadhaarId) {
		this.aadhaarId = aadhaarId;
	}
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return "NonLiveUnblockEntity [refId=" + refId + ", subAuaCode=" + subAuaCode + ", responseCode=" + responseCode
				+ ", deviceCode=" + deviceCode + ", transactionId=" + transactionId + ", batchId=" + batchId
				+ ", blockDate=" + blockDate + ", aadhaarId=" + aadhaarId + ", ssoId=" + ssoId + ", fromDate="
				+ fromDate + ", endDate=" + endDate + "]";
	}

}
