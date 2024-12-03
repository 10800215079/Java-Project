package com.gov.Authmis.entity;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="SECURITY_AUDIT_REPORT")
public class UploadSecurityAuditReportEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SECURITY_AUDIT_REPORT_SEQ")
	@SequenceGenerator(name = "SECURITY_AUDIT_REPORT_SEQ", sequenceName = "SECURITY_AUDIT_REPORT_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SUBAUA_NAME", length = 200, nullable = false)
	private String subauaName;
	
	@Column(name = "SUBAUA_CODE", length = 200, nullable = false)
	private String subauaCode;

	@Column(name = "APP_NAME", length = 200, nullable = false)
	private String appName;

	@Lob
    @Column(name = "AUDIT_REPORT")
    private byte[] auditReport;

	@Column(name = "CERTIFICATE_DATE")
	private Timestamp certificateDate;

	@Column(name = "DUE_DATE")
	private Timestamp dueDate;

	@CreationTimestamp
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@Column(name = "CREATED_BY", length = 100, nullable = false)
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubauaName() {
		return subauaName;
	}

	public void setSubauaName(String subauaName) {
		this.subauaName = subauaName;
	}

	public String getSubauaCode() {
		return subauaCode;
	}

	public void setSubauaCode(String subauaCode) {
		this.subauaCode = subauaCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public byte[] getAuditReport() {
		return auditReport;
	}

	public void setAuditReport(byte[] auditReport) {
		this.auditReport = auditReport;
	}

	public Timestamp getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Timestamp certificateDate) {
		this.certificateDate = certificateDate;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "UploadSecurityAuditReportEntity [id=" + id + ", subauaName=" + subauaName + ", subauaCode=" + subauaCode
				+ ", appName=" + appName + ", auditReport=" + Arrays.toString(auditReport) + ", certificateDate="
				+ certificateDate + ", dueDate=" + dueDate + ", createdDate=" + createdDate + ", status=" + status
				+ ", createdBy=" + createdBy + "]";
	}

	public UploadSecurityAuditReportEntity(Long id, String subauaName, String subauaCode, String appName,
			byte[] auditReport, Timestamp certificateDate, Timestamp dueDate, Timestamp createdDate, Integer status,
			String createdBy) {
		super();
		this.id = id;
		this.subauaName = subauaName;
		this.subauaCode = subauaCode;
		this.appName = appName;
		this.auditReport = auditReport;
		this.certificateDate = certificateDate;
		this.dueDate = dueDate;
		this.createdDate = createdDate;
		this.status = status;
		this.createdBy = createdBy;
	}

	public UploadSecurityAuditReportEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	
}
