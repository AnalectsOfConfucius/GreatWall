package com.hg.core.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.weixin.dao.WeixinDao;
import com.hg.core.weixin.entity.OrderPay;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.entity.UserAccount;
import com.hg.core.weixin.entity.UserPlatform;
import com.hg.core.weixin.service.WeixinService;
import com.hg.dqsj.base.entity.FEUser;

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

	@Override
	public Map<String, Object> selectUserByOpenId(String openId, String platformFlag) {
		Map<String, String> m = new HashMap<>();
		m.put("openId", openId);
		m.put("platformFlag", platformFlag);

		UserAccount userAccount = this.weixinDao.selectUserByOpenId(m);

		FEUser user = new FEUser();
		user.setId(userAccount.getId());
		user.setUserName(userAccount.getUserName());
		user.setUserPhone(userAccount.getUserPhone());
		user.setUserTel(userAccount.getUserTel());
		user.setUserPassWord(userAccount.getUserPassWord());
		user.setUserEmail(userAccount.getUserEmail());
		user.setUserGender(userAccount.getUserGender());
		user.setUserPicUrl(userAccount.getUserPicUrl());
		user.setIsLocked(userAccount.getIsLocked());
		user.setUserOrder(userAccount.getUserOrder());
		user.setRemark(userAccount.getRemark());

		Map<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("user", user);
		rtnMap.put("platformId", userAccount.getPlatformId());
		rtnMap.put("accessToken", userAccount.getAccessToken());
		rtnMap.put("expiresEndTime", userAccount.getExpiresEndTime());
		rtnMap.put("refreshToken", userAccount.getRefreshToken());

		return rtnMap;
	}

	@Override
	@Transactional
	public int insertUserInfo(FEUser user, UserPlatform userPlatform) throws Exception {
		int iInsertResult = this.weixinDao.insertUser(user);
		if (iInsertResult > 0) {
			iInsertResult = this.weixinDao.insertUserPlatform(userPlatform);
			if (iInsertResult < 1) {
				throw new Exception("新增用户平台信息失败");
			}
		}
		return iInsertResult;
	}

	@Override
	public int updateUser(FEUser user) {
		return this.weixinDao.updateUser(user);
	}

	@Override
	public int updateUserPlatform(UserPlatform userPlatform) {
		return this.weixinDao.updateUserPlatform(userPlatform);
	}

	@Override
	public int insertOrderPayInfo(OrderPay orderPay) {
		return this.insertOrderPayInfo(orderPay);
	}

	@Override
	public int updateOrderPayInfo(OrderPay orderPay) {
		return this.updateOrderPayInfo(orderPay);
	}

}