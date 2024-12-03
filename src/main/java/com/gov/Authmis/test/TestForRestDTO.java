package com.gov.Authmis.test;

import java.sql.Timestamp;

public class TestForRestDTO {
	private Long id;
    private String subauaName;
    private String subauaCode;
    private String appName;
    private String reportType;
    private Integer selectYear;
    private Timestamp createdDate;
    private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubauaName() {
		return subauaName;
	}
	public void setSubauaName(String subauaName) {
		this.subauaName = subauaName;
	}
	public String getSubauaCode() {
		return subauaCode;
	}
	public void setSubauaCode(String subauaCode) {
		this.subauaCode = subauaCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getSelectYear() {
		return selectYear;
	}
	public void setSelectYear(Integer selectYear) {
		this.selectYear = selectYear;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}
