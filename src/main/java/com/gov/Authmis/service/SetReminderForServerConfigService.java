package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;

import com.gov.Authmis.model.SetReminderForServerConfigModel;

public interface SetReminderForServerConfigService {

	List<String> GetProjectName();

	Object save(SetReminderForServerConfigModel setRemainderForServerConfigModel);

	HashMap<String, Object> getAllRemainders();

	void deactivateRemainder(Long sRNO, Long status);

	void deactivateRemainderWithReason(Long srno, String reason, String ssoid);

}
