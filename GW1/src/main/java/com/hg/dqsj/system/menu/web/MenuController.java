package com.hg.dqsj.system.menu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.entity.MenuInfo;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * 功能：菜单管理Controller类
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping(value = "menu")
public class MenuController {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@Autowired
	private MenuService menuService;// 菜单SERVICE接口

	/**
	 * 显示菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "system/menu/list";
	}

	/**
	 * 显示菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/childMenuPage", method = RequestMethod.GET)
	public String childMenuPage(HttpServletRequest request, ModelMap model) {

		try {
			String parentId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuId")); // 菜单id
			model.put("parentId", parentId);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return "common/error/500";
		}

		return "system/menu/childList";
	}

	/**
	 * 显示父菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/parentMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> parentMenu(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			String menuName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuName")); // 菜单名称
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "1";
			}
			Map<String, String> param = new HashMap<>();
			param.put("menuName", menuName);
			List<Menu> list = menuService.selectParentMenu(param);
			if (null != list && list.size() > 0) {

				int totalCount = list.size();
				Pager p = new Pager();
				p.setCurrentPage(Integer.parseInt(currentPageNo));
				p.setTotalCount(totalCount);
				p.setItems(list);

				@SuppressWarnings("unchecked")
				List<Menu> blist = (List<Menu>) p.doPagination();

				result.put("currentPage", p.getCurrentPage());
				result.put("totalCnt", totalCount);
				result.put("rows", blist);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询菜单信息失败！");
		}

		return result;
	}

	/**
	 * 显示子菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/childMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> childMenu(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			String menuName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuName")); // 菜单名称
			String parentId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "parentId")); // 父id
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "1";
			}
			Map<String, String> param = new HashMap<>();
			param.put("menuName", menuName);
			param.put("parentId", parentId);
			List<MenuInfo> list = menuService.selectChildMenu(param);
			if (null != list && list.size() > 0) {

				int totalCount = list.size();
				Pager p = new Pager();
				p.setCurrentPage(Integer.parseInt(currentPageNo));
				p.setTotalCount(totalCount);
				p.setItems(list);

				@SuppressWarnings("unchecked")
				List<Menu> blist = (List<Menu>) p.doPagination();

				result.put("currentPage", p.getCurrentPage());
				result.put("totalCnt", totalCount);
				result.put("rows", blist);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询菜单信息失败！");
		}

		return result;
	}

	/**
	 * 请求新增菜单信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String menuName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuName")); // 菜单名称
			String parentId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "parentId")); // 菜单父ID
			String menuUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuUrl")); // 菜单URL
			String picUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "picUrl")); // 图片URL
			String menuCss = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuCss")); // 菜单CSS样式
			String menuOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!UUIdUtil.matchUUIDOrBlank(parentId, false)) {
				result.put("msg", "非法请求！");
			} else if (!StringUtil.isChs(menuName, true)) {
				result.put("msg", "请输入纯中文的菜单名称");
			}
			// else if (!StringUtil.isUrl(menuUrl, false)) {
			// result.put("msg", "请输入正确的菜单URL");
			// }
			else if (!StringUtil.isUrl(picUrl, false)) {
				result.put("msg", "请输入正确的图片URL");
			}
			// else if (!StringUtil.isEnAndBlank(menuCss, false)) {
			// result.put("msg", "请输入正确的菜单CSS样式");
			// }
			else if (!NumberUtil.isInt(menuOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Menu menu = new Menu();
				String strCurrentDateTime = DateUtil.getFullTime();

				menu.setId(UUIdUtil.getUUID());
				menu.setMenuName(menuName);
				menu.setParentId(parentId);
				menu.setMenuUrl(menuUrl);
				menu.setPicUrl(picUrl);
				menu.setMenuCss(menuCss);
				menu.setMenuOrder(NumberUtil.strToInt(menuOrder));
				menu.setRemark(remark);
				menu.setCreateDate(strCurrentDateTime);
				menu.setCreateUserId(auth.getUserId());
				menu.setUpdateDate(strCurrentDateTime);
				menu.setUpdateUserId(auth.getUserId());
				menu.setDeleteFlag("0");

				this.menuService.insertMenu(menu);

				result.put("isError", "0");
				result.put("msg", "新建菜单信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "新建菜单信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求编辑菜单信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String menuId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuId")); // 菜单ID
			String menuName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuName")); // 菜单名称
			String parentId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "parentId")); // 菜单父ID
			String menuUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuUrl")); // 菜单URL
			String picUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "picUrl")); // 图片URL
			String menuCss = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuCss")); // 菜单CSS样式
			String menuOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!UUIdUtil.matchUUIDOrBlank(menuId, true)) {
				result.put("msg", "非法请求！");
			} else if (!UUIdUtil.matchUUIDOrBlank(parentId, false)) {
				result.put("msg", "非法请求！");
			} else if (!StringUtil.isChs(menuName, true)) {
				result.put("msg", "请输入纯中文的菜单名称");
			}
			// else if (!StringUtil.isUrl(menuUrl, false)) {
			// result.put("msg", "请输入正确的菜单URL");
			// }
			else if (!StringUtil.isUrl(picUrl, false)) {
				result.put("msg", "请输入正确的图片URL");
			}
			// else if (!StringUtil.isEnAndBlank(menuCss, false)) {
			// result.put("msg", "请输入正确的菜单CSS样式");
			// }
			else if (!NumberUtil.isInt(menuOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Menu menu = new Menu();

				menu.setId(menuId);
				menu.setMenuName(menuName);
				menu.setParentId(parentId);
				menu.setMenuUrl(menuUrl);
				menu.setPicUrl(picUrl);
				menu.setMenuCss(menuCss);
				menu.setMenuOrder(NumberUtil.strToInt(menuOrder));
				menu.setRemark(remark);
				menu.setUpdateDate(DateUtil.getFullTime());
				menu.setUpdateUserId(auth.getUserId());

				this.menuService.updateMenu(menu);

				result.put("isError", "0");
				result.put("msg", "修改菜单信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "修改菜单信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求逻辑删除菜单信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String menuId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "menuId")); // 菜单ID

			if (!UUIdUtil.matchUUIDOrBlank(menuId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);

				this.menuService.deleteMenu(menuId, auth.getUserId());

				result.put("isError", "0");
				result.put("msg", "删除菜单信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "删除菜单信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

}
