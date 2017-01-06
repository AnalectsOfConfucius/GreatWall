package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.base.dao.FeMenuDao;
import com.hg.dqsj.base.entity.FeMenuInfo;
import com.hg.dqsj.base.service.FeMenuService;
import com.hg.dqsj.base.view.VFeMenuInfo;

/**
 * 功能：系统菜单管理Service实现类
 * 
 * @author 吴晓敏
 *
 */

@Service
public class FeMenuServiceImpl implements FeMenuService {

	@Autowired
	FeMenuDao dao;// 前端菜单DAO接口

	@Override
	public List<VFeMenuInfo> selectParentMenu() {
		return dao.selectParentMenu();
	}

	@Override
	public List<FeMenuInfo> selectChildMenu(String parentId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("parentId", parentId);
		return dao.selectChildMenu(param);
	}

}
