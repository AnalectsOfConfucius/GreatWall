package com.hg.core.platform.dao;

import java.util.Map;

import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.entity.UserAccount;
import com.hg.core.weixin.entity.UserPlatform;
import com.hg.dqsj.base.entity.FEUser;

/**
 * 角色管理DAO接口
 * 
 * @author mj
 *
 */
public interface PlatformDao {

	// 查询平台信息
	public PlatformAccount selectPlatformAccount(String platformFlag);

	// 根据OpenId查询用户信息
	public UserAccount selectUserByOpenId(Map<String, String> m);

	// 新增用户
	public int insertUser(FEUser user);

	// 新增用户平台信息
	public int insertUserPlatform(UserPlatform userPlatform);

	// 修改用户信息
	public int updateUser(FEUser user);

	// 修改用户平台信息
	public int updateUserPlatform(UserPlatform userPlatform);

}
