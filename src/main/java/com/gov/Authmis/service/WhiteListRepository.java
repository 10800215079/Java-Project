package com.gov.Authmis.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.model.WhiteListSubAUAIPAddress;

@Repository
public interface WhiteListRepository extends JpaRepository<WhiteListSubAUAEntity, Integer> {

	WhiteListSubAUAEntity findById(Long id);

	boolean existsBySubAuaCodeAndIpAddress(String subAuaCode, String iPAddress);

	List<WhiteListSubAUAEntity> findByStatusAndSubAuaCode(Long status, String department);

	List<WhiteListSubAUAEntity> findBySubAuaCode(String department);

	List<WhiteListSubAUAEntity> findByStatus(Long status);

	

}
