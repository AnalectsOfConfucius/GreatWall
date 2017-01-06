package com.hg.dqsj.love.center.refund.web;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.service.OfOrderService;
import com.hg.dqsj.love.center.order.view.VOfOrder;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;
import com.hg.dqsj.love.center.refund.entity.OfRefund;
import com.hg.dqsj.love.center.refund.entity.OfRefundGoods;
import com.hg.dqsj.love.center.refund.service.OfRefundGoodsService;
import com.hg.dqsj.love.center.refund.service.OfRefundService;
import com.hg.dqsj.love.center.refund.view.VOfRefundGoods;


/**
 * 退款Controller类
 * 
 * @author joe
 *
 */
@Controller
@RequestMapping(value = "refund")
public class RefundController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private OfOrderService ofOrderService; // 订单SERVICE接口
	@Autowired
	private OfRefundService ofRefundService; // 退款SERVICE接口
	@Autowired
	private OfRefundGoodsService ofRefundGoodsService; // 退款商品SERVICE接口

	/**
	 * 退款列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		String userId = auth.getUserId();
		if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
			model.put("userId", userId);
		}
		return "love/center/refund/refundList";
	}
	/**
	 * 详细信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {			
			result = ofRefundService.selectByCriterias(request); // 查询结果
			if (result.get("oneVo")!=null) {
				model.put("refund", result.get("oneVo"));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "love/center/refund/refundDetail";
	}
/**
 * 进入申请退款页面
 * 
 * @param request
 * @param model
 * @return
 */
@RequestMapping(value = "applyPage", method = RequestMethod.GET)
public String applyPage(HttpServletRequest request, ModelMap model) {
	String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
	if (UUIdUtil.matchUUID(id)) {
		List<VOfOrder> ofOrders = ofOrderService.selectOrderDetailsById(id); // 订单信息
		List<VOfOrderGoods> goodsDetails = new ArrayList<>(); // 订单商品信息
		VOfOrder detail = new VOfOrder();
		if (ofOrders != null && ofOrders.size() > 0) {
			detail.setId(ofOrders.get(0).getId()); // 订单ID
			detail.setOrderNo(ofOrders.get(0).getOrderNo()); // 订单号
			detail.setOrderTypeCode(ofOrders.get(0).getOrderTypeCode()); // 订单类型代码[1:门票][2:酒店][3:年卡][4:商品][5:美食]
			detail.setOrderPrice(ofOrders.get(0).getOrderPrice()); // 订单总金额

			/**
			 * 订单详情
			 */
			// 门票
			if ("1".equals(detail.getOrderTypeCode())) {
				detail.setUseDate(ofOrders.get(0).getUseDate()); // 使用时间（入园时间）
			}
			// 酒店
			else if ("2".equals(detail.getOrderTypeCode())) {
				detail.setLinkUserName(ofOrders.get(0).getLinkUserName()); // 联系人名称
				detail.setCheckInDate(ofOrders.get(0).getCheckInDate()); // 入住日期
				detail.setCheckOutDate(ofOrders.get(0).getCheckOutDate()); // 离开日期
			}

			/**
			 * 订单商品
			 */
			for (VOfOrder ofOrder : ofOrders) {
				VOfOrderGoods goodsInfo = new VOfOrderGoods();
				goodsInfo.setGoodsNum(ofOrder.getGoodsNum()); // 商品数量
				goodsInfo.setGoodsPrice(ofOrder.getGoodsPrice()); // 商品价格
				goodsInfo.setRefundGoodsNum(ofOrder.getRefundGoodsNum());
				// 酒店
				if ("2".equals(detail.getOrderTypeCode())) {
					goodsInfo.setGoodsId(ofOrder.getGuestRoomId()); // 客房ID
					goodsInfo.setGoodsName(ofOrder.getGuestRoomName()); // 客房名称
				} else {
					goodsInfo.setGoodsId(ofOrder.getGoodsId()); // 商品ID
					goodsInfo.setGoodsName(ofOrder.getGoodsName()); // 商品名称
				}
				goodsDetails.add(goodsInfo);
			}
		}
		model.put("orderId", id);
		model.put("detail", detail);
		model.put("goodsDetails", goodsDetails);
	}
	return "love/center/refund/comfirmRefund";
}
/**
 *确认退款
 * 
 * @param request
 * @param response
 * @param model
 * @return
 */
