package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gov.Authmis.entity.ProjectInfraDataHardwareDetails;

public interface ProjectInfraDataHardwareDetailsRepository extends JpaRepository<ProjectInfraDataHardwareDetails, Long> {

	List<ProjectInfraDataHardwareDetails> findByProjectIdAndEnvironment(Long projectId, String environment);


}
