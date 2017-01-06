package com.hg.dqsj.system.feUser.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.feUser.entity.FeUser;
import com.hg.dqsj.system.feUser.service.FeUserService;

/**
 * 会员用户管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "feuser")
public class FeUserController extends BaseController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@Autowired
	private FeUserService userService;

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {

		return "system/user/list";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName")); // 用户名称
			String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); // 用户手机号码
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
			if (StringUtil.isNullorEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "1";
			}
			int curno = Integer.parseInt(currentPageNo);
			int pz = Integer.parseInt(pageSize);
			int startRowNo = pz * (curno - 1);

			if (!StringUtil.isChsEnNum(userName, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的用户名！");
			} else if (!NumberUtil.isLong(userPhone, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由数字组成的手机号码！");
			} else if (!NumberUtil.isInt(currentPageNo, false)) {
				result.put("isError", "1");
				result.put("msg", "非法请求！");
			} else {

				Map<String, String> m = new HashMap<>();
				m.put("startRowNo", String.valueOf(startRowNo));
				m.put("endRowNo", pageSize);

				m.put("userName", userName);
				m.put("userPhone", userPhone);

				int totalCount = userService.getUserCount(m);
				List<FeUser> lsUser = userService.selectFeUser(m);

				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", lsUser);

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询用户信息失败！");
		}

		return result;
	}
	/**
	 * 请求修改后管用户信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID
			 String isLocked = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isLocked")); // 是否激活

			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			FeUser user = new FeUser();
			
			user.setId(userId);
			user.setIsLocked(isLocked);
			user.setUpdateDate(DateUtil.getFullTime());
			user.setUpdateUserId(auth.getUserId());
			
			result.put("isError", "0");
			String msg = "";
			if("0".equals(isLocked)){
				msg = "用户账号激活成功";
			}else if("1".equals(isLocked)){
				msg = "用户账号锁死成功";
			}
			result.put("msg", msg);
		} catch (Exception ex) {
			result.put("msg", "系统错误，操作失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求逻辑删除用户信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID

			if (!UUIdUtil.matchUUIDOrBlank(userId, true)) {
				result.put("msg", "非法请求！");
			} else {
				//Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Map<String,String> param = new HashMap<String, String>();
				param.put("id", userId);
				this.userService.deleteFeUser(param);

				result.put("isError", "0");
				result.put("msg", "删除用户信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，删除用户信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}
	/**
	 * 会员用户，锁定、激活
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "setLocked", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setLocked(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		
		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID
			String isLocked = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isLocked")); // 0-激活，1-锁定
			
			if (!UUIdUtil.matchUUIDOrBlank(userId, true)) {
				result.put("msg", "非法请求！");
			}else if(!"0".equals(isLocked) && !"1".equals(isLocked)){
				result.put("msg", "非法请求，是否激活标志获得错误");
			}else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String fdate = DateUtil.getFullTime();
				
				FeUser user = new FeUser();
				user.setId(userId);
				user.setIsLocked(isLocked);
				user.setUpdateUserId(auth.getUserId());
				user.setUpdateDate(fdate);
				userService.updateFeUser(user);
				
				String fname = "";
				if("0".equals(isLocked)){
					fname = "用户激活成功";
				}else if("1".equals(isLocked)){
					fname = "用户锁死成功";
				}
				result.put("isError", "0");
				result.put("msg", fname);
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，请联系管理员");
            logger.error(ex.getMessage());
		}
		return result;
	}
	
}
