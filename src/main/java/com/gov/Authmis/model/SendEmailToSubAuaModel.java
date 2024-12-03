package com.gov.Authmis.model;


import java.net.InetAddress;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;



public class SendEmailToSubAuaModel {

	private Long srno;
	
	private String subAuaCode;
	
	private String to;
	
	private String cc;
	
	private String subject;
	
	private String content;
	
	//private String send_email_date;
    private Date send_email_date =new Date();
	
    //get system IP address
    private InetAddress ip;
  
    //-------------------------------------------------File Data--------------------------------------------------
   
    private String fileId;

	private String fileName[];

	private String[] fileType;

	/*
	 * @Lob private byte[] data;
	 */
	private byte[] data = null;
	private String  uri[];
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
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String[] getFileName() {
		return fileName;
	}
	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}
	public String[] getFileType() {
		return fileType;
	}
	public void setFileType(String[] fileType) {
		this.fileType = fileType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String[] getUri() {
		return uri;
	}
	public void setUri(String[] uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		return "SendEmailToSubAuaModel [srno=" + srno + ", subAuaCode=" + subAuaCode + ", to=" + to + ", cc=" + cc
				+ ", subject=" + subject + ", content=" + content + ", send_email_date=" + send_email_date + ", ip="
				+ ip + ", fileId=" + fileId + ", fileName=" + Arrays.toString(fileName) + ", fileType=" + fileType
				+ ", data=" + Arrays.toString(data) + ", uri=" + Arrays.toString(uri) + "]";
	}
	public SendEmailToSubAuaModel(Long srno, String subAuaCode, String to, String cc, String subject, String content,
			Date send_email_date, InetAddress ip, String fileId, String[] fileName, String[] fileType, byte[] data,
			String[] uri) {
		super();
		this.srno = srno;
		this.subAuaCode = subAuaCode;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.content = content;
		this.send_email_date = send_email_date;
		this.ip = ip;
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
		this.uri = uri;
	}
	public SendEmailToSubAuaModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
