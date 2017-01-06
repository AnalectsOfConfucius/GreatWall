package com.hg.dqsj.orderManagement.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.service.OfOrderService;
import com.hg.dqsj.orderManagement.view.VOfOrder;
import com.hg.dqsj.orderManagement.view.VOfOrderGoods;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * 订单管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private OfOrderService ofOrderService; // 订单SERVICE接口
	@Autowired
	private OfOrderGoodsService ofOrderGoodsService; // 订单商品SERVICE接口
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE

	/**
	 * 订单管理页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "orderManagement/order";
	}

	/**
	 * 加载列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			/*if (menuService.isHasOperator(request, "search")) {

			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}*/
			result = ofOrderService.selectByCriterias(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请重试！");
		}
		return result;
	}

	/**
	 * 详细信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			if (UUIdUtil.matchUUIDOrBlank(id, true)) {
				VOfOrder detail = ofOrderService.selectVById(id);
				result.put("detail", detail);
			} else {
				result.put("isError", "1");
				result.put("msg", "非法请求，获得ID错误！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 加载商品详情列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "goodsQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goodsQuery(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			if (menuService.isHasOperator(request, "view")) {
				String orderId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderId"));
				if (UUIdUtil.matchUUIDOrBlank(orderId, true)) {
					List<VOfOrderGoods> details = null;
					OfOrder ofOrder = ofOrderService.selectVById(orderId);
					if (ofOrder != null) {
						String goodsType = null;

						if (ofOrder.getOrderTypeCode().equals("1")) {
							goodsType = "门票";
						} else if (ofOrder.getOrderTypeCode().equals("2")) {
							goodsType = "客房";
						} else if (ofOrder.getOrderTypeCode().equals("3")) {
							goodsType = "年卡";
						} else if (ofOrder.getOrderTypeCode().equals("4")) {
							goodsType = "特色商品";
						} else if (ofOrder.getOrderTypeCode().equals("5")) {
							goodsType = "美食";
						}

						if (ofOrder.getOrderTypeCode().equals("2")) {
							details = ofOrderGoodsService.selectGuestRoomByOrderId(orderId);
						} else {
							details = ofOrderGoodsService.selectGoodsByOrderId(orderId);
						}
						result.put("details", details);
						result.put("goodsType", goodsType);
					}
				} else {
					result.put("isError", "1");
					result.put("msg", "非法请求，获得ID错误！");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查看权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请重试！");
		}
		return result;
	}

	/**
	 * 确认领取
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "confirmGetFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmGetFlag(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			if (menuService.isHasOperator(request, "update")) {
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				if (UUIdUtil.matchUUIDOrBlank(id, true)) {
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					OfOrder ofOrder = ofOrderService.selectVById(id);
					if (ofOrder != null) {
						ofOrder.setUpdateDate(DateUtil.getFullTime());
						ofOrder.setUpdateUserId(auth.getUserId());
						ofOrder.setGetFlag("1");
						ofOrderService.updateGetFlag(ofOrder);
						result.put("isError", "0"); // 是否报错[0:否][1:是]
					} else {
						result.put("isError", "1"); // 是否报错[0:否][1:是]
						result.put("msg", "操作失败，请重试！");
					}
				} else {
					result.put("isError", "1");
					result.put("msg", "非法请求，获得ID错误！");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}
}
