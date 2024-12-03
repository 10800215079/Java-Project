package com.gov.Authmis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gov.Authmis.model.SQLGrammarException;
import com.gov.Authmis.service.QueryBuilderService;
import com.gov.Authmis.service.SubAuaRegistrationService;

@RestController
public class QueryBuilderSelectQueryRestController {
	
	@Autowired
	private QueryBuilderService service;
	@Autowired
	SubAuaRegistrationService subAuaRegistrationService;
	
	private String SELECT = "SELECT";
	
	@PostMapping("/executeQueryTest")
	@ResponseBody
	public Map<String,Object> saveQueryData( @RequestParam("UUID") String Uuid, @RequestParam("queryString") String queryString,
			HttpServletRequest request) throws Exception, com.gov.Authmis.model.OracleDatabaseException,
			UnexpectedRollbackException, SQLGrammarException {
		
		
		// Main Menu
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		System.out.println("========>>>>>>>>ssoid from QueryBuilderSelectQueryRestController " + ssoid);
	
		String ipAddress = request.getRemoteAddr();
		System.out.println("=======**************************>>>>>System Ip :::: " + ipAddress);
		List<Map<String, Object>> queryResult = null;
		Map<String,Object> dataMap = new HashMap<>();
		String input = "";
		String output = null;
		input = queryString.trim();
		input = input.replace("::", "\\:\\:");
		String convertedToUpperCase = input.toUpperCase();
		
		//logger.info("QueryBuildController======>inside method2" + convertedToUpperCase);
		
			// Condition for select query
			 if (convertedToUpperCase.contains(SELECT)) {
				 service.SaveSelectQueryBuilderLogData(Uuid,queryString,ssoid,ipAddress);
				 queryResult = service.getExecuteSelectQuery(convertedToUpperCase);
				// Store the query result in the model
				//return queryResult;
			}
			 dataMap.put("columns", queryResult.get(0).keySet());
			 dataMap.put("data", queryResult);
			return dataMap;
			
		}
}
