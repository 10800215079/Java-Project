package com.gov.Authmis.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.SubauaLicencekeyUpdation;
import com.gov.Authmis.model.SubauaLicencekeyUpdationModel;
@Repository
public interface SubauaLicencekeyUpdationModelRepository extends JpaRepository<SubauaLicencekeyUpdation, String> {
	@Query(value = "SELECT * FROM aadhaar.subaua WHERE tid = :tid", nativeQuery = true)
    SubauaLicencekeyUpdation findByTid(@Param("tid") String tid);

}
