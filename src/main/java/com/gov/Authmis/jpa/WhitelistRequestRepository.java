

package com.gov.Authmis.jpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;

@Repository
public interface WhitelistRequestRepository extends CrudRepository<RequestForIpWhitelistEntity, Long> {

	List<RequestForIpWhitelistEntity> findBySubAUACodeIn(List<String> subAuaCode);

}