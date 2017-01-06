package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.DateUtil;
import com.hg.dqsj.base.dao.FEUserDao;
import com.hg.dqsj.base.entity.FEUser;
import com.hg.dqsj.base.service.FEUserService;
import com.hg.dqsj.base.view.VFEUser;

/**
 * 功能：微端用户管理Service实现类
 * 
 * @author 吴晓敏
 *
 */

@Service
public class FEUserServiceImpl implements FEUserService {
	@Autowired
	private FEUserDao fEUserDao; // 微端用户DAO接口

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public FEUser selectById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return fEUserDao.selectById(map);
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userName
	 * @return
	 */
	public FEUser selectByUserName(String userName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		return fEUserDao.selectByUserName(map);
	}

	/**
	 * 根据手机号码查询用户信息
	 * 
	 * @param userPhone
	 * @return
	 */
	public FEUser selectByUserPhone(String userPhone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userPhone", userPhone);
		return fEUserDao.selectByUserPhone(map);
	}

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEUser selectVById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		VFEUser vFEUser = fEUserDao.selectVById(map);
		if (vFEUser == null) {
			return null;
		}

		if ("1".equals(vFEUser.getUserGender())) {
			vFEUser.setUserGenderStr("男");
		} else if ("2".equals(vFEUser.getUserGender())) {
			vFEUser.setUserGenderStr("女");
		}
		return vFEUser;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param fEUser
	 * @return
	 */
	public Integer updateUser(FEUser fEUser) {
		fEUser.setUpdateDate(DateUtil.getFullDate());
		return fEUserDao.updateUser(fEUser);
	}

	/**
	 * 用户名是否已存在
	 * 
	 * @param id
	 * @param userName
	 * @return
	 */
	public boolean isExistUserName(String id, String userName) {
		FEUser fEUser = selectByUserName(userName);
		// 用户名不存在
		if (fEUser == null) {
			return false;
		}
		// 用户名为当前用户所属
		if (fEUser.getId().equals(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 手机号码是否已存在
	 * 
	 * @param id
	 * @param userPhone
	 * @return
	 */
	public boolean isExistUserPhone(String id, String userPhone) {
		FEUser fEUser = selectByUserPhone(userPhone);
		// 手机号码不存在
		if (fEUser == null) {
			return false;
		}
		// 手机号码为当前用户所属
		if (fEUser.getId().equals(id)) {
			return false;
		}
		return true;
	}
}
