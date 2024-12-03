package com.gov.Authmis.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class MinuteWiseTransactionModel {
	 public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	private String subCode;
	
	private String fromdate;
	
	private String Enddate;

	

	public MinuteWiseTransactionModel(String subCode, String fromdate, String enddate) {
		super();
		this.subCode = subCode;
		this.fromdate = fromdate;
		Enddate = enddate;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getEnddate() {
		return Enddate;
	}

	public void setEnddate(String enddate) {
		Enddate = enddate;
	}

	@Override
	public String toString() {
		return "MinuteWiseTransactionModel [subCode=" + subCode + ", fromdate=" + fromdate + ", Enddate=" + Enddate
				+ "]";
	}

	/*
	 * public String getSubCode() { return this.subCode; }
	 * 
	 * public void setSubCode(String subCode) { this.subCode = subCode; }
	 * 
	 * 
	 * public String getFromdate() { return this.fromdate; }
	 * 
	 * public void setFromdate(String fromdate) { this.fromdate = fromdate; }
	 * 
	 * public String getEnddate() { return this.Enddate; }
	 * 
	 * public void setEnddate(String enddate) { this.Enddate = enddate; }
	 */
	
}
