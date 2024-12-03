package com.gov.Authmis.model;

public class SubAuaWiseErrorDetailsReportModel {
	private String servicetype;
	private String subAuaCode;
	private String status;
	private String fromdate;
	private String Enddate;
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
	@Override
	public String toString() {
		return "SubAuaWiseErrorDetailsReportModel [servicetype=" + servicetype + ", subAuaCode=" + subAuaCode
				+ ", status=" + status + ", fromdate=" + fromdate + ", Enddate=" + Enddate + "]";
	}
	public SubAuaWiseErrorDetailsReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubAuaWiseErrorDetailsReportModel(String servicetype, String subAuaCode, String status, String fromdate,
			String enddate) {
		super();
		this.servicetype = servicetype;
		this.subAuaCode = subAuaCode;
		this.status = status;
		this.fromdate = fromdate;
		Enddate = enddate;
	}
	
	
}
