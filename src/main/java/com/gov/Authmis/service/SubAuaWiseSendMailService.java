package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;

import com.gov.Authmis.dao.SubAuaWiseSendMailServiceImpl;

public interface SubAuaWiseSendMailService {

	/*
	 * void sendMessageWithAttachment( String to, String subject, String text,
	 * String pathToAttachment, FileSystemResource pdfFileToSend);
	 */
	SubAuaWiseSendMailServiceImpl daoEmailImpl = new SubAuaWiseSendMailServiceImpl();
	
	public static List<Map<String, Object>> getSubAuaData() throws SQLException
	{
		return null;
		//return daoEmailImpl.getSubAuaData();
	}
}
