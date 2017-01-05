package com.hg.dqsj.system.feUser.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.feUser.entity.FeUser;

/**
 * 
 * @author hlz
 *
 */
public interface FeUserService {

	// 用户总条数
	public int getUserCount(Map<String, String> param);
	
	// 查询用户
	public List<FeUser> selectFeUser(Map<String, String> param);
	// 逻辑删除用户
	public void deleteFeUser(Map<String, String> param);
	// 修改用户
	public void updateFeUser(FeUser feUser);
}
