package com.gov.Authmis.model;

import java.io.Serializable;

public class sendMailModel implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long srNo;
	 private String subAuaCode;
     private String appName;
     private String ipAddress;
     private String toCC;
     private String to;
     private String subject;
     private String mailBody;
     
	public String getSubAuaCode() {
		return subAuaCode;
	}
	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getToCC() {
		return toCC;
	}
	public void setToCC(String toCC) {
		this.toCC = toCC;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
	public Long getSrNo() {
		return srNo;
	}
	public void setSrNo(Long srNo) {
		this.srNo = srNo;
	}
	@Override
	public String toString() {
		return "sendMailModel [srNo=" + srNo + ", subAuaCode=" + subAuaCode + ", appName=" + appName + ", ipAddress="
				+ ipAddress + ", toCC=" + toCC + ", to=" + to + ", subject=" + subject + ", mailBody=" + mailBody + "]";
	}
	
	
     

}
