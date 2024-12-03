package com.gov.Authmis.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.entity.ProjectInfraDataDatabaseDetails;
import com.gov.Authmis.entity.ProjectInfraDataHardwareDetails;
import com.gov.Authmis.entity.ProjectInfraDataLBServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataLeasedLineDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetails;
import com.gov.Authmis.entity.ProjectInfraDataServerDetailsDTO;
import com.gov.Authmis.entity.ProjectInfraViewDetailsDTO;
import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.service.ProjectInfraDataService;

@Controller
public class ProjectInfraDataController {
	@Autowired
	ProjectInfraDataService projectServerDetailsService;
	
	@GetMapping(path = "/viewProjectServerConfigurations")
	public String viewProjectServerConfigurations(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}						
		List<Object[]> projectList = projectServerDetailsService.GetProjectName();		
		model.addAttribute("projectList", projectList);
		return "ProjectInfraData";

	}
	
	//Save LB Data
	@PostMapping("/saveLBServerDetails")
    public ResponseEntity<String> saveLBServerDetails(@RequestBody ProjectInfraDataLBServerDetails data, HttpServletRequest request) {
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		data.setCreatedBy(ssoId);
		data.setStatus(1);		
		projectServerDetailsService.saveLBDetails(data);		
        return ResponseEntity.ok("Data saved successfully");
    }
	
	//Save DB Data
	@PostMapping("/saveDBServerDetails")
    public ResponseEntity<String> saveDBServerDetails(@RequestBody ProjectInfraDataDatabaseDetails data, HttpServletRequest request) {
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		data.setCreatedBy(ssoId);
		data.setStatus(1);	
		projectServerDetailsService.saveDBDetails(data);      
        return ResponseEntity.ok("Data saved successfully");
    }
	
	//Save Hardware Details
	@PostMapping("/saveHardwareDetails")
    public ResponseEntity<String> saveHardwareDetails(@RequestBody ProjectInfraDataHardwareDetails data, HttpServletRequest request) {
		String ssoId = (String) request.getSession().getAttribute("SSOID");
		data.setCreatedBy(ssoId);
		data.setStatus(1);	
		projectServerDetailsService.saveHardwareDetails(data);     
        return ResponseEntity.ok("Data saved successfully");
    }
	
	
	//Save Leased Line  Details
		@PostMapping("/saveLeasedLineDetails")
	    public ResponseEntity<String> saveLeasedLineDetails(@RequestBody ProjectInfraDataLeasedLineDetails data, HttpServletRequest request) {
			String ssoId = (String) request.getSession().getAttribute("SSOID");
			data.setCreatedBy(ssoId);
			data.setStatus(1);			
			projectServerDetailsService.saveLeasedLineDetails(data);	       
	        return ResponseEntity.ok("Data saved successfully");
	    }
		
		// Save Leased Line Details
		@PostMapping("/saveServerDetails")
		public ResponseEntity<String> saveServerDetails(@RequestBody ProjectInfraDataServerDetails data, HttpServletRequest request) {
			String ssoId = (String) request.getSession().getAttribute("SSOID");
			data.setCreatedBy(ssoId);
			data.setStatus(1);
			projectServerDetailsService.saveServerDetails(data);
			return ResponseEntity.ok("Data saved successfully");
		}
		
		/*
		 * //View
		 * 
		 * @GetMapping("/viewDetails/{projectId}/{environment}") public String
		 * viewDetails(@PathVariable String projectId, @PathVariable String environment)
		 * { System.out.println("on vied page......................");
		 * 
		 * return "detailsView"; }
		 */

		// View
		@GetMapping("/viewDetails")
		public String viewDetails(@RequestParam Long projectId, @RequestParam String environment, Model model) {		   
			System.out.println("on vied page......................");
			ProjectInfraViewDetailsDTO projectDetails = projectServerDetailsService.getProjectDetailsWithIdAndEnvironment(projectId, environment);	  
			
		    model.addAttribute("projectDetails", projectDetails);		    		   		    
		    return "ProjectInfraDataListView";	        
		}
		
		@ResponseBody
		@GetMapping("/downloadSigningCertificate/{id}")
		public ProjectInfraDataServerDetailsDTO downloadSigningCertificate(@PathVariable Long id) {
			Optional<ProjectInfraDataServerDetailsDTO> downloadCertificate = projectServerDetailsService.getData(id);			
			 return downloadCertificate.get();
		}
		
		@ResponseBody
		@GetMapping("/downloadEncryptedCertificate/{id}")
		public ProjectInfraDataServerDetailsDTO downloadEncryptedCertificate(@PathVariable Long id) {
			Optional<ProjectInfraDataServerDetailsDTO> downloadCertificate = projectServerDetailsService.getData(id);			
			 return downloadCertificate.get();
		}
		 
}
