package com.hg.dqsj.guide.trafficDirection.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

import java.util.Map;

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
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String enter(HttpServletRequest request, ModelMap model) {
		VFEInfo transportation = infoService.selectVSingleByTypeCode("transportation");
		VFEInfo contactWay = infoService.selectVSingleByTypeCode("contactWay");
		model.put("transportation", transportation);
		model.put("contactWay", contactWay);
		return "guide/trafficDirection/map";
	}

	@RequestMapping(value = "transportation")
	public String transportation(HttpServletRequest request, ModelMap model)  {
		VFEInfo transportation = infoService.selectVSingleByTypeCode("transportation");
		model.put("detail", transportation);
		return "guide/trafficDirection/detailsmap";
	}

}
