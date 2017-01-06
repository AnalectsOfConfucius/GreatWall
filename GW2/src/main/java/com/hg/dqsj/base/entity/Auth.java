package com.hg.dqsj.base.entity;

/**
 * 功能：微信通用接口凭证
 * 
 */
public class Auth {
	private String platformId; // 平台ID
	private String appId; // 平台的唯一标识
	private String openId; // OpenID
	private String atoken; // 网页授权access_token
	private long expiresEndTime; // 超时截止时间，单位（毫秒）
	private String refreshToken; // 用户刷新access_token
	private String userId; // 用户ID
	private FEUser user; // 用户信息

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAtoken() {
		return atoken;
	}

	public void setAtoken(String atoken) {
		this.atoken = atoken;
	}

	public long getExpiresEndTime() {
		return expiresEndTime;
	}

	public void setExpiresEndTime(long expiresEndTime) {
		this.expiresEndTime = expiresEndTime;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public FEUser getUser() {
		return user;
	}

	public void setUser(FEUser user) {
		this.user = user;
	}

}