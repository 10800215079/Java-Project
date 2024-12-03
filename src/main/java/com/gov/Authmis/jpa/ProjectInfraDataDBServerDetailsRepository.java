package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.ProjectInfraDataDatabaseDetails;


public interface ProjectInfraDataDBServerDetailsRepository extends JpaRepository<ProjectInfraDataDatabaseDetails, Long> {

	List<ProjectInfraDataDatabaseDetails> findByProjectIdAndEnvironment(Long projectId, String environment);


}
