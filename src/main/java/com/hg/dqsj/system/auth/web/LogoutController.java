package com.hg.dqsj.system.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hg.core.session.SessionProvider;

/**
 * 退出处理类
 *
 */
@Controller
public class LogoutController {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().invalidate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "redirect:/login";
	}

}
