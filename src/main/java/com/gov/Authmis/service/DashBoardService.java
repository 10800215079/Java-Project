package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;
import com.gov.Authmis.util.DepartmentWiseTransaction;
import com.gov.Authmis.util.DeviceFailTransaction;
import com.gov.Authmis.util.DeviceWisePercentage;
import com.gov.Authmis.util.ErrorCodeAvgResp;
import com.gov.Authmis.util.HoursWiseTrans;
import com.gov.Authmis.util.UniqueSuccessFailGraph;

public interface DashBoardService {
	/* HashMap<String, Object> GetResult(); */
	 HashMap<String, Object> GetResult(String ssoid,String role_id);
	  List<DepartmentWiseTransaction> GetDashBoardResult();
	  
	  List<DeviceFailTransaction> GetDeviceFailResult();
	  
	  List<DeviceWisePercentage> GetDeviceWisePercentResult();
	  
	  List<HoursWiseTrans> GetHoursWiseTransResult();
	  
	  List<UniqueSuccessFailGraph> GetUniqueSuccessFailGraphResult();
	  
	  List<ErrorCodeAvgResp> GetErrorCodeAvgRespResult();
	  
	  List<ErrorCodeAvgResp> GetAvgRespResult();
	  
		/* public void callStoredProcedure(); */
  public void callStoredProcedure(String ssoid,String role_id);
	  
	  List<String> getsubauadata(String ssoid, String role_id);
	}






