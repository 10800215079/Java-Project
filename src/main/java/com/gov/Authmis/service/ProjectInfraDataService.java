package com.gov.Authmis.service;

import java.util.List;
import java.util.Optional;

import com.gov.Authmis.entity.ProjectInfraDataDatabaseDetails;
import com.gov.Authmis.entity.ProjectInfraDataHardwareDetails;
import com.gov.Authmis.entity.ProjectInfraDataLBServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataLeasedLineDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetailsDTO;
import com.gov.Authmis.entity.ProjectInfraViewDetailsDTO;

public interface ProjectInfraDataService {

	List<Object[]> GetProjectName();

	void saveLBDetails(ProjectInfraDataLBServerDetails data);

	void saveDBDetails(ProjectInfraDataDatabaseDetails data);

	void saveHardwareDetails(ProjectInfraDataHardwareDetails data);

	void saveLeasedLineDetails(ProjectInfraDataLeasedLineDetails data);

	void saveServerDetails(ProjectInfraDataServerDetails data);

	ProjectInfraViewDetailsDTO getProjectDetailsWithIdAndEnvironment(Long id, String environment);

	Optional<ProjectInfraDataServerDetailsDTO> getData(Long id);

	


}
