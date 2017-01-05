package com.hg.dqsj.eval.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.eval.entity.Eval;
import com.hg.dqsj.eval.service.EvalService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * Created by Administrator on 2016/5/26.
 */
@Controller
@RequestMapping(value = "eval")
public class EvalController {

	@Autowired
	private EvalService evalService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE

	/**
	 * Uri Redirect
	 */
	@RequestMapping(value = "")
	public String list(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		return "eval/list";
	}

	/**
	 * selectList
	 * 
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectList")
	@ResponseBody
	public Map<String, Object> selectList(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			if (menuService.isHasOperator(request, "search")) {
				String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo"));
				String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize"));
				String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName"));

				if (!NumberUtil.isInt(currentPageNo, false)) {
					result.put("msg", "非法请求，请求页码非法");
				} else if (!NumberUtil.isInt(pageSize, false)) {
					result.put("msg", "非法请求，当页显示留言条数输入非法");
				} else {
					// 处理逻辑
					List<Eval> evalList = this.evalService.selectList(goodsName, currentPageNo, pageSize);
					if (evalList == null || evalList.size() <= 0) {
						result.put("list", evalList);
						result.put("currentPageNo", 0); // 当前页码
						result.put("totalCount", 0); // 总记录数
					} else {
						Integer totalCount = this.evalService.countEvalByCriterias(goodsName);
						result.put("list", evalList);
						result.put("currentPageNo", currentPageNo); // 当前页码
						result.put("totalCount", totalCount); // 总记录数
					}
					result.put("isError", "0");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			result.put("msg", "操作失败");
		}

		return result;

	}

	@RequestMapping(value = "selectById")
	@ResponseBody
	public Map<String, Object> selectEvalById(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try {
			if (menuService.isHasOperator(request, "view")) {
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "非法请求，输入非法");
				} else {
					// 处理逻辑
					Eval eval = this.evalService.selectById(id);
					result.put("detail", eval);
					result.put("isError", "0");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查看权限！");
			}
		} catch (Exception e) {
			result.put("isError", 1);
		}

		return result;

	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			if (menuService.isHasOperator(request, "delete")) {
				Auth auth = (Auth) sessionProvider.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "非法请求，id输入非法");
				} else {
					// 处理逻辑
					Eval eval = new Eval();
					eval.setId(id);
					eval.setUpdateDate(DateUtil.getFullTime());
					String userId = auth.getUserId();
					eval.setUpdateUserId(userId);
					this.evalService.delete(eval);
					result.put("isError", "0");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的删除权限！");
			}
		} catch (Exception e) {
			result.put("isError", 1);
		}
		return result;
	}

}
