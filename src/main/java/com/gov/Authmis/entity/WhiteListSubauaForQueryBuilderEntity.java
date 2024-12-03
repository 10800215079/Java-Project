package com.gov.Authmis.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="SSO_IP_WHITELIST")
public class WhiteListSubauaForQueryBuilderEntity {

	@Id
	@Column(name = "SRNO")
	private Long SRNO;
	
	@Column(name = "SSO_ID")
	private String ssoId;
	
	@Column(name = "IP")
	private String IP;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String CREATED_BY;
		
	@Column(name = "AADHHAR_ID")
	private String aadhaarId;
	
	@Column(name = "TRANSACTION_ID")
	private String TRANSACTION_ID;
	
	@Column(name = "STATUS")
	private Long STATUS;

	@Transient
    private String otp;
	
	@Transient
	private String ref;
	

	
	
   
    
	public String getAadhaarId() {
		return aadhaarId;
	}
	public void setAadhaarId(String aadhaarId) {
		this.aadhaarId = aadhaarId;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public Long getSRNO() {
		return SRNO;
	}
	public void setSRNO(Long sRNO) {
		SRNO = sRNO;
	}

	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getTRANSACTION_ID() {
		return TRANSACTION_ID;
	}
	public void setTRANSACTION_ID(String tRANSACTION_ID) {
		TRANSACTION_ID = tRANSACTION_ID;
	}
	
	
	public Long getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Long sTATUS) {
		STATUS = sTATUS;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	

	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public WhiteListSubauaForQueryBuilderEntity(Long sRNO, String sSO_ID, String iP, Date cREATED_DATE,
			String cREATED_BY, String aADHHAR_ID, String tRANSACTION_ID, Long sTATUS, String otp
			) {
		super();
		SRNO = sRNO;
		ssoId = sSO_ID;
		IP = iP;
		createdDate = cREATED_DATE;
		CREATED_BY = cREATED_BY;
		aadhaarId = aADHHAR_ID;
		TRANSACTION_ID = tRANSACTION_ID;
		STATUS = sTATUS;

		this.otp = otp;
	}
	public WhiteListSubauaForQueryBuilderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
    
	
}
