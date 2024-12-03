package com.gov.Authmis.entity;

import java.util.Date;

public class SubAuaAdmin {
	

	private Long srno;

	private String ssoid;

	private String user_roles;

	private String subauaname;
	
	private Date CREATED_DATE=new Date();

	public SubAuaAdmin(Long srno, String ssoid, String user_roles, String subauaname, Date cREATED_DATE) {
		super();
		this.srno = srno;
		this.ssoid = ssoid;
		this.user_roles = user_roles;
		this.subauaname = subauaname;
		CREATED_DATE = cREATED_DATE;
	}

	public SubAuaAdmin() {
		super();
	}

	public Long getSrno() {
		return srno;
	}

	public String getSsoid() {
		return ssoid;
	}

	public String getUser_roles() {
		return user_roles;
	}

	public String getSubauaname() {
		return subauaname;
	}

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	public void setUser_roles(String user_roles) {
		this.user_roles = user_roles;
	}

	public void setSubauaname(String subauaname) {
		this.subauaname = subauaname;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	@Override
	public String toString() {
		return "SubAuaAdmin [srno=" + srno + ", ssoid=" + ssoid + ", user_roles=" + user_roles + ", subauaname="
				+ subauaname + ", CREATED_DATE=" + CREATED_DATE + "]";
	}

	
  

}
