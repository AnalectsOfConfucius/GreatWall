package com.hg.dqsj.guide.promotions.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 优惠活动管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "promotions")
public class PromotionsController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 优惠活动列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String parkSummary(HttpServletRequest request, ModelMap model) {
		return "guide/promotions/promotions";
	}

	/**
	 * 详细信息页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
		if (UUIdUtil.matchUUID(id)) {
			VFEInfo detail = infoService.selectVById(id);
			model.put("detail", detail);
		}
		return "guide/promotions/promotions-detail";
	}

}
