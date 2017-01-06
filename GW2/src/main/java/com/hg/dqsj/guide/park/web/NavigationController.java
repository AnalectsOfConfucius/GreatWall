package com.hg.dqsj.guide.park.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 园区导览管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "navigation")
public class NavigationController {

	/**
	 * 园区导览详情页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String navigation(HttpServletRequest request, ModelMap model) {
		return "guide/park/park-navigation";
	}
	
	/**
	 * 园区导览地图页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "map", method = RequestMethod.GET)
	public String map(HttpServletRequest request, ModelMap model) {
		return "guide/park/park-navigation-map";
	}
}
