package com.gov.Authmis.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SUBAUA")
@Entity
public class SubAua {
    @Id
    private String ID;
    private String NAME;
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * @return the nAME
	 */
	public String getNAME() {
		return NAME;
	}
	/**
	 * @param nAME the nAME to set
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}
    
    
}
