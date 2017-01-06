package com.hg.dqsj.base.dao;

import java.util.Map;

import com.hg.dqsj.base.entity.FEUser;
import com.hg.dqsj.base.view.VFEUser;

/**
 * 微端用户DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEUserDao {
	/**
	 * 根据ID查询用户信息
	 * 
	 * @param map
	 * @return
	 */
	public FEUser selectById(Map<String, String> map);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param map
	 * @return
	 */
	public FEUser selectByUserName(Map<String, String> map);

	/**
	 * 根据手机号码查询用户信息
	 * 
	 * @param map
	 * @return
	 */
	public FEUser selectByUserPhone(Map<String, String> map);

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param map
	 * @return
	 */
	public VFEUser selectVById(Map<String, String> map);

	/**
	 * 更新用户信息
	 * 
	 * @param fEUser
	 * @return
	 */
	public Integer updateUser(FEUser fEUser);
}
