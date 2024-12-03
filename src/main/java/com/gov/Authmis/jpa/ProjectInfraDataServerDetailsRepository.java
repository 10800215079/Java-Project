package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.ProjectInfraDataServerDetails;

public interface ProjectInfraDataServerDetailsRepository extends  JpaRepository<ProjectInfraDataServerDetails, Long>{

	List<ProjectInfraDataServerDetails> findByProjectIdAndEnvironment(Long projectId, String environment);


}
