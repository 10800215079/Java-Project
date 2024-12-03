package com.gov.Authmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBLSUBMENUMASTER")
public class AddSubMenuUpdateEntity {

	@Id
	@Column(name = "SUBMENUID")
	private Long SUBMENUID;
	@Column(name = "MENUID")
	private Long MENUID;
	@Column(name = "SUBMENUNAME")
	private String SUBMENUNAME;
	@Column(name = "SUBMENUDESC")
	private String SUBMENUDESC;
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
	@Column(name = "SUBMENUID_VAR")
	private Integer SUBMENUID_VAR;
	@Column(name = "ISNEWTAB")
	private Integer ISNEWTAB;
	@Column(name = "SUBMENUID_SEQ")
	private Integer SUBMENUID_SEQ;
	public Long getSUBMENUID() {
		return SUBMENUID;
	}
	public void setSUBMENUID(Long sUBMENUID) {
		SUBMENUID = sUBMENUID;
	}
	public Long getMENUID() {
		return MENUID;
	}
	public void setMENUID(Long mENUID) {
		MENUID = mENUID;
	}
	public String getSUBMENUNAME() {
		return SUBMENUNAME;
	}
	public void setSUBMENUNAME(String sUBMENUNAME) {
		SUBMENUNAME = sUBMENUNAME;
	}
	public String getSUBMENUDESC() {
		return SUBMENUDESC;
	}
	public void setSUBMENUDESC(String sUBMENUDESC) {
		SUBMENUDESC = sUBMENUDESC;
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
	public Integer getSUBMENUID_VAR() {
		return SUBMENUID_VAR;
	}
	public void setSUBMENUID_VAR(Integer sUBMENUID_VAR) {
		SUBMENUID_VAR = sUBMENUID_VAR;
	}
	public Integer getISNEWTAB() {
		return ISNEWTAB;
	}
	public void setISNEWTAB(Integer iSNEWTAB) {
		ISNEWTAB = iSNEWTAB;
	}
	public Integer getSUBMENUID_SEQ() {
		return SUBMENUID_SEQ;
	}
	public void setSUBMENUID_SEQ(Integer sUBMENUID_SEQ) {
		SUBMENUID_SEQ = sUBMENUID_SEQ;
	}
	public AddSubMenuUpdateEntity(Long sUBMENUID, Long mENUID, String sUBMENUNAME, String sUBMENUDESC, String uRL_REPO,
			Integer pRIORITY_TYPE, Integer sTATUS, String cREATIONDATE, String cREATIONBY, Integer sUBMENUID_VAR,
			Integer iSNEWTAB, Integer sUBMENUID_SEQ) {
		super();
		SUBMENUID = sUBMENUID;
		MENUID = mENUID;
		SUBMENUNAME = sUBMENUNAME;
		SUBMENUDESC = sUBMENUDESC;
		URL_REPO = uRL_REPO;
		PRIORITY_TYPE = pRIORITY_TYPE;
		STATUS = sTATUS;
		CREATIONDATE = cREATIONDATE;
		CREATIONBY = cREATIONBY;
		SUBMENUID_VAR = sUBMENUID_VAR;
		ISNEWTAB = iSNEWTAB;
		SUBMENUID_SEQ = sUBMENUID_SEQ;
	}
	@Override
	public String toString() {
		return "AddSubMenuUpdateEntity [SUBMENUID=" + SUBMENUID + ", MENUID=" + MENUID + ", SUBMENUNAME=" + SUBMENUNAME
				+ ", SUBMENUDESC=" + SUBMENUDESC + ", URL_REPO=" + URL_REPO + ", PRIORITY_TYPE=" + PRIORITY_TYPE
				+ ", STATUS=" + STATUS + ", CREATIONDATE=" + CREATIONDATE + ", CREATIONBY=" + CREATIONBY
				+ ", SUBMENUID_VAR=" + SUBMENUID_VAR + ", ISNEWTAB=" + ISNEWTAB + ", SUBMENUID_SEQ=" + SUBMENUID_SEQ
				+ "]";
	}
	public AddSubMenuUpdateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
}
