package com.hg.dqsj.system.auth.dao;

import java.util.Map;

import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.user.entity.User;

public interface AuthDao {
	public User selectUserByNameAndPw(Map<String, String> map);

	public Auth selectUserServiceCar(Map<String, String> map);
}
