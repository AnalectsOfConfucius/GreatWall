package com.hg.dqsj.system.role.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.role.entity.Operate;
import com.hg.dqsj.system.role.entity.Role;
import com.hg.dqsj.system.role.entity.RoleAuth;
import com.hg.dqsj.system.role.entity.RoleMenu;
import com.hg.dqsj.system.role.entity.RoleOperate;

/**
 * 功能：角色管理DAO接口
 * 
 * @author Administrator
 *
 */
public interface RoleDao {

	// 查询所有级别的角色
	public List<Role> selectAllRole();
	
	// 新增角色
	public void insertRole(Role role);
	
	// 修改角色信息
	public void updateRole(Role role);
	
	// 逻辑删除角色
	public void deleteRole(Map<String, String> m);
	
	// 根据角色ID和角色名称查询角色信息
	public List<Role> selectRoleByIdAndName(Map<String, String> m);
	
	// 根据角色ID查询角色权限
	public List<RoleAuth> selectRoleAuth(Map<String, String> param);
	// 查询菜单
	public List<RoleMenu> selectMenu(Map<String, String> param);
	// 查询操作
	public List<Operate> selectOperate(Map<String, String> param);
	// 新增角色操作
	public void insertRoleOperate(RoleOperate roleOperate);
	public void deleteRoleOperate(Map<String, String> param);
}
