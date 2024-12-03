package com.gov.Authmis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.SendEmailDAO;

public class SendEmailDAOImpl implements SendEmailDAO 
{		
	// get subaua data
	public List<Map<String, Object>> getSubAuaData() throws SQLException 
	{
		List<Map<String, Object>> subAuaData = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{		
			Class.forName("oracle.jdbc.OracleDriver");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR", "aadhaar123#$"); // preprod exa03
			con = DriverManager.getConnection("jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR", "AADHAAR123#");  // prod exa05
			
			if (con != null) 
			{
				stmt = con.createStatement();
				//rs = stmt.executeQuery("SELECT SUBAUA_CODE,ORGNAME,EMAIL FROM AADHAAR.SUBAUA WHERE ACTIVE=1");
				rs = stmt.executeQuery("select sub_aua_code, sub_aua_name, POCEMAIL_T from aadhaar.subaua_ekyc_lk where status=1 and SUB_AUA_CODE is not null and POCEMAIL_T is not null order by SUB_AUA_NAME");
				subAuaData = ResultSetToListConverter.getListFromResultSet(rs);				
			}			
		}
		catch (Exception e)
		{
			if(rs!= null)
			{
				rs.close();
			}
			System.out.println("Error while calling SUBAUA table from createConnection()"+e);
		}
		finally 
		{
			stmt.close();
			con.close();
		}
		return subAuaData;
	}
	
	public List<Map<String, Object>> getSingleSubAuaData(String subauacode) throws SQLException 
	{
		List<Map<String, Object>> subAuaData = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{		
			Class.forName("oracle.jdbc.OracleDriver");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR", "aadhaar123#$"); // preprod exa03
			con = DriverManager.getConnection("jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR", "AADHAAR123#");  // prod exa05
			
			if (con != null) 
			{
				stmt = con.createStatement();
				//rs = stmt.executeQuery("SELECT SUBAUA_CODE,ORGNAME,EMAIL FROM AADHAAR.SUBAUA WHERE ACTIVE=1");
				rs = stmt.executeQuery("select sub_aua_code, sub_aua_name, POCEMAIL_T from aadhaar.subaua_ekyc_lk where  status=1 and  "
						+ ""
						+ " SUB_AUA_CODE =" +"'"+subauacode+"' order by SUB_AUA_NAME") ;
				subAuaData = ResultSetToListConverter.getListFromResultSet(rs);				
			}			
		}
		catch (Exception e)
		{
			if(rs!= null)
			{
				rs.close();
			}
			System.out.println("Error while calling SUBAUA table from createConnection()"+e);
		}
		finally 
		{
			stmt.close();
			con.close();
		}
		return subAuaData;
	}
}
