package com.hg.dqsj.love.center.credit.web;

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
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.base.service.FEUserService;
import com.hg.dqsj.base.view.VFEUser;
import com.hg.dqsj.love.center.credit.entity.CreditUser;
import com.hg.dqsj.love.center.credit.service.CreditService;

/**
 * 个人中心-我的积分Controller类
 * 
 * @author joe
 *
 */
@Controller
@RequestMapping(value = "credit")
public class CreditController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private CreditService creditService; // 积分SERVICE接口类

	/**
	 *  个人中心-我的积分
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String credit(HttpServletRequest request, ModelMap model) {
		// 用户登录保存所属权限信息
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		String userId = auth.getUserId();
		if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
			Map<String,String> param = new HashMap<String,String>();
			param.put("userId", userId);
			CreditUser cuser = creditService.selectCreditUser(param);
			model.put("cuser", cuser);
			model.put("userId", userId);
		}
		return "center/credit/myCredit";
	}
	/**
	 *  积分规则
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "rules", method = RequestMethod.GET)
	public String creditRules(HttpServletRequest request, ModelMap model) {
		// 用户登录保存所属权限信息
		try{
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		String userId = auth.getUserId();
		if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
			Map<String,String> param = new HashMap<String,String>();
			param.put("userId", userId);
			CreditUser cuser = creditService.selectCreditUser(param);
			String rules = new String(cuser.getCreditRule(),"UTF-8");
			model.put("rules", rules);
		}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "center/credit/creditRules";
	}
	/**
	 * 加载获得/消费积分明细
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "creditGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadDetails(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		String type = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "type")); // 当前页数
		try{	
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String userId = auth.getUserId();
			if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
				Map<String,String> param = new HashMap<String,String>();
				param.put("userId", userId);
				if (type.equals("0")) {
					result = creditService.selectCreditGet(request); // 获得积分明细
				}else if (type.equals("1")) {
					result = creditService.selectCreditUse(request); // 消费积分明细
				}else{
					result = creditService.selectCreditGet(request); // 获得积分明细
				}
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	
}
