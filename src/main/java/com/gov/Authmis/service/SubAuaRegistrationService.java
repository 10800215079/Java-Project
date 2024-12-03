package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.OtpAuthenticationRequest;
import com.gov.Authmis.entity.OtpAuthenticationRequestforQueryBuilder;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;
import com.gov.Authmis.model.UsersRoleMapping;

public interface SubAuaRegistrationService {

	List<OtpRespose> getOtp(String uuid);

	List<UsersRoleMapping> getSsoProfile(String ssoId);

	OtpRespose OtpAuthentication(OtpAuthenticationRequest otpAuthenticationRequest);

	boolean  isRegistered(OtpAuthenticationRequest otpAuthenticationRequest);

	AuthResponseEntity athentication(OtpAuthenticationRequest otpAuthenticationRequest);

	AuthResponseEntity athentication(OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder);

	AuthResponseEntity athenticationForWhiteListSSO(
			WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity);

	int updateSubaua(Long tid);

}
