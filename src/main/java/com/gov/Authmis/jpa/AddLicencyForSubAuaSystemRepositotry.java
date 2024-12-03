package com.gov.Authmis.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.AddLicencyForSubAuaSystem;
@Repository
public interface AddLicencyForSubAuaSystemRepositotry  extends JpaRepository<AddLicencyForSubAuaSystem, Long> {

	@Query(value="select  * from subaua",nativeQuery = true)
	List<Map<String, Object>> findSubAuaAll();
	@Query(value="select * from subaua where tid=?1",nativeQuery = true)
	List<Map<String, Object>> findBySubAuaId(Long tid);
	
	
    
}
