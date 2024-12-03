package com.gov.Authmis.controller;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import com.gov.Authmis.dao.SchedulerForRemainderServiceImpl;
import com.gov.Authmis.service.SchedulerForRemainderService;




@Component
@EnableScheduling
@Validated
public class SchedulerForRemainderController {

    @Autowired
    public SchedulerForRemainderService schedulerForRemainderService;

    // for test
    @Autowired
    public SchedulerForRemainderServiceImpl schedulerForRemainderServiceImpl;
    ScheduledFuture<?> scheduledFuture;

    @Scheduled(cron = "0 0 10 * * ?")// for 10:00 AM every day
    //@Scheduled(cron = "0 24 16 * * ?")

    @PostMapping("/checkremainder")
    public void performScheduledTask() throws SQLException, UnknownHostException {
    	schedulerForRemainderService.setRemainder();
        // Perform other scheduled tasks if needed
    }
    
}