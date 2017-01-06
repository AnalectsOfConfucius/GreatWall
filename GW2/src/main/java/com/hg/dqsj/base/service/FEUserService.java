package com.hg.dqsj.base.service;

import com.hg.dqsj.base.entity.FEUser;
import com.hg.dqsj.base.view.VFEUser;

/**
 * 功能：微端用户SERVICE接口类
 * 
 * @author 吴晓敏
 *
 */
public interface FEUserService {
	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public FEUser selectById(String id);

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEUser selectVById(String id);

	/**
	 * 更新用户信息
	 * 
	 * @param fEUser
	 * @return
	 */
	public Integer updateUser(FEUser fEUser);

	/**
	 * 用户名是否已存在
	 * 
	 * @param id
	 * @param userName
	 * @return
	 */
	public boolean isExistUserName(String id, String userName);

	/**
	 * 手机号码是否已存在
	 * 
	 * @param id
	 * @param userPhone
	 * @return
	 */
	public boolean isExistUserPhone(String id, String userPhone);
}