@RequestMapping(value = "doRefund", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> payOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
	Map<String, Object> resultMap = new HashMap<String, Object>(); // 返回结果集
	resultMap.put("isError", "1"); // [1:失败]
	resultMap.put("msg", "系统异常，请重试！");
	
	try {
		String orderId = RequestUtils.getQueryParam(request, "orderId"); // 订单ID
		
		if (!UUIdUtil.matchUUIDOrBlank(orderId, true)) {
			return resultMap;
		}

		/**
		 * 登录校验
		 */
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.WEIXIN_KEY);
		if (auth == null) {
			resultMap.put("msg", "您还未登陆，请先登陆！");
		} else {
			String goods = RequestUtils.getQueryParam(request, "goods");
			String sum = RequestUtils.getQueryParam(request, "sum");
			List<OfRefundGoods> refundGoods = JSON.parseArray(goods, OfRefundGoods.class);
			OfOrder order = ofOrderService.selectById(orderId); // 订单信息
			// 循环遍历选中的购物车产品
			if (order == null) {
				resultMap.put("msg", "该订单不存在!");
			} else if ("1".equals(order.getDeleteFlag())) {
				resultMap.put("msg", "该订单已被删除!");
			}else {	
				String uuid = UUIdUtil.getUUID();
				String createUserId = auth.getUserId();
				String createDate = DateUtil.getFullTime();
				String updateUserId = auth.getUserId();
				String updateDate = DateUtil.getFullTime();
				String deleteFlag = "0";
				

				// 获取退款号
				String refundNo = ofRefundService.getRefundNo();
				if (StringUtil.isNullorEmpty(refundNo)) {
					resultMap.put("msg", "操作失败，请重试！");
					return resultMap;
				}
				
				OfRefund refund = new OfRefund();
				refund.setId(uuid);
				refund.setRefundNo(refundNo);
				refund.setOrderId(orderId);
				refund.setRefundTotalAmount(Double.valueOf(sum));
				refund.setRefundFlag("0");
	            refund.setUserId(createUserId);
	            refund.setRefundDate(createDate);
	            refund.setConfirmDate(createDate);
	            refund.setRowLockNum(1);
	            refund.setAccountFlag("0");
	            refund.setCreateDate(createDate);
	            refund.setCreateUserId(createUserId);
	            refund.setUpdateDate(updateDate);
	            refund.setUpdateUserId(updateUserId);
	            refund.setDeleteFlag(deleteFlag);
	            ofRefundService.saveRefund(refund);
	            for(OfRefundGoods good :refundGoods){
	            	if (good.getGoodsNum()>0) {
	            		String uuuid = UUIdUtil.getUUID();
	            		good.setId(uuuid);
	            		good.setRefundId(refund.getId());
	            		good.setCreateDate(createDate);
	            		good.setCreateUserId(createUserId);
	            		good.setUpdateDate(updateDate);
	            		good.setUpdateUserId(updateUserId);
	            		good.setDeleteFlag(deleteFlag);
	            		ofRefundGoodsService.saveRefundGoods(good);
					}
	            }
	            resultMap.put("isError", "0"); 
	        	resultMap.put("msg", "申请退款成功！");   
		}
		}
	} catch (Exception e) {
		logger.error(e);
	}

	return resultMap;
}
	/**
	 * 加载退款列表
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
			result = ofRefundService.selectByCriterias(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "网络错误，请稍后再试！");
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
			String refundId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundId"));
			List<VOfRefundGoods> details = null;
			OfRefund ofRefund = ofRefundService.selectVById(refundId);
			OfOrder ofOrder = ofOrderService.selectVById(ofRefund.getOrderId());
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
					details = ofRefundGoodsService.selectGuestRoomByRefundId(refundId);
				} else {
					details = ofRefundGoodsService.selectGoodsByRefundId(refundId);
				}
				result.put("details", details);
				result.put("goodsType", goodsType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}

	/**
	 * 退款选择
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateRefundFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmGetFlag(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String refundId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundId"));
			String refundFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundFlag"));
			String failReason = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "failReason"));
			Integer rowLockNum = NumberUtil.strToInt(StringUtil.trimBlank(RequestUtils.getQueryParam(request, "rowLockNum")));
			OfRefund ofRefund = ofRefundService.selectVById(refundId);	
			if (ofRefund != null) {
				ofRefund.setRowLockNum(rowLockNum);
				ofRefund.setUpdateDate(DateUtil.getFullTime());
				ofRefund.setUpdateUserId(auth.getUserId());
				ofRefund.setRefundFlag(refundFlag);
				if (failReason!=null) {
					ofRefund.setFailReason(failReason);
				}else{
					ofRefund.setFailReason("");
				}
				
				if (ofRefundService.updateGetFlag(ofRefund) == 1) {
					result.put("isError", "0"); // 是否报错[0:否][1:是]
				} else {
					result.put("isError", "1"); // 是否报错[0:否][1:是]
					result.put("msg", "操作失败，请联系管理员！");
				}
			} else {
				result.put("isError", "1"); // 是否报错[0:否][1:是]
				result.put("msg", "操作失败，请联系管理员！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请联系管理员！");
			logger.error(e.getMessage());
		}
		return result;
	}
}
