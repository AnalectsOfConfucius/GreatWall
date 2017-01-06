package com.hg.dqsj.system.menu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.entity.MenuInfo;

/**
 * 功能：菜单管理SERVICE接口类
 * 
 * @author Administrator
 *
 */
public interface MenuService {
	// 查询用户所有级别的菜单
	public Map<String, Object> selectAllLevelMenu(String userId, String adminFlag);

	// 查询用户菜单权限
	public Map<String, Operate> selectMenuOp(String userId, String adminFlag);

	// 查询所有菜单
	public List<Menu> selectAllMenu();

	// 新增菜单
	public void insertMenu(Menu menu);

	// 修改菜单信息
	public void updateMenu(Menu menu);

	// 逻辑删除菜单
	public void deleteMenu(String menuId, String updateUserId);

	// 查询父菜单
	public List<Menu> selectParentMenu(Map<String, String> param);

	// 查询子菜单
	public List<MenuInfo> selectChildMenu(Map<String, String> param);

	// 当前操作权限
	public boolean getOper(String menuName, String oper, HttpServletRequest request, SessionProvider session);

	/**
	 * 查询所有级别的权限菜单列表
	 * 
	 * @param menuUrl
	 * @return
	 */
	public String selectIdByMenuUrl(String menuUrl);

	/**
	 * 判断是否拥有操作权限
	 * 
	 * @param request
	 * @param operator
	 * @return
	 */
	public boolean isHasOperator(HttpServletRequest request, String operator);
}
