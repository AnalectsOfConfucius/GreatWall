package com.hg.dqsj.system.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hg.dqsj.system.role.dao.UserRoleDao;
import com.hg.dqsj.system.role.entity.UserRole;
import com.hg.dqsj.system.role.service.UserRoleService;

/**
 * 功能：用户角色SERVICE接口
 * 
 * @author Administrator
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleDao dao;

	@Override
	public List<UserRole> selectUserRole(Map<String, String> map) {
		return dao.selectUserRole(map);
	}

	@Override
	public void insertUserRole(UserRole userRole) {
		dao.insertUserRole(userRole);
	}

	@Override
	public void updateUserRole(UserRole userRole) {
		dao.updateUserRole(userRole);
	}

	@Override
	public void deleteUserRole(UserRole userRole) {
		dao.deleteUserRole(userRole);
	}
}
