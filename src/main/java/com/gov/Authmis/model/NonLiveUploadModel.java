package com.gov.Authmis.model;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class NonLiveUploadModel {
	
	private String fromdate;
	private String enddate;
	private String emailDate;
	private MultipartFile excelFile;
	private MultipartFile mailPdfFile;
	
	
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getEmailDate() {
		return emailDate;
	}
	public void setEmailDate(String emailDate) {
		this.emailDate = emailDate;
	}
	public MultipartFile getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}
	public MultipartFile getMailPdfFile() {
		return mailPdfFile;
	}
	public void setMailPdfFile(MultipartFile mailPdfFile) {
		this.mailPdfFile = mailPdfFile;
	}
}
