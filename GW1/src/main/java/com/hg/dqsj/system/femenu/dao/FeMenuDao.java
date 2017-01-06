package com.hg.dqsj.system.femenu.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.femenu.entity.FeMenu;
import com.hg.dqsj.system.femenu.entity.FeMenuInfo;

/**
 * 功能：系统菜单管理DAO接口类
 * 
 * @author Administrator
 *
 */
public interface FeMenuDao {
	
	// 查询所有级别的菜单
	public List<FeMenu> selectAllMenu();
	
	// 新增菜单
	public void insertMenu(FeMenu menu);
	
	// 修改菜单信息
	public void updateMenu(FeMenu menu);
	
	// 逻辑删除菜单
	public void deleteMenu(Map<String, String> m);
	
	// 查询父菜单
	public List<FeMenu> selectParentMenu(Map<String, String> param);
	
	// 查询子菜单
	public List<FeMenuInfo> selectChildMenu(Map<String, String> param);
}
