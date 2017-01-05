package com.hg.dqsj.system.feUser.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.system.feUser.dao.FeUserDao;
import com.hg.dqsj.system.feUser.entity.FeUser;
import com.hg.dqsj.system.feUser.service.FeUserService;

/**
 * 
 * @author hlz
 */
@Service
public class FeUserServiceImpl implements FeUserService {

	@Autowired
	private FeUserDao dao;

	@Override
	public int getUserCount(Map<String, String> param) {
		return dao.getUserCount(param);
	}

	@Override
	public List<FeUser> selectFeUser(Map<String, String> param) {
		return dao.selectFeUser(param);
	}

	@Override
	public void deleteFeUser(Map<String, String> param) {
		dao.deleteFeUser(param);
	}

	@Override
	public void updateFeUser(FeUser feUser) {
		dao.updateFeUser(feUser);
	}

	

}