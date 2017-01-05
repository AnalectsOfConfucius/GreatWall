package com.hg.dqsj.system.role.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：系统角色信息POJO类
 * 
 * @author Administrator
 *
 */
public class Role extends BaseInfoEntity {
	private String roleName;// 角色名称
	private Integer roleOrder;// 排序
	private String remark;// 角色备注述信息
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Integer getRoleOrder() {
		return roleOrder;
	}
	
	public void setRoleOrder(Integer roleOrder) {
		this.roleOrder = roleOrder;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
