package com.gov.Authmis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SendEmailToSubAuaModel;
import com.gov.Authmis.service.SubAuaWiseSendMailService;

@Service
public class SendMailExceptionhandleServiceImpl {

	// update status on db
	public String updateStatusForFailedMail(String serialno) throws SQLException {
		String subAuaData = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR",
				//	"aadhaar123#$"); // preprod exa03
			 con
			 =DriverManager.getConnection("jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR","AADHAAR123#");
			// // prod exa05

			if (con != null) {

				String query = "UPDATE AADHAAR.SEND_MAILS_To_SUBAUA_LOGS_DATA SET STATUS =0 WHERE SRNO ='" + serialno
						+ "'";
				stmt = con.prepareStatement(query);
				int updatedRows = stmt.executeUpdate();
				System.out.println(updatedRows + " rows updated");
			}
		} catch (Exception e) {
			if (stmt != null) {
				stmt.close();
			}
			System.out.println("Error while calling SUBAUA table from createConnection()" + e);
		} finally {
			stmt.close();
			con.close();
		}
		return subAuaData;
	}

	
	public List<Map<String, byte[]>> getAllFileDataAccordingToSrno(Object srno) throws SQLException {
	    List<Map<String, byte[]>> subAuaData = new ArrayList<>();
	    Connection con = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        Class.forName("oracle.jdbc.OracleDriver");
	       //con = DriverManager.getConnection("jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR", "aadhaar123#$"); // preprod exa03
	        con = DriverManager.getConnection("jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR", "AADHAAR123#");  // prod exa05

	        if (con != null) {
	            stmt = con.createStatement();
	            rs = stmt.executeQuery("SELECT FILENAME, FILE_DATA FROM AADHAAR.EMAIL_FILE_DATA WHERE srno='" + srno + "'");
	            
	            while (rs.next()) {
	                String fileName = rs.getString("FILENAME");
	                byte[] fileData = rs.getBytes("FILE_DATA");
	                
	                Map<String, byte[]> fileMap = new HashMap<>();
	                fileMap.put(fileName, fileData);
	                
	                subAuaData.add(fileMap);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    }
	    return subAuaData;
	}

	/*
	 * public List<Map<String, byte[]>> getAllFileDataAccordingToSrno(Object srno)
	 * throws SQLException {
	 * 
	 * List<Map<String, byte[]>> subAuaData = null; Connection con = null; Statement
	 * stmt = null; ResultSet rs = null;
	 * 
	 * try { Class.forName("oracle.jdbc.OracleDriver"); con =
	 * DriverManager.getConnection(
	 * "jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR", "AADHAAR",
	 * "aadhaar123#$"); // preprod exa03 //con = DriverManager.getConnection(
	 * "jdbc:oracle:thin:@exa05-scan5.sdc.raj.gov.in:1521/AADHAARPROD","AADHAAR",
	 * "AADHAAR123#"); // prod exa05
	 * 
	 * if (con != null) { stmt = con.createStatement();
	 * 
	 * //rs = stmt.
	 * executeQuery("Select A.SUBAUA_CODE, A.EMAIL, A.ORGNAME, B.EXPIRY_DATE  from AADHAAR.SUBAUA A join AADHAAR.SUBAUA_EKYC_LK B on A.subaua_code=B.SUB_AUA_CODE where A.SUBAUA_CODE='"
	 * +subAuaCode+"'  and status ='1'"); rs = stmt.
	 * executeQuery("select FILENAME , FILE_DATA from AADHAAR.EMAIL_FILE_DATA where srno='"
	 * +srno+"' "); subAuaData = ResultSetToListConverter.getListFromResultSet1(rs);
	 * while(rs.next()) { SendEmailToSubAuaModel sendEmailToSubAuaModel =new
	 * SendEmailToSubAuaModel(); sendEmailToSubAuaModel.getFileName(rs.getArray(1))
	 * }
	 * 
	 * 
	 * } } catch (Exception e) { if(rs!= null) { rs.close(); }
	 * System.out.println("Error while calling SUBAUA table from createConnection()"
	 * +e); } finally { stmt.close(); con.close(); } return subAuaData; }
	 */

}
