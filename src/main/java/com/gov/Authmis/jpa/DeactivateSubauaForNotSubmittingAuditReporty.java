package com.gov.Authmis.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;


public interface DeactivateSubauaForNotSubmittingAuditReporty extends JpaRepository<ViewShareLicenceKeyDetails, Long> {
	
	@Modifying
    @Query("UPDATE OtpAuthenticationRequest u SET u.active = :active WHERE u.subAuaCode = :subAuaCode")
	void updateStatus(@Param("subAuaCode") String subAuaCode, @Param("active") Long active);
}
