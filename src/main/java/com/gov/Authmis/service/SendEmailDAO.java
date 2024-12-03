package com.gov.Authmis.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.gov.Authmis.dao.SendEmailDAOImpl;


public interface SendEmailDAO {

	SendEmailDAOImpl daoImpl = new SendEmailDAOImpl();
	public static List<Map<String, Object>> getSubAuaData() throws SQLException
	{
		return daoImpl.getSubAuaData();
	}
}
