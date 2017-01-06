package com.hg.dqsj.trafficDirection.web;

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
@RequestMapping(value = "trafficDirection")
public class TrafficDirectionController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "contactWay", method = RequestMethod.GET)
	public String contactWay(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "contactWay");
		return "base/info-detail";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "transportation", method = RequestMethod.GET)
	public String transportation(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "transportation");
		return "base/info-detail";
	}
}
