package com.gov.Authmis.controller;


import java.io.FileNotFoundException;
import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import com.gov.Authmis.service.SchedulerForSendingFluctuationReportInMailService;


//@Controller


/*@Component
@EnableScheduling
@Validated
public class SchedulerForSendingFluctuationReportInMailController {
	@Autowired
	SchedulerForSendingFluctuationReportInMailService schedulerForSendingFluctuationReportInMailService;
		@Scheduled(cron = "0 0/5 * * * ?")
	    //@PostMapping("/fluctuationReport")
	    public void performScheduledTask() throws FileNotFoundException, IOException, MessagingException{
	    	schedulerForSendingFluctuationReportInMailService.checkSuccessRate();
	    }
}*/

@Controller
public class SchedulerForSendingFluctuationReportInMailController {
	@Autowired
	SchedulerForSendingFluctuationReportInMailService schedulerForSendingFluctuationReportInMailService;
	    @PostMapping("/fluctuationReport")
	    public void performScheduledTask() throws FileNotFoundException, IOException, MessagingException{
	    	schedulerForSendingFluctuationReportInMailService.checkSuccessRate();
	    }
}
