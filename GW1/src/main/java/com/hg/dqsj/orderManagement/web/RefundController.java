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
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.pay.entity.req.RefundRequest;
import com.hg.core.weixin.service.WeixinService;
import com.hg.core.weixin.util.RandomString;
import com.hg.core.weixin.util.WXPayUtil;
import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.entity.OfOrderGoods;
import com.hg.dqsj.orderManagement.entity.OfRefund;
import com.hg.dqsj.orderManagement.entity.OfRefundGoods;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.service.OfOrderService;
import com.hg.dqsj.orderManagement.service.OfRefundGoodsService;
import com.hg.dqsj.orderManagement.service.OfRefundService;
import com.hg.dqsj.orderManagement.view.VOfOrder;
import com.hg.dqsj.orderManagement.view.VOfRefund;
import com.hg.dqsj.orderManagement.view.VOfRefundGoods;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

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
	private OfOrderGoodsService ofOrderGoodsService; // 订单商品SERVICE接口
	@Autowired
	private OfRefundService ofRefundService; // 退款SERVICE接口
	@Autowired
	private OfRefundGoodsService ofRefundGoodsService; // 退款商品SERVICE接口
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE
	@Autowired
	private WeixinService weixinService; // 微信平台SERVICE接口

	/**
	 * 订单管理页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "orderManagement/refund";
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
			if (menuService.isHasOperator(request, "search")) {
				result = ofRefundService.selectByCriterias(request); // 查询结果
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
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
			if (menuService.isHasOperator(request, "view")) {
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "非法请求，获得ID错误！");
				} else {
					// 处理逻辑
					VOfRefund detail = ofRefundService.selectVById(id);
					result.put("detail", detail);
					result.put("isError", "0");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查看权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请联系管理员！");
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
				String refundId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundId"));
				if (!UUIdUtil.matchUUIDOrBlank(refundId, true)) {
					result.put("msg", "非法请求，获得ID错误！");
				} else {
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
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查看权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}

	/**
	 * 拒绝退款
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "refuseRefund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmGetFlag(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			if (menuService.isHasOperator(request, "update")) {
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
					if (failReason != null) {
						ofRefund.setFailReason(failReason);
					} else {
						ofRefund.setFailReason("");
					}

					if (ofRefundService.updateGetFlag(ofRefund) == 1) {
						result.put("isError", "0"); // 是否报错[0:否][1:是]
					} else {
						result.put("isError", "1"); // 是否报错[0:否][1:是]
						result.put("msg", "操作失败，请联系管理员！");
					}
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请联系管理员！");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 同意退款处理
	 * 
	 * @param model
	 * @param session
	 * @param refund
	 * @param itemId
	 * @param orderId
	 * @return
	 */

	@RequestMapping(value = "agreeRefund", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> submitRefundOrder(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "0"); 
		result.put("msg", "退款成功!");
		if (menuService.isHasOperator(request, "update")) {
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String refundId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundId"));
			String refundFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "refundFlag"));
			Integer rowLockNum = NumberUtil.strToInt(StringUtil.trimBlank(RequestUtils.getQueryParam(request, "rowLockNum")));
			OfRefund ofRefund = ofRefundService.selectVById(refundId);
			VOfOrder order = ofOrderService.selectVById(ofRefund.getOrderId());
			String platformFlag = "1";
			boolean isTrue = true;
			PlatformAccount platformAccount = weixinService.selectPlatformAccount(platformFlag);

			if (ofRefund != null && order != null) {
				if (ofRefund.getRefundFlag() == "2") {// 商家同意退款 、退款状态不为1(1为退款成功)
					isTrue = false;
					result.put("isError", "1"); // 是否报错[0:否][1:是]
					result.put("msg", "该退款已被同意！");
					return result;
				}
				if (ofRefund.getRefundFlag() == "1") {
					isTrue = false;
					result.put("isError", "1"); // 是否报错[0:否][1:是]
					result.put("msg", "该退款已被拒绝！");
					return result;
				}
			} else {
				isTrue = false;
				result.put("isError", "1"); // 是否报错[0:否][1:是]
				result.put("msg", "操作失败，请稍后再试！");
				return result;
			}

			// 订单不为空
			if (isTrue) {

				RefundRequest refundRequest = new RefundRequest();
				refundRequest.setAppid(platformAccount.getAppId());

				int totalFee = (int) (order.getOrderPrice() * 100);
				int refundFee = (int) (ofRefund.getRefundTotalAmount() * 100);

				// 待修改 微信支付分配的商户号
				refundRequest.setMch_id(platformAccount.getMchId());
				refundRequest.setOp_user_id(platformAccount.getMchId());
				refundRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
				refundRequest.setOut_refund_no(ofRefund.getRefundNo()); // 商户退款单号
				// refundRequest.setTransaction_id(refund.getOrder().getTransactionId());//
				// 微信订单号
				refundRequest.setOut_trade_no(order.getOrderNo());// 商户订单号
				refundRequest.setTotal_fee(totalFee);
				refundRequest.setRefund_fee(refundFee);
				System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>商户退款单号:" + ofRefund.getRefundNo());
				// 统一支付签名
				String sign = WXPayUtil.doSign(refundRequest, platformAccount.getPaySecret());
				refundRequest.setSign(sign);
				// 转换xml格式
				String requestXML = WXPayUtil.refundRequestToXml(refundRequest);
				System.out.println(">>>>>>>>>>>>requestXML:" + requestXML);

				if (null != requestXML && !requestXML.equals("")) {
					String responseXML = WXPayUtil.HttpsSecureRequest4XML(WXPayUtil.REFUND_URL, WXPayUtil.CALIB_PATH, platformAccount.getMchId(), requestXML);
					if (responseXML != null && responseXML.contains("SUCCESS")) {

						System.out.println(responseXML);

						// RefundResponse refundResponse =
						// WXPayUtil.xmlToRefundResponse(responseXML);
                  try{
						ofRefund.setRowLockNum(rowLockNum);
						ofRefund.setUpdateDate(DateUtil.getFullTime());
						ofRefund.setAccountDate(DateUtil.getFullTime());
						ofRefund.setAccountFlag("1");
						ofRefund.setUpdateUserId(auth.getUserId());
						ofRefund.setRefundFlag(refundFlag);

						if (ofRefundService.updateGetFlag(ofRefund) != 1) {
							Integer rowLockNum2 = ofRefund.getRowLockNum();
							ofRefund.setRowLockNum(rowLockNum2);
							ofRefundService.updateGetFlag(ofRefund);
						}
						/**
						 * 更新订单商品的已退款商品数量
						 */
						List<OfOrderGoods> orderGoods = ofOrderGoodsService.selectByOrderId(ofRefund.getOrderId());
						List<OfRefundGoods> refundGoods = ofRefundGoodsService.selectByRefundId(refundId);
						if (null!=orderGoods && null!= refundGoods) {
							if (orderGoods.size()>0 && refundGoods.size()>0) {
								for(OfRefundGoods rg : refundGoods ){
									for(OfOrderGoods og : orderGoods ){
										if (rg.getGoodsId().equals(og.getGoodsId())) {
											String updateUserId = auth.getUserId();
											String updateDate = DateUtil.getFullTime();
											Integer refundGoodsNum = rg.getGoodsNum();
											Integer oldRefundGoodsNum = og.getRefundGoodsNum();
											og.setRefundGoodsNum(refundGoodsNum+oldRefundGoodsNum);
											og.setUpdateDate(updateDate);
											og.setUpdateUserId(updateUserId);
											ofOrderGoodsService.uptRefundGoodsNum(og);
										}
									}
								}
							}
						}
						 result.put("isError", "0"); // 是否报错[0:否][1:是]
						 result.put("msg", "退款成功!");
						/**
						 * 退款成功，发送消息给用户
						 */
						// String openId =
						// refund.getOrder().getMember().getMemberWeiXin().getOpenId();
						// sendTemple4User(openId, "您好，您的退款已成功。", "民生大千世界",
						// order.getOrderNo(),
						// refund.getWxRefundId(), refund.getOutRefundNo(),
						// refund.getRefundFee() + "", "感谢你的使用!",
						// "http://www.hnlvbaodao.com/goorder?status=6");

						/**
						 * 发送消息给商家
						 */
						// sendTemple4Admin("退款成功通知",
						// refund.getOrder().getCode(),
						// refund.getRefundFee() + "", "已成功退款!", "#");
                  } catch (Exception e) {
                	  result.put("isError", "1"); // 是否报错[0:否][1:是]
					  result.put("msg", "异常错误,请联系管理员!");
        		}
					} else {
						result.put("isError", "1"); // 是否报错[0:否][1:是]
						result.put("msg", "退款失败,请检查是否金额不足!");
					}
				}
			}
		} else {
			result.put("isError", "1");
			result.put("msg", "你未拥有当前菜单的编辑权限！");
		}
		return result;
	}
}
