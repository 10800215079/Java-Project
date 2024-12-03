package com.gov.Authmis.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.gov.Authmis.entity.OtpAuthenticationRequestforQueryBuilder;
import com.gov.Authmis.model.SQLGrammarException;
import com.gov.Authmis.service.QueryBuilderService;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {
	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	

	// New Code To Execute Query
	@Override
	@Transactional
	public String queryInsertUpdateAlterRename(
			OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder)
			throws Exception {
		String qury = otpAuthenticationRequestforQueryBuilder.getQuerys();
		String input = qury.trim();
		String qry = input.replace("::", "\\:\\:");
		String output = null;
		String errorMessage = null;
		try {
			Query query = entityManager.createNativeQuery(qry);
			int count = query.executeUpdate();
			System.out.println("count=========>>>>>>>>>>>" + count);
			if (count != 0) {
				String queryforchangestatus = "UPDATE AADHAAR.QUERY_BUILDER_LOG_DETAIL SET STATUS=1 WHERE TRANSACTION_ID='"
						+ otpAuthenticationRequestforQueryBuilder.getTxn() + "' ";
				entityManager.createNativeQuery(queryforchangestatus).executeUpdate();
			}
		} catch (PersistenceException pe) {
			errorMessage = "Persistence Exception: " + pe.getMessage();
			return errorMessage;
		}catch (SQLGrammarException sq) {
			errorMessage = "SQLGrammarException: " + sq.getMessage();
			return errorMessage;
		}catch (UnexpectedRollbackException pe) {
			errorMessage = "UnexpectedRollbackException: " + pe.getMessage();
			return errorMessage;
		} catch (Exception e) {
			errorMessage = "General Exception: " + e.getMessage();
			e.printStackTrace();
		}

		return errorMessage;
	}

	// Save Log Data
	@Transactional
	public void SaveQueryBuilderLogData(OtpAuthenticationRequestforQueryBuilder otpAuthenticationRequestforQueryBuilder,String ipAddress)
			throws UnknownHostException {
	
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
		String executinDate = formatter.format(date);

		String query = "INSERT INTO AADHAAR.QUERY_BUILDER_LOG_DETAIL( SSO_ID, AADHHAR_ID, IP,EXECUTED_QUERY, EXECUTION_DATE, STATUS, TRANSACTION_ID)"
				+ " VALUES ( :a, :b, :c, :d, :e, :f, :g)";

		Object o = entityManager.createNativeQuery(query)
				.setParameter("a", otpAuthenticationRequestforQueryBuilder.getSso())
				.setParameter("b", otpAuthenticationRequestforQueryBuilder.getUuid()).setParameter("c", ipAddress)
				.setParameter("d", otpAuthenticationRequestforQueryBuilder.getQuerys()).setParameter("e", executinDate)
				.setParameter("f", 0).setParameter("g", otpAuthenticationRequestforQueryBuilder.getTxn())
				.executeUpdate();
		System.out.println("successfully save data !!!!!! ");

	}

	@Transactional
	public void SaveSelectQueryBuilderLogData(String uuid, String queryString, String ssoid, String ipAddress)
			throws UnknownHostException {
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
		String executinDate = formatter.format(date);

		String query = "INSERT INTO AADHAAR.QUERY_BUILDER_LOG_DETAIL( SSO_ID, AADHHAR_ID, IP,EXECUTED_QUERY, EXECUTION_DATE, STATUS, TRANSACTION_ID)"
				+ " VALUES ( :a, :b, :c, :d, :e, :f, :g)";

		Object o = entityManager.createNativeQuery(query).setParameter("a", ssoid).setParameter("b", null)
				.setParameter("c", ipAddress).setParameter("d", queryString).setParameter("e", executinDate)
				.setParameter("f", 0).setParameter("g", null).executeUpdate();
		System.out.println("successfully save data !!!!!! ");

	}

	// For Select Query
	@Transactional
	public List<Map<String, Object>> getExecuteSelectQuery(String executequery) {
		System.out.println("Query=======>>>>>> :" + executequery);
		List<Map<String, Object>> resultList = null;
		String blobPlaceholder = "BLOB";
		try {
			Query query = entityManager.createNativeQuery(executequery);
			// Execute the query to get the result list
			//List<Object[]> result = query.getResultList();

			Object result = query.getResultList();
			System.out.println("result------->>>>>>" + result);

			// Get column names from the database table mentioned in the SQL query
			String tableName = extractTableNameFromQuery(executequery);
			List<String> columnNames;
			
			//condition for select query to get column name 
			 if (executequery.toLowerCase().contains("*")) {
		            
		            columnNames = getColumnNamesFromDatabaseTable(tableName);
		        } else {
		        	columnNames = extractSelectedColumnNamesFromQuery(executequery);
		        }
			 

			 if (result instanceof List && !((List<?>) result).isEmpty() && ((List<?>) result).get(0) instanceof Object[]) {
				 	List<Object[]> resultOf = (List<Object[]>) result;
				 	// Process the result as List<Object[]>
				 	System.out.println("Result is List<Object[]>");
				 	System.out.println("columnNames ======>> : " + columnNames);
					
					
					resultList = resultOf.stream().map(row -> {
					    Map<String, Object> rowMap = new HashMap<>();
					    for (int i = 0; i < columnNames.size(); i++) {
					        String columnName = columnNames.get(i);
					        Object columnValue = row[i];

					        // Check if the column value is a Blob and replace it with the placeholder
					        if (columnValue instanceof java.sql.Blob) {
					            rowMap.put(columnName, blobPlaceholder);
					        } else {
					            // For non-Blob values, simply add them to the rowMap
					            rowMap.put(columnName, columnValue);
					        }
					    }
					    return rowMap;
					}).collect(Collectors.toList());
					

				 	// Your further processing logic here...
			 } else {
				 	System.out.println("Result is not List<Object[]>");
				 	 List<Object> objectListSingle = (List<Object>) (List<?>) result;// Replace this with your actual list

				 	resultList = objectListSingle.stream().map(value -> {
				 	    Map<String, Object> rowMap = new HashMap<>();
				 	    for (int i = 0; i < columnNames.size(); i++) {
				 	        String columnName = columnNames.get(i);

				 	        // Assuming each value in resultOf corresponds to a column
				 	        Object columnValue = value;

				 	        // Check if the column value is a Blob and replace it with the placeholder
				 	        if (columnValue instanceof java.sql.Blob) {
				 	            rowMap.put(columnName, blobPlaceholder);
				 	        } else {
				 	            // For non-Blob values, simply add them to the rowMap
				 	            rowMap.put(columnName, columnValue);
				 	        }
				 	    }
				 	    return rowMap;
				 	}).collect(Collectors.toList());
			 }
			
			 /**
			System.out.println("columnNames ======>> : " + columnNames);
			String blobPlaceholder = "BLOB";
			
			List<Map<String, Object>> resultList = result.stream().map(row -> {
			    Map<String, Object> rowMap = new HashMap<>();
			    for (int i = 0; i < columnNames.size(); i++) {
			        String columnName = columnNames.get(i);
			        Object columnValue = row[i];

			        // Check if the column value is a Blob and replace it with the placeholder
			        if (columnValue instanceof java.sql.Blob) {
			            rowMap.put(columnName, blobPlaceholder);
			        } else {
			            // For non-Blob values, simply add them to the rowMap
			            rowMap.put(columnName, columnValue);
			        }
			    }
			    return rowMap;
			}).collect(Collectors.toList());
			
			**/
			
			// update status
			updateStatus();

			return resultList;
		} catch (SQLGrammarException e) {
			throw new SQLGrammarException("Error executing SQL query: " + e.getMessage());
		}

	}

	private String extractTableNameFromQuery(String sqlQuery) {
		String regex = "(?i)\\bFROM\\s+([a-zA-Z0-9_]+)\\b";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sqlQuery);

		if (matcher.find()) {
			return matcher.group(1);
		} else {
			// Table name not found in the query
			return null;
		}
	}

	private List<String> getColumnNamesFromDatabaseTable(String tableName) {
		List<String> columnNames = new ArrayList<>();
		String sql = "SELECT column_name FROM user_tab_cols WHERE table_name = UPPER(:tableName) AND hidden_column = 'NO' ORDER BY column_id";
		Query columnQuery = entityManager.createNativeQuery(sql);
		columnQuery.setParameter("tableName", tableName);

		List<String> result = columnQuery.getResultList();
		for (Object row : result) {
			columnNames.add((String) row);
		}

		return columnNames;
	}
	
	
	//New  Code 
	// Add this method to handle the case where specific columns are selected
	private List<String> extractSelectedColumnNamesFromQuery(String sqlQuery) {
	    String regex = "(?i)\\bSELECT\\s+(.+)\\b\\bFROM\\s+[a-zA-Z0-9_]+\\b";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(sqlQuery);

	    List<String> columnNames = new ArrayList<>();

	    if (matcher.find()) {
	        String columnsPart = matcher.group(1);
	        String[] columns = columnsPart.split(",");
	        for (String column : columns) {
	            columnNames.add(column.trim());
	        }
	    }

	    return columnNames;
	}
	
	@Override
	public List<Object> getLogReport() {
		try {
			String sql = "";

			//sql = "SELECT sso_id, aadhhar_id, ip, executed_query, execution_date,CASE WHEN status = 1 THEN 'Y' ELSE 'N' END AS status_description,transaction_id FROM aadhaar.QUERY_BUILDER_LOG_DETAIL ORDER BY execution_date DESC";
			sql ="SELECT sso_id,aadhhar_id,ip,executed_query,TO_CHAR(execution_date, 'DD-MON-RR') AS formatted_execution_date,CASE WHEN status = 1 THEN 'Y' ELSE 'N' END AS status_description, "
					+ "  transaction_id FROM aadhaar.QUERY_BUILDER_LOG_DETAIL ORDER BY execution_date DESC " ;
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> obj = query.getResultList();
			return obj;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	//Update status
	public void updateStatus() {
		String sql = "SELECT SRNO FROM AADHAAR.QUERY_BUILDER_LOG_DETAIL ORDER BY SRNO DESC FETCH FIRST 1 ROWS ONLY";
		Query q = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unused")
		Object srno = q.getSingleResult();
		String queryforchangestatus = "UPDATE AADHAAR.QUERY_BUILDER_LOG_DETAIL SET STATUS=1 WHERE SRNO='" + srno + "' ";
		entityManager.createNativeQuery(queryforchangestatus).executeUpdate();
	}



	//Validate or check whitelist sso
    @Override
    public int validateSSOWhiteList(String ipAddress,String ssoId) {
            
        //String sql = "SELECT IP FROM AADHAAR.SSO_IP_WHITELIST WHERE UPPER(SSO_ID) = :ssoId and IP = :ipAddress";
    	String sql = "SELECT COUNT(1) FROM AADHAAR.SSO_IP_WHITELIST WHERE UPPER(SSO_ID) = :ssoId AND IP = :ipAddress AND STATUS = 1";
		Query query = this.entityManager.createNativeQuery(sql);
		query.setParameter("ipAddress", ipAddress);
		query.setParameter("ssoId", ssoId.toUpperCase());

		@SuppressWarnings("unchecked")
		List<Object> result = query.getResultList();

		if (((Number) result.get(0)).intValue() == 1) {
			return 1;
		} else {
			return 0;
		}
            
    }

	
}