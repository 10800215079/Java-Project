package com.gov.Authmis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gov.Authmis.model.ErrorCodeModel;

@Service
public interface ErrorCodeService {

	/* HashMap<String, Object> errorCodeService1(ErrorCodeModel errorCodeModel); */

	public List<ErrorCodeModel> errorCodeService(ErrorCodeModel errorCodeModel,String ssoid1) throws Exception;

}
