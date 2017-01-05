package com.hg.dqsj.goods.annualCard.web;

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
 * 年卡
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "annualCard")
public class AnnualCardController {
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 年卡列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("goodsName", "年卡");
		model.put("typeCode", "2");
		return "fegoods/list";
	}

	/**
	 * 年卡广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ad", method = RequestMethod.GET)
	public String ad(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("annualCard");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}
}
