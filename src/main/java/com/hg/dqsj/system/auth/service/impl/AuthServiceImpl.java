package com.hg.dqsj.system.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.system.auth.dao.AuthDao;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.auth.service.AuthService;
import com.hg.dqsj.system.user.entity.User;

/**
 * 
 * @author 麦俊
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthDao dao;// 权限操作DAO

	/** 根据用户信息获取权限操作Map **/
	@Override
	public Map<String, Operate> findMenuOperationByUser(String userId) {
		Map<String, Operate> menuOperations = new HashMap<String, Operate>();

		return menuOperations;
	}

	@Override
	public User selectUserByNameAndPw(String userName, String password) {
		Map<String, String> map = new HashMap<>();
		map.put("userName", userName);
		map.put("password", password);

		return this.dao.selectUserByNameAndPw(map);
	}

	@Override
	public Auth selectUserServiceCar(Map<String, String> map) {
		return dao.selectUserServiceCar(map);
	}
}