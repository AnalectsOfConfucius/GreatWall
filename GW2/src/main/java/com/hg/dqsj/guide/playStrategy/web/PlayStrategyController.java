package com.hg.dqsj.guide.playStrategy.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.base.service.FEAdService;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEAd;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 
 * @author 卢俊星
 *
 */
@Controller
@RequestMapping(value = "playStrategy")
public class PlayStrategyController {

	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String enter(HttpServletRequest request, ModelMap model) {
		List<VFEAd> ads = feAdService.selectByTypeCode("playStrategy");
		model.put("ads", ads);
		return "guide/playStrategy/play-raiders";
	}
	
	/**
	 * 详细信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			VFEInfo detail = infoService.selectVById(id);
			model.put("detail", detail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "guide/playStrategy/play-raiders-details";
	}

}
