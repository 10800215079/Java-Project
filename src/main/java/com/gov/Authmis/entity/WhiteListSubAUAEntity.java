package com.gov.Authmis.entity;


import java.sql.Date;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;


//@Table(name = "SUBAUA_IP_WHITELIST")
//@Column(name="SUB_AUA_CODE")

@Entity
@Table(name = "subaua_ip_whitelist")
public class WhiteListSubAUAEntity {

	/*
	 * ID NOT NULL NUMBER SUB_AUA_CODE VARCHAR2(150) SUB_AUA_NAME VARCHAR2(255)
	 * REQUEST_TYPE VARCHAR2(50) IP_ADDRESS VARCHAR2(150) INSERT_DATE DATE STATUS
	 * NUMBER(10)
	 */
	// @Generated( value = GenerationTime.INSERT)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subaua_ip_whitelist_seq_gen")
//    @SequenceGenerator(name = "subaua_ip_whitelist_seq_gen", sequenceName = "subaua_ip_whitelist_seq", allocationSize = 1)
    @Column(name = "ID")
	private Long id;

	
	@Column(name = "SUB_AUA_CODE")
	private String subAuaCode;

	@Column(name = "SUB_AUA_NAME")
	private String subAuaName;

	@Column(name = "REQUEST_TYPE")
	private String servicetype;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "INSERT_DATE")
	private Date insertDate;

	@Column(name = "Status")
	private Long status;
	
	@Column(name = "APP_NAME")
	private String appName;
	
	
	@Column(name = "IPBELONGSTO")
    private String ipBelongsTo;

    @Column(name = "APPLICATONURL")
    private String applicatonUrl;

    @Column(name = "SCHEMENAME")
    private String schemeName;

    @Lob
    @Column(name = "PUBLISHEDDOC")
    private byte[] publishedDoc;

    @Column(name = "ISDOCPUBLISHED")
    private Integer  isDocPublished;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "UPDATEDDATE")
    private Date updatedDate;
    
    @Transient
    private MultipartFile inputFile;
    
	public MultipartFile getFile() {
		return inputFile;
	}

	public void setFile(MultipartFile file) {
		this.inputFile = file;
	}

	public String getIpBelongsTo() {
		return ipBelongsTo;
	}

	public void setIpBelongsTo(String ipBelongsTo) {
		this.ipBelongsTo = ipBelongsTo;
	}

	public String getApplicatonUrl() {
		return applicatonUrl;
	}

	public void setApplicatonUrl(String applicatonUrl) {
		this.applicatonUrl = applicatonUrl;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public byte[] getPublishedDoc() {
		return publishedDoc;
	}

	public void setPublishedDoc(byte[] publishedDoc) {
		this.publishedDoc = publishedDoc;
	}

	public int getIsDocPublished() {
		return isDocPublished;
	}

	public void setIsDocPublished(int isDocPublished) {
		this.isDocPublished = isDocPublished;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String ssoid) {
		this.createdBy = ssoid;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public WhiteListSubAUAEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubAuaCode() {
		return subAuaCode;
	}

	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}

	public String getSubAuaName() {
		return subAuaName;
	}

	public void setSubAuaName(String subAuaName) {
		this.subAuaName = subAuaName;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String iPAddress) {
		this.ipAddress = iPAddress;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public WhiteListSubAUAEntity(Long id, String subAuaCode, String subAuaName, String servicetype, String iPAddress,
			Date insertDate, Long status, String appName, String ipBelongsTo, String applicatonUrl, String schemeName,
			byte[] publishedDoc, int isDocPublished, String updatedBy, String createdBy, Date updatedDate) {
		super();
		this.id = id;
		this.subAuaCode = subAuaCode;
		this.subAuaName = subAuaName;
		this.servicetype = servicetype;
		this.ipAddress = iPAddress;
		this.insertDate = insertDate;
		this.status = status;
		this.appName = appName;
		this.ipBelongsTo = ipBelongsTo;
		this.applicatonUrl = applicatonUrl;
		this.schemeName = schemeName;
		this.publishedDoc = publishedDoc;
		this.isDocPublished = isDocPublished;
		this.updatedBy = updatedBy;
		this.createdBy = createdBy;
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "WhiteListSubAUAEntity [id=" + id + ", subAuaCode=" + subAuaCode + ", subAuaName=" + subAuaName
				+ ", servicetype=" + servicetype + ", iPAddress=" + ipAddress + ", insertDate=" + insertDate
				+ ", status=" + status + ", appName=" + appName + ", ipBelongsTo=" + ipBelongsTo + ", applicatonUrl="
				+ applicatonUrl + ", schemeName=" + schemeName + ", publishedDoc=" + Arrays.toString(publishedDoc)
				+ ", isDocPublished=" + isDocPublished + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + "]";
	}

	
	


}
