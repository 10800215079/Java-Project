package com.gov.Authmis.model;

public class ModuleMaster {
	private String id;
	private String name;
	private String role_id;
	private String role_name;
	private String menu_str;
	private String submenu_str;
	private String menu_id;
	private String sub_menu_id;
	private String sub_menu_url;
	private String status;
	public ModuleMaster(String id, String name, String role_id, String role_name, String menu_str, String submenu_str,
			String menu_id, String sub_menu_id, String sub_menu_url, String status) {
		super();
		this.id = id;
		this.name = name;
		this.role_id = role_id;
		this.role_name = role_name;
		this.menu_str = menu_str;
		this.submenu_str = submenu_str;
		this.menu_id = menu_id;
		this.sub_menu_id = sub_menu_id;
		this.sub_menu_url = sub_menu_url;
		this.status = status;
	}
	public ModuleMaster() {
		super();
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getRole_id() {
		return role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public String getMenu_str() {
		return menu_str;
	}
	public String getSubmenu_str() {
		return submenu_str;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public String getSub_menu_id() {
		return sub_menu_id;
	}
	public String getSub_menu_url() {
		return sub_menu_url;
	}
	public String getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public void setMenu_str(String menu_str) {
		this.menu_str = menu_str;
	}
	public void setSubmenu_str(String submenu_str) {
		this.submenu_str = submenu_str;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public void setSub_menu_id(String sub_menu_id) {
		this.sub_menu_id = sub_menu_id;
	}
	public void setSub_menu_url(String sub_menu_url) {
		this.sub_menu_url = sub_menu_url;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ModuleMaster [id=" + id + ", name=" + name + ", role_id=" + role_id + ", role_name=" + role_name
				+ ", menu_str=" + menu_str + ", submenu_str=" + submenu_str + ", menu_id=" + menu_id + ", sub_menu_id="
				+ sub_menu_id + ", sub_menu_url=" + sub_menu_url + ", status=" + status + "]";
	}
	
	
	
	
}
