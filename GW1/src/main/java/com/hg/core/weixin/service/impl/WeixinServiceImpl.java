package com.hg.core.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.weixin.dao.WeixinDao;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.service.WeixinService;

/**
 * 
 * @author 麦俊
 */
@Service
public class WeixinServiceImpl implements WeixinService {

	@Autowired
	private WeixinDao weixinDao;

	@Override
	public PlatformAccount selectPlatformAccount(String platformFlag) {
		return this.weixinDao.selectPlatformAccount(platformFlag);
	}

}