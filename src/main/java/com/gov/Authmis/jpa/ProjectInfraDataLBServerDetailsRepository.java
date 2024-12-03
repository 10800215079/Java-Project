package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.ProjectInfraDataLBServerDetails;

public interface ProjectInfraDataLBServerDetailsRepository extends JpaRepository<ProjectInfraDataLBServerDetails, Long> {

	List<ProjectInfraDataLBServerDetails> findByProjectIdAndEnvironment(Long projectId, String environment);


}
