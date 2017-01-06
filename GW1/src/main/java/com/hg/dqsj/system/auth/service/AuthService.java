package com.hg.dqsj.system.auth.service;

import java.util.Map;

import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.user.entity.User;

/**
 *
 * @author 麦俊
 */
public interface AuthService {

	// 用户登录
	public User selectUserByNameAndPw(String userName, String password);

	// 根据用户信息获取权限操作信息
	public Map<String, Operate> findMenuOperationByUser(String userId);

	// 当前职员的餐车与服务点
	public Auth selectUserServiceCar(Map<String, String> map);

}
