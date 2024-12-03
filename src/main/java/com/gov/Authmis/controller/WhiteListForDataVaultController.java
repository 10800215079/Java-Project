package com.gov.Authmis.controller;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.gov.Authmis.dao.SubAuaWiseSendMail;
import com.gov.Authmis.entity.SubAua;
import com.gov.Authmis.model.WhitelistForDataVaultModel;
import com.gov.Authmis.service.MenuAndSubMenuService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.service.WhiteListRequestForDataVoultService;

@Controller
public class WhiteListForDataVaultController {
	
	@Autowired
	private MenuAndSubMenuService menuAndSubMenuService;
	
	@Autowired
	SubauaWiseTransactionService transactionService;
	
	@Autowired
	public SubAuaWiseSendMail subAuaWiseSendMail;
	
	@Autowired
	private WhiteListRequestForDataVoultService dataVoultService;
	
	@GetMapping("/requestForWhiteListForDataVoult")
	public String requestForWhitelist(HttpServletRequest request,Model model) throws SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String roleId = (String) request.getSession().getAttribute("roleId");
		Boolean b = menuAndSubMenuService.hasRole(roleId, "requestForWhiteListForDataVoult");
		if(!b) {
			return "redirect:/BackToSSO";
		}
		List<WhitelistForDataVaultModel> dataList = dataVoultService.getAllWhiteListRecords();
		model.addAttribute("dataList", dataList);
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		List<SubAua> subAuaList = this.transactionService.GetSubauaCodeName(ssoid);
		System.out.println("subAuaList===>" + subAuaList);
		model.addAttribute("subAuaList", subAuaList);
		return "whiteListReqForDataVault";
	}
	
	@PostMapping("/saveIpWhitelistRequest")
	public ResponseEntity<String> submitData(@RequestBody List<WhitelistForDataVaultModel> dataList,HttpServletRequest request) {
	    
	    return dataVoultService.saveWhiteListRequest(dataList, request);
	}
	
	@GetMapping("/getRecordById/{id}")
	public ResponseEntity<WhitelistForDataVaultModel> getRecordById(@PathVariable("id") Long id) {
		// Fetch the record by id from the database or service
		WhitelistForDataVaultModel record = dataVoultService.getWhiteListRecordById(id);
		if (record != null) {
			return ResponseEntity.ok(record);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
