package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.ProjectInfraDataLeasedLineDetails;



public interface ProjectInfraDataLeaseLineDetailsRepository extends JpaRepository<ProjectInfraDataLeasedLineDetails, Long>  {

	List<ProjectInfraDataLeasedLineDetails> findByProjectIdAndEnvironment(Long projectId, String environment);


}
