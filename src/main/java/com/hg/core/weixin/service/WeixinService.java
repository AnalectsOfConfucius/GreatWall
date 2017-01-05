package com.hg.core.weixin.service;

import java.util.Map;

import com.hg.core.weixin.entity.PlatformAccount;

/**
 * 
 * @author 麦俊
 *
 */
public interface WeixinService {

	// 查询平台信息
	public PlatformAccount selectPlatformAccount(String platformFlag);



}
