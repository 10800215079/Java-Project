package com.gov.Authmis.service;


import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.OtpAuthenticationRequestforQueryBuilder;
import com.gov.Authmis.model.QueryBuilder;


@Service
public interface QueryBuilderService {

	public List<Map<String, Object>> getExecuteSelectQuery(String convertedToUpperCase);

	public void SaveQueryBuilderLogData(OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder, String ipAddress) throws UnknownHostException;

	public void SaveSelectQueryBuilderLogData(String uuid, String queryString, String ssoid, String ipAddress) throws UnknownHostException;

	public String queryInsertUpdateAlterRename(OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder) throws Exception;

	public List<Object> getLogReport();

	public int validateSSOWhiteList(String ipAddress, String ssoId);


}
