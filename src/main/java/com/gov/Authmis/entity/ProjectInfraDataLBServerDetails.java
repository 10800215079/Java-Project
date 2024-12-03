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
@Table(name = "LB_SERVER_DETAILS")
public class ProjectInfraDataLBServerDetails {		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LB_SERVER_DETAILS_SEQ")
	@SequenceGenerator(name = "LB_SERVER_DETAILS_SEQ", sequenceName = "LB_SERVER_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@Column(name = "ENVIRONMENT")
	private String environment;
	
	@Column(name = "PRIVATE_IP")
	private String privateIp;

	@Column(name = "PUBLIC_IP")
	private String publicIp;

	/*
	 * @Column(name = "LB_LOCATION") private String location;
	 */
	
	@Column(name = "PORT_NO")
	private Long port;
	
	@Column(name = "STATUS")
	private Integer status;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;	

	@Column(name = "CREATED_BY")
	private String createdBy;

	/*
	 * @Column(name = "UPDATED_DATE") private Timestamp updatedDate;
	 * 
	 * @Column(name = "UPDATED_BY") private String updatedBy;
	 */

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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
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
		return "ProjectInfraDataLBServerDetails [id=" + id + ", projectId=" + projectId + ", environment=" + environment
				+ ", privateIp=" + privateIp + ", publicIp=" + publicIp + ", port=" + port + ", status=" + status
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", projectDetails=" + projectDetails
				+ "]";
	}

	public ProjectInfraDataLBServerDetails(Long id, Long projectId, String environment, String privateIp,
			String publicIp, Long port, Integer status, Timestamp createdDate, String createdBy,
			ProjectDetailsEntity projectDetails) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.environment = environment;
		this.privateIp = privateIp;
		this.publicIp = publicIp;
		this.port = port;
		this.status = status;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.projectDetails = projectDetails;
	}

	public ProjectInfraDataLBServerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
