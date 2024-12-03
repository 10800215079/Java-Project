package com.gov.Authmis.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gov.Authmis.entity.AddApplicationForSecurityAuditEntity;
import com.gov.Authmis.entity.UploadSecurityAuditReportEntity;
import com.gov.Authmis.service.MenuAndSubMenuService;
import com.gov.Authmis.service.UploadSecurityAuditReportService;
import com.gov.Authmis.service.WhiteListSubAUAIPAddressService;

@Controller
public class UploadSecurityAuditReportController {
	@Autowired
	WhiteListSubAUAIPAddressService whiteListSubAUAIPAddresservice;
	@Autowired
	UploadSecurityAuditReportService uploadSecurityAuditReportService;
	@Autowired
	private MenuAndSubMenuService service;

	@GetMapping(path = "/saveApplication")
	public String shareClientCode(Model model, HttpServletRequest request) {
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		String roleId = (String) request.getSession().getAttribute("roleId");
		Boolean b = service.hasRole(roleId, "saveApplication");
		if(!b) {
			return "redirect:/BackToSSO";
		}

		List<Map<String, Object>> subAuaList = this.whiteListSubAUAIPAddresservice.GetSubauaCodeName();
		model.addAttribute("subAuaList", subAuaList);
		
		List<UploadSecurityAuditReportEntity> showAuditReport = uploadSecurityAuditReportService.getAllSecurityDetails();		
		model.addAttribute("showAuditReport", showAuditReport );	
					
		return "UploadSecurityAuditReport";
	}
	
	@PostMapping("/saveApplicationDetails")
	public ResponseEntity<String> saveClientCodeDetails(@RequestBody AddApplicationForSecurityAuditEntity data,
	        HttpServletRequest request) {
	    try {
	        String ssoId = (String) request.getSession().getAttribute("SSOID");
	        data.setCreatedBy(ssoId);
	        data.setStatus(1);
	        uploadSecurityAuditReportService.saveAppDetails(data);
	        return ResponseEntity.ok("Data saved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data");
	    }
	}
	
	@GetMapping("/getAppNames")
	@ResponseBody
	public List<Map<String, String>> getAppNames(@RequestParam String department) {
	    List<Map<String, String>> appNames = uploadSecurityAuditReportService.getAppNamesByDepartment(department);
	    return appNames;
	}


	@PostMapping("/saveSecurityAuditDetails")
	public ResponseEntity<String> saveSecurityAuditDetails(@RequestBody UploadSecurityAuditReportEntity data,
	        HttpServletRequest request) {
	    try {
	        String ssoId = (String) request.getSession().getAttribute("SSOID");
	        data.setCreatedBy(ssoId);
	        data.setStatus(1);
	        uploadSecurityAuditReportService.saveSecurityAuditDetails(data);
	        return ResponseEntity.ok("Data saved successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data");
	    }
	}
	  
	@PostMapping("/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody Map<String, Object> payload) {
		Long id = Long.valueOf(payload.get("id").toString());
		String action = payload.get("action").toString();
		Integer status = action.equals("activate") ? 1 : 0;

		uploadSecurityAuditReportService.updateStatus(id, status);

		return ResponseEntity.ok("Status updated successfully");
	}
	
	@PostMapping("/downloadAuditDocument/{id}")
	 public  ResponseEntity<UploadSecurityAuditReportEntity> downloadPublishedDoc(@PathVariable Long id) {

		 Optional<UploadSecurityAuditReportEntity> downloaddata = uploadSecurityAuditReportService.getAuditData(id);
		  return ResponseEntity.ok(downloaddata.get());
	 }
	
	
}
