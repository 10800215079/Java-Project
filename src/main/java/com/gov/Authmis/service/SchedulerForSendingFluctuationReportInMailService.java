package com.gov.Authmis.service;


import javax.mail.MessagingException;

public interface SchedulerForSendingFluctuationReportInMailService {

	void checkSuccessRate() throws MessagingException ;

}
