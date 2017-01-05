package com.hg.dqsj.system.role.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.role.entity.UserRole;

/**
 * 功能：用户角色信息DAO接口
 * 
 * @author Administrator
 *
 */
public interface UserRoleDao {

	public List<UserRole> selectUserRole(Map<String, String> map);

	public void insertUserRole(UserRole userRole);

	public void updateUserRole(UserRole userRole);

	public void deleteUserRole(UserRole userRole);
}
