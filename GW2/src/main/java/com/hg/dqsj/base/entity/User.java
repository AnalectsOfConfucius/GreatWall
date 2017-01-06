package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：会员用户信息类
 * 
 * @author hlz
 *
 */
public class User extends BaseInfoEntity {
	private String userPlatformId; // 用户名
	private String userName; // 用户名
	private String userPhone; // 手机号码
	private String userTel; // 固话号码
	private String userPassWord; // 密码
	private String userEmail; // 邮箱地址
	private String userGender; // 性别
	private String userPicUrl; // 用户头像地址
	private String userBirthday; // 生日
	private String isLocked; // 是否激活
	private int userOrder; // 排序
	private String remark; // 备注
	
	public String getUserPlatformId() {
		return userPlatformId;
	}
	public void setUserPlatformId(String userPlatformId) {
		this.userPlatformId = userPlatformId;
	}
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
	public String getUserPassWord() {
		return userPassWord;
	}
	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public int getUserOrder() {
		return userOrder;
	}
	public void setUserOrder(int userOrder) {
		this.userOrder = userOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}