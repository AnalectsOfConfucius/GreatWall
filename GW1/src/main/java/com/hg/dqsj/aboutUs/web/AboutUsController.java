package com.hg.dqsj.aboutUs.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;

/**
 * 关于我们Controller类
 * 
 * @author 卢俊星
 *
 */
@Controller
@RequestMapping(value = "aboutUs")
public class AboutUsController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 关于我们详情页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "aboutUs");
		return "base/info-detail";
	}
}
