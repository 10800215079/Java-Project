package com.gov.Authmis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;

import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.SubAuaWiseSendMailService;

public class SubAuaWiseSendMailServiceImpl implements SubAuaWiseSendMailService {

	
	// get subaua data
		public List<Map<String,Object>> getSubAuaData(String subAuaCode) throws SQLException 
		{
			List<Map<String,Object>> subAuaData = null;
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
					
					rs = stmt.executeQuery("Select A.SUBAUA_CODE, A.EMAIL, A.ORGNAME, B.EXPIRY_DATE  from AADHAAR.SUBAUA A join AADHAAR.SUBAUA_EKYC_LK B on A.subaua_code=B.SUB_AUA_CODE where A.SUBAUA_CODE='"+subAuaCode+"'  and status ='1'");
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
