package com.gov.Authmis.test;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TESTTABLE")
public class TestForRestEntity {
	@Id
	private Long id;
	@Column(name = "SUBAUA_NAME")
    private String subauaName;
	@Column(name="SUBAUA_CODE")
    private String subauaCode;
	@Column(name="APP_NAME")
    private String appName;
	@Column(name="REPORT_TYPE")
    private String reportType;
	@Column(name="SELECT_YEAR")
    private Integer selectYear;
	@Column(name="CREATED_DATE")
    private Timestamp createdDate;
	@Column(name="STATUS")
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
	@Override
	public String toString() {
		return "TestForRestEntity [id=" + id + ", subauaName=" + subauaName + ", subauaCode=" + subauaCode
				+ ", appName=" + appName + ", reportType=" + reportType + ", selectYear=" + selectYear
				+ ", createdDate=" + createdDate + ", status=" + status + "]";
	}
	public TestForRestEntity(Long id, String subauaName, String subauaCode, String appName, String reportType,
			Integer selectYear, Timestamp createdDate, Integer status) {
		super();
		this.id = id;
		this.subauaName = subauaName;
		this.subauaCode = subauaCode;
		this.appName = appName;
		this.reportType = reportType;
		this.selectYear = selectYear;
		this.createdDate = createdDate;
		this.status = status;
	}
	public TestForRestEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
