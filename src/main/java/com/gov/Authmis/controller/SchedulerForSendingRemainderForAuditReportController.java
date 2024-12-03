package com.gov.Authmis.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.gov.Authmis.service.SchedulerForSendingRemainderForAuditReportService;

//@Controller
@Component
@EnableScheduling
@Validated
public class SchedulerForSendingRemainderForAuditReportController {
	@Autowired
    public SchedulerForSendingRemainderForAuditReportService schedulerForSendingRemainderForAuditReportService;

    ScheduledFuture<?> scheduledFuture;
    
    @Scheduled(cron = "0 30 9 * * ?")
   // @PostMapping("/test")
    //@Scheduled(cron = "0 7 12 * * ?")
    public void performScheduledTask() throws SQLException, UnknownHostException, ParseException {
    	schedulerForSendingRemainderForAuditReportService.createConnection();
    }
    
}
