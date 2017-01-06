package com.hg.dqsj.park.parkSummary.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.base.service.InfoService;

/**
 * 园区概括管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "parkSummary")
public class ParkSummaryController {
	@Autowired
	private SessionProvider session;
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 园区概括详情页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "1");
		model.put("typeCode", "parkSummary");
		return "base/info-detail";
	}
}
