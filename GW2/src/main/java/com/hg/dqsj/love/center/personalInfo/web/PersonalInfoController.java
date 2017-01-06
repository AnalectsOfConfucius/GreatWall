package com.hg.dqsj.love.center.personalInfo.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.base.entity.FEUser;
import com.hg.dqsj.base.service.FEUserService;
import com.hg.dqsj.base.view.VFEUser;

/**
 * 个人信息管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "personalInfo")
public class PersonalInfoController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEUserService fEUserService; // 微端用户SERVICE接口类

	/**
	 * 个人信息页面
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
		return "love/center/personalInfo/personalInfo";
	}

	/**
	 * 更新用户信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone"));
			String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName"));
			String userPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPicUrl"));
			String userGender = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userGender"));
			String userBirthday = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userBirthday"));
			if (UUIdUtil.matchUUID(id)) {
				FEUser fEUser = fEUserService.selectById(id);
				if (fEUser != null) {
					result.put("isError", "1");
					if (!StringUtil.isNullorEmpty(userBirthday)) {
						fEUser.setUserBirthday(userBirthday);
					}
					if (!StringUtil.isNullorEmpty(userPhone)) {
						if (!StringUtil.isMobilePhone(userPhone, true)) {
							result.put("msg", "请输入正确的手机号码！");
							return result;
						} else {
							if (fEUserService.isExistUserPhone(id, userPhone)) {
								result.put("msg", "手机号码已存在！");
								return result;
							} else {
								fEUser.setUserPhone(userPhone);
							}
						}
					}
					if (!StringUtil.isNullorEmpty(userName)) {
						if (fEUserService.isExistUserName(id, userName)) {
							result.put("msg", "用户名已存在！");
							return result;
						}
						if (userName.length() > 16) {
							result.put("msg", "用户名长度不能大于16！");
							return result;
						} else {
							fEUser.setUserName(userName);
						}
					}
					if (!StringUtil.isNullorEmpty(userPicUrl)) {
						fEUser.setUserPicUrl(userPicUrl);
					}
					if (!StringUtil.isNullorEmpty(userGender)) {
						fEUser.setUserGender(userGender);
					}
					fEUserService.updateUser(fEUser);
					result.put("isError", "0");
				} else {
					result.put("isError", "1");
					result.put("msg", "系统错误，请重试！");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "系统错误，请重试！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请重试！");
		}
		return result;
	}
}
