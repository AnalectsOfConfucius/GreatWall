package com.hg.dqsj.guide.park.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.service.FEAdService;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEAd;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 十大世界管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "tenWorlds")
public class TenWorldsController {
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口

	/**
	 * 十大世界列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String parkSummary(HttpServletRequest request, ModelMap model) {
		List<VFEAd> ads = feAdService.selectByTypeCode("tenWorlds");
		model.put("ads", ads);
		return "guide/park/park-tenWorlds";
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
		return "guide//park/park-tenWorlds-detail";
	}
}
