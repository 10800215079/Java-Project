package com.gov.Authmis.service;

import java.util.HashMap;
import com.gov.Authmis.model.FluctuationReportModel;

public interface FluctuationReportService
{
	HashMap<String, Object> GetFlucationReportData(FluctuationReportModel flucationReportModel, String ssoid1);

}
