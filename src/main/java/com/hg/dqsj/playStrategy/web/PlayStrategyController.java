package com.hg.dqsj.playStrategy.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

/**
 * 
 * @author 卢俊星
 *
 */
@Controller
@RequestMapping(value = "playStrategy")
public class PlayStrategyController {
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "2");
		model.put("typeCode", "playStrategy");
		return "base/info-list";
	}

	/**
	 * 游玩攻略广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ad", method = RequestMethod.GET)
	public String ad(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("playStrategy");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}
}
