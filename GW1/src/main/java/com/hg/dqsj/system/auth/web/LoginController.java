package com.hg.dqsj.system.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.MD5Util;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.message.service.MessageService;
import com.hg.dqsj.message.view.VMessage;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.auth.service.AuthService;
import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.user.entity.User;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成
 * 
 * @author 麦俊
 */
@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;
	@Autowired
	private AuthService authService;
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE
	@Autowired
	private MessageService messageService;

	/**
	 * 功能：进入系统主页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String main(HttpServletRequest request, ModelMap model) {
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		if (auth != null) {
			User user = auth.getUser();
			Map<String, Object> mAllLevelMenu = this.menuService.selectAllLevelMenu(user.getId(), user.getAdminFlag());
			List<Menu> topMenuList = (List<Menu>) mAllLevelMenu.get("topMenuList");
			Map<String, List<Menu>> mChildMenu = (Map<String, List<Menu>>) mAllLevelMenu.get("mChildMenu");
			List<VMessage> messages = messageService.selectFewMessageByCriterias();
			Integer msgCount = messageService.countNoReply();

			model.addAttribute("user", user);
			model.addAttribute("topMenuList", topMenuList);
			model.addAttribute("mChildMenu", mChildMenu);
			model.addAttribute("messages", messages);
			model.addAttribute("msgCount", msgCount > 99 ? "99+" : msgCount.toString());
			Map<String, Operate> map = auth.getMenuOperations();
			JSONObject opmap = new JSONObject();
			for (String id : map.keySet()) {
				JSONObject m = new JSONObject();
				m.put("search", map.get(id).isSearch());
				m.put("view", map.get(id).isView());
				m.put("add", map.get(id).isAdd());
				m.put("update", map.get(id).isUpdate());
				m.put("delete", map.get(id).isDelete());
				m.put("print", map.get(id).isPrint());
				m.put("imp", map.get(id).isImp());
				m.put("exp", map.get(id).isExp());
				m.put("audit", map.get(id).isAudit());
				opmap.put(id, m);
			}

			model.addAttribute("mMenuOp", String.valueOf(opmap));
			return "index";
		} else {
			return "system/auth/login";
		}
	}

	/**
	 * 功能：用户登录操作
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName"));// 登录账号
		String password = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "password"));// 登录密码

		try {

			if (!StringUtil.isChsEnNum(userName, true)) {
				result.put("msg", "请输入中文、英文、数字或下划线组成的用户名");
			} else if (StringUtil.isNullorEmpty(password)) {
				result.put("msg", "请输入密码");
			} else if (1 > userName.length() || 32 < userName.length() || 1 > password.length() || 32 < password.length()) {
				result.put("msg", "用户名或密码不正确");
			} else {
				User user = this.authService.selectUserByNameAndPw(userName, MD5Util.getMD5(password));
				if (user != null) {// 查询用户存在
					if (StringUtil.isNullorEmpty(user.getUserPicUrl())) {
						user.setUserPicUrl("/img/profile_small.jpg");
					}
					if ("1".equals(user.getIsLocked())) {
						result.put("msg", "登录失败！该用户已被管理员锁定，请联系管理员解除锁定");
					} else {
						Auth auth = new Auth();
						// 设置用户信息
						auth.setUserId(user.getId());
						auth.setWeixinOpenId("");
						auth.setUser(user);
						Map<String, Operate> mMenuOp = this.menuService.selectMenuOp(user.getId(), user.getAdminFlag());

						auth.setMenuOperations(mMenuOp);// 用户对应权限操作Map

						this.session.setAttribute(request, response, SessionValidFilter.AUTH_KEY, auth);
						return "redirect:login";
					}
				} else {
					result.put("msg", "登录失败！用户名不存在或密码错误");
				}
			}
			model.addAttribute("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "登录失败！系统错误，请与管理员联系");
		}

		model.addAttribute("result", result);
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		return "system/auth/login";
	}

	/**
	 * 功能：获取客户端真是IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		logger.debug("1- X-Forwarded-For ip=".concat(ip == null ? "" : ip));
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("2- Proxy-Client-IP ip=".concat(ip == null ? "" : ip));
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("3- WL-Proxy-Client-IP ip=".concat(ip == null ? "" : ip));
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.debug("4- HTTP_CLIENT_IP ip=".concat(ip == null ? "" : ip));
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.debug("5- HTTP_X_FORWARDED_FOR ip=".concat(ip == null ? "" : ip));
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("6- getRemoteAddr ip=".concat(ip == null ? "" : ip));
		}
		logger.debug("finally ip=".concat(ip == null ? "" : ip));
		return ip;
	}

	@RequestMapping(value = "sessiontimeout", method = RequestMethod.GET)
	public String sessionTimeout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "system/auth/sessiontimeout";
	}
}
