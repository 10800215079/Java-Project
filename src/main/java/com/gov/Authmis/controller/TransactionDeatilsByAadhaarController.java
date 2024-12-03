package com.gov.Authmis.controller;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.dao.TransactionDeatilsByAadhaarServiceImpl;
import com.gov.Authmis.model.DSMTokanize;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.TransactionDeatilsByAadhaarModel;
import com.gov.Authmis.util.GenerateReferenceNo;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class TransactionDeatilsByAadhaarController {

	@Autowired
	TransactionDeatilsByAadhaarServiceImpl transactionDeatilsByAadhaarServiceImpl;
	SSOLoginController ssoLoginController = new SSOLoginController();
	static Logger logger = LoggerFactory.getLogger(TransactionDeatilsByAadhaarController.class);
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping({ "/TransactionDeatilsByAadhaar" })
	public String TransactionDeatilsByAadhaar(HttpServletRequest request, Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		return "TransactionDeatilsByAadhaar";
	}

	@GetMapping({ "/DataForTransactionDeatilsByAadhaar" })
	public String DataForTransactionDeatilsByAadhaar(Model model, HttpSession session,
			TransactionDeatilsByAadhaarModel transactionDeatilsByAadhaarModel, HttpServletRequest request)
			throws IOException, JAXBException, SQLException, KeyManagementException, NoSuchAlgorithmException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		String refeno = GenerateReferenceNo.getReferenceNumber(transactionDeatilsByAadhaarModel.getAadhaarid());
		System.out.println("Controller transactionDeatilsByAadhaar ========>>>>>> :" + refeno);
		transactionDeatilsByAadhaarModel.setAadhaarid(refeno);

		HashMap<String, Object> result = new HashMap<>();
		result = this.transactionDeatilsByAadhaarServiceImpl.getDataByAadhaarID(transactionDeatilsByAadhaarModel);
		logger.info("TransactionDeatilsByAadhaarController=================>" + result.get("details"));

		model.addAttribute("details", result.get("details"));

		return "TransactionDeatilsByAadhaar";
	}

}