package com.hg.dqsj.buy.ticketInfo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 
 * @author 卢俊星
 *
 */
@Controller
@RequestMapping(value = "ticketInfo")
public class TicketInfoController {

	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String enter(HttpServletRequest request, ModelMap model) {
		VFEInfo ticketInfo = infoService.selectVSingleByTypeCode("ticketInfo");
		model.put("detail", ticketInfo);
		return "buy/ticketInfo/ca-introduce";
	}

}
