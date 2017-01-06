package com.hg.dqsj.index.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.base.service.FeMenuService;
import com.hg.dqsj.base.service.FEUserService;
import com.hg.dqsj.base.view.VFeMenuInfo;

/**
 * 首页管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "index")
public class IndexController {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FeMenuService feMenuService; // 菜单管理SERVICE接口类
	@Autowired
	private FEUserService fEUserService; // 微端用户SERVICE接口类

	/**
	 * 首页页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 用户登录保存所属权限信息
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		if (auth == null) {
			auth = new Auth();
		}
		auth.setUserId("b81d3059-78cf-4641-b28c-b6aa497b6dc4");
		auth.setUser(fEUserService.selectById(auth.getUserId()));
		session.setAttribute(request, response, SessionValidFilter.AUTH_KEY, auth);
		return "index";
	}

	/**
	 * 加载菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "queryMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMenu(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			List<VFeMenuInfo> details = feMenuService.selectParentMenu();
			for (VFeMenuInfo menu : details) {
				menu.setChildren(feMenuService.selectChildMenu(menu.getId()));
			}
			result.put("details", details);
			result.put("isError", "0");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
}
