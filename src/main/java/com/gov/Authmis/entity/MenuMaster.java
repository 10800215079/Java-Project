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
@Table(name = "TBLMENUMASTER", schema = "AADHAAR")
public class MenuMaster {

    @Id
    @Column(name = "MENUID")
    private Long menuId;

    @Column(name = "MENUNAME")
    private String menuName;



	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public MenuMaster() {
		
	}

	public MenuMaster(Long menuId, String menuName) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
	}
    
    
}

