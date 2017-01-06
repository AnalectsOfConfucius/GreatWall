package com.hg.dqsj.love.center.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.base.service.FEUserService;
import com.hg.dqsj.base.view.VFEUser;

/**
 * 个人中心管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "center")
public class CenterController {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEUserService fEUserService; // 微端用户SERVICE接口类

	/**
	 * 个人中心列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String center(HttpServletRequest request, ModelMap model) {
		// 用户登录保存所属权限信息
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
			VFEUser user = fEUserService.selectVById(auth.getUserId());
			model.put("user", user);
		}
		return "love/center/center";
	}
}
