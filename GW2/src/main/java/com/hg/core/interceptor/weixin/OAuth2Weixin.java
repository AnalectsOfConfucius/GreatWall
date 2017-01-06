//package com.hg.core.interceptor.weixin;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.hg.core.filters.SessionValidFilter;
//import com.hg.core.session.SessionProvider;
//import com.hg.core.util.DateUtil;
//import com.hg.core.util.RequestUtils;
//import com.hg.core.util.StringUtil;
//import com.hg.core.util.UUIdUtil;
//import com.hg.core.weixin.entity.PlatformAccount;
//import com.hg.core.weixin.entity.SNSUserInfo;
//import com.hg.core.weixin.entity.UserPlatform;
//import com.hg.core.weixin.entity.WeixinOauth2Token;
//import com.hg.core.weixin.service.WeixinService;
////import com.hg.core.weixin.util.AdvancedUtil;
//import com.hg.dqsj.base.entity.Auth;
//import com.hg.dqsj.base.entity.FEUser;
//
///**
// * <p>
// * OAuth2拦截器完成微信授权登陆
// * </p>
// *
// * @author: mj
// * @date: 2014-10-27上午11:19:05
// */
//public class OAuth2Weixin extends HandlerInterceptorAdapter {
//
//	/**
//	 * 当前类自己的logger
//	 */
//	private static Logger logger = Logger.getLogger(OAuth2Weixin.class);
//
//	private String appId = "";
//	private String appSecret = "";
//
//	@Autowired
//	private SessionProvider session;
//
//	@Autowired
//	private WeixinService weixinService;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//		try {
//			String request_url = new StringBuffer((StringUtil.isNullorEmpty(request.getRequestURL())) ? "" : request.getRequestURL()).toString();
//			String query_string = new StringBuffer((StringUtil.isNullorEmpty(request.getQueryString())) ? "" : ("?" + request.getQueryString())).toString();
//
//			// 获取当前请求URL
//			String redirect_uri = new StringBuffer().append(request_url).append(query_string).toString();
//			redirect_uri = redirect_uri.replace("localhost:60901", "www.zaocanche.com");
//
//			/*
//			 * ======获取站点信息，拦截所有请求======
//			 */
//			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.WEIXIN_KEY);
//			if (auth == null) {
//				if (setPlatformAccountInfo()) { // 获取商户平台ID和密钥
//					String code = RequestUtils.getQueryParam(request, "code");
//
//					if (StringUtil.isNullorEmpty(code)) {
//						String scope = "snsapi_userinfo";
//						String oauth2URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
//
//						oauth2URL = oauth2URL.replace("APP_ID", this.appId).replace("REDIRECT_URI", redirect_uri).replace("SCOPE", scope).replace("STATE", "1");
//						response.sendRedirect(oauth2URL);
//						return false;
//
//					} else { // 用户同意授权
//						if (redirect_uri.contains("doSign")) {
//							WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(this.appId, this.appSecret, code);
//							auth = new Auth();
//							auth.setAtoken(weixinOauth2Token.getAccessToken());
//							this.session.setAttribute(request, response, SessionValidFilter.WEIXIN_KEY, auth);
//						} else {
//							this.OAuth2(request, response, code, true);
//						}
//					}
//				} else {
//					return false;
//				}
//			} else {
//				long expiresEndTime = auth.getExpiresEndTime();
//				if (expiresEndTime > System.currentTimeMillis()) {
//					WeixinOauth2Token weixinOauth2Token = AdvancedUtil.refreshOauth2AccessToken(auth.getAppId(), auth.getRefreshToken());
//					// 网页授权接口访问凭证
//					String accessToken = weixinOauth2Token.getAccessToken();
//					// 用户刷新access_token
//					String refreshToken = weixinOauth2Token.getRefreshToken();
//					// 超时截止时间
//					expiresEndTime = System.currentTimeMillis() + weixinOauth2Token.getExpiresIn() * 1000;
//
//					auth.setAtoken(accessToken);
//					auth.setExpiresEndTime(expiresEndTime);
//					auth.setRefreshToken(refreshToken);
//					this.session.setAttribute(request, response, SessionValidFilter.WEIXIN_KEY, auth);
//
//					UserPlatform userPlatform = new UserPlatform();
//					userPlatform.setId(auth.getPlatformId());
//					userPlatform.setUserPlatformId(weixinOauth2Token.getOpenId());
//					userPlatform.setAccessToken(weixinOauth2Token.getAccessToken());
//					userPlatform.setExpiresEndTime(expiresEndTime);
//					userPlatform.setRefreshToken(weixinOauth2Token.getRefreshToken());
//					userPlatform.setUpdateDate(DateUtil.getFullTime());
//
//					this.weixinService.updateUserPlatform(userPlatform);
//				}
//			}
//		} catch (Exception ex) {
//			logger.error(ex);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * OAuth2鉴权操作
//	 *
//	 * @param code
//	 * @param needOAuthUserinfo
//	 * @return 0：认证成功；1：用户被锁死；2：用户已被删除
//	 * @throws Exception
//	 */
//	private String OAuth2(HttpServletRequest request, HttpServletResponse response, String code, boolean needOAuthUserinfo) throws Exception {
//		String rtnResult = "0"; // 0：认证成功；1：用户被锁死；2：用户已被删除
//		String openId = "";
//		Auth auth = null;
//
//		if ("T@e……s$t2[-1~]9".equals(code)) { // 模拟测试用
//
//			openId = "oIHjSsni13zeXX4TRZy2YvSk-QOQ";
//			Map<String, Object> userAccount = this.weixinService.selectUserByOpenId(openId, "1");
//			auth = new Auth();
//			auth.setPlatformId(userAccount.get("platformId").toString());
//			auth.setAppId(this.appId);
//			auth.setOpenId(openId);
//			auth.setAtoken(userAccount.get("accessToken").toString());
//			auth.setExpiresEndTime(Long.valueOf(userAccount.get("expiresEndTime").toString()));
//			auth.setRefreshToken(userAccount.get("refreshToken").toString());
//			auth.setUser((FEUser) userAccount.get("user"));
//
//		} else if (!"authdeny".equals(code)) { // 用户同意授权
//			// 获取网页授权access_token
//			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(this.appId, this.appSecret, code);
//			if (weixinOauth2Token != null) {
//				// 用户标识
//				openId = weixinOauth2Token.getOpenId();
//
//				// 网页授权接口访问凭证
//				String accessToken = weixinOauth2Token.getAccessToken();
//				// 用户刷新access_token
//				String refreshToken = weixinOauth2Token.getRefreshToken();
//				// 超时截止时间
//				long expiresEndTime = System.currentTimeMillis() + weixinOauth2Token.getExpiresIn() * 1000;
//
//				if (StringUtils.isNotEmpty(openId)) {
//					Map<String, Object> userAccount = this.weixinService.selectUserByOpenId(openId, "1");
//					// 如果用户记录不存在，则新增用户记录
//					if (null == userAccount) {
//
//						// 同步用户信息
//						FEUser user = new FEUser();
//						String userId = UUIdUtil.getUUID();
//						String currentDateTime = DateUtil.getFullTime(); // 当前时间
//						user.setId(userId);
//						user.setIsLocked("0");
//						user.setCreateDate(currentDateTime);
//						user.setUpdateDate(currentDateTime);
//						user.setDeleteFlag("0");
//						SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
//						if (null != snsUserInfo) {
//							user.setUserName(snsUserInfo.getNickname());
//							user.setUserGender(snsUserInfo.getSex());
//							user.setUserPicUrl(snsUserInfo.getHeadImgUrl());
//						}
//
//						// 同步微信用户详细数据
//						String platformId = UUIdUtil.getUUID();
//						UserPlatform userPlatform = new UserPlatform();
//						userPlatform.setId(platformId);
//						userPlatform.setUserPlatformId(openId);
//						userPlatform.setPlatformFlag("1");
//						userPlatform.setUserId(userId);
//						userPlatform.setAccessToken(accessToken);
//						userPlatform.setExpiresEndTime(expiresEndTime);
//						userPlatform.setRefreshToken(weixinOauth2Token.getRefreshToken());
//						userPlatform.setCreateDate(currentDateTime);
//						userPlatform.setUpdateDate(currentDateTime);
//						userPlatform.setDeleteFlag("0");
//
//						this.weixinService.insertUserInfo(user, userPlatform);
//
//						// session数据
//						auth = new Auth();
//						auth.setPlatformId(platformId);
//						auth.setAppId(this.appId);
//						auth.setOpenId(openId);
//						auth.setAtoken(accessToken);
//						auth.setExpiresEndTime(expiresEndTime);
//						auth.setRefreshToken(refreshToken);
//						auth.setUser(user);
//
//					} else {
//						FEUser user = (FEUser) userAccount.get("user");
//						if ("1".equals(user.getIsLocked())) {
//							rtnResult = "1"; // 用户被锁死
//						} else if ("1".equals(user.getDeleteFlag())) {
//							rtnResult = "2"; // 用户已被删除
//						} else {
//							UserPlatform userPlatform = new UserPlatform();
//							userPlatform.setId(userAccount.get("platformId").toString());
//							userPlatform.setAccessToken(accessToken);
//							userPlatform.setExpiresEndTime(expiresEndTime);
//							userPlatform.setRefreshToken(weixinOauth2Token.getRefreshToken());
//							userPlatform.setUpdateDate(DateUtil.getFullTime());
//							this.weixinService.updateUserPlatform(userPlatform);
//
//							// session数据
//							auth = new Auth();
//							auth.setPlatformId(userPlatform.getId());
//							auth.setAppId(this.appId);
//							auth.setOpenId(openId);
//							auth.setAtoken(accessToken);
//							auth.setExpiresEndTime(expiresEndTime);
//							auth.setRefreshToken(refreshToken);
//							auth.setUser(user);
//						}
//					}
//				}
//			}
//		}
//
//		// 保存session
//		this.session.setAttribute(request, response, SessionValidFilter.WEIXIN_KEY, auth);
//
//		return rtnResult;
//	}
//
//	public String getAppId() {
//		return appId;
//	}
//
//	public void setAppId(String defaltAppId) {
//		this.appId = defaltAppId;
//	}
//
//	public String getAppSecret() {
//		return appSecret;
//	}
//
//	public void setAppSecret(String defaltAppSecret) {
//		this.appSecret = defaltAppSecret;
//	}
//
//	/**
//	 * 获取商户平台ID和密钥
//	 *
//	 * @return true：成功；false：失败
//	 */
//	private boolean setPlatformAccountInfo() {
//		boolean bRtn = false;
//		PlatformAccount platformAccount = this.weixinService.selectPlatformAccount("1");
//		if (platformAccount != null) {
//			this.appId = platformAccount.getAppId();
//			this.appSecret = platformAccount.getAppSecret();
//			bRtn = true;
//		}
//		return bRtn;
//	}
//
//}