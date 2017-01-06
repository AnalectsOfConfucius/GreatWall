package com.hg.dqsj.system.role.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：角色用户信息POJO类
 * 
 * @author Administrator
 *
 */
public class UserRoleInfo  extends BaseInfoEntity {
	private String userName;
	private String userPhone;
	private String userTel;
	private String userEmail;
	private String roleName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
