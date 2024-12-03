package com.gov.Authmis.model;

import java.util.Date;

public class Menu {

	private Long MENUID;

	private String MENUNAME;

	private String MENUDESC;

	private String URL_REPO;

	private Integer PRIORITY_TYPE;

	private Integer STATUS;

	private Date CREATIONDATE = new Date();

	private String CREATIONBY;

	private Integer MTYPID;

	private Integer ISNEWTAB;

	private String SUBMENUNAME;
	private String SUBMENUDESC;
	private String URL_REPO_submenu;
	
	private String roles;

	public Menu(Long mENUID, String mENUNAME, String mENUDESC, String uRL_REPO, Integer pRIORITY_TYPE, Integer sTATUS,
			Date cREATIONDATE, String cREATIONBY, Integer mTYPID, Integer iSNEWTAB, String sUBMENUNAME,
			String sUBMENUDESC, String uRL_REPO_submenu,String Roles) {
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
		SUBMENUNAME = sUBMENUNAME;
		SUBMENUDESC = sUBMENUDESC;
		URL_REPO_submenu = uRL_REPO_submenu;
		Roles=roles;
	}

	public Menu() {
		super();
	}

	public Long getMENUID() {
		return MENUID;
	}

	public String getMENUNAME() {
		return MENUNAME;
	}

	public String getMENUDESC() {
		return MENUDESC;
	}

	public String getURL_REPO() {
		return URL_REPO;
	}

	public Integer getPRIORITY_TYPE() {
		return PRIORITY_TYPE;
	}

	public Integer getSTATUS() {
		return STATUS;
	}

	public Date getCREATIONDATE() {
		return CREATIONDATE;
	}

	public String getCREATIONBY() {
		return CREATIONBY;
	}

	public Integer getMTYPID() {
		return MTYPID;
	}

	public Integer getISNEWTAB() {
		return ISNEWTAB;
	}

	public String getSUBMENUNAME() {
		return SUBMENUNAME;
	}

	public String getSUBMENUDESC() {
		return SUBMENUDESC;
	}

	public String getURL_REPO_submenu() {
		return URL_REPO_submenu;
	}

	public void setMENUID(Long mENUID) {
		MENUID = mENUID;
	}

	public void setMENUNAME(String mENUNAME) {
		MENUNAME = mENUNAME;
	}

	public void setMENUDESC(String mENUDESC) {
		MENUDESC = mENUDESC;
	}

	public void setURL_REPO(String uRL_REPO) {
		URL_REPO = uRL_REPO;
	}

	public void setPRIORITY_TYPE(Integer pRIORITY_TYPE) {
		PRIORITY_TYPE = pRIORITY_TYPE;
	}

	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}

	public void setCREATIONDATE(Date cREATIONDATE) {
		CREATIONDATE = cREATIONDATE;
	}

	public void setCREATIONBY(String cREATIONBY) {
		CREATIONBY = cREATIONBY;
	}

	public void setMTYPID(Integer mTYPID) {
		MTYPID = mTYPID;
	}

	public void setISNEWTAB(Integer iSNEWTAB) {
		ISNEWTAB = iSNEWTAB;
	}

	public void setSUBMENUNAME(String sUBMENUNAME) {
		SUBMENUNAME = sUBMENUNAME;
	}

	public void setSUBMENUDESC(String sUBMENUDESC) {
		SUBMENUDESC = sUBMENUDESC;
	}

	public void setURL_REPO_submenu(String uRL_REPO_submenu) {
		URL_REPO_submenu = uRL_REPO_submenu;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Menu [MENUID=" + MENUID + ", MENUNAME=" + MENUNAME + ", MENUDESC=" + MENUDESC + ", URL_REPO=" + URL_REPO
				+ ", PRIORITY_TYPE=" + PRIORITY_TYPE + ", STATUS=" + STATUS + ", CREATIONDATE=" + CREATIONDATE
				+ ", CREATIONBY=" + CREATIONBY + ", MTYPID=" + MTYPID + ", ISNEWTAB=" + ISNEWTAB + ", SUBMENUNAME="
				+ SUBMENUNAME + ", SUBMENUDESC=" + SUBMENUDESC + ", URL_REPO_submenu=" + URL_REPO_submenu + ", roles="
				+ roles + "]";
	}


	// Getters and setters

}