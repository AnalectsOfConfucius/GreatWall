package com.hg.dqsj.system.menu.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.entity.MenuInfo;
import com.hg.dqsj.system.menu.entity.MenuOperate;

/**
 * 功能：系统菜单管理DAO接口类
 * 
 * @author Administrator
 *
 */
public interface MenuDao {

	// 查询所有级别的菜单
	public List<Menu> selectAllMenu();

	// 查询用户的菜单操作权限
	public List<MenuOperate> selectUserMenuOpration(String userId);

	// 新增菜单
	public void insertMenu(Menu menu);

	// 修改菜单信息
	public void updateMenu(Menu menu);

	// 逻辑删除菜单
	public void deleteMenu(Map<String, String> m);

	// 查询父菜单
	public List<Menu> selectParentMenu(Map<String, String> param);

	// 查询子菜单
	public List<MenuInfo> selectChildMenu(Map<String, String> param);

	/**
	 * 查询所有级别的权限菜单列表
	 * 
	 * @param menuUrl
	 * @return
	 */
	public String selectIdByMenuUrl(String menuUrl);
}
