package com.gov.Authmis.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBLMENUMASTER")
public class AddMenuUpdateEntity {
	@Id
	@Column(name = "MENUID")
	private Long MENUID;
	@Column(name = "MENUNAME")
	private String MENUNAME;
	@Column(name = "MENUDESC")
	private String MENUDESC;
	@Column(name = "URL_REPO")
	private String URL_REPO;
	@Column(name = "PRIORITY_TYPE")
	private Integer PRIORITY_TYPE;
	@Column(name = "STATUS")
	private Integer STATUS;
	@Column(name = "CREATIONDATE")
	private String CREATIONDATE ;
	@Column(name = "CREATIONBY")
	private String CREATIONBY;
	@Column(name = "MTYPID")
	private Integer MTYPID;
	@Column(name = "ISNEWTAB")
	private Integer ISNEWTAB;
	
	public Long getMENUID() {
		return MENUID;
	}
	public void setMENUID(Long mENUID) {
		MENUID = mENUID;
	}
	public String getMENUNAME() {
		return MENUNAME;
	}
	public void setMENUNAME(String mENUNAME) {
		MENUNAME = mENUNAME;
	}
	public String getMENUDESC() {
		return MENUDESC;
	}
	public void setMENUDESC(String mENUDESC) {
		MENUDESC = mENUDESC;
	}
	public String getURL_REPO() {
		return URL_REPO;
	}
	public void setURL_REPO(String uRL_REPO) {
		URL_REPO = uRL_REPO;
	}
	public Integer getPRIORITY_TYPE() {
		return PRIORITY_TYPE;
	}
	public void setPRIORITY_TYPE(Integer pRIORITY_TYPE) {
		PRIORITY_TYPE = pRIORITY_TYPE;
	}
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	public String getCREATIONDATE() {
		return CREATIONDATE;
	}
	public void setCREATIONDATE(String cREATIONDATE) {
		CREATIONDATE = cREATIONDATE;
	}
	public String getCREATIONBY() {
		return CREATIONBY;
	}
	public void setCREATIONBY(String cREATIONBY) {
		CREATIONBY = cREATIONBY;
	}
	public Integer getMTYPID() {
		return MTYPID;
	}
	public void setMTYPID(Integer mTYPID) {
		MTYPID = mTYPID;
	}
	public Integer getISNEWTAB() {
		return ISNEWTAB;
	}
	public void setISNEWTAB(Integer iSNEWTAB) {
		ISNEWTAB = iSNEWTAB;
	}
	@Override
	public String toString() {
		return "AddMenuUpdateModel [MENUID=" + MENUID + ", MENUNAME=" + MENUNAME + ", MENUDESC=" + MENUDESC
				+ ", URL_REPO=" + URL_REPO + ", PRIORITY_TYPE=" + PRIORITY_TYPE + ", STATUS=" + STATUS
				+ ", CREATIONDATE=" + CREATIONDATE + ", CREATIONBY=" + CREATIONBY + ", MTYPID=" + MTYPID + ", ISNEWTAB="
				+ ISNEWTAB + "]";
	}
	public AddMenuUpdateEntity(Long mENUID, String mENUNAME, String mENUDESC, String uRL_REPO, Integer pRIORITY_TYPE,
			Integer sTATUS, String cREATIONDATE, String cREATIONBY, Integer mTYPID, Integer iSNEWTAB) {
		super();
		MENUID = mENUID;
		MENUNAME = mENUNAME;
		MENUDESC = mENUDESC;
		URL_REPO = uRL_REPO;
		PRIORITY_TYPE = pRIORITY_TYPE;
		STATUS = sTATUS;
		CREATIONDATE = cREATIONDATE;
		CREATIONBY = cREATIONBY;
		MTYPID = mTYPID;
		ISNEWTAB = iSNEWTAB;
	}
	
	public AddMenuUpdateEntity() {
		super();
	}

	
	
	
}
