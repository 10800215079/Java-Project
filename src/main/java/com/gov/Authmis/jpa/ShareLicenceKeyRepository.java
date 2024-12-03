package com.gov.Authmis.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gov.Authmis.entity.ShareLicenceKeyEntity;

public interface ShareLicenceKeyRepository extends  JpaRepository<ShareLicenceKeyEntity, Long> {

	@Query("SELECT s.srno, s.subAuaCode, s.subAuaName, s.expiryDate FROM ShareLicenceKeyEntity s WHERE s.subAuaCode IN :subAuaCodes AND s.status = 1")
	List<Object[]> findBySubAuaCode(@Param("subAuaCodes") List<String> subAuaCodes);

	Optional<ShareLicenceKeyEntity> findBySubAuaCodeAndStatus(String subAuaCode, Integer status);

}
