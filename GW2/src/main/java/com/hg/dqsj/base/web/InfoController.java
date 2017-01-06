package com.hg.dqsj.base.web;

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

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.base.service.InfoService;

/**
 * 资讯信息管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "info")
public class InfoController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 加载详细信息列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loadDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadDetails(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			result = infoService.selectByCriterias(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
}
