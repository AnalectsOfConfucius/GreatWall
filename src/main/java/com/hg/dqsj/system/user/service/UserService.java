package com.hg.dqsj.system.user.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.role.entity.UserRole;
import com.hg.dqsj.system.user.entity.User;

/**
 * 
 * @author 麦俊
 *
 */
public interface UserService {

	// 查询所有级别的用户
	public List<User> selectAllUser();

	// 新增用户
	public void insertUser(User user);

	// 修改用户信息
	public void updateUser(User user);

	// 逻辑删除用户
	public void deleteUser(String userId, String updateUserId);

	// 根据用户ID、用户名称和手机号码查询角色信息
	public List<User> selectUserByIdAndNameAndPhone(Map<String, String> m);

	// 查询用户原密码是否正确
	public User selectUserByPw(String userId, String password);

	// 修改用户信息
	public void changePassword(User user);

	// 修改用户信息
	public void updateUserAuth(User user);

	// 查询用户角色
	public List<UserRole> selectUserRoleByUserId(Map<String, String> param);

	// 用户总条数
	public int getUserCount(Map<String, String> param);

	// 操作记录总条数
	public int getLogCount(Map<String, String> param);

	// 根据用户名查询用户
	public User selectUserByName(Map<String, String> param);
}
