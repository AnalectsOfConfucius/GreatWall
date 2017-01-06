package com.hg.dqsj.system.femenu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.DateUtil;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.femenu.dao.FeMenuDao;
import com.hg.dqsj.system.femenu.entity.FeMenu;
import com.hg.dqsj.system.femenu.entity.FeMenuInfo;
import com.hg.dqsj.system.femenu.service.FeMenuService;
import com.hg.dqsj.system.menu.dao.MenuDao;
import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.entity.MenuOperate;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * 功能：系统菜单管理Service实现类
 * 
 * @author Administrator
 *
 */

@Service
public class FeMenuServiceImpl implements FeMenuService {
	
	@Autowired
	FeMenuDao dao;// 前端菜单DAO接口

	@Override
	public List<FeMenu> selectAllMenu() {
		return dao.selectAllMenu();
	}

	@Override
	public void insertMenu(FeMenu menu) {
		dao.insertMenu(menu);
	}

	@Override
	public void updateMenu(FeMenu menu) {
		dao.updateMenu(menu);
	}

	@Override
	public void deleteMenu(Map<String, String> m) {
		dao.deleteMenu(m);
	}

	@Override
	public List<FeMenu> selectParentMenu(Map<String, String> param) {
		return dao.selectParentMenu(param);
	}

	@Override
	public List<FeMenuInfo> selectChildMenu(Map<String, String> param) {
		return dao.selectChildMenu(param);
	}

}
