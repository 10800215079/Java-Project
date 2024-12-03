package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gov.Authmis.entity.AddApplicationForSecurityAuditEntity;
import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.UploadSecurityAuditReportEntity;


public interface UploadSecurityAuditReportService {

	List<Map<String, String>> getAppNamesByDepartment(String department);

	void saveAppDetails(AddApplicationForSecurityAuditEntity data);

	void saveSecurityAuditDetails(UploadSecurityAuditReportEntity data);

	List<UploadSecurityAuditReportEntity> getAllSecurityDetails();

	void updateStatus(Long id, int i);

	Optional<UploadSecurityAuditReportEntity> getAuditData(Long id);

}
