package com.hg.dqsj.love.bussinessHours.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

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
		VFEInfo openGarden = infoService.selectVSingleByTypeCode("openGarden");
		model.put("detail", openGarden);
		return "love/bussinessHours/openGarden";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "equipment", method = RequestMethod.GET)
	public String equipment(HttpServletRequest request, ModelMap model) {
		VFEInfo equipment = infoService.selectVSingleByTypeCode("equipment");
		model.put("detail", equipment);
		return "love/bussinessHours/equipment";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "performance", method = RequestMethod.GET)
	public String performance(HttpServletRequest request, ModelMap model) {
		VFEInfo performance = infoService.selectVSingleByTypeCode("performance");
		model.put("detail", performance);
		return "love/bussinessHours/performance";
	}
	
	
}
