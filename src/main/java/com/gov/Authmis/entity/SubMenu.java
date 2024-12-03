package com.gov.Authmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TBLSUBMENUMASTER", schema = "AADHAAR")
public class SubMenu {

    @Id
    @Column(name = "SUBMENUID")
    private Long submenuId;

    @Column(name = "SUBMENUNAME")
    private String submenuName;

    @Column(name = "URL_REPO")
    private String urlRepo;
    
    @Column(name = "MENUID")
    private Long menuId;
    
    @Column(name = "STATUS")
    private Long status;

    public Long getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Long submenuId) {
		this.submenuId = submenuId;
	}

	public String getSubmenuName() {
		return submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	public String getUrlRepo() {
		return urlRepo;
	}

	public void setUrlRepo(String urlRepo) {
		this.urlRepo = urlRepo;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public SubMenu(Long submenuId, String submenuName, String urlRepo, Long menuId, Long status) {
		super();
		this.submenuId = submenuId;
		this.submenuName = submenuName;
		this.urlRepo = urlRepo;
		this.menuId = menuId;
		this.status = status;
	}

	public SubMenu() {
		super();
		
	}

}
