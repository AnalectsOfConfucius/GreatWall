package com.hg.dqsj.park.tenWorlds.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

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
	private SessionProvider session;
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 十大世界列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		model.put("infoMapType", "2");
		model.put("typeCode", "tenWorlds");
		return "base/info-list";
	}

	/**
	 * 十大世界广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ad", method = RequestMethod.GET)
	public String ad(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("tenWorlds");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}
}
