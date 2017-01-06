package com.hg.dqsj.guide.park.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

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
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 园区概括详情页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String parkSummary(HttpServletRequest request, ModelMap model) {
		VFEInfo detail = infoService.selectVSingleByTypeCode("parkSummary");
		model.put("detail", detail);
		return "guide/park/park-parkSummary";
	}
}
