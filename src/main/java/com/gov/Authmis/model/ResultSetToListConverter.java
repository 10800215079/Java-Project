package com.gov.Authmis.model;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gov.Authmis.entity.NonLiveUnblockEntity;


public class ResultSetToListConverter {
	public static List<Map<String, Object>> getListFromResultSet(ResultSet rs) {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		if(rs!=null) {
			try {
				ResultSetMetaData md = rs.getMetaData();
				int noOfColumns = md.getColumnCount();
				while(rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for(int i=1; i<= noOfColumns; i++) {
						row.put(md.getColumnName(i), rs.getObject(i));
					}
					rows.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return rows;
	}
	
	public static List<Map<String, Object>> getNonLiveunblockFromResultSet(ResultSet rs,List<NonLiveUnblockEntity> finalList) {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		
		 //finalList = new ArrayList<NonLiveUnblockEntity>();
		finalList.removeAll(finalList);
		
		if(rs!=null) {
			try {
				ResultSetMetaData md = rs.getMetaData();
				int noOfColumns = md.getColumnCount();
				while(rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for(int i=1; i<= noOfColumns; i++) {
						row.put(md.getColumnName(i), rs.getObject(i));
						//unblock = (NonLiveUnblockEntity) rs.getObject(i);
					}
					rows.add(row);
					
					//add data to POJO list
//					unblock.setRefId(rs.getString(1));
//					unblock.setSubAuaCode(rs.getString(2));
//					unblock.setResponseCode(rs.getString(3));
//					unblock.setDeviceCode(rs.getString(4));
//					unblock.setTransactionId(rs.getString(5));
//					unblock.setBatchId(rs.getString(6));
//					unblock.setBlockDate(rs.getString(7));
//					unblock.setFromDate(rs.getString(8));
//					unblock.setEndDate(rs.getString(9));
//					unblock.setAadhaarId(rs.getString(10));
					NonLiveUnblockEntity unblock = new NonLiveUnblockEntity();
					
					unblock.setRefId(rs.getString("ref_id"));
					unblock.setSubAuaCode(rs.getString("subaua_code")+"");
					unblock.setResponseCode(rs.getString("response_code"));
					unblock.setDeviceCode(rs.getString("device_code"));
					unblock.setTransactionId(rs.getString("transaction_id"));
					unblock.setBatchId(rs.getString("batch_id"));
					unblock.setBlockDate(rs.getString("block_date"));
					unblock.setFromDate(rs.getString("from_date"));
					unblock.setEndDate(rs.getString("end_date"));
					unblock.setAadhaarId(rs.getString("aadhaar_id"));
					
					System.out.println("unblock=="+unblock);
					finalList.add(unblock);
				}
				System.out.println("finalList0===>"+finalList);
				//finalList=finalListTemp;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return rows;
	}
	
	public static List<Map<String, Object>> getListFromResultSetForStringType(ResultSet rs) {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		if(rs!=null) {
			try {
				ResultSetMetaData md = rs.getMetaData();
				int noOfColumns = md.getColumnCount();
				while(rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for(int i=1; i<= noOfColumns; i++) {
						row.put(md.getColumnName(i), rs.getString(i));
					}
					rows.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return rows;
	}
}
