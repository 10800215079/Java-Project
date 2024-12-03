package com.gov.Authmis.service;

import java.net.UnknownHostException;
import java.text.ParseException;

public interface SchedulerForSendingRemainderForAuditReportService {

	void createConnection() throws UnknownHostException, ParseException;

}
