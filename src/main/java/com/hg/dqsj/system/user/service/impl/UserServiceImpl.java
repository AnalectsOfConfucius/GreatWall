package com.hg.dqsj.system.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.DateUtil;
import com.hg.dqsj.system.role.entity.UserRole;
import com.hg.dqsj.system.user.dao.UsersDao;
import com.hg.dqsj.system.user.entity.User;
import com.hg.dqsj.system.user.service.UserService;

/**
 * 
 * @author 麦俊
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersDao userDao;

	@Override
	public List<User> selectAllUser() {
		return this.userDao.selectAllUser();
	}

	@Override
	public void insertUser(User user) {
		this.userDao.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

	@Override
	public void deleteUser(String userId, String updateUserId) {
		Map<String, String> m = new HashMap<>();
		m.put("id", userId);
		m.put("updateDate", DateUtil.getFullTime());
		m.put("updateUserId", updateUserId);
		this.userDao.deleteUser(m);
	}

	@Override
	public List<User> selectUserByIdAndNameAndPhone(Map<String, String> m) {
		return this.userDao.selectUserByIdAndNameAndPhone(m);
	}

	@Override
	public void changePassword(User user) {
		this.userDao.changePassword(user);
	}

	@Override
	public User selectUserByPw(String userId, String password) {
		Map<String, String> m = new HashMap<>();
		m.put("userId", userId);
		m.put("password", password);

		return this.userDao.selectUserByPw(m);
	}

	@Override
	public void updateUserAuth(User user) {
		this.userDao.updateUserAuth(user);
	}

	@Override
	public List<UserRole> selectUserRoleByUserId(Map<String, String> param) {
		return this.userDao.selectUserRoleByUserId(param);
	}

	@Override
	public int getUserCount(Map<String, String> param) {
		return userDao.getUserCount(param);
	}

	@Override
	public int getLogCount(Map<String, String> param) {
		return userDao.getLogCount(param);
	}

	@Override
	public User selectUserByName(Map<String, String> param) {
		return userDao.selectUserByName(param);
	}

}