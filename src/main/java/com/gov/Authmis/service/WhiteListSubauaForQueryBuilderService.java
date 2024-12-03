package com.gov.Authmis.service;


import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;


@Service
public interface WhiteListSubauaForQueryBuilderService {

	HashMap<String, Object> getAllWhiteListSSO();

	Object save(WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity, String ssoid);
	
	void delete(Long sRNO);

	/* void updateStatus(Long sRNO, Integer status); */


	void updateStatus(Long srno);

	boolean isIPDuplicate(String ipAddress);

}
