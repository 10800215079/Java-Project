package com.gov.Authmis.model;

import java.sql.Blob;
import java.util.Date;

public class SendMailReportModel {

	private Long srno;
	
	private String subAuaCode;
	
	private String to;
	
	private String cc;
	
	private String subject;
	
	private String content;
	
    //private Date send_email_date =new Date();
	private Date send_email_date;
	
    private String ip;
    
    private int status;
   

	private String fileId;

	private String fileName;

	private String fileType;

	//private String data;
	private Blob data;
	
	private String  uri;
	
	
	
	private String fromdate;
	  
	private String Enddate;
     
	private String frdate;
	  
	private String todate;
   


	public Long getSrno() {
		return srno;
	}

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public String getSubAuaCode() {
		return subAuaCode;
	}

	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSend_email_date() {
		return send_email_date;
	}

	public void setSend_email_date(Date send_email_date) {
		this.send_email_date = send_email_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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
	
		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getFrdate() {
			return frdate;
		}

		public void setFrdate(String frdate) {
			this.frdate = frdate;
		}

		public String getTodate() {
			return todate;
		}

		public void setTodate(String todate) {
			this.todate = todate;
		}

		

		public SendMailReportModel(Long srno, String subAuaCode, String to, String cc, String subject, String content,
				Date send_email_date, String ip, int status, String fileId, String fileName, String fileType,
				Blob data, String uri, String fromdate, String enddate, String frdate, String todate) {
			super();
			this.srno = srno;
			this.subAuaCode = subAuaCode;
			this.to = to;
			this.cc = cc;
			this.subject = subject;
			this.content = content;
			this.send_email_date = send_email_date;
			this.ip = ip;
			this.status = status;
			this.fileId = fileId;
			this.fileName = fileName;
			this.fileType = fileType;
			this.data = data;
			this.uri = uri;
			this.fromdate = fromdate;
			Enddate = enddate;
			this.frdate = frdate;
			this.todate = todate;
		}

		public SendMailReportModel() {
			super();
			// TODO Auto-generated constructor stub
		}

		public void setSend_email_date(String formattedDate) {
			// TODO Auto-generated method stub
			
		}


		

}
