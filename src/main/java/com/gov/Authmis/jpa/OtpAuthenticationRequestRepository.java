package com.gov.Authmis.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gov.Authmis.entity.OtpAuthenticationRequest;

public interface OtpAuthenticationRequestRepository  extends JpaRepository<OtpAuthenticationRequest, Long>{

}
