package com.gov.Authmis.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.entity.ProjectDetailsEntity;
import com.gov.Authmis.entity.ProjectInfraDataDatabaseDetails;
import com.gov.Authmis.entity.ProjectInfraDataHardwareDetails;
import com.gov.Authmis.entity.ProjectInfraDataLBServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataLeasedLineDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetailsDTO;
import com.gov.Authmis.entity.ProjectInfraViewDetailsDTO;
import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.jpa.ProjectDetailsRepository;
import com.gov.Authmis.jpa.ProjectInfraDataDBServerDetailsRepository;
import com.gov.Authmis.jpa.ProjectInfraDataHardwareDetailsRepository;
import com.gov.Authmis.jpa.ProjectInfraDataLBServerDetailsRepository;
import com.gov.Authmis.jpa.ProjectInfraDataLeaseLineDetailsRepository;
import com.gov.Authmis.jpa.ProjectInfraDataServerDetailsDTORepository;
import com.gov.Authmis.jpa.ProjectInfraDataServerDetailsRepository;
import com.gov.Authmis.service.ProjectInfraDataService;

@Service
public class ProjectInfraDataImpl implements ProjectInfraDataService {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ProjectInfraDataLBServerDetailsRepository projectServerDataLBServerDetailsRepository;
	@Autowired
	private ProjectInfraDataDBServerDetailsRepository projectServerDataDBServerDetailsRepository;
	@Autowired
	private ProjectInfraDataHardwareDetailsRepository projectServerDataHardwareDetailsRepository;
	@Autowired
	private ProjectInfraDataLeaseLineDetailsRepository projectServerDataLeaseLineDetailsRepository;
	@Autowired
	private ProjectInfraDataServerDetailsRepository projectServerDataServerDetailsRepository;
	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;
	@Autowired
	ProjectInfraDataServerDetailsDTORepository projectInfraDataServerDetailsDTORepository;


	// For the list of project
	@Override
	public List<Object[]> GetProjectName() {
		String sql = "SELECT PROJECT_ID,PROJECT_NAME FROM PROJECT_DETAILS WHERE STATUS = 1";
		Query query = this.em.createNativeQuery(sql);

		@SuppressWarnings("unchecked") // Suppress warning for this specific case
		List<Object[]> projectList = query.getResultList();
		return projectList;

	}

	@Override
	public void saveLBDetails(ProjectInfraDataLBServerDetails data) {
		// Save data using JPA repository
		projectServerDataLBServerDetailsRepository.save(data);

	}

	@Override
	public void saveDBDetails(ProjectInfraDataDatabaseDetails data) {
		projectServerDataDBServerDetailsRepository.save(data);

	}

	@Override
	public void saveHardwareDetails(ProjectInfraDataHardwareDetails data) {
		projectServerDataHardwareDetailsRepository.save(data);

	}

	@Override
	public void saveLeasedLineDetails(ProjectInfraDataLeasedLineDetails data) {
		projectServerDataLeaseLineDetailsRepository.save(data);

	}

	@Override
	public void saveServerDetails(ProjectInfraDataServerDetails data) {
		projectServerDataServerDetailsRepository.save(data);

	}

	@Override
	public ProjectInfraViewDetailsDTO getProjectDetailsWithIdAndEnvironment(Long projectId, String environment) {
		ProjectDetailsEntity projectDetails = projectDetailsRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Table not found"));

        projectDetails.setDatabaseDetails(projectServerDataDBServerDetailsRepository.findByProjectIdAndEnvironment(projectId, environment));
        projectDetails.setHardwareDetails(projectServerDataHardwareDetailsRepository.findByProjectIdAndEnvironment(projectId, environment));
        projectDetails.setLeasedLineDetails(projectServerDataLeaseLineDetailsRepository.findByProjectIdAndEnvironment(projectId, environment));
        projectDetails.setServerDetails(projectServerDataServerDetailsRepository.findByProjectIdAndEnvironment(projectId, environment));
        projectDetails.setLbServerDetails(projectServerDataLBServerDetailsRepository.findByProjectIdAndEnvironment(projectId, environment));
        
        ProjectInfraViewDetailsDTO dto = transformToDTO(projectDetails);
        return transformToDTO(projectDetails);
	}
	
