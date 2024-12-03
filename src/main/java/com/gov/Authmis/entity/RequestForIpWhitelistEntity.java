package com.gov.Authmis.entity;


import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "IPWHITLISTREQUEST")
public class RequestForIpWhitelistEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SUB_AUA_CODE", length = 50)
	private String subAUACode;

	@Column(name = "Sub_AUA_Name", length = 100)
	private String subAUAName;

	@Column(name = "IP_ADDRESS", length = 50)
	private String ipAddress;

	@Column(name = "IPBELONGSTO", length = 100)
	private String ipBelongsTo;

	@Column(name = "APP_NAME", length = 100)
	private String appName;

	@Column(name = "APPLICATONURL", length = 100)
	private String applicationUrl;

	@Column(name = "REQUEST_TYPE", length = 50)
	private String requestType;

	@Column(name = "SERVICE_URL_TYPE", length = 50)
	private String serviceUrlType;

	@Column(name = "CREATERIP", length = 50)
	private String CREATERIP ;

	@Lob
	@Column(name = "CONSENTDOCUMENT")
	private byte[] consentDocument;

	@Column(name = "SCHEMENAME", length = 100)
	private String schemeName;

	@Column(name = "ACTOFAADHAAR", length = 100)
	private String ACTOFAADHAAR;

	@Column(name = "STATUS")
	private Long status;
	
	@Column(name = "CURRENTSTATUS")
	private Long currentStatus;

	@Column(name = "Updated_By", length = 100)
	private String updatedBy;

	@Column(name = "Created_By", length = 100)
	private String createdBy;

	@Column(name = "Updated_Date")
	private Timestamp updatedDate;

	@Column(name = "Created_Date")
	private Timestamp createdDate;

	@Column(name = "Reason", length = 255)
	private String reason;
	
	@Lob
	@Column(name = "PUBLISHEDDOC")
	private byte[] publishedDoc;

	@Column(name = "ISDOCPUBLISHED")
	private Integer isDocPublished;
	
	@Column(name = "ISMAILSENT")
	private Integer isMailSent;
	
	@Lob
	@Column(name = "AUDITDOCUMENT")
	private byte[] auditDocument;
	
	@Lob
	@Column(name = "APPLICATIONVAPTREPORT")
	private byte[] applicationVAPTReport;
	
	@Lob
	@Column(name = "SOURCECODEREVIEW")
	private byte[] sourceCodeReview;
	
	@Lob
	@Column(name = "NOCHANGECERT")
	private byte[] nochangeCert;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubAUACode() {
		return subAUACode;
	}

	public void setSubAUACode(String subAUA_Code) {
		this.subAUACode = subAUA_Code;
	}

	public String getSubAUAName() {
		return subAUAName;
	}

	public void setSubAUAName(String subAUA_Name) {
		this.subAUAName = subAUA_Name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpBelongsTo() {
		return ipBelongsTo;
	}

	public void setIpBelongsTo(String ipBelongsTo) {
		this.ipBelongsTo = ipBelongsTo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getServiceUrlType() {
		return serviceUrlType;
	}

	public void setServiceUrlType(String serviceUrlType) {
		this.serviceUrlType = serviceUrlType;
	}

	public String getCREATERIP() {
		return CREATERIP;
	}

	public void setCREATERIP(String cREATERIP) {
		CREATERIP = cREATERIP;
	}

	public byte[] getConsentDocument() {
		return consentDocument;
	}

	public void setConsentDocument(byte[] consentDocument) {
		this.consentDocument = consentDocument;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getACTOFAADHAAR() {
		return ACTOFAADHAAR;
	}

	public void setACTOFAADHAAR(String aCTOFAADHAAR) {
		ACTOFAADHAAR = aCTOFAADHAAR;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public byte[] getPublishedDoc() {
		return publishedDoc;
	}

	public void setPublishedDoc(byte[] publishedDoc) {
		this.publishedDoc = publishedDoc;
	}

	public Integer getIsDocPublished() {
		return isDocPublished;
	}

	public void setIsDocPublished(Integer isDocPublished) {
		this.isDocPublished = isDocPublished;
	}
	
	

	public Long getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Long currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Integer getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Integer isMailSent) {
		this.isMailSent = isMailSent;
	}

	public byte[] getAuditDocument() {
		return auditDocument;
	}

	public void setAuditDocument(byte[] auditDocument) {
		this.auditDocument = auditDocument;
	}
	
	

	public byte[] getApplicationVAPTReport() {
		return applicationVAPTReport;
	}

	public void setApplicationVAPTReport(byte[] applicationVAPTReport) {
		this.applicationVAPTReport = applicationVAPTReport;
	}

	public byte[] getSourceCodeReview() {
		return sourceCodeReview;
	}

	public void setSourceCodeReview(byte[] sourceCodeReview) {
		this.sourceCodeReview = sourceCodeReview;
	}

	public byte[] getNochangeCert() {
		return nochangeCert;
	}

	public void setNochangeCert(byte[] nochangeCert) {
		this.nochangeCert = nochangeCert;
	}

	

	@Override
	public String toString() {
		return "RequestForIpWhitelistEntity [id=" + id + ", subAUACode=" + subAUACode + ", subAUAName=" + subAUAName
				+ ", ipAddress=" + ipAddress + ", ipBelongsTo=" + ipBelongsTo + ", appName=" + appName
				+ ", applicationUrl=" + applicationUrl + ", requestType=" + requestType + ", serviceUrlType="
				+ serviceUrlType + ", CREATERIP=" + CREATERIP + ", consentDocument=" + Arrays.toString(consentDocument)
				+ ", schemeName=" + schemeName + ", ACTOFAADHAAR=" + ACTOFAADHAAR + ", status=" + status
				+ ", currentStatus=" + currentStatus + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + ", createdDate=" + createdDate + ", reason=" + reason
				+ ", publishedDoc=" + Arrays.toString(publishedDoc) + ", isDocPublished=" + isDocPublished
				+ ", isMailSent=" + isMailSent + ", auditDocument=" + Arrays.toString(auditDocument)
				+ ", applicationVAPTReport=" + Arrays.toString(applicationVAPTReport) + ", sourceCodeReview="
				+ Arrays.toString(sourceCodeReview) + ", nochangeCert=" + Arrays.toString(nochangeCert) + "]";
	}

	
	public RequestForIpWhitelistEntity(Long id, String subAUACode, String subAUAName, String ipAddress,
			String ipBelongsTo, String appName, String applicationUrl, String requestType, String serviceUrlType,
			String cREATERIP, byte[] consentDocument, String schemeName, String aCTOFAADHAAR, Long status,
			Long currentStatus, String updatedBy, String createdBy, Timestamp updatedDate, Timestamp createdDate,
			String reason, byte[] publishedDoc, Integer isDocPublished, Integer isMailSent, byte[] auditDocument,
			byte[] applicationVAPTReport, byte[] sourceCodeReview, byte[] nochangeCert) {
		super();
		this.id = id;
		this.subAUACode = subAUACode;
		this.subAUAName = subAUAName;
		this.ipAddress = ipAddress;
		this.ipBelongsTo = ipBelongsTo;
		this.appName = appName;
		this.applicationUrl = applicationUrl;
		this.requestType = requestType;
		this.serviceUrlType = serviceUrlType;
		CREATERIP = cREATERIP;
		this.consentDocument = consentDocument;
		this.schemeName = schemeName;
		ACTOFAADHAAR = aCTOFAADHAAR;
		this.status = status;
		this.currentStatus = currentStatus;
		this.updatedBy = updatedBy;
		this.createdBy = createdBy;
		this.updatedDate = updatedDate;
		this.createdDate = createdDate;
		this.reason = reason;
		this.publishedDoc = publishedDoc;
		this.isDocPublished = isDocPublished;
		this.isMailSent = isMailSent;
		this.auditDocument = auditDocument;
		this.applicationVAPTReport = applicationVAPTReport;
		this.sourceCodeReview = sourceCodeReview;
		this.nochangeCert = nochangeCert;
	}

	public RequestForIpWhitelistEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
