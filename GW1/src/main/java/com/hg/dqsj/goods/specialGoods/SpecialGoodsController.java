package com.hg.dqsj.goods.specialGoods;

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
 * 特色商品
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "specialGoods")
public class SpecialGoodsController {
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 特色商品列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("goodsName", "特色商品");
		model.put("typeCode", "1");
		return "fegoods/list";
	}

	/**
	 * 特色商品广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ad", method = RequestMethod.GET)
	public String ad(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("goods");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}
}
