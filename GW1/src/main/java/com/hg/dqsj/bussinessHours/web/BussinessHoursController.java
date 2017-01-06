package com.hg.dqsj.bussinessHours.web;

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
@RequestMapping(value = "bussinessHours")
public class BussinessHoursController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "openGarden", method = RequestMethod.GET)
	public String openGarden(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "openGarden");
		return "base/info-detail";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "equipment", method = RequestMethod.GET)
	public String equipment(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "equipment");
		return "base/info-detail";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "performance", method = RequestMethod.GET)
	public String transportation(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "performance");
		return "base/info-detail";
	}
	
}
