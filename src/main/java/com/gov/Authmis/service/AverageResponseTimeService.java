package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gov.Authmis.model.AverageResponseTimeModel;

public interface AverageResponseTimeService {

	List<AverageResponseTimeModel> GetAverageResponseTimeData(AverageResponseTimeModel averageResponseTimeModel, String ssoid1) throws Exception;

	List<Object> getaverageResponseTimeDetails(String rEQUEST_TYPE, String fROM_DATE, String eND_DATE);

}
