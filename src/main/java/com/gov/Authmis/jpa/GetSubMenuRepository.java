package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gov.Authmis.entity.SubMenu;

public interface GetSubMenuRepository extends JpaRepository<SubMenu, Long> {
	 //List<SubMenu> findAllByStatus(Integer status);
}
