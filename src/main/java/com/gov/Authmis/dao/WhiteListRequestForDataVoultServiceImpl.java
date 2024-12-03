package com.gov.Authmis.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gov.Authmis.controller.WhiteListForDataVaultController;
import com.gov.Authmis.model.WhitelistForDataVaultModel;
import com.gov.Authmis.service.WhiteListRequestForDataVoultService;

@Service
public class WhiteListRequestForDataVoultServiceImpl implements WhiteListRequestForDataVoultService{

	static Logger logger = LoggerFactory.getLogger(WhiteListForDataVaultController.class);
	
	@Transactional
	@Override
	public ResponseEntity<String> saveWhiteListRequest(List<WhitelistForDataVaultModel> dataList, HttpServletRequest request) {
		
		logger.info("+++++++++++++++++++++  Entered in saveWhiteListRequest ");
		
	    String url = "jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR";
	    String user = "AADHAARDATAVAULT";
	    String password = "aadhaardatavault#123";

	    // Assuming you have a table structure for this data
	    String sql = "INSERT INTO WHITELIST_DATA_VAULT  (SUB_AUA_CODE, ip_addresses, SERVICE_TYPE, APP_CODE, CREATED_DATE, CREATED_BY, STATUS, SUB_AUA_NAME, APP_NAME) VALUES (?, ?, ?, ?,?,?,?,?,?)";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
		String ssoid = (String) request.getSession().getAttribute("SSOID");

	    try {
	        connection = DriverManager.getConnection(url, user, password);
	        connection.setAutoCommit(false);
	        preparedStatement = connection.prepareStatement(sql);

	        for (WhitelistForDataVaultModel item : dataList) {
	            // Set parameters
	            preparedStatement.setString(1, item.getSubAuaCode());
	            preparedStatement.setString(2, String.join(", ", item.getIpAddresses())); // Join IP addresses with a comma
	            preparedStatement.setString(3, item.getServiceType());
	            preparedStatement.setString(4, item.getAppCode());
	            preparedStatement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
	            preparedStatement.setString(6, ssoid);
	            preparedStatement.setLong(7, 1);
	            preparedStatement.setString(8, item.getSubAuaName());
	            preparedStatement.setString(9, item.getAppName());
	            preparedStatement.addBatch();
	        }

	        // Execute batch
	        preparedStatement.executeBatch();
	        
	        // Commit transaction
	        connection.commit();

	        logger.info("+++++++++++++++++++++  Data successfully submitted ");
	        return ResponseEntity.ok("Data successfully submitted");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                // Rollback transaction in case of error
	                connection.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting data into the database");

	    } finally {
	        // Close resources
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	@Override
	public List<WhitelistForDataVaultModel> getAllWhiteListRecords() {
	    logger.info("+++++++++++++++++++++  Entered in getAllWhiteListRecords");

	    String url = "jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR";
	    String user = "AADHAARDATAVAULT";
	    String password = "aadhaardatavault#123";

	    String sql = "SELECT SUB_AUA_CODE, ip_addresses, SERVICE_TYPE, APP_CODE,CREATED_DATE, CREATED_BY, STATUS, ID, SUB_AUA_NAME, APP_NAME FROM WHITELIST_DATA_VAULT";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    List<WhitelistForDataVaultModel> dataList = new ArrayList<>();

	    try {
	        connection = DriverManager.getConnection(url, user, password);
	        preparedStatement = connection.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            WhitelistForDataVaultModel model = new WhitelistForDataVaultModel();
	            model.setSubAuaCode(resultSet.getString("SUB_AUA_CODE"));
	            model.setIpAddresses(Arrays.asList(resultSet.getString("ip_addresses").split(",\\s*")));
	            model.setServiceType(resultSet.getString("SERVICE_TYPE"));
	            model.setAppCode(resultSet.getString("APP_CODE"));
	            model.setCreatedDate(resultSet.getDate("CREATED_DATE"));
	            model.setCreatedBy(resultSet.getString("CREATED_BY"));
	            model.setStatus(resultSet.getLong("STATUS"));
	            model.setId(resultSet.getLong("ID"));
	            model.setSubAuaName(resultSet.getString("SUB_AUA_NAME"));
	            model.setAppName(resultSet.getString("APP_NAME"));
	            dataList.add(model);
	        }

	        logger.info("+++++++++++++++++++++  Data successfully retrieved");
	        return dataList;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;

	    } finally {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	@Override
	public WhitelistForDataVaultModel getWhiteListRecordById(Long id) {
	    logger.info("+++++++++++++++++++++ Entered in getWhiteListRecordById with ID: " + id);

	    String url = "jdbc:oracle:thin:@exa03-scan.rajasthan.gov.in:1521/AADHAAR";
	    String user = "AADHAARDATAVAULT";
	    String password = "aadhaardatavault#123";

	    String sql = "SELECT SUB_AUA_CODE, ip_addresses, SERVICE_TYPE, APP_CODE, CREATED_DATE, CREATED_BY, STATUS, ID, SUB_AUA_NAME FROM WHITELIST_DATA_VAULT WHERE ID = ?";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    WhitelistForDataVaultModel model = null;

	    try {
	        connection = DriverManager.getConnection(url, user, password);
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setLong(1, id); // Set the ID parameter
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            model = new WhitelistForDataVaultModel();
	            model.setSubAuaCode(resultSet.getString("SUB_AUA_CODE"));
	            model.setIpAddresses(Arrays.asList(resultSet.getString("ip_addresses").split(",\\s*")));
	            model.setServiceType(resultSet.getString("SERVICE_TYPE"));
	            model.setAppCode(resultSet.getString("APP_CODE"));
	            model.setCreatedDate(resultSet.getDate("CREATED_DATE"));
	            model.setCreatedBy(resultSet.getString("CREATED_BY"));
	            model.setStatus(resultSet.getLong("STATUS"));
	            model.setId(resultSet.getLong("ID"));
	            model.setSubAuaName(resultSet.getString("SUB_AUA_NAME"));
	            logger.info("+++++++++++++++++++++ Data successfully retrieved for ID: " + id);
	        } else {
	            logger.info("+++++++++++++++++++++ No record found for ID: " + id);
	        }

	    } catch (SQLException e) {
	        logger.error("Error while retrieving data for ID: " + id, e);

	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return model;
	}


}
