package com.gov.Authmis.controller;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.gov.Authmis.dao.SchedulerForErrorReportServiceImpl;
import com.gov.Authmis.service.SchedulerForErrorReportService;

@Component
@EnableScheduling
@Validated
public class SchedulerForErrorReportController {

    @Autowired
    public SchedulerForErrorReportService schedulerForErrorReportService;

    // for test
    @Autowired
    public SchedulerForErrorReportServiceImpl schedulerForErrorReportServiceImpl;
    ScheduledFuture<?> scheduledFuture;

   // @Scheduled(cron = "0 00 22 * * ?") // Example cron expression for 10:00 PM every day
    @Scheduled(cron = "0 30 8 * * ?")
    public void performScheduledTask() throws SQLException, UnknownHostException {
        schedulerForErrorReportServiceImpl.createConnection();
        // Perform other scheduled tasks if needed
    }
}
