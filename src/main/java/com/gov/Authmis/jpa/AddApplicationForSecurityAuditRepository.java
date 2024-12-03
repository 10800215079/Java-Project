package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gov.Authmis.entity.AddApplicationForSecurityAuditEntity;

public interface AddApplicationForSecurityAuditRepository extends JpaRepository<AddApplicationForSecurityAuditEntity, Long> {
	
	@Query("SELECT DISTINCT a.department, a.appName FROM AddApplicationForSecurityAuditEntity a WHERE a.department = :department")
	List<Object[]> findAppNamesByDepartment(@Param("department") String department);



}
