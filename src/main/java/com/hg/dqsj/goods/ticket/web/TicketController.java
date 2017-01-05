package com.hg.dqsj.goods.ticket.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 门票
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "ticket")
public class TicketController {
	/**
	 * 门票列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("goodsName", "门票");
		model.put("typeCode", "4");
		return "fegoods/list";
	}
}
