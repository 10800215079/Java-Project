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
@Table(name = "HARDWARE_DETAILS")
public class ProjectInfraDataHardwareDetails {
	
    
	public ProjectInfraDataHardwareDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ProjectInfraDataHardwareDetails [id=" + id + ", deviceName=" + deviceName + ", deviceMaker="
				+ deviceMaker + ", deviceModelName=" + deviceModelName + ", deviceSerialNo=" + deviceSerialNo
				+ ", deviceEsnNo=" + deviceEsnNo + ", deviceType=" + deviceType + ", deviceLocation=" + deviceLocation
				+ ", managementIpAddress=" + managementIpAddress + ", rfsServerIp=" + rfsServerIp + ", clientIp="
				+ clientIp + ", maximumClient=" + maximumClient + ", workOrderNumber=" + workOrderNumber
				+ ", workOrderStartDate=" + workOrderStartDate + ", workOrderEndDate=" + workOrderEndDate
				+ ", environment=" + environment + ", projectId=" + projectId + ", status=" + status + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", vendorName=" + vendorName + ", mentionReason="
				+ mentionReason + ", workingStatus=" + workingStatus + ", projectDetails=" + projectDetails + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HARDWARE_DETAILS_SEQ")
	@SequenceGenerator(name = "HARDWARE_DETAILS_SEQ", sequenceName = "HARDWARE_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DEVICE_NAME")
	private String deviceName;

	@Column(name = "DEVICE_MAKER")
	private String deviceMaker;

	@Column(name = "DEVICE_MODEL_NAME")
	private String deviceModelName;

	@Column(name = "DEVICE_SERIAL_NO")
	private String deviceSerialNo;

	@Column(name = "DEVICE_ESN_NO")
	private String deviceEsnNo;

	@Column(name = "DEVICE_TYPE")
	private String deviceType;

	@Column(name = "DEVICE_LOCATION")
	private String deviceLocation;

	@Column(name = "MANAGEMENT_IP_ADDRESS")
	private String managementIpAddress;

	@Column(name = "RFS_SERVER_IP")
	private String rfsServerIp;

	@Column(name = "CLIENT_IP")
	private String clientIp;

	@Column(name = "MAXIMUM_CLIENT")
	private Integer maximumClient;

	@Column(name = "WORK_ORDER_NUMBER")
	private Long workOrderNumber;

	@Column(name = "WORK_ORDER_START_DATE")
	private Timestamp workOrderStartDate;

	@Column(name = "WORK_ORDER_END_DATE")
	private Timestamp workOrderEndDate;

	@Column(name = "DEVICE_ENVIRONMENT")
	private String environment;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "STATUS")
	private Integer status;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "VENDOR_NAME")
	private String vendorName;
	
	@Column(name = "REASON")
	private String mentionReason;
	
	@Column(name = "WORKING_STATUS")
	private Integer workingStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", insertable = false, updatable = false)
	private ProjectDetailsEntity projectDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceMaker() {
		return deviceMaker;
	}

	public void setDeviceMaker(String deviceMaker) {
		this.deviceMaker = deviceMaker;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public String getDeviceEsnNo() {
		return deviceEsnNo;
	}

	public void setDeviceEsnNo(String deviceEsnNo) {
		this.deviceEsnNo = deviceEsnNo;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public String getManagementIpAddress() {
		return managementIpAddress;
	}

	public void setManagementIpAddress(String managementIpAddress) {
		this.managementIpAddress = managementIpAddress;
	}

	public String getRfsServerIp() {
		return rfsServerIp;
	}

	public void setRfsServerIp(String rfsServerIp) {
		this.rfsServerIp = rfsServerIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getMaximumClient() {
		return maximumClient;
	}

	public void setMaximumClient(Integer maximumClient) {
		this.maximumClient = maximumClient;
	}

	public Long getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(Long workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
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

	

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getMentionReason() {
		return mentionReason;
	}

	public void setMentionReason(String mentionReason) {
		this.mentionReason = mentionReason;
	}

	public Integer getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(Integer workingStatus) {
		this.workingStatus = workingStatus;
	}

	public ProjectDetailsEntity getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(ProjectDetailsEntity projectDetails) {
		this.projectDetails = projectDetails;
	}

	public ProjectInfraDataHardwareDetails(Long id, String deviceName, String deviceMaker, String deviceModelName,
			String deviceSerialNo, String deviceEsnNo, String deviceType, String deviceLocation,
			String managementIpAddress, String rfsServerIp, String clientIp, Integer maximumClient,
			Long workOrderNumber, Timestamp workOrderStartDate, Timestamp workOrderEndDate, String environment,
			Long projectId, Integer status, Timestamp createdDate, String createdBy, String vendorName,
			String mentionReason, Integer workingStatus, ProjectDetailsEntity projectDetails) {
		super();
		this.id = id;
		this.deviceName = deviceName;
		this.deviceMaker = deviceMaker;
		this.deviceModelName = deviceModelName;
		this.deviceSerialNo = deviceSerialNo;
		this.deviceEsnNo = deviceEsnNo;
		this.deviceType = deviceType;
		this.deviceLocation = deviceLocation;
		this.managementIpAddress = managementIpAddress;
		this.rfsServerIp = rfsServerIp;
		this.clientIp = clientIp;
		this.maximumClient = maximumClient;
		this.workOrderNumber = workOrderNumber;
		this.workOrderStartDate = workOrderStartDate;
		this.workOrderEndDate = workOrderEndDate;
		this.environment = environment;
		this.projectId = projectId;
		this.status = status;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.vendorName = vendorName;
		this.mentionReason = mentionReason;
		this.workingStatus = workingStatus;
		this.projectDetails = projectDetails;
	}

	
	

	/*
	 * @Column(name = "UPDATED_DATE") private Timestamp updatedDate;
	 * 
	 * @Column(name = "UPDATED_BY") private String updatedBy;
	 */
	

}
