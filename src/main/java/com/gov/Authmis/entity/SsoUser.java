package com.gov.Authmis.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class SsoUser {
	private String sAMAccountName;
	private ArrayList<String>  Roles;
	private String  OldSSOIDs;
	private String  UserType;
	
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	public ArrayList<String> getRoles() {
		return Roles;
	}
	public void setRoles(ArrayList<String> roles) {
		Roles = roles;
	}
	public String getOldSSOIDs() {
		return OldSSOIDs;
	}
	public void setOldSSOIDs(String oldSSOIDs) {
		OldSSOIDs = oldSSOIDs;
	}
	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}

}