	private ProjectInfraViewDetailsDTO transformToDTO(ProjectDetailsEntity projectDetails) {
	    ProjectInfraViewDetailsDTO dto = new ProjectInfraViewDetailsDTO();
	    
	    dto.setProjectId(projectDetails.getProjectId());
	    dto.setProjectName(projectDetails.getProjectName());
	    
	    dto.setDatabaseDetails(
	        projectDetails.getDatabaseDetails().stream()
	            .map(db -> new ProjectInfraViewDetailsDTO.DatabaseDetailsDTO(
	                db.getId(), db.getDatabaseName(), db.getUsername(), db.getEnvironment(), db.getIp(),
	                db.getDatabaseVersion(), db.getServerPassword(), db.getServiceName(), db.getServerPort(), db.getStatus(),
	                db.getCreatedDate(), db.getCreatedBy()))
	            .collect(Collectors.toList())
	    );

	    dto.setHardwareDetails(
	        projectDetails.getHardwareDetails().stream()
	            .map(hardware -> new ProjectInfraViewDetailsDTO.HardwareDetailsDTO(
	                hardware.getId(), hardware.getDeviceName(), hardware.getDeviceMaker(), hardware.getDeviceModelName(),
	                hardware.getDeviceSerialNo(), hardware.getDeviceEsnNo(), hardware.getDeviceType(), hardware.getDeviceLocation(),
	                hardware.getManagementIpAddress(), hardware.getRfsServerIp(), hardware.getClientIp(), hardware.getMaximumClient(),
	                hardware.getWorkOrderNumber(), hardware.getWorkOrderStartDate(), hardware.getWorkOrderEndDate(), hardware.getEnvironment(),
	                hardware.getStatus(), hardware.getCreatedDate(), hardware.getCreatedBy(), hardware.getVendorName(), hardware.getMentionReason(),
	                hardware.getWorkingStatus()))
	            .collect(Collectors.toList())
	    );

	    dto.setLbServerDetails(
	        projectDetails.getLbServerDetails().stream()
	            .map(lbServer -> new ProjectInfraViewDetailsDTO.LbServerDetailsDTO(
	                lbServer.getId(), lbServer.getProjectId(), lbServer.getEnvironment(), lbServer.getPrivateIp(), lbServer.getPublicIp(), lbServer.getPort(),lbServer.getStatus()))
	            .collect(Collectors.toList())
	    );

	    dto.setLeasedLineDetails(
	        projectDetails.getLeasedLineDetails().stream()
	            .map(leasedLine -> new ProjectInfraViewDetailsDTO.LeasedLineDetailsDTO(
	                leasedLine.getId(), leasedLine.getProjectId(), leasedLine.getEnvironment(), leasedLine.getNetworkProvider(),
	                leasedLine.getPrivateIp(), leasedLine.getPrivateIpLocation(), leasedLine.getPublicIp(), leasedLine.getPublicIpLocation(),
	                leasedLine.getMaximumClient(), leasedLine.getStatus(), leasedLine.getCreatedBy(), leasedLine.getCreatedDate(),
	                leasedLine.getWorkOrderStartDate(), leasedLine.getWorkOrderEndDate()))
	            .collect(Collectors.toList())
	    );

	    dto.setServerDetails(
	        projectDetails.getServerDetails().stream()
	            .map(server -> new ProjectInfraViewDetailsDTO.ServerDetailsDTO(
	                server.getId(), server.getProjectId(), server.getEnvironment(), server.getProjectName(), server.getIp(), 
	                server.getServerType(), server.getCpuCode(), server.getSetRam(), server.getOsType(), server.getOsVersion(), 
	                server.getTechnology(), server.getSelectWebSphere(), server.getServerLocation(), server.getEncryptedCertificate(),
	                server.getEncryptedCertificateExp(), server.getSigningCertificate(), server.getSigningCertificateExp(), 
	                server.getCreatedBy(), server.getCreatedDate(), server.getStatus()))
	            .collect(Collectors.toList())
	    );

	    return dto;
	}

	@Override
	public Optional<ProjectInfraDataServerDetailsDTO> getData(Long id) {
		Optional<ProjectInfraDataServerDetailsDTO> data = projectInfraDataServerDetailsDTORepository.findById(id);
		return data;
	}

	
}

