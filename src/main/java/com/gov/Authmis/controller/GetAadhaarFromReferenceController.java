package com.gov.Authmis.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gov.Authmis.dao.OracleCon;






@Component
@EnableScheduling
@Validated
public class GetAadhaarFromReferenceController
{
	@Autowired
	public OracleCon oracleCon;
	
	ScheduledFuture<?> scheduledFuture;

	@RequestMapping(value = ("/"), method = RequestMethod.GET)	
	//@Scheduled(cron = "0 30 8 * * *")     //corn job for everyday at 8:30 am
	//@Scheduled(cron = "0 0/30 * * * ?")   //  Corn job for everyday for every 30 min
	//@Scheduled(cron = "0 30 8 ? * 2") // Corn job for Tuesday at 8:30 am
	
	@Scheduled(cron = "0 00 22 * * ?")
	//@Scheduled(cron = "0 13 16 * * ?")
	public String updateAadhaarByRefId() throws UnknownHostException, SQLException 
	{
		 this.oracleCon.createConnection();
		 return "index";
	}
}

