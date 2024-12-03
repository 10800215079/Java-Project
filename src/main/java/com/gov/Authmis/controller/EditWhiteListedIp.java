package com.gov.Authmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.service.WhiteListRepository;


@RestController
@RequestMapping("/editWhiteListedIp")
public class EditWhiteListedIp {

	@Autowired
	private WhiteListRepository whiteListRepository;
	
	@GetMapping("/{id}")
	 public WhiteListSubAUAEntity getDetailsById(@PathVariable Long id) {

		WhiteListSubAUAEntity o = whiteListRepository.findById(id);
		  return o;
	 }
	
	@GetMapping("/downloadPublishedDoc/{id}")
	 public WhiteListSubAUAEntity downloadPublishedDoc(@PathVariable Long id) {

		WhiteListSubAUAEntity data = whiteListRepository.findById(id);
		  return data;
	 }
}



