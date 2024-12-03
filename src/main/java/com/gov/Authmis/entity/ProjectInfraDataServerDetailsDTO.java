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
@Table(name = "SERVER_DETAILS")
public class ProjectInfraDataServerDetailsDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVER_DETAILS_SEQ")
	@SequenceGenerator(name = "SERVER_DETAILS_SEQ", sequenceName = "SERVER_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "ENVIRONMENT")
	private String environment;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "IP")
	private String ip;

	@Column(name = "SERVER_TYPE")
	private String serverType;

	@Column(name = "CPU_CORE")
	private String cpuCode;

	@Column(name = "RAM")
	private String setRam;

	@Column(name = "OS_TYPE")
	private String osType;

	@Column(name = "OS_VERSION")
	private Integer osVersion;

	@Column(name = "TECHNOLOGY")
	private String technology;

	@Column(name = "WEBSPHERE")
	private String selectWebSphere;
	
	@Column(name = "SERVER_LOCATION")
	private String serverLocation;

	@Lob
	@Column(name = "ENCRYPTION_CERTIFICATE")
	private byte[] encryptedCertificate;
	
	@Column(name = "ENCRYPTION_CERTIFICATE_EXP")
	private Timestamp encryptedCertificateExp;

	@Lob
	@Column(name = "SIGNING_CERTIFICATE")
	private byte[] signingCertificate;
	
	@Column(name = "SIGNING_CERTIFICATE_EXP")
	private Timestamp signingCertificateExp;
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "STATUS")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getCpuCode() {
		return cpuCode;
	}

	public void setCpuCode(String cpuCode) {
		this.cpuCode = cpuCode;
	}

	public String getSetRam() {
		return setRam;
	}

	public void setSetRam(String setRam) {
		this.setRam = setRam;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Integer getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(Integer osVersion) {
		this.osVersion = osVersion;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getSelectWebSphere() {
		return selectWebSphere;
	}

	public void setSelectWebSphere(String selectWebSphere) {
		this.selectWebSphere = selectWebSphere;
	}

	public String getServerLocation() {
		return serverLocation;
	}

	public void setServerLocation(String serverLocation) {
		this.serverLocation = serverLocation;
	}

	public byte[] getEncryptedCertificate() {
		return encryptedCertificate;
	}

	public void setEncryptedCertificate(byte[] encryptedCertificate) {
		this.encryptedCertificate = encryptedCertificate;
	}

	public Timestamp getEncryptedCertificateExp() {
		return encryptedCertificateExp;
	}

	public void setEncryptedCertificateExp(Timestamp encryptedCertificateExp) {
		this.encryptedCertificateExp = encryptedCertificateExp;
	}

	public byte[] getSigningCertificate() {
		return signingCertificate;
	}

	public void setSigningCertificate(byte[] signingCertificate) {
		this.signingCertificate = signingCertificate;
	}

	public Timestamp getSigningCertificateExp() {
		return signingCertificateExp;
	}

	public void setSigningCertificateExp(Timestamp signingCertificateExp) {
		this.signingCertificateExp = signingCertificateExp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	@Override
	public String toString() {
		return "ProjectInfraDataServerDetailsDTO [id=" + id + ", projectId=" + projectId + ", environment="
				+ environment + ", projectName=" + projectName + ", ip=" + ip + ", serverType=" + serverType
				+ ", cpuCode=" + cpuCode + ", setRam=" + setRam + ", osType=" + osType + ", osVersion=" + osVersion
				+ ", technology=" + technology + ", selectWebSphere=" + selectWebSphere + ", serverLocation="
				+ serverLocation + ", encryptedCertificate=" + Arrays.toString(encryptedCertificate)
				+ ", encryptedCertificateExp=" + encryptedCertificateExp + ", signingCertificate="
				+ Arrays.toString(signingCertificate) + ", signingCertificateExp=" + signingCertificateExp
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", status=" + status + "]";
	}

	public ProjectInfraDataServerDetailsDTO(Long id, Long projectId, String environment, String projectName, String ip,
			String serverType, String cpuCode, String setRam, String osType, Integer osVersion, String technology,
			String selectWebSphere, String serverLocation, byte[] encryptedCertificate,
			Timestamp encryptedCertificateExp, byte[] signingCertificate, Timestamp signingCertificateExp,
			String createdBy, Timestamp createdDate, Integer status) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.environment = environment;
		this.projectName = projectName;
		this.ip = ip;
		this.serverType = serverType;
		this.cpuCode = cpuCode;
		this.setRam = setRam;
		this.osType = osType;
		this.osVersion = osVersion;
		this.technology = technology;
		this.selectWebSphere = selectWebSphere;
		this.serverLocation = serverLocation;
		this.encryptedCertificate = encryptedCertificate;
		this.encryptedCertificateExp = encryptedCertificateExp;
		this.signingCertificate = signingCertificate;
		this.signingCertificateExp = signingCertificateExp;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.status = status;
	}

	public ProjectInfraDataServerDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


}
