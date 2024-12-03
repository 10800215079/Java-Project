package com.gov.Authmis.test;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestForRestServiceImpl<TestTableDTO> implements TestForRestService{
	@Autowired
	private TestForRestRepository testForRestRepository;
	
	@Override
	public List<TestForRestDTO> getAllData() {
		
		 return testForRestRepository.findAll().stream().map(e -> {
			TestForRestDTO dto = new TestForRestDTO();
				dto.setId(e.getId());
				dto.setSubauaCode(e.getSubauaCode());
				dto.setSubauaName(e.getSubauaName());
				dto.setAppName(e.getAppName());
				dto.setReportType(e.getReportType());
				dto.setSelectYear(e.getStatus());
				dto.setCreatedDate(e.getCreatedDate());
				dto.setStatus(e.getStatus());
				return dto;
		}).toList();
		
	}

	@Override
	public TestForRestDTO getDataById(Long id) {
		  return testForRestRepository.findById(id).map(e -> {
			  TestForRestDTO dto = new TestForRestDTO();
			  dto.setId(e.getId());
				dto.setSubauaCode(e.getSubauaCode());
				dto.setSubauaName(e.getSubauaName());
				dto.setAppName(e.getAppName());
				dto.setReportType(e.getReportType());
				dto.setSelectYear(e.getStatus());
				dto.setCreatedDate(e.getCreatedDate());
				dto.setStatus(e.getStatus());
				return dto;
		  }).orElseThrow(() -> new RuntimeException("Data not found for ID : " + id));
		
	}

}
