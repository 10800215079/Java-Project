package com.gov.Authmis.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.OtpAuthenticationRequest;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.AddLicencyForSubAuaSystemService;
import com.gov.Authmis.service.SubAuaRegistrationService;
import com.gov.Authmis.service.SubauaLicencekeyUpdationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
public class SubauaLicencekeyUpdationController {
	@Autowired
	private AddLicencyForSubAuaSystemService addLicencyForSubAuaSystemservice;
	@Autowired
	private SubauaLicencekeyUpdationService service;
	@Autowired
	SubauaLicencekeyUpdationService transactionService;
	@Autowired
	SubAuaRegistrationService subAuaRegistrationService;
	@Autowired
	RestTemplate restTemplate;
	
	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	private EntityManager entityManager;
	static Logger logger = LoggerFactory.getLogger(SubauaLicencekeyUpdationController.class);
	@GetMapping("/subauaLicenseKeydata")
	public String viewDataForsubaua(Model m, HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		} else {
			String ssoId  = (String) request.getSession().getAttribute("SSOID");
			List<UsersRoleMapping> userProfile = subAuaRegistrationService.getSsoProfile(ssoId);
			m.addAllAttributes(userProfile);
		
			List<SubauaLicencekeyUpdation> str = service.findSubAuaAll();

			m.addAttribute("subauaLicenseKeydata", str);
//			redirectAttributes.addFlashAttribute("success", "Updated Successfully.");
			return "subauaLicenseKeydata";
		}
	}

	@GetMapping("/edit1/{TID}")
	public String viewdata(Model m, @PathVariable("TID") String TID,HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		} else {

		String ssoId  = (String) request.getSession().getAttribute("SSOID");
		List<UsersRoleMapping> userProfile = subAuaRegistrationService.getSsoProfile(ssoId);
		m.addAllAttributes(userProfile);
		List<Map<String, Object>> subAuaList1 = this.transactionService.GetSubauaCodeName();
		logger.info("SubauaLicencekeyUpdationController===>subAuaList===>" + subAuaList1);
		m.addAttribute("subAuaList1", subAuaList1);
		m.addAttribute("subauaLicenseKeydata", service.findById(TID));
		return "subauaupdatedetails";
		}
	}
	
	@ResponseBody
	@RequestMapping(path = { "/subauaa/{TID}" }, method = RequestMethod.POST)
	public String updateTutorialOfSubAua(@PathVariable("TID") String TID,
			@RequestBody OtpAuthenticationRequest tutorial, Model m, BindingResult bb,
			RedirectAttributes redirectAttributes,HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		} else {
			
//		String response = addUserForGenricFaceAuth(tutorial.getOrgName(),tutorial.getEmail(),tutorial.getSubAuaCode(), tutorial.getActive());
//		JSONObject jsonResponse = new JSONObject(response);
//		boolean isSuccess = jsonResponse.getBoolean("IsSuccess");
//		if(isSuccess) {
			SubauaLicencekeyUpdation tutorialData = service.findById(TID);
			SubauaLicencekeyUpdation dd = updatedata(tutorialData,tutorial);
			if(dd == null) {
			  return "";
			} else {
			m.addAttribute("subauaLicenseKeydata", dd);
			List<Map<String, Object>> subAuaList1 = this.transactionService.GetSubauaCodeName();
			logger.info("SubauaLicencekeyUpdationController===>subAuaList===>" + subAuaList1);
			m.addAttribute("subAuaList1", subAuaList1);
			return "otpGeneration";
			}
		}
		//}
	}

	@GetMapping("/subauadepname/{ORGNAME}")
	public @ResponseBody List<String> getsubauaCode(@PathVariable(value = "ORGNAME") String ORGNAME, Model m) {
		List<String> str = service.getSubauaCode(ORGNAME);
		System.out.println(str + "subaua code");
		m.addAttribute("ORGNAME", str);
		return str;

	}
	
	@ResponseBody
	@PostMapping({"/OTPValidation/{TID}"})
	public OtpRespose otpValidation(@PathVariable("TID") String TID,HttpServletRequest request,@RequestBody 
			OtpAuthenticationRequest otpAuthenticationRequest, Model model, RedirectAttributes redirectAttributes) throws IOException  {
		
		javax.servlet.http.HttpSession session = request.getSession();
	    String txn = (String) session.getAttribute("txn");
	    otpAuthenticationRequest.setTxn(txn);
	    System.out.println(txn);
	    OtpRespose otpResp = new OtpRespose();
	    AuthResponseEntity resp = subAuaRegistrationService.athentication(otpAuthenticationRequest);
	    if(resp.getAuth().getStatus().equalsIgnoreCase("Y")) { 
	    	
	    	SubauaLicencekeyUpdation tutorialData = service.findById(TID);
			
				String response = addUserForGenricFaceAuth(otpAuthenticationRequest.getSubAuaCode(),otpAuthenticationRequest.getOrgName(), otpAuthenticationRequest.getActive());
				JSONObject jsonResponse = new JSONObject(response);
				boolean isSuccess = jsonResponse.getBoolean("IsSuccess");
				if(isSuccess) {
					
			    	SubauaLicencekeyUpdation dd = updatedata(tutorialData,otpAuthenticationRequest);
			    	model.addAttribute("subauaLicenseKeydata", dd);
					List<Map<String, Object>> subAuaList1 = this.transactionService.GetSubauaCodeName();
					logger.info("SubauaLicencekeyUpdationController===>subAuaList===>" + subAuaList1);
					model.addAttribute("subAuaList1", subAuaList1);
					otpResp.setStatus(resp.getAuth().getStatus());
					otpResp.setError(resp.getMessage());
					return otpResp;
				} else {
					otpResp.setStatus("n");
					otpResp.setError(jsonResponse.getString("Message"));
					return otpResp;
				}
			}
	    	
	     else {
	    	otpResp.setStatus(resp.getAuth().getStatus());
	    	otpResp.setError(resp.getMessage());
	    	return otpResp;
	    }
	}
	
	@PostMapping("/downloadPdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("TID") String tid, @RequestParam("status") String status) throws IOException {
        // Replace pdfBytes with your actual byte array containing the PDF data
    	
    	SubauaLicencekeyUpdation newList = service.findById(tid);
    	byte[] pdfBytes = newList.getApprovalDoc();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Approval.pdf");

        return ResponseEntity.ok()
            .headers(headers)
            .body(pdfBytes);
    }
    
    
    @PostMapping("/deactivationDocDownloadPdf")
    public ResponseEntity<byte[]> deactivationDocdownloadPdf(@RequestParam("TID") String tid, @RequestParam("status") String status) throws IOException {
        // Replace pdfBytes with your actual byte array containing the PDF data
    	;
    	SubauaLicencekeyUpdation newList = service.findById(tid);
    	byte[]  pdfBytes = newList.getDEREGISTRATIONAPPROVALDOC();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Approval.pdf");

        return ResponseEntity.ok()
            .headers(headers)
            .body(pdfBytes);
    }
    
    public SubauaLicencekeyUpdation updatedata(SubauaLicencekeyUpdation tutorialData,OtpAuthenticationRequest tutorial) {
		if(tutorial.getActive() == 1) {
			tutorialData.setACTIVE("1");
		}else {
			tutorialData.setACTIVE("0");
		}
		tutorialData.setAPPROVALDATE(tutorial.getApprovalDate());
		tutorialData.setDESIGNATION(tutorial.getDesignation());
		tutorialData.setEMAIL(tutorial.getEmail());
		if(tutorial.getIsRegWithUid() == 1) {
			tutorialData.setIS_REG_WITH_UID("1");
		}else {
			tutorialData.setIS_REG_WITH_UID("0");
		}
		if(tutorial.getTxn() != null ) {
			tutorialData.setDEACTIVATIONTXNID(tutorial.getTxn());
		}
		tutorialData.setMPOCCONTACT(tutorial.getMpocContact());
		tutorialData.setMPOCDESIGNATION(tutorial.getMpocDesignation());
		tutorialData.setMPOCEMAIL(tutorial.getMpocEmail());
		tutorialData.setMPOCNAME(tutorial.getMpocName());
		tutorialData.setOFFICERNAME(tutorial.getOfficerName());
		tutorialData.setPHONE(tutorial.getPhone());
		tutorialData.setSCHEME_PURPOSE(tutorial.getSchemePurpose());
		tutorialData.setSECTIONAADHARACT(tutorial.getSectionAadharAct());
		tutorialData.setURL(tutorial.getUrl());
		if(tutorial.getApprovalDoc().length>0) {
			tutorialData.setApprovalDoc(tutorial.getApprovalDoc());
		}
		LocalDate currentDate = LocalDate.now();
		String currentDateStr = currentDate.toString();
		tutorialData.setUPDATEDDATE(currentDateStr);
		tutorialData.setDEREGISTRATIONAPPROVALDATE(tutorial.getDeRegistrationApprovalDate());
		tutorialData.setDEREGISTRATIONAPPROVALDOC(tutorial.getDeRegistrationApprovalDoc());	
 		SubauaLicencekeyUpdation dd = service.save(tutorialData);
        return dd;
    }
    
public String addUserForGenricFaceAuth( String subauaCode, String subAuaName,Long status ) {
	
	
		String sql = " SELECT SUB_AUA_LK  FROM SUBAUA_EKYC_LK  WHERE SUB_AUA_CODE = '" + subauaCode
				+ "'  and status = 1 Fetch First rows only";
	
		Query query = this.entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> data = query.getResultList();
		String lk = "NA";
		if (!data.isEmpty()) {
			lk = data.get(0);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = "{ \"LK\": \"" + lk + "\", \"SUBAUA_CODE\": \"" + subauaCode + "\", \"ORG_NAME\": \"" + subAuaName + "\", \"IS_ACTIVE\": "+status+" }";

        System.out.println("responsBody ==============="+ requestBody);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(Constant.UPDATECREATE_URL, requestEntity,
				String.class);
		if (responseEntity.getStatusCode().is2xxSuccessful() ) {
			String responseBody = responseEntity.getBody();
			System.out.println("API Response: " + responseBody);
		} else {
			System.err.println("Error: " + responseEntity.getStatusCode());
		}
		return responseEntity.getBody();
	}
}
