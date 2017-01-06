package com.hg.dqsj.system.role.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：角色用户信息POJO类
 * 
 * @author Administrator
 *
 */
public class UserRole  extends BaseInfoEntity {
	/**角色ID**/
	private String roleId;
	
	/**用户ID**/
	private String userId;

	/** getter/setter method **/
	public String getRoleId() {
		return roleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
