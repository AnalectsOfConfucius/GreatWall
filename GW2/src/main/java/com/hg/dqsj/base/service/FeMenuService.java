package com.hg.dqsj.base.service;

import java.util.List;

import com.hg.dqsj.base.entity.FeMenuInfo;
import com.hg.dqsj.base.view.VFeMenuInfo;

/**
 * 功能：菜单管理SERVICE接口类
 * 
 * @author Administrator
 *
 */
public interface FeMenuService {

	// 查询父菜单
	public List<VFeMenuInfo> selectParentMenu();

	// 查询子菜单
	public List<FeMenuInfo> selectChildMenu(String parentId);
}
