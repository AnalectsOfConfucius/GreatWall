package com.hg.dqsj.orderManagement.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 酒店预订Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "hotelReservation")
public class HotelReservationController {
	/**
	 * 酒店预订页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "orderManagement/hotelReservation";
	}
}
