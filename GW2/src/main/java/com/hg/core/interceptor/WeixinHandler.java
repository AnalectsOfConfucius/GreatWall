package com.hg.core.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * OAuth2拦截器完成微信授权登陆
 * </p>
 *
 * @author: wangdr
 * @date: 2014-10-27上午11:19:05
 */
public class WeixinHandler extends HandlerInterceptorAdapter {
	//
	// private String defaltAppId = "";
	// private String defaltAppSecret = "";
	//
	// @Resource
	// private SiteConfigService siteService;
	// @Resource
	// private WeixinAccountService weixinAccountService;
	// @Resource
	// private MemberService memberService;
	// @Resource
	// private CartService cartService;
	//
	// private static final Logger LOG = Logger.getLogger(WeixinHandler.class);
	//
	// @Override
	// public boolean preHandle(HttpServletRequest request, HttpServletResponse
	// response, Object handler) throws Exception {
	// System.out.println(">>>>>>OAuth2Interceptor-start-----------------------------------<<<");
	//
	// String request_url = new StringBuffer(
	// (StringUtils.isNotEmpty(request.getRequestURL())) ?
	// request.getRequestURL() : "").toString();
	// String query_string = new StringBuffer(
	// (StringUtils.isNotEmpty(request.getQueryString())) ? ("?" +
	// request.getQueryString()) : "").toString();
	//
	// // 获取当前请求URL
	// String redirect_uri = new
	// StringBuffer().append(request_url).append(query_string).toString();
	// System.out.println(">>>requestURL: " + redirect_uri);
	// System.out.println(">>>requestURL: " + query_string);
	// System.out.println(">>>requestURL: " + request_url);
	//
	// /*
	// * ======获取站点信息，拦截所有方法======
	// */
	// HttpSession session = request.getSession();
	//
	// SiteConfig siteConfig = (SiteConfig) session.getAttribute("site");
	// if (null == siteConfig) {
	//
	// System.out.println(">>>>>>session.getAttribute('site') is null...");
	// siteConfig = siteService.getSiteConfig((long) 1);
	// session.setAttribute("site", siteConfig);
	// System.out.println(">>>>>>session.setAttribute('site')");
	// }
	// session.setAttribute("site", siteConfig);
	// if (null != siteConfig) {
	// SiteTemplet siteTemplet = siteConfig.getSiteTemplet();
	// if (null != siteTemplet) {
	// session.setAttribute("template", siteTemplet.getPath());
	// }
	// }
	//
	// session.setAttribute("site", siteConfig);
	//
	// /*session.setAttribute("member", memberService.getMember(1L));
	// session.setAttribute("openId", "1");
	// */
	// System.out.println(session.getAttribute("member"));
	// if (session.getAttribute("member") == null) {
	// System.out.println("member==null");
	// String code = request.getParameter("code");
	// if (code == null) {
	// System.out
	// .println(">>>>>>>>>>跳转到微信授权页面: StringUtils.isNotEmpty(openId) && needOAuthUserinfo && (null == snsUserInfo)");
	// String scope = "snsapi_userinfo";
	// String oauth2URL =
	// "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	//
	// oauth2URL = oauth2URL.replace("APP_ID",
	// getDefaltAppId()).replace("REDIRECT_URI", redirect_uri)
	// .replace("SCOPE", scope).replace("STATE", "1");
	// System.out.println(oauth2URL);
	// response.sendRedirect(oauth2URL);
	// return false;
	// } else {
	// if (redirect_uri.contains("doSign")) {
	// System.out.println("获取token");
	// WeixinOauth2Token weixinOauth2Token =
	// AdvancedUtil.getOauth2AccessToken(getDefaltAppId(),
	// getDefaltAppSecret(), code);
	// session.setAttribute("atoken", weixinOauth2Token.getAccessToken());
	// System.out.println("token:" + weixinOauth2Token.getAccessToken());
	// } else {
	// OAuth2(code, siteConfig, session, true);
	// }
	// }
	// }
	//
	// System.out.println("member!=null");
	// return true;
	// }
	//
	// /**
	// * OAuth2鉴权操作
	// *
	// * @param request
	// * @param siteConfig
	// * @param session
	// * @param needOAuthUserinfo
	// * @return
	// */
	// private String OAuth2(String code, SiteConfig siteConfig, HttpSession
	// session, boolean needOAuthUserinfo) {
	// System.out.println(">>>>>>-start-Oauth2鉴权");
	//
	// session.setAttribute("code", code);
	// String openId = "";
	// User user = null;
	//
	// // 用户同意授权
	// if (StringUtils.isNotEmpty(code)) {
	// WeixinAccount weixinAccount = (WeixinAccount)
	// session.getAttribute("weixinAccount");
	// System.out.println(">>>>>>(WeixinAccount) session.getAttribute(weixinAccount)_AccountName: "
	// + ((null != weixinAccount) ? weixinAccount.getAccountName() : ""));
	// if (null == weixinAccount) {
	// user = siteConfig.getUser();
	// if (null != user) {
	// weixinAccount = weixinAccountService.getAccountByUserId(user.getId());
	// session.setAttribute("weixinAccount", weixinAccount);
	// }
	// }
	//
	// String appId = defaltAppId;
	// String appSecret = defaltAppSecret;
	// if ((null != weixinAccount) &&
	// StringUtils.isNotEmpty(weixinAccount.getAccountAppId())
	// && StringUtils.isNotEmpty(weixinAccount.getAccountAppSecret())) {
	// appId = weixinAccount.getAccountAppId();
	// appSecret = weixinAccount.getAccountAppSecret();
	// System.out.println(">>>>>>(null != weixinAccount) && !userDefaltAppId #appId:"
	// + appId + " appSecret: "
	// + appSecret);
	// }
	// System.out.println(">>>>>>appId:" + appId + " appSecret: " + appSecret);
	//
	// if (StringUtils.isNotEmpty(code) && "T@e……s$t2[-1~]9".equals(code)) { //
	// 模拟测试用
	// openId = "oIHjSsni13zeXX4TRZy2YvSk-QOQ";
	// session.setAttribute("openId", openId);
	// Member member = memberService.getByOpenId(openId);
	// session.setAttribute("member", member);
	// } else if (StringUtils.isNotEmpty(code) && !"authdeny".equals(code)) { //
	// 用户同意授权
	// // 获取网页授权access_token
	// WeixinOauth2Token weixinOauth2Token =
	// AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
	// if (weixinOauth2Token != null) {
	// // 用户标识
	// openId = weixinOauth2Token.getOpenId();
	// // 设置要传递的参数
	// session.setAttribute("openId", openId);
	//
	// // 网页授权接口访问凭证
	// String accessToken = weixinOauth2Token.getAccessToken();
	// session.setAttribute("accessToken", accessToken);
	//
	// System.out.println(">>>>>>session.setAttribute('openId'): " + openId
	// + " \n>>>>>>session.setAttribute('accessToken'): " + accessToken);
	//
	// if (StringUtils.isNotEmpty(openId)) {
	// Member member = memberService.getByOpenId(openId);
	// /* 如果会员记录不存在，则新增会员记录（并） */
	// if (null == member) {
	// member = new Member();
	// member.setStatus(true);
	// member.setRegisterTime(new Timestamp(System.currentTimeMillis()));
	// // 自动绑定微信账号
	// MemberWeiXin memberWeiXin = new MemberWeiXin();
	// memberWeiXin.setMember(member);
	// memberWeiXin.setAppId(appId);
	// memberWeiXin.setOpenId(openId);
	// member.setMemberWeiXin(memberWeiXin);
	// }
	//
	// member.getMemberWeiXin().setAccessToken(accessToken);
	// member.getMemberWeiXin().setRefreshToken(weixinOauth2Token.getRefreshToken());
	// member.getMemberWeiXin().setTokenValidTime(
	// new Timestamp(System.currentTimeMillis() +
	// weixinOauth2Token.getExpiresIn()));
	//
	// memberService.saveMember(member);
	//
	// session.setAttribute("member", member);
	// session.setAttribute("cart",
	// cartService.getCartByMemberId(member.getId()));
	// /**
	// * ??? 啥时候同步微信用户详细数据比较合理
	// */
	// if (needOAuthUserinfo) {
	// // 获取用户信息
	// SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,
	// openId);
	// if (null != snsUserInfo) {
	// // 设置要传递的参数
	// session.setAttribute("snsUserInfo", snsUserInfo);
	// System.out.println(">>>>>>session.setAttribute('snsUserInfo') _ nickname: "
	// + snsUserInfo.getNickname());
	//
	// MemberWeiXin memberWeiXin = member.getMemberWeiXin();
	// memberWeiXin.setAppId(appId);
	// memberWeiXin.setOpenId(openId);
	// memberWeiXin.setAccessToken(accessToken);
	// memberWeiXin.setRefreshToken(weixinOauth2Token.getRefreshToken());
	// memberWeiXin.setTokenValidTime(new Timestamp(System.currentTimeMillis()
	// + weixinOauth2Token.getExpiresIn()));
	//
	// memberWeiXin.setNickName(snsUserInfo.getNickname());
	// memberWeiXin.setSex(snsUserInfo.getSex() + "");
	// memberWeiXin.setCity(snsUserInfo.getCity());
	// memberWeiXin.setCountry(snsUserInfo.getCountry());
	// memberWeiXin.setProvince(snsUserInfo.getProvince());
	// memberWeiXin.setHeadImgUrl(snsUserInfo.getHeadImgUrl());
	//
	// member.setMemberWeiXin(memberWeiXin);
	//
	// memberService.saveMember(member);
	// }
	// } else {
	// session.setAttribute("snsUserInfo", null);
	// }
	// }
	//
	// } else {
	// session.setAttribute("openId", null);
	// session.setAttribute("snsUserInfo", null);
	// }
	// }
	// }
	// System.out.println("<<<<<<-end-Oauth2鉴权");
	// return openId;
	// }
	//
	// public String getDefaltAppId() {
	// return defaltAppId;
	// }
	//
	// public void setDefaltAppId(String defaltAppId) {
	// this.defaltAppId = defaltAppId;
	// }
	//
	// public String getDefaltAppSecret() {
	// return defaltAppSecret;
	// }
	//
	// public void setDefaltAppSecret(String defaltAppSecret) {
	// this.defaltAppSecret = defaltAppSecret;
	// }

}