package com.gov.Authmis.service;

import java.util.List;
import java.util.Optional;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.sendMailModel;

public interface IPwhiteListRequestService {

	public List<RequestForIpWhitelistEntity> getAllRequestedIPList();
	
	public Object updateRejectedRequest(Long id, String reason, String ssoid);

	//public Object addApproveRequest(Long id, String ssoid);

	Optional<RequestForIpWhitelistEntity> getRequestToApprove(Long id);

	Object updateApproveRequest(Long id, String ssoId);

	Object addWhitListIp(WhiteListSubAUAEntity entity);

	List<RequestForIpWhitelistEntity> findByStatusAndSubAUA(Long status, String subAUA);

	public List<RequestForIpWhitelistEntity> getRequestedIPListByStatus(Long status);

	List<RequestForIpWhitelistEntity> getRequestedIPListBySubAUACode(String subAUACode);

	Object updateEmailStatus(List<sendMailModel> items, String ssoId);
}
