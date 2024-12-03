package com.gov.Authmis.model;

public class ParameterWiseTransactionReportModel {

	/*
	 * public enum parameter { referenceNo, responseCode, mi, deviceCode, dpId,
	 * errorCode };
	 */

	private String servicetype;
	private String subAuaCode;
	private String status;
	private String fromdate;
	private String Enddate;
	private String paraeter;
	private String paramValue;

	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	public String getSubAuaCode() {
		return subAuaCode;
	}
	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getParaeter() {
		return paraeter;
	}
	public void setParaeter(String paraeter) {
		this.paraeter = paraeter;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	//private parameter para;

	/*
	 * public parameter getPara() { return para; }
	 * 
	 * public void setPara(parameter para) { this.para = para; }
	 */

	
}
