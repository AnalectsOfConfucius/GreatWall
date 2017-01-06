package com.hg.dqsj.system.role.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：角色用户信息POJO类
 * 
 * @author Administrator
 *
 */
public class RoleOperate  extends BaseInfoEntity {
	private String roleId;//角色名称
	private String menuId;//角色名称
	private String opId;//角色名称
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getOpId() {
		return opId;
	}
	public void setOpId(String opId) {
		this.opId = opId;
	}
	
}
