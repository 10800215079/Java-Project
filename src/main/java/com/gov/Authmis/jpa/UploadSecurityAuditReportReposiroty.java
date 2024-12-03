package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gov.Authmis.entity.UploadSecurityAuditReportEntity;

public interface UploadSecurityAuditReportReposiroty extends JpaRepository<UploadSecurityAuditReportEntity, Long> {

	@Modifying
    @Query("UPDATE UploadSecurityAuditReportEntity u SET u.status = :status WHERE u.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

	List<UploadSecurityAuditReportEntity> findByStatus(int status);

	 @Query("SELECT u FROM UploadSecurityAuditReportEntity u WHERE subauaCode = :subauaCode AND u.appName = :appName AND u.status = '1'")
	 List<UploadSecurityAuditReportEntity> findByDepartmentAndAppNameWithStatus(@Param("subauaCode") String subauaCode, @Param("appName") String appName);



}
