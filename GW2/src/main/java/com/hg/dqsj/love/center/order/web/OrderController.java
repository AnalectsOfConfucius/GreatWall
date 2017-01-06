package com.hg.dqsj.love.center.order.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.json.JSONObject;

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
import com.hg.core.weixin.entity.OrderPay;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.pay.entity.req.OrderQueryRequest;
import com.hg.core.weixin.pay.entity.req.UnifiedOrderRequest;
import com.hg.core.weixin.pay.entity.resp.OrderQueryResponse;
import com.hg.core.weixin.pay.entity.resp.UnifiedOrderResponse;
import com.hg.core.weixin.service.WeixinService;
import com.hg.core.weixin.util.RandomString;
import com.hg.core.weixin.util.WXPayUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.service.OfOrderService;
import com.hg.dqsj.love.center.order.view.VOfOrder;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;

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
	private WeixinService weixinService; // 平台商户信息SERVICE接口

	/**
	 * 订单列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String order(HttpServletRequest request, ModelMap model) {
		return "love/center/order/order";
	}

	/**
	 * 加载详细信息列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loadDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadDetails(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			result = ofOrderService.selectByCriterias(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请重试！");
		}
		return result;
	}

	/**
	 * 详细信息页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
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
				detail.setOrderQRCode(ofOrders.get(0).getOrderQRCode()); // 消费码
				detail.setOrderTime(ofOrders.get(0).getOrderTime()); // 下单时间
				detail.setPayFlag(ofOrders.get(0).getPayFlag()); // 支付状态标识
				detail.setGetFlag(ofOrders.get(0).getGetFlag()); // 领取状态标识
				detail.setEvalFlag(ofOrders.get(0).getEvalFlag()); // 评价状态标识
				detail.setRowLockNum(ofOrders.get(0).getRowLockNum()); // 行锁号
				detail.setLinkUserPhone(ofOrders.get(0).getLinkUserPhone()); // 联系人手机号
				detail.setDeductCredit(StringUtil.isNullorEmpty(ofOrders.get(0).getDeductCredit()) ? "" : ofOrders.get(0).getDeductCredit().replace(".00", "")); // 扣除积分
				detail.setIsWorthMoney(ofOrders.get(0).getIsWorthMoney()); // 抵消金额
				detail.setPayTime(ofOrders.get(0).getPayTime()); // 付款时间

				if (detail.getPayFlag().equals("0")) {
					detail.setOrderState("等待付款");
					if ("2".equals(detail.getOrderTypeCode())) {
						detail.setOrderState("到店支付");
					}
				} else if (detail.getGetFlag().equals("0")) {
					detail.setOrderState("等待领取");
				} else if (detail.getEvalFlag().equals("0")) {
					detail.setOrderState("等待评价");
				} else {
					detail.setOrderState("已完成");
				}

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

			model.put("detail", detail);
			model.put("goodsDetails", goodsDetails);
		}
		return "love/center/order/order-detail";
	}

	/**
	 * 确认领取
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateGetFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGetFlag(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			// 用户登录保存所属权限信息
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			if (auth == null || StringUtil.isNullorEmpty(auth.getUserId())) {
				result.put("isError", "1");
				result.put("msg", "操作失败，请重试！");
				return result;
			}
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			if (UUIdUtil.matchUUID(id)) {
				OfOrder ofOrder = ofOrderService.selectById(id);
				if (ofOrder != null && "0".equals(ofOrder.getGetFlag())) {
					ofOrder.setGetFlag("1");
					ofOrder.setUpdateUserId(auth.getUserId());
					ofOrder.setUpdateDate(DateUtil.getFullTime());
					if (ofOrderService.updateGetFlag(ofOrder)) {
						result.put("isError", "0");
						result.put("msg", "确认领取成功！");
					} else {
						result.put("isError", "1");
						result.put("msg", "确认领取失败，请刷新重试！");
					}
				} else {
					result.put("isError", "1");
					result.put("msg", "确认领取失败，请刷新重试！");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "操作失败，请重试！");
			}
		} catch (Exception e) {
			result.put("isError", "1");
			result.put("msg", "操作失败，请重试！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 订单支付
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pay", method = RequestMethod.POST)
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
				resultMap.put("msg", "您还未登陆，请先登陆再购买！");
			} else {
				String openId = auth.getOpenId();
				OfOrder order = this.ofOrderService.selectById(orderId); // 订单信息
				// 循环遍历选中的购物车产品
				if (order == null) {
					resultMap.put("msg", "该订单不存在!");
				} else if ("1".equals(order.getDeleteFlag())) {
					resultMap.put("msg", "该订单已被删除，请重新下单!");
				} else if (DateUtil.strToDate(order.getInvalidEndTime(), "yyyy-MM-dd HH:mm:ss").before(new Date())) {
					resultMap.put("msg", "该订单已失效，请重新下单!");
				} else if ("1".equals(order.getPayFlag())) {
					resultMap.put("msg", "该订单已支付!");
				} else {
					/* ------执行购买操作------- */

					// 获取微信商户信息
					PlatformAccount platformAccount = this.weixinService.selectPlatformAccount("1");
					if (platformAccount == null || StringUtil.isNullorEmpty(platformAccount.getPaySecret())) {
						resultMap.put("msg", "商家未开通微信支付!");
					} else {

						/**
						 * 获取商品名称
						 */
						List<VOfOrder> goodsInfos = ofOrderService.selectOrderDetailsById(orderId); // 订单信息
						String productName = "";
						boolean isContinue = true;
						Double dTotalNum = 0.0; // 商品总量
						for (int i = 0; i < goodsInfos.size(); i++) {
							if (isContinue) {
								if (i >= 2) {
									productName = productName + "等";
									isContinue = false;
								}
								if (i == 0) {
									productName = goodsInfos.get(i).getGoodsName();
								} else {
									productName = productName + "、" + goodsInfos.get(i).getGoodsName();
								}
								if ((i == 0 && goodsInfos.size() == 1) || i == 1 && goodsInfos.size() == 2) {
									isContinue = false;
								}
							}
							dTotalNum = NumberUtil.add(dTotalNum, goodsInfos.get(i).getGoodsNum());
						}
						productName = productName + dTotalNum.intValue() + "件商品";

						// 商品总金额
						int totalPrice = Integer.valueOf(String.valueOf((order.getOrderPrice() * 100)));

						String currentTime = DateUtil.getFullTime();
						OrderPay orderPay = new OrderPay();
						orderPay.setId(UUIdUtil.getUUID());
						orderPay.setOrderId(orderId);
						orderPay.setPlatformId(platformAccount.getId());
						orderPay.setReqOrNotifyFlag("1");
						orderPay.setCreateDate(currentTime);
						orderPay.setCreateUserId(auth.getUser().getId());
						orderPay.setUpdateDate(currentTime);
						orderPay.setUpdateUserId(auth.getUser().getId());
						orderPay.setDeleteFlag("0");

						// 调用微信统一下单接口
						Map<String, String> parameters = this.WXUnifiedOrder("JSAPI", platformAccount.getAppId(), platformAccount.getMchId(), platformAccount.getPaySecret(), openId, orderId,
								productName, order.getOrderNo(), totalPrice, "", orderPay);
						if (null == parameters) {
							resultMap.put("msg", "微信支付统一下单失败，请重试!");
						} else {
							// 保存统一下单接口
//							JSONObject JSAPIParameters = JSONObject.fromObject(parameters);

							resultMap.put("isError", "0");
							resultMap.put("msg", "预支付交易单生成成功，请支付!");
//							resultMap.put("JSAPIParameters", JSAPIParameters);

							// /**
							// *
							// 用户下单成功，调用接口给用户发送"您有一笔订单已经生成但尚未支付，请尽快到“我的订单”支付"订单提醒消息
							// * 标题：title
							// * 订单号码：keyword1
							// * 商品名称：keyword2
							// * 商品数量：keyword3
							// * 支付金额：keyword4
							// * 备注说明：remark
							// */
							// this.sendTemple4User(openId,
							// "您有一笔订单已经生成但尚未支付,请尽快到'我的订单'支付", order.getCode(),
							// productCount + "", order.getTotal() + "",
							// "订单8小时后自动取消，若有疑虑请拨打客服热线4008859059",
							// "http://www.frgohainan.com/goorder?status=0");

						}
					}

				}
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return resultMap;
	}

	/**
	 * 微信统一支付接口
	 * 
	 * @param tradeType
	 *            JSAPI
	 * @param appId
	 * @param mchId
	 * @param key
	 * @param openId
	 * @param orderId
	 * @param productName
	 * @param outTradeNo
	 * @param totalFee
	 * @return
	 */
	private Map<String, String> WXUnifiedOrder(String tradeType, String appId, String mchId, String key, String openId, String orderId, String productName, String outTradeNo, int totalFee,
			String sid, OrderPay orderPay) {
		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
		unifiedOrderRequest.setAppid(appId);
		unifiedOrderRequest.setAttach(">>>" + mchId + ">>>" + appId + ">>>" + orderId);
		// 注：请求报文不能带空格，否则签名验证不通过
		productName = productName.replaceAll(" ", "");
		unifiedOrderRequest.setBody((productName.length() > 50) ? productName.substring(0, 50) + "..." : productName);
		unifiedOrderRequest.setMch_id(mchId);
		unifiedOrderRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		unifiedOrderRequest.setNotify_url(WXPayUtil.notifyURL.replace("@sitePath", sid));
		unifiedOrderRequest.setOut_trade_no(outTradeNo.substring(0, 14));
		unifiedOrderRequest.setAttach(outTradeNo);
		try {
			unifiedOrderRequest.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		unifiedOrderRequest.setTotal_fee(totalFee);
		unifiedOrderRequest.setTrade_type(tradeType);
		unifiedOrderRequest.setProduct_id(orderId);
		unifiedOrderRequest.setOpenid(openId);
		// 统一支付签名
		String sign = WXPayUtil.doSign(unifiedOrderRequest, key);
		unifiedOrderRequest.setSign(sign);
		// 转换统一支付请求报文
		String requestXML = WXPayUtil.unifiedOrderRequestToXml(unifiedOrderRequest);
		logger.info(requestXML);

		orderPay.setId(UUIdUtil.getUUID());
		orderPay.setOrderId(orderId);
		orderPay.setReqOrNotifyUrl(WXPayUtil.UNIFIEDORDER_URL);
		orderPay.setReqOrNotifyParams(requestXML.getBytes());
		this.weixinService.insertOrderPayInfo(orderPay);

		// 提交统一支付接口请求
		String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.UNIFIEDORDER_URL, "POST", requestXML);
		logger.info(responseXML);
		orderPay.setBackParams(responseXML.getBytes());
		orderPay.setUpdateDate(DateUtil.getFullTime());
		this.weixinService.updateOrderPayInfo(orderPay);

		SortedMap<String, String> JSAPIParameters = null;
		if (!StringUtil.isNullorEmpty(responseXML)) {
			// 转换统一支付接口响应报文为对应的Java对象
			UnifiedOrderResponse unifiedOrderResponse = WXPayUtil.xmlToUnifiedOrderResponse(responseXML);
			if ((null != unifiedOrderResponse) && "SUCCESS".equals(unifiedOrderResponse.getReturn_code()) && "SUCCESS".equals(unifiedOrderResponse.getResult_code())) {
				/*
				 * 统一支付（下预支付订单）成功，根据返回的"返回预支付订单号"封装H5页面JSAPI提交需要的数据
				 */
				JSAPIParameters = new TreeMap<String, String>();
				// 公众号 id 是 String(16) 商户注册具有支付权限的公众号成功后即可获得
				JSAPIParameters.put("appId", appId);
				// 时间戳 是 String(32) 商户生成，从 1970 年 1 月 1日 00：00：00
				// 至今的秒数，即当前的时间，且最终需要转换为字符串形式；
				JSAPIParameters.put("timeStamp", (new Date()).getTime() + "");
				// 随机字符串 是 String(32) 商户生成的随机字符串；
				JSAPIParameters.put("nonceStr", new RandomString().getRandomString(32, "ilu"));
				// 订单详情扩展字符串 是 String(128) 统 一 支 付 接 口 返 回 的prepay_id 参数值，
				// 提交格式如：prepay_id=***
				JSAPIParameters.put("package", "prepay_id=" + unifiedOrderResponse.getPrepay_id());
				// 签名方式 是 String(32) 按照文档中所示填入 MD5；
				JSAPIParameters.put("signType", "MD5");
				// 签名 是 String(64) 签名方式与其他接口中 sign的生成方式一致， 详见第 3.2节
				JSAPIParameters.put("paySign", WXPayUtil.doSign(JSAPIParameters, key));
				// 因为package是js的关键字，需要重新赋值packages参数名
				JSAPIParameters.put("packages", "prepay_id=" + unifiedOrderResponse.getPrepay_id());
				// 订单号
				JSAPIParameters.put("outTradeNo", outTradeNo);
			}
		}
		return JSAPIParameters;
	}

	/**
	 * 订单结果查询
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPayResult(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>(); // 返回结果集
		resultMap.put("isError", "1"); // [1:失败]
		resultMap.put("msg", "系统异常，请稍后在我的订单查看支付是否成功！");

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
				resultMap.put("msg", "系统异常，请稍后在我的订单查看支付是否成功！");
			} else {
				OfOrder order = this.ofOrderService.selectById(orderId); // 订单信息
				// 循环遍历选中的购物车产品
				if (order == null) {
					resultMap.put("msg", "该订单不存在!");
				} else if ("1".equals(order.getPayFlag())) {
					resultMap.put("msg", "该订单支付成功!");
				} else {
					/* ------执行订单查询，确认是否已支付成功------- */

					// 获取微信商户信息
					PlatformAccount platformAccount = this.weixinService.selectPlatformAccount("1");
					if (platformAccount == null || StringUtil.isNullorEmpty(platformAccount.getPaySecret())) {
						resultMap.put("msg", "系统异常，请稍后在我的订单查看支付是否成功！");
					} else {

						String a_appId = platformAccount.getAppId();
						String a_mchId = platformAccount.getMchId();
						String paySecret = platformAccount.getPaySecret();

						// 调用微信查询订单接口
						OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
						orderQueryRequest.setAppid(a_appId); // 公众账号ID
						orderQueryRequest.setMch_id(a_mchId); // 商户号
						// orderQueryRequest.setTransaction_id(transaction_id);
						// // 微信订单号（与商户订单号二选一，优先使用微信订单号）
						orderQueryRequest.setOut_trade_no(order.getOrderNo()); // 商户订单号
						orderQueryRequest.setNonce_str(new RandomString().getRandomString(32, "ilu")); // 随机字符串
						orderQueryRequest.setSign(WXPayUtil.doSign(orderQueryRequest, paySecret)); // 签名

						// 转换查询订单请求报文
						String requestXML = WXPayUtil.orderQueryRequestToXml(orderQueryRequest);
						logger.info(requestXML);

						// 提交查询订单接口请求
						String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.ORDERQUERY_URL, "POST", requestXML);
						logger.info(responseXML);

						if (!StringUtil.isNullorEmpty(responseXML)) {
							// 转换查询订单接口响应报文为对应的Java对象
							OrderQueryResponse orderQueryResponse = WXPayUtil.xmlToOrderQueryResponse(responseXML);
							if ((null != orderQueryResponse) && "SUCCESS".equals(orderQueryResponse.getReturn_code()) && "SUCCESS".equals(orderQueryResponse.getResult_code())) {

								String appId = orderQueryResponse.getAppid();
								String mchId = orderQueryResponse.getMch_id();

								// 根据公众号-appId、商户号-mchId获取商户支付密钥key
								String sign = null;
								if ((!StringUtil.isNullorEmpty(a_appId)) && a_appId.equals(appId) && (!StringUtil.isNullorEmpty(a_mchId)) && a_mchId.equals(mchId)) {
									// 获取报文签名
									sign = WXPayUtil.doSign(orderQueryResponse, paySecret);
								}

								if ((null != sign) && sign.equals(orderQueryResponse.getSign())) {
									// 支付成功
									if ("SUCCESS".equals(orderQueryResponse.getTrade_state())) {

										String currentTime = DateUtil.getFullTime();
										OrderPay orderPay = new OrderPay();
										orderPay.setId(UUIdUtil.getUUID());
										orderPay.setOrderId(order.getId());
										orderPay.setPlatformId(platformAccount.getId());
										orderPay.setReqOrNotifyFlag("2");
										orderPay.setReqOrNotifyUrl(WXPayUtil.ORDERQUERY_URL);
										orderPay.setReqOrNotifyParams(responseXML.getBytes());
										orderPay.setNotifyFlag("0");
										orderPay.setCreateDate(currentTime);
										orderPay.setUpdateDate(currentTime);
										orderPay.setDeleteFlag("0");

										order.setPayFlag("1"); // 已支付
										order.setUpdateDate(currentTime);

										this.ofOrderService.updatePayFlag(order, orderPay);

										// =======================================
										// 用户支付成功后送积分
										// =======================================

										resultMap.put("isError", "0");
										resultMap.put("msg", "订单支付成功，请按照取餐时间去取餐！");

									} else {
										resultMap.put("msg", "支付失败，请重试！");
									}
								} else {
									resultMap.put("msg", "签名验证失败");
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return resultMap;
	}
}
