package com.hg.dqsj.visitor.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;

/**
 * 
 * @author 卢俊星
 *
 */
@Controller
@RequestMapping(value = "visitor")
public class VisitorController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "visitor");
		return "base/info-detail";
	}
}
