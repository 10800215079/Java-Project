package com.gov.Authmis.model;

import java.util.Date;

public class MailContentModal {
	
	private Long srNo;
	private String subAuaCode;
    private String appName;
    private String ipAddress;
    private String[] toCC;
    private String[] to;
    private String subject;
    private String mailBody;
    private String phone;
    private String subAuaLk;
    private Date newExpiryDate;
    private String oldExpirydate;
    private String subauaName;
    private String password;
    private String officername;
    
    
   
	public String getOfficername() {
		return officername;
	}
	public void setOfficername(String officername) {
		this.officername = officername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubauaName() {
		return subauaName;
	}
	public void setSubauaName(String subauaName) {
		this.subauaName = subauaName;
	}
	public Date getNewExpiryDate() {
		return newExpiryDate;
	}
	public void setNewExpiryDate(Date newExpiryDate) {
		this.newExpiryDate = newExpiryDate;
	}
	public String getOldExpirydate() {
		return oldExpirydate;
	}
	public void setOldExpirydate(String output) {
		this.oldExpirydate = output;
	}
	public Long getSrNo() {
		return srNo;
	}
	public void setSrNo(Long srNo) {
		this.srNo = srNo;
	}
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
	
	public String[] getToCC() {
		return toCC;
	}
	public void setToCC(String[] toCC) {
		this.toCC = toCC;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSubAuaLk() {
		return subAuaLk;
	}
	public void setSubAuaLk(String subAuaLk) {
		this.subAuaLk = subAuaLk;
	}
    
    

}
