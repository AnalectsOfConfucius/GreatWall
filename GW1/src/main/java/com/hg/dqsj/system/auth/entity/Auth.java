package com.hg.dqsj.system.auth.entity;

import java.util.Map;

import com.hg.dqsj.system.user.entity.User;

/**
 * 功能：用户登录保存所属权限信息
 * 
 */
public class Auth {

	private String weixinOpenId;// 微信OpenID
	private String userId;// 用户ID
	private User user; // 用户信息
	private Map<String, Operate> menuOperations;// 菜单操作权限信息

	public String getWeixinOpenId() {
		return weixinOpenId;
	}

	public void setWeixinOpenId(String weixinOpenId) {
		this.weixinOpenId = weixinOpenId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, Operate> getMenuOperations() {
		return menuOperations;
	}

	public void setMenuOperations(Map<String, Operate> menuOperations) {
		this.menuOperations = menuOperations;
	}

}