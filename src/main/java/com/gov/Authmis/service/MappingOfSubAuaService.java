package com.gov.Authmis.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gov.Authmis.model.UsersRoleMapping;

public interface MappingOfSubAuaService {

	List<UsersRoleMapping> findAll() throws SQLException, ParseException;

	/* UsersRoleMapping getSubauaCode(UsersRoleMapping usersRoleMapping); */

	Object getSubauaCode(int srno, int status,String ssoid1);

}
