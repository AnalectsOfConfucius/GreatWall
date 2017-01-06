/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.hg.dqsj.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.util.DateUtil;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.core.weixin.entity.OrderPay;
import com.hg.core.weixin.entity.PlatformAccount;
import com.hg.core.weixin.pay.entity.notify.NotifyReceive;
import com.hg.core.weixin.pay.entity.notify.NotifyReply;
import com.hg.core.weixin.service.WeixinService;
import com.hg.core.weixin.util.WXPayUtil;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.service.OfOrderService;

/**
 * 微信支付订单支付结果同步控制器
 *
 */
@Controller
@RequestMapping(value = "/wxpayIface")
public class WXPayIfaceController {

	/**
	 * 当前类自己的logger
	 */
	private static Logger logger = Logger.getLogger(WXPayIfaceController.class);

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private OfOrderService ofOrderService; // 订单SERVICE接口

	/**
	 * 订单支付结果异步同步接口地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "notify", method = RequestMethod.POST)
	@ResponseBody
	public String notify(HttpServletRequest request, HttpSession session) {
		NotifyReply notifyReply = new NotifyReply();

		try {
			// 接收微信支付结果同步请求，转换为XML格式报文
			String notifyReceiveXML = WXPayUtil.parseXml(request);

			/*
			 * TODO: 接收微信支付结果同步请求报文 写入Log文件
			 */
			NotifyReceive notifyReceive = null;
			if (!StringUtil.isNullorEmpty(notifyReceiveXML)) {
				logger.info(notifyReceiveXML);
				// 转换微信支付结果同步报文为对应的Java对象
				notifyReceive = WXPayUtil.xmlToNotifyReceive(notifyReceiveXML);
				if ((null != notifyReceive)) {
					if ("SUCCESS".equals(notifyReceive.getReturn_code()) && "SUCCESS".equals(notifyReceive.getResult_code())) {
						String appId = notifyReceive.getAppid();
						String mchId = notifyReceive.getMch_id();

						// 获取微信公众号-appId、商户号-mchId
						PlatformAccount platformAccount = this.weixinService.selectPlatformAccount("1");
						if (platformAccount != null) {
							String a_appId = platformAccount.getAppId();
							String a_mchId = platformAccount.getMchId();
							String paySecret = platformAccount.getPaySecret();

							// 根据公众号-appId、商户号-mchId获取商户支付密钥key
							String sign = null;
							if ((!StringUtil.isNullorEmpty(a_appId)) && a_appId.equals(appId) && (!StringUtil.isNullorEmpty(a_mchId)) && a_mchId.equals(mchId)) {
								// 获取报文签名
								sign = WXPayUtil.doSign(notifyReceive, paySecret);
							}

							// 验证报文签名
							if ((null != sign) && sign.equals(notifyReceive.getSign())) {

								logger.info(">>>【签名验证成功】");

								OfOrder order = this.ofOrderService.selectByOrderNo(notifyReceive.getOut_trade_no()); // 订单信息
								if (order != null) {
									if ("0".equals(order.getPayFlag())) {
										// 接收微信支付结果同步请求报文 写入数据库
										String currentTime = DateUtil.getFullTime();
										OrderPay orderPay = new OrderPay();
										orderPay.setId(UUIdUtil.getUUID());
										orderPay.setOrderId(order.getId());
										orderPay.setPlatformId(platformAccount.getId());
										orderPay.setReqOrNotifyFlag("2");
										orderPay.setReqOrNotifyUrl(WXPayUtil.notifyURL);
										orderPay.setReqOrNotifyParams(notifyReceiveXML.getBytes());
										orderPay.setNotifyFlag("0");
										orderPay.setCreateDate(currentTime);
										orderPay.setUpdateDate(currentTime);
										orderPay.setDeleteFlag("0");

										order.setPayFlag("1");
										order.setUpdateDate(currentTime);
										this.ofOrderService.updatePayFlag(order, orderPay);

										notifyReply.setReturn_code("SUCCESS");
										notifyReply.setReturn_msg("OK");
									} else {
										logger.info(">>>【订单已支付成功】");
										notifyReply.setReturn_code("SUCCESS");
										notifyReply.setReturn_msg("OK");
									}
								} else {
									logger.info(">>>【根据订单号查询订单信息失败】");
									notifyReply.setReturn_code("FAIL");
									notifyReply.setReturn_msg("订单号不存在");
								}
							} else {
								logger.info(">>>【签名验证失败】");
								notifyReply.setReturn_code("FAIL");
								notifyReply.setReturn_msg("签名验证失败");
							}
						} else {
							logger.info(">>>【获取微信公众号appId、商户号mchId时异常】");
							notifyReply.setReturn_code("FAIL");
							notifyReply.setReturn_msg("系统异常");
						}
					} else if ("FAIL".equals(notifyReceive.getReturn_code())) {
						logger.info(">>>【通信出错】");
						notifyReply.setReturn_code("FAIL");
						notifyReply.setReturn_msg("通信出错");
					} else if ("FAIL".equals(notifyReceive.getResult_code())) {
						logger.info(">>>【业务出错】");
						notifyReply.setReturn_code("FAIL");
						notifyReply.setReturn_msg("业务出错");
					}
				} else {
					logger.info(">>>【报文格式转换异常】");
					notifyReply.setReturn_code("FAIL");
					notifyReply.setReturn_msg("报文格式转换异常");
				}
			} else {
				logger.info(">>>【报文接收异常】");
				notifyReply.setReturn_code("FAIL");
				notifyReply.setReturn_msg("报文接收异常");
			}
		} catch (Exception ex) {
			logger.error(ex);
			notifyReply.setReturn_code("FAIL");
			notifyReply.setReturn_msg("系统异常");
		}

		// 转换支付结果通知接口 响应 报文
		String notifyReplyXML = WXPayUtil.notifyReplyToXml(notifyReply);
		logger.info(">>>notifyReplyXML:" + notifyReply);

		/*
		 * TODO: 使用单独的log文件保存通知（请求/相应）报文
		 */

		return notifyReplyXML;
	}

	// /**
	// * 处理订单信息
	// *
	// * @param notifyReceive
	// * @param order
	// */
	// private void handleOrder(NotifyReceive notifyReceive, Order order) {
	// // 比较支付金额
	// System.out.println(">>>比较支付金额 ");
	// if ((order.getTotal() * 100) ==
	// Double.parseDouble(notifyReceive.getTotal_fee())) {
	// // 更新订单状态、支付状态为：已支付
	// System.out.println(">>>更新订单状态、支付状态为：已支付 ");
	// order.setStatus(1);
	// order.setPaymentStatus(1);
	// } else {
	// System.out.println(">>>【支付金额与订单金额不一致】");
	// order.setStatus(0);
	// order.setPaymentStatus(2);
	// order.setNotifyMsg(order.getNotifyMsg() + new Date() +
	// ">>>:【支付金额与订单金额不一致】 ");
	// }
	//
	// System.out.println(">>>更新订单数据 ...");
	// orderService.saveOrder(order);
	// }

	// /**
	// * 处理产品销量
	// *
	// * @param order
	// */
	// private void handleSaleCount(Order order) {
	// List<OrderItem> orderItemList = order.getOrderItemList();
	// if ((null != orderItemList) && (orderItemList.size() > 0)) {
	// // 订单支付成功，产品销量+1
	// System.out.println(">>>订单支付成功，产品销量+1...");
	// for (OrderItem orderItem : orderItemList) {
	// // 更新产品记录销量
	// Product product = orderItem.getProduct();
	// Long pf_saleCount = (null != product.getSaleCount() ?
	// product.getSaleCount() : 0);
	// product.setSaleCount(pf_saleCount + orderItem.getCount());
	// productService.saveProduct(product);
	// }
	// }
	// }

	// /**
	// * 用户支付成功，调用接口给用户发送"付款成功"提醒消息
	// * 标题：title
	// * 订单号：keyword1
	// * 支付时间：keyword2
	// * 支付金额：keyword3
	// * 支付方式：keyword4
	// * 备注说明：remark
	// * 订单链接：url
	// *
	// * @return
	// */
	//
	// private boolean sendTemple4User(String openId,String title, String
	// keyword1, String keyword2, String keyword3,
	// String remark, String url) {
	// boolean isSend = false;
	// WeixinAccount weixinAccount =
	// weixinAccountService.getAccountByUserId((long) 1);
	// if (null != weixinAccount) {
	//
	// String accessToken = weixinAccountService.getAccessToken(weixinAccount);
	// if (StringUtils.isNotEmpty(accessToken)) {
	// // 发送模板消息
	//
	// String data = "\"first\": {\"value\":\"" + title
	// + "\", \"color\":\"#173177\"},\"keyword1\":{\"value\":\"" + keyword1
	// + "\", \"color\":\"#173177\"},\"keyword2\":{\"value\":\"" + keyword2
	// + "\", \"color\":\"#173177\"},\"keyword3\":{\"value\":\"" + keyword3
	// + "\", \"color\":\"#173177\"},\"remark\":{\"value\":\"" + remark +
	// "\", \"color\":\"#173177\"}";
	// String jsonMsg =
	// "{\"touser\":\"%s\",\"template_id\":\"%s\",\"url\":\"%s\",\"topcolor\":\"%s\",\"data\":{%s}}";
	// jsonMsg = String.format(jsonMsg, openId,
	// "yrfh08zZtf9S00L0hCaVTvVT2Mzjw1opdGYSRVB616I", url, "#FF0000", data);
	//
	// JSONObject jsonObject = AdvancedUtil.sendTemplate(accessToken, jsonMsg);
	// if (null != jsonObject) {
	// int errorCode = jsonObject.getInt("errcode");
	// String errorMsg = jsonObject.getString("errmsg");
	// if (0 == errorCode) {
	// System.out.println("发送模板消息成功 errcode:{} errmsg:{}" + errorCode +
	// errorMsg);
	// isSend = true;
	// } else {
	// System.out.println("发送模板消息失败 errcode:{} errmsg:{}" + errorCode +
	// errorMsg);
	// }
	// }
	// }
	// }
	// return isSend;
	// }
	//
	// /**
	// * 用户下单成功，调用接口给商家发送新订单已生成，
	// * 标题：title
	// * 商品名称：keyword1
	// * 订单编号：keyword2
	// * 支付金额：keyword3
	// * 订单链接：url
	// *
	// * @return
	// */
	//
	// private boolean sendTemple4Admin(String openId,String title, String
	// keyword1, String keyword2, String keyword3, String remark, String url) {
	// boolean isSend = false;
	// WeixinAccount weixinAccount =
	// weixinAccountService.getAccountByUserId((long) 1);
	// if (null != weixinAccount) {
	//
	// String accessToken = weixinAccountService.getAccessToken(weixinAccount);
	// if (StringUtils.isNotEmpty(accessToken)) {
	// // 发送模板消息
	//
	// String data = "\"first\": {\"value\":\"" + title
	// + "\", \"color\":\"#173177\"},\"keyword1\":{\"value\":\"" + keyword1
	// + "\", \"color\":\"#173177\"},\"keyword2\":{\"value\":\"" + keyword2
	// + "\", \"color\":\"#173177\"},\"keyword3\":{\"value\":\"" + keyword3
	// + "\", \"color\":\"#173177\"},\"remark\":{\"value\":\"" + remark +
	// "\", \"color\":\"#173177\"}";
	// String jsonMsg =
	// "{\"touser\":\"%s\",\"template_id\":\"%s\",\"url\":\"%s\",\"topcolor\":\"%s\",\"data\":{%s}}";
	// jsonMsg = String.format(jsonMsg, openId,
	// "yrfh08zZtf9S00L0hCaVTvVT2Mzjw1opdGYSRVB616I", url, "#FF0000", data);
	//
	// JSONObject jsonObject = AdvancedUtil.sendTemplate(accessToken, jsonMsg);
	// if (null != jsonObject) {
	// int errorCode = jsonObject.getInt("errcode");
	// String errorMsg = jsonObject.getString("errmsg");
	// if (0 == errorCode) {
	// System.out.println("发送模板消息成功 errcode:{} errmsg:{}" + errorCode +
	// errorMsg);
	// isSend = true;
	// } else {
	// System.out.println("发送模板消息失败 errcode:{} errmsg:{}" + errorCode +
	// errorMsg);
	// }
	// }
	// }
	// }
	// return isSend;
	// }

}