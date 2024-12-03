package com.gov.Authmis.test;

import java.util.List;

public interface TestForRestService {

	List<TestForRestDTO> getAllData();

	TestForRestDTO getDataById(Long id);

}
