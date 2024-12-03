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
@Table(name = "LEASED_LINE_DETAILS")
public class ProjectInfraDataLeasedLineDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEASED_LINE_DETAILS_SEQ")
	@SequenceGenerator(name = "LEASED_LINE_DETAILS_SEQ", sequenceName = "LEASED_LINE_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "ENVIRONMENT")
	private String environment;
	
	@Column(name = "NETWORK_PROVIDER")
	private String networkProvider;

	@Column(name = "PRIVATE_IP")
	private String privateIp;
	
	@Column(name = "PRIVATE_IP_LOCATION")
	private String privateIpLocation;

	@Column(name = "PUBLIC_IP")
	private String publicIp;
	
	@Column(name = "PUBLIC_IP_LOCATION")
	private String publicIpLocation;

	@Column(name = "MAXIMUM_CLIENT")
	private Integer maximumClient;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "WORK_ORDER_START_DATE")
	private Timestamp workOrderStartDate;
	
	@Column(name = "WORK_ORDER_END_DATE")
	private Timestamp workOrderEndDate;
	
	
	/*
	 * @Column(name = "UPDATED_BY") private String updatedBy;
	 * 
	 * @Column(name = "UPDATED_DATE") private Timestamp updatedDate;
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


	public String getNetworkProvider() {
		return networkProvider;
	}


	public void setNetworkProvider(String networkProvider) {
		this.networkProvider = networkProvider;
	}


	public String getPrivateIp() {
		return privateIp;
	}


	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}


	public String getPrivateIpLocation() {
		return privateIpLocation;
	}


	public void setPrivateIpLocation(String privateIpLocation) {
		this.privateIpLocation = privateIpLocation;
	}


	public String getPublicIp() {
		return publicIp;
	}


	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}


	public String getPublicIpLocation() {
		return publicIpLocation;
	}


	public void setPublicIpLocation(String publicIpLocation) {
		this.publicIpLocation = publicIpLocation;
	}


	public Integer getMaximumClient() {
		return maximumClient;
	}


	public void setMaximumClient(Integer maximumClient) {
		this.maximumClient = maximumClient;
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


	public Timestamp getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}


	public Timestamp getWorkOrderStartDate() {
		return workOrderStartDate;
	}


	public void setWorkOrderStartDate(Timestamp workOrderStartDate) {
		this.workOrderStartDate = workOrderStartDate;
	}


	public Timestamp getWorkOrderEndDate() {
		return workOrderEndDate;
	}


	public void setWorkOrderEndDate(Timestamp workOrderEndDate) {
		this.workOrderEndDate = workOrderEndDate;
	}


	public ProjectDetailsEntity getProjectDetails() {
		return projectDetails;
	}


	public void setProjectDetails(ProjectDetailsEntity projectDetails) {
		this.projectDetails = projectDetails;
	}


	@Override
	public String toString() {
		return "ProjectInfraDataLeasedLineDetails [id=" + id + ", projectId=" + projectId + ", environment="
				+ environment + ", networkProvider=" + networkProvider + ", privateIp=" + privateIp
				+ ", privateIpLocation=" + privateIpLocation + ", publicIp=" + publicIp + ", publicIpLocation="
				+ publicIpLocation + ", maximumClient=" + maximumClient + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", workOrderStartDate=" + workOrderStartDate
				+ ", workOrderEndDate=" + workOrderEndDate + ", projectDetails=" + projectDetails + "]";
	}


	public ProjectInfraDataLeasedLineDetails(Long id, Long projectId, String environment, String networkProvider,
			String privateIp, String privateIpLocation, String publicIp, String publicIpLocation, Integer maximumClient,
			Integer status, String createdBy, Timestamp createdDate, Timestamp workOrderStartDate,
			Timestamp workOrderEndDate, ProjectDetailsEntity projectDetails) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.environment = environment;
		this.networkProvider = networkProvider;
		this.privateIp = privateIp;
		this.privateIpLocation = privateIpLocation;
		this.publicIp = publicIp;
		this.publicIpLocation = publicIpLocation;
		this.maximumClient = maximumClient;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.workOrderStartDate = workOrderStartDate;
		this.workOrderEndDate = workOrderEndDate;
		this.projectDetails = projectDetails;
	}


	public ProjectInfraDataLeasedLineDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
}
