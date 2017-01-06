package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FeMenuInfo;
import com.hg.dqsj.base.view.VFeMenuInfo;

/**
 * 功能：系统菜单管理DAO接口类
 * 
 * @author Administrator
 *
 */
public interface FeMenuDao {
	// 查询父菜单
	public List<VFeMenuInfo> selectParentMenu();

	// 查询子菜单
	public List<FeMenuInfo> selectChildMenu(Map<String, String> param);
}
