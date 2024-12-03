package com.gov.Authmis.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.AddApplicationForSecurityAuditEntity;
import com.gov.Authmis.entity.UploadSecurityAuditReportEntity;
import com.gov.Authmis.jpa.AddApplicationForSecurityAuditRepository;
import com.gov.Authmis.jpa.UploadSecurityAuditReportReposiroty;
import com.gov.Authmis.service.UploadSecurityAuditReportService;


@Service
public class UploadSecurityAuditReportServiceImpl implements UploadSecurityAuditReportService{
	@Autowired
	AddApplicationForSecurityAuditRepository addApplicationForSecurityAuditRepository;
	@Autowired
	UploadSecurityAuditReportReposiroty uploadSecurityAuditReportReposiroty;
	
	@Override
	public void saveAppDetails(AddApplicationForSecurityAuditEntity data) {
		addApplicationForSecurityAuditRepository.save(data);
		
	}

    public List<Map<String, String>> getAppNamesByDepartment(String departmentId) {
        List<Object[]> results = addApplicationForSecurityAuditRepository.findAppNamesByDepartment(departmentId);
        List<Map<String, String>> appNames = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, String> map = new HashMap<>();
            map.put("appName", (String) result[1]); // Assuming the app name is the second element
            appNames.add(map);
        }

        return appNames;
    }

	@Override
	@Transactional
	public void saveSecurityAuditDetails(UploadSecurityAuditReportEntity data) {
		//Check already entry is present . 
		//If present decativate that one and then update new one
		
		String subauaCode = data.getSubauaCode();
		String appName =  data.getAppName();		
		List<UploadSecurityAuditReportEntity> checkbeforeSave = uploadSecurityAuditReportReposiroty
				.findByDepartmentAndAppNameWithStatus(subauaCode,appName);
						
		if(!checkbeforeSave.isEmpty()) {
			Long id = checkbeforeSave.get(0).getId();
			uploadSecurityAuditReportReposiroty.updateStatus(id, 0);
		}
		
		uploadSecurityAuditReportReposiroty.save(data);
		
	}

	@Override
	public List<UploadSecurityAuditReportEntity> getAllSecurityDetails() {
		List<UploadSecurityAuditReportEntity> getAlldetails = uploadSecurityAuditReportReposiroty.findAll();
		return getAlldetails;
	}

	@Override
	@Transactional
	public void updateStatus(Long id, int status) {
		uploadSecurityAuditReportReposiroty.updateStatus(id, status);
		
	}

	@Override
	public Optional<UploadSecurityAuditReportEntity> getAuditData(Long id) {
		Optional<UploadSecurityAuditReportEntity> data = uploadSecurityAuditReportReposiroty.findById(id);
		return data;
	}

   

}
