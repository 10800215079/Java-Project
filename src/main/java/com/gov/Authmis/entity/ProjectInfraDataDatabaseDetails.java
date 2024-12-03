package com.gov.Authmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "DATABASE_DETAILS")
public class ProjectInfraDataDatabaseDetails {
	
	public ProjectInfraDataDatabaseDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATABASE_DETAILS_SEQ")
	@SequenceGenerator(name = "DATABASE_DETAILS_SEQ", sequenceName = "DATABASE_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "DATABASE_NAME")
	private String databaseName;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ENVIRONMENT")
	private String environment;

	@Column(name = "IP")
	private String ip;

	@Column(name = "DATABASE_VERSION")
	private String databaseVersion;

	@Column(name = "SERVER_PASSWORD")
	private String serverPassword;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	@Column(name = "Server_PORT")
	private Integer serverPort;

	@Column(name = "STATUS")
	private Integer status;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", insertable = false, updatable = false)
	private ProjectDetailsEntity projectDetails;

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

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDatabaseVersion() {
		return databaseVersion;
	}

	public void setDatabaseVersion(String databaseVersion) {
		this.databaseVersion = databaseVersion;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ProjectDetailsEntity getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(ProjectDetailsEntity projectDetails) {
		this.projectDetails = projectDetails;
	}

	@Override
	public String toString() {
		return "ProjectInfraDataDatabaseDetails [id=" + id + ", projectId=" + projectId + ", databaseName="
				+ databaseName + ", username=" + username + ", environment=" + environment + ", ip=" + ip
				+ ", databaseVersion=" + databaseVersion + ", serverPassword=" + serverPassword + ", serviceName="
				+ serviceName + ", serverPort=" + serverPort + ", status=" + status + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", projectDetails=" + projectDetails + "]";
	}

	public ProjectInfraDataDatabaseDetails(Long id, Long projectId, String databaseName, String username,
			String environment, String ip, String databaseVersion, String serverPassword, String serviceName,
			Integer serverPort, Integer status, Timestamp createdDate, String createdBy,
			ProjectDetailsEntity projectDetails) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.databaseName = databaseName;
		this.username = username;
		this.environment = environment;
		this.ip = ip;
		this.databaseVersion = databaseVersion;
		this.serverPassword = serverPassword;
		this.serviceName = serviceName;
		this.serverPort = serverPort;
		this.status = status;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.projectDetails = projectDetails;
	}

	
	/*
	 * @Column(name = "UPDATED_DATE") private Timestamp updatedDate;
	 * 
	 * @Column(name = "UPDATED_BY") private String updatedBy;
	 */
	
	

}
