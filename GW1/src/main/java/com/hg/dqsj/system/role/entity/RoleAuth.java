package com.hg.dqsj.system.role.entity;

import java.util.List;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：角色用户信息POJO类
 * 
 * @author Administrator
 *
 */
public class RoleAuth extends BaseInfoEntity {
	
	private String roleName;//角色名称
	private List<RoleMenu> mlist;//菜单列表
	
	
	public List<RoleMenu> getMlist() {
		return mlist;
	}
	public void setMlist(List<RoleMenu> mlist) {
		this.mlist = mlist;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
