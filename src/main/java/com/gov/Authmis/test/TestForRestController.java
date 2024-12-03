package com.gov.Authmis.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestForRestController {
	
	@Autowired
	private TestForRestService testForRestService;
	
	@GetMapping("/getTestData")
	public ResponseEntity<List<TestForRestDTO>> getAllData(){
		List<TestForRestDTO> allData = testForRestService.getAllData();		
		return ResponseEntity.ok(allData);
	}
	
	@GetMapping("/testForId/{id}")
	public ResponseEntity<TestForRestDTO> getDataById(@PathVariable Long id){
		TestForRestDTO testForRestDTO =  testForRestService.getDataById(id);		
		return ResponseEntity.ok(testForRestDTO);
	}

}
