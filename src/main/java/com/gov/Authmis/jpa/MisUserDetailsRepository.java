package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gov.Authmis.entity.MisUserDetails;

public interface MisUserDetailsRepository extends JpaRepository<MisUserDetails, Long> {
    List<MisUserDetails> findBySsoId(String ssoId);
}
