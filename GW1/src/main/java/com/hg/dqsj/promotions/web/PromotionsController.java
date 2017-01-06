package com.hg.dqsj.promotions.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 优惠活动管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "promotions")
public class PromotionsController {
	/**
	 * 优惠活动列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "2");
		model.put("typeCode", "promotions");
		return "base/info-promotions";
	}
}
