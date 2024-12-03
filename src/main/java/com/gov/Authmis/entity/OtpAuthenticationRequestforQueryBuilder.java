package com.gov.Authmis.entity;

import java.util.Date;

public class OtpAuthenticationRequestforQueryBuilder {
	
	private Long srno;
	  
	private String sso;
	
	private String txn;

    private String otp;

    private String uuid;
    
    private String querys;
    
    private String insertDate;
    
    private String ip;
	
	
	
  

	
	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQuerys() {
		return querys;
	}

	public void setQuerys(String querys) {
		this.querys = querys;
	}

	public Long getSrno() {
		return srno;
	}

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public OtpAuthenticationRequestforQueryBuilder(Long srno, String sso, String txn, String otp, String uuid,
			String querys, String insertDate, String ip) {
		super();
		this.srno = srno;
		this.sso = sso;
		this.txn = txn;
		this.otp = otp;
		this.uuid = uuid;
		this.querys = querys;
		this.insertDate = insertDate;
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "OtpAuthenticationRequestforQueryBuilder [srno=" + srno + ", sso=" + sso + ", txn=" + txn + ", otp="
				+ otp + ", uuid=" + uuid + ", querys=" + querys + ", insertDate=" + insertDate + ", ip=" + ip + "]";
	}

	
    
    
    

}
