package com.hg.dqsj.goods.food.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.goods.store.entity.Store;
import com.hg.dqsj.goods.store.service.StoreService;

/**
 * 食品
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "food")
public class FoodController {

	@Autowired
	private StoreService storeService;
	/**
	 * 食品列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		Map<String, String> param = new HashMap<>();
		param.put("storeType", "1");
		List<Store> list = storeService.selectStore(param);
		
		model.put("slist", list);
		model.put("goodsName", "食品");
		model.put("typeCode", "3");
		return "fegoods/list";
	}
}
