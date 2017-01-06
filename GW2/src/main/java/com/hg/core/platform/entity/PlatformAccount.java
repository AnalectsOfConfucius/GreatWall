package com.hg.core.platform.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * @Title: 平台账号信息
 * 
 * @author mj
 *
 */
public class PlatformAccount extends BaseInfoEntity {

	private String platformFlag; // 平台标识
	private String appId; // 公众号的唯一标识
	private String appSecret; // 公众号的密钥
	
	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
