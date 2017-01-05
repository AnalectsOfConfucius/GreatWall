package com.hg.dqsj.system.feUser.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.feUser.entity.FeUser;

/**
 * 会员用户管理DAO接口
 * 
 * @author hlz
 *
 */
public interface FeUserDao {
	// 用户总条数
	public int getUserCount(Map<String, String> param);
	
	// 查询用户
	public List<FeUser> selectFeUser(Map<String, String> param);
	// 逻辑删除用户
	public void deleteFeUser(Map<String, String> param);
	// 修改用户
	public void updateFeUser(FeUser feUser);
	
}
