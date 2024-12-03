package com.gov.Authmis.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;

public interface ViewshareLicenceKeyRepository extends  JpaRepository<ViewShareLicenceKeyDetails, Long>  {

	Optional<ViewShareLicenceKeyDetails> findBySubAuaCode(String subauacode);



	Optional<ViewShareLicenceKeyDetails> findBySubAuaCodeAndActive(String subauacode, int i);

}
