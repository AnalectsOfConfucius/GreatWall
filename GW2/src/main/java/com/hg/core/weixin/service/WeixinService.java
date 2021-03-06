package com.hg.core.weixin.service;

import java.util.Map;

import com.hg.core.weixin.entity.OrderPay;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.entity.UserPlatform;
import com.hg.dqsj.base.entity.FEUser;

/**
 * 
 * @author 麦俊
 *
 */
public interface WeixinService {

	// 查询平台信息
	public PlatformAccount selectPlatformAccount(String platformFlag);

	// 根据OpenId查询用户信息
	public Map<String, Object> selectUserByOpenId(String openId, String platformFlag);

	// 新增用户及其平台信息
	public int insertUserInfo(FEUser user, UserPlatform userPlatform) throws Exception;

	// 修改用户信息
	public int updateUser(FEUser user);

	// 修改用户平台信息
	public int updateUserPlatform(UserPlatform userPlatform);

	// 新建订单支付信息
	public int insertOrderPayInfo(OrderPay orderPay);

	// 修改订单支付信息
	public int updateOrderPayInfo(OrderPay orderPay);

}
