package com.gov.Authmis.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;



@Service
public interface ShareLicenceKeyService {

	List<Map<String, Object>> getLicenceExpiryDates(List<Map<String, Object>> subAuaList);

	Optional<ViewShareLicenceKeyDetails> getSubauaDetails(String subauacode);

	ResponseEntity<String> shareLicense(ViewShareLicenceKeyDetails data);


}
