package com.gov.Authmis.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class ProjectInfraViewDetailsDTO {

	private Long projectId;
	private String projectName;
	private List<DatabaseDetailsDTO> databaseDetails;
	private List<HardwareDetailsDTO> hardwareDetails;
	private List<LbServerDetailsDTO> lbServerDetails;
	private List<LeasedLineDetailsDTO> leasedLineDetails;
	private List<ServerDetailsDTO> serverDetails;
	
	@Override
	public String toString() {
		return "ProjectInfraViewDetailsDTO [projectId=" + projectId + ", projectName=" + projectName
				+ ", databaseDetails=" + databaseDetails + ", hardwareDetails=" + hardwareDetails + ", lbServerDetails="
				+ lbServerDetails + ", leasedLineDetails=" + leasedLineDetails + ", serverDetails=" + serverDetails
				+ "]";
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<DatabaseDetailsDTO> getDatabaseDetails() {
		return databaseDetails;
	}

	public void setDatabaseDetails(List<DatabaseDetailsDTO> databaseDetails) {
		this.databaseDetails = databaseDetails;
	}

	public List<HardwareDetailsDTO> getHardwareDetails() {
		return hardwareDetails;
	}

	public void setHardwareDetails(List<HardwareDetailsDTO> hardwareDetails) {
		this.hardwareDetails = hardwareDetails;
	}

	public List<LbServerDetailsDTO> getLbServerDetails() {
		return lbServerDetails;
	}

	public void setLbServerDetails(List<LbServerDetailsDTO> lbServerDetails) {
		this.lbServerDetails = lbServerDetails;
	}

	public List<LeasedLineDetailsDTO> getLeasedLineDetails() {
		return leasedLineDetails;
	}

	public void setLeasedLineDetails(List<LeasedLineDetailsDTO> leasedLineDetails) {
		this.leasedLineDetails = leasedLineDetails;
	}

	public List<ServerDetailsDTO> getServerDetails() {
		return serverDetails;
	}

	public void setServerDetails(List<ServerDetailsDTO> serverDetails) {
		this.serverDetails = serverDetails;
	}

	

	// Constructors, getters, and setters
	

	// Inner DTO classes
	public static class DatabaseDetailsDTO {
		private Long id;
		private String databaseName;
		private String username;
		private String environment;
		private String ip;
		private String databaseVersion;
		private String serverPassword;
		private String serviceName;
		private Integer serverPort;
		private Integer status;
		private Timestamp createdDate;
		private String createdBy;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
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
		@Override
		public String toString() {
			return "DatabaseDetailsDTO [id=" + id + ", databaseName=" + databaseName + ", username=" + username
					+ ", environment=" + environment + ", ip=" + ip + ", databaseVersion=" + databaseVersion
					+ ", serverPassword=" + serverPassword + ", serviceName=" + serviceName + ", serverPort="
					+ serverPort + ", status=" + status + ", createdDate=" + createdDate + ", createdBy=" + createdBy
					+ "]";
		}
		public DatabaseDetailsDTO(Long id, String databaseName, String username, String environment, String ip,
				String databaseVersion, String serverPassword, String serviceName, Integer serverPort, Integer status,
				Timestamp createdDate, String createdBy) {
			super();
			this.id = id;
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
		}
		// Constructors, getters, and setters
	}

	public static class HardwareDetailsDTO {
		private Long id;
		private String deviceName;
		private String deviceMaker;
		private String deviceModelName;
		private String deviceSerialNo;
		private String deviceEsnNo;
		private String deviceType;
		private String deviceLocation;
		private String managementIpAddress;
		private String rfsServerIp;
		private String clientIp;
		private Integer maximumClient;
		private Long workOrderNumber;
		private Timestamp workOrderStartDate;
		private Timestamp workOrderEndDate;
		private String environment;
		private Integer status;
		private Timestamp createdDate;
		private String createdBy;
		private String vendorName;
		private String mentionReason;
		private Integer workingStatus;
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
		@Override
		public String toString() {
			return "HardwareDetailsDTO [id=" + id + ", deviceName=" + deviceName + ", deviceMaker=" + deviceMaker
					+ ", deviceModelName=" + deviceModelName + ", deviceSerialNo=" + deviceSerialNo + ", deviceEsnNo="
					+ deviceEsnNo + ", deviceType=" + deviceType + ", deviceLocation=" + deviceLocation
					+ ", managementIpAddress=" + managementIpAddress + ", rfsServerIp=" + rfsServerIp + ", clientIp="
					+ clientIp + ", maximumClient=" + maximumClient + ", workOrderNumber=" + workOrderNumber
					+ ", workOrderStartDate=" + workOrderStartDate + ", workOrderEndDate=" + workOrderEndDate
					+ ", environment=" + environment + ", status=" + status + ", createdDate=" + createdDate
					+ ", createdBy=" + createdBy + ", vendorName=" + vendorName + ", mentionReason=" + mentionReason
					+ ", workingStatus=" + workingStatus + "]";
		}
		public HardwareDetailsDTO(Long id, String deviceName, String deviceMaker, String deviceModelName,
				String deviceSerialNo, String deviceEsnNo, String deviceType, String deviceLocation,
				String managementIpAddress, String rfsServerIp, String clientIp, Integer maximumClient,
				Long workOrderNumber, Timestamp workOrderStartDate, Timestamp workOrderEndDate, String environment,
				Integer status, Timestamp createdDate, String createdBy, String vendorName, String mentionReason,
				Integer workingStatus) {
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
			this.status = status;
			this.createdDate = createdDate;
			this.createdBy = createdBy;
			this.vendorName = vendorName;
			this.mentionReason = mentionReason;
			this.workingStatus = workingStatus;
		}

	}

	public static class LbServerDetailsDTO {
		private Long id;
		private Long projectId;
		private String environment;
		private String privateIp;
		private String publicIp;
		private Integer status;
		private Long port;
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
		@Override
		public String toString() {
			return "LbServerDetailsDTO [id=" + id + ", projectId=" + projectId + ", environment=" + environment
					+ ", privateIp=" + privateIp + ", publicIp=" + publicIp + ", status=" + status + ", port=" + port
					+ "]";
		}
		public LbServerDetailsDTO(Long id, Long projectId, String environment, String privateIp, String publicIp,
				Long port, Integer status) {
			super();
			this.id = id;
			this.projectId = projectId;
			this.environment = environment;
			this.privateIp = privateIp;
			this.publicIp = publicIp;
			this.status = status;
			this.port = port;
		}
		public LbServerDetailsDTO() {
			super();
			// TODO Auto-generated constructor stub
		}				
		
	}

	public static class LeasedLineDetailsDTO {
		private Long id;
		private Long projectId;
		private String environment;
		private String networkProvider;
		private String privateIp;
		private String privateIpLocation;
		private String publicIp;
		private String publicIpLocation;
		private Integer maximumClient;
		private Integer status;
		private String createdBy;
		private Timestamp createdDate;
		private Timestamp workOrderStartDate;
		private Timestamp workOrderEndDate;
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
		public LeasedLineDetailsDTO(Long id, Long projectId, String environment, String networkProvider,
				String privateIp, String privateIpLocation, String publicIp, String publicIpLocation,
				Integer maximumClient, Integer status, String createdBy, Timestamp createdDate,
				Timestamp workOrderStartDate, Timestamp workOrderEndDate) {
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
		}
		@Override
		public String toString() {
			return "LeasedLineDetailsDTO [id=" + id + ", projectId=" + projectId + ", environment=" + environment
					+ ", networkProvider=" + networkProvider + ", privateIp=" + privateIp + ", privateIpLocation="
					+ privateIpLocation + ", publicIp=" + publicIp + ", publicIpLocation=" + publicIpLocation
					+ ", maximumClient=" + maximumClient + ", status=" + status + ", createdBy=" + createdBy
					+ ", createdDate=" + createdDate + ", workOrderStartDate=" + workOrderStartDate
					+ ", workOrderEndDate=" + workOrderEndDate + "]";
		}

		
		// Constructors, getters, and setters
		
	}

	public static class ServerDetailsDTO {
		private Long id;
		private Long projectId;
		private String environment;
		private String projectName;
		private String ip;
		private String serverType;
		private String cpuCode;
		private String setRam;
		private String osType;
		private String osVersion;
		private String technology;
		private String selectWebSphere;
		private String serverLocation;
		private byte[] encryptedCertificate;
		private Timestamp encryptedCertificateExp;
		private byte[] signingCertificate;
		private Timestamp signingCertificateExp;
		private String createdBy;
		private Timestamp createdDate;
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
		public String getOsVersion() {
			return osVersion;
		}
		public void setOsVersion(String osVersion) {
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
		public ServerDetailsDTO(Long id, Long projectId, String environment, String projectName, String ip,
				String serverType, String cpuCode, String setRam, String osType, String osVersion, String technology,
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
		@Override
		public String toString() {
			return "ServerDetailsDTO [id=" + id + ", projectId=" + projectId + ", environment=" + environment
					+ ", projectName=" + projectName + ", ip=" + ip + ", serverType=" + serverType + ", cpuCode="
					+ cpuCode + ", setRam=" + setRam + ", osType=" + osType + ", osVersion=" + osVersion
					+ ", technology=" + technology + ", selectWebSphere=" + selectWebSphere + ", serverLocation="
					+ serverLocation + ", encryptedCertificate=" + Arrays.toString(encryptedCertificate)
					+ ", encryptedCertificateExp=" + encryptedCertificateExp + ", signingCertificate="
					+ Arrays.toString(signingCertificate) + ", signingCertificateExp=" + signingCertificateExp
					+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", status=" + status + "]";
		}

		// Constructors, getters, and setters
		
		
		
	}

}
