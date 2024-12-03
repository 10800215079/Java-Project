package com.gov.Authmis.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.gov.Authmis.model.SendEmailToSubAuaModel;
//import com.gov.Authmis.webservices.restfulwebservices.uploaddownloadfile.DatabaseFile;
import com.itextpdf.text.DocumentException;


public interface SendMailToSubAuaService {

	List<SendEmailToSubAuaModel> findAll() throws SQLException;

	public List<Map<String, Object>> GetSubauaCodeName();

	public List<Map<String, Object>> GetServiceType();
	

	//Object save(SendEmailToSubAuaModel sendEmailToSubAuaModel) throws UnknownHostException, IOException;

	/* List<String> getSubauaCode(String subauacode); */

	String[] getSubauaCode1(List<String> subauacode);

	public List<Map<String, Object>> GetEmail();
	
	//FILE UPLOAD
	List<SendEmailToSubAuaModel> storeFile(MultipartFile files) throws SerialException, SQLException;

	//Object save(SendEmailToSubAuaModel sendEmailToSubAuaModel, ArrayList arrayList);

	//Object save(SendEmailToSubAuaModel sendEmailToSubAuaModel, ArrayList arrayList) throws IOException, SerialException, SQLException, DocumentException;

	Object save(SendEmailToSubAuaModel sendEmailToSubAuaModel, List<SendEmailToSubAuaModel> fileMap) throws IOException, SerialException, SQLException, DocumentException;
	

}
