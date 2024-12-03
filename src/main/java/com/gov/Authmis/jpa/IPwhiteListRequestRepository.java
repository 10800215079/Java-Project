package com.gov.Authmis.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;

public interface IPwhiteListRequestRepository extends JpaRepository<RequestForIpWhitelistEntity, Long>{
	Optional<RequestForIpWhitelistEntity> findById(Long id);

	List<RequestForIpWhitelistEntity> findByStatusAndSubAUACode(Long status, String subAUACode);

	List<RequestForIpWhitelistEntity> findByStatus(Long status);

	List<RequestForIpWhitelistEntity> findBysubAUACode(String subAUACode);

}
