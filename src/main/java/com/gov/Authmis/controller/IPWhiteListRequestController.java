package com.gov.Authmis.controller;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gov.Authmis.dao.SubAuaWiseSendMail;
import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.sendMailModel;
import com.gov.Authmis.service.IPwhiteListRequestService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.service.WhiteListRepository;
import com.gov.Authmis.service.WhiteListSubAUAIPAddressService;
import com.itextpdf.text.DocumentException;

@Controller
public class IPWhiteListRequestController {
 
	@Autowired
	private IPwhiteListRequestService iPwhiteListRequestService;
	
	@Autowired
	SubauaWiseTransactionService transactionService;
	
	@Autowired
	public SubAuaWiseSendMail subAuaWiseSendMail;
	
	@GetMapping("/getAllRequest")
	public String getRequestedIpwhiteList(HttpServletRequest request,Model model) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid);
		System.out.println("subAuaList===>" + subAuaList);
		model.addAttribute("subAuaList", subAuaList);
		List<RequestForIpWhitelistEntity> ipWhitelistEntities = iPwhiteListRequestService.getAllRequestedIPList();
		model.addAttribute("details", ipWhitelistEntities);
		return "WhiltelistRequest";
	}
	
	@GetMapping("/getRequest")
	public String getRequestByDeptAndStatus(@RequestParam Long status, @RequestParam String department, HttpServletRequest request,Model model) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<RequestForIpWhitelistEntity> ipWhitelistEntities;
		if(department.equalsIgnoreCase("All") && status ==0 ) {
			ipWhitelistEntities = iPwhiteListRequestService.getAllRequestedIPList();
		} else if(department.equalsIgnoreCase("All") && status !=0) {
			ipWhitelistEntities = iPwhiteListRequestService.getRequestedIPListByStatus(status);
		} else if(!department.equalsIgnoreCase("All") && status ==0) {
			ipWhitelistEntities = iPwhiteListRequestService.getRequestedIPListBySubAUACode(department);
		} else {
			ipWhitelistEntities = iPwhiteListRequestService.findByStatusAndSubAUA(status, department);
		}

//		request.setAttribute("status",status );
//		request.setAttribute("department",department);
		
		HttpSession session = request.getSession();
		session.setAttribute("subAuaCode", department);
		
		Long getidofstatus = status;
		if (getidofstatus != null) {
		    session.setAttribute("status1", getidofstatus);
		}	
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid);
		System.out.println("subAuaList===>" + subAuaList);
		model.addAttribute("subAuaList", subAuaList);
		
		model.addAttribute("details", ipWhitelistEntities);
		return "WhiltelistRequest";
	}
	
	@PostMapping("/rejectRequest")
	public String rejectRequest(HttpServletRequest request,Model model, @RequestParam("id") Long id, 
			@RequestParam("reason") String reason, RedirectAttributes redirectAttributes) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		iPwhiteListRequestService.updateRejectedRequest(id,reason, ssoid );
		redirectAttributes.addFlashAttribute("success", "Request has been Rejected Succesfully");
		
		return "WhiltelistRequest";
	}
	
	@PostMapping("/approveRequest")
	public String approveRequest(HttpServletRequest request,Model model, @RequestParam("id") Long id,  RedirectAttributes redirectAttributes) 
			throws SQLException, ServerException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		
		Optional<RequestForIpWhitelistEntity> requestList =  iPwhiteListRequestService.getRequestToApprove(id);
		RequestForIpWhitelistEntity ipWhitelistEntity = new RequestForIpWhitelistEntity(); 
		if(requestList.isPresent()) {
			ipWhitelistEntity = requestList.get();
		}
		WhiteListSubAUAEntity entity = new WhiteListSubAUAEntity();
		entity.setApplicatonUrl(ipWhitelistEntity.getApplicationUrl());
		entity.setAppName(ipWhitelistEntity.getAppName());
		entity.setCreatedBy(ssoid);
		entity.setIpAddress(ipWhitelistEntity.getIpAddress());
		entity.setIpBelongsTo(ipWhitelistEntity.getIpBelongsTo());
		entity.setIsDocPublished(ipWhitelistEntity.getIsDocPublished());
		entity.setPublishedDoc(ipWhitelistEntity.getPublishedDoc());
		entity.setSchemeName(ipWhitelistEntity.getSchemeName());
		entity.setServicetype(ipWhitelistEntity.getRequestType());
		entity.setStatus(ipWhitelistEntity.getStatus());
		entity.setSubAuaCode(ipWhitelistEntity.getSubAUACode());
		entity.setSubAuaName(ipWhitelistEntity.getSubAUAName());
		
		Object user = iPwhiteListRequestService.addWhitListIp(entity);
		iPwhiteListRequestService.updateApproveRequest(id, ssoid);
      
	    if (user == null) {
	        throw new ServerException(null, null);
	    } else {
	    	redirectAttributes.addFlashAttribute("success", "Request has been Approved Succesfully");
	        return "redirect:/white_List_SubAUA_IPAddress";
	    }
	}
	
	@ResponseBody
	@PostMapping("/sendMailToSewadwar")
	public sendMailModel sendMailToSewadwar(@RequestBody List<sendMailModel> selectedItems, HttpSession session) {
		String mailbody = null;
		String emails = subAuaWiseSendMail.getEmails(selectedItems);
		session.setAttribute("selectedList", selectedItems);
		String newEmails = emails.concat(",pm.uid@rajasthan.gov.in, support.uid@rajasthan.gov.in, pankajjaldeep.doit@rajasthan.gov.in");
		sendMailModel mailModel = new  sendMailModel();
		mailModel.setToCC(newEmails);
		mailModel.setTo("apim.rsd@rajasthan.gov.in");
		mailModel.setSubject("IP Whitelist Request for Aadhaar Production Services");
		
		try {
			mailbody = subAuaWiseSendMail.getMailContent( selectedItems);
		} catch (SQLException | DocumentException | IOException | ParseException e) {
			e.printStackTrace();
		}
		mailModel.setMailBody(mailbody);
        return mailModel;
    }
	
	@ResponseBody
	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMail(@RequestBody sendMailModel selectedItems, HttpServletRequest request) {
		
		@SuppressWarnings("unchecked")
		List<sendMailModel> items = (List<sendMailModel>) request.getSession().getAttribute("selectedList");
		
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		try {
		    subAuaWiseSendMail.sendHtmlMailTosewadwar(selectedItems);
		    iPwhiteListRequestService.updateEmailStatus(items, ssoid);
		    return ResponseEntity.ok("Mail Sent to Sewadwar successfully");
		} catch (MessagingException | DocumentException | IOException e) {
		    // Handle exceptions and return an appropriate error response
		    String errorMessage = "Failed to send mail: " + e.getMessage();
		    e.printStackTrace(); // Log the stack trace for debugging purposes
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
}
