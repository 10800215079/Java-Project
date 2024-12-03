package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;

public interface AddLicencyForSubAuaSystemRepo extends JpaRepository<AddLicencyForSubAuaSystem, Long> {
	List<AddLicencyForSubAuaSystem> findByStatus(String status);
}
