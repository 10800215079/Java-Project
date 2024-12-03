package com.gov.Authmis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;
import com.gov.Authmis.service.RequestForIpWhitelistService;
import com.gov.Authmis.service.ShareLicenceKeyService;



@Controller
public class ShareLicenceKeyController {
	
	@Autowired 
	private ShareLicenceKeyService shareLicenceKeyService;
	 	 
	@Autowired
	private RequestForIpWhitelistService requestForIpWhitelistService;
	
	@GetMapping(path="/shareLicenceKey")
	public String shareLicenceKey(Model model, HttpServletRequest request) {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");	
		
		//get department
		 List<SubAua> detlist = requestForIpWhitelistService.getDepartment(ssoid);	
		 String departmentid = null;
		 List<String> subAuaList1  = new ArrayList<>();
		 List<Map<String, Object>> subAuaList = new ArrayList<>();
		
		 for (SubAua subAua : detlist) {
			 subAuaList1.add(subAua.getID());
			 Map<String, Object> subAuaMap = new HashMap<>();
			 subAuaMap.put("ID",subAua.getID());
			 subAuaMap.put("NAME", subAua.getNAME());
			 subAuaList.add(subAuaMap);
			 departmentid += subAua.getID()+","; 			   
			}		
		 	
		List<Map<String, Object>> lkdetails = shareLicenceKeyService.getLicenceExpiryDates(subAuaList);		
		model.addAttribute("lkdetails", lkdetails );									
		return "ShareLicenceKey";
	}
	
	//Get all data
	@GetMapping("/viewLkDetail/{subauacode}")
	@ResponseBody
	public ResponseEntity<ViewShareLicenceKeyDetails> viewDatabaseDetail(@PathVariable String subauacode) {
		Optional<ViewShareLicenceKeyDetails> loadLineDetails = shareLicenceKeyService.getSubauaDetails(subauacode);
		return loadLineDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/shareLicense")
	public ResponseEntity<String> shareLicense(@RequestBody ViewShareLicenceKeyDetails data,
			HttpServletRequest request) {
		return shareLicenceKeyService.shareLicense(data);
	}	

}
