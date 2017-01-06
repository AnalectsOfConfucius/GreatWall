package com.hg.dqsj.love.center.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.weixin.entity.OrderPay;
import com.hg.core.weixin.service.WeixinService;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.order.dao.OfOrderDao;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.service.OfOrderGoodsService;
import com.hg.dqsj.love.center.order.service.OfOrderService;
import com.hg.dqsj.love.center.order.view.VOfOrder;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;

/**
 * 功能：订单SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class OfOrderServiceImpl implements OfOrderService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private OfOrderDao ofOrderDao; // 订单DAO接口
	@Autowired
	private OfOrderGoodsService ofOrderGoodsService; // 订单商品SERVICE接口
	@Autowired
	private WeixinService weixinService; // 平台商户信息SERVICE接口

	/**
	 * 生成查询条件
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createCriteria(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		// 用户登录保存所属权限信息
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		map.put("startRowNo", (map.get("currentRecords") == null || map.get("currentRecords").equals("")) ? "0" : map.get("currentRecords").toString()); // 开始行数
		map.put("rowSize", "6"); // 查询范围

		if (auth != null && !StringUtil.isNullorEmpty(auth.getUserId())) {
			map.put("userId", auth.getUserId());
			deleteInvalidOrder(auth.getUserId()); // 删除失效订单
		}
		return map;
	}

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<VOfOrder> details = ofOrderDao.selectByCriterias(map);
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("totalRecords", 0); // 总记录数
		} else {
			for (VOfOrder vOfOrder : details) {
				completeOrderDetail(vOfOrder);
			}
			Integer totalRecords = ofOrderDao.countByCriterias(map);
			result.put("details", details);
			result.put("totalRecords", totalRecords); // 总记录数
		}
		return result;
	}

	/**
	 * 完善订单详细信息
	 * 
	 * @param detail
	 */
	private void completeOrderDetail(VOfOrder detail) {
		if (detail == null) {
			return;
		}

		// 订单类型
		if ("1".equals(detail.getOrderTypeCode())) {
			detail.setOrderTypeCodeStr("门票");
		} else if ("2".equals(detail.getOrderTypeCode())) {
			detail.setOrderTypeCodeStr("酒店");
		} else if ("3".equals(detail.getOrderTypeCode())) {
			detail.setOrderTypeCodeStr("年卡");
		} else if ("4".equals(detail.getOrderTypeCode())) {
			detail.setOrderTypeCodeStr("特色商品");
		} else if ("5".equals(detail.getOrderTypeCode())) {
			detail.setOrderTypeCodeStr("美食");
		}

		// 【是否可退款标识】默认[1:是]
		detail.setIsRefundFlag("1");
		String orderState = null;
		if (detail.getPayFlag().equals("0")) {
			orderState = "待支付";
			if ("2".equals(detail.getOrderTypeCode())) {
				orderState = "到店支付";
			}
		} else if (detail.getGetFlag().equals("0")) {
			orderState = "待领取";
			if ("1".equals(detail.getOrderTypeCode()) && DateUtil.isDate(detail.getUseDate(), "yyyy-MM-dd HH:mm:ss", true) && DateUtil.strToDate(detail.getUpdateDate(), "yyyy-MM-dd HH:mm:ss").after(new Date())) {
				// 【是否可退款标识】默认[0:否]
				detail.setIsRefundFlag("0");
			}
		} else if (detail.getEvalFlag().equals("0")) {
			orderState = "待评价";
		} else {
			orderState = "已完成";
		}
		detail.setOrderState(orderState);

		// 订单商品信息
		if ("1".equals(detail.getOrderTypeCode()) || "3".equals(detail.getOrderTypeCode())) {
			detail.setGoodsDetails(ofOrderGoodsService.selectGoodsByOrderId(detail.getId()));
		} else if ("2".equals(detail.getOrderTypeCode())) {
			detail.setGoodsDetails(ofOrderGoodsService.selectGuestRoomByOrderId(detail.getId()));
		} else {
			Integer totalNum = 0; // 商品总量
			String goodsNameInfos = null; // 商品信息
			List<VOfOrderGoods> goodsDetails = ofOrderGoodsService.selectGoodsByOrderId(detail.getId());
			for (int i = 0; i < goodsDetails.size(); i++) {
				if (i == 0) {
					goodsNameInfos = goodsDetails.get(i).getGoodsName();
				} else if (i < 3) {
					goodsNameInfos = goodsNameInfos + "、" + goodsDetails.get(i).getGoodsName();
				}
				totalNum += goodsDetails.get(i).getGoodsNum().intValue();
			}

			if (StringUtil.isNullorEmpty(goodsNameInfos)) {
				return;
			}
			if (goodsDetails.size() < 3) {
				detail.setGoodsInfo(goodsNameInfos + totalNum + "件商品");
			} else {
				detail.setGoodsInfo(goodsNameInfos + "等" + totalNum + "件商品");
			}
		}

	}

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfOrder selectVById(String id) {
		VOfOrder detail = ofOrderDao.selectVById(id);
		completeOrderDetail(detail);
		return detail;
	}

	/**
	 * 更新领取状态
	 * 
	 * @param ofOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean updateGetFlag(OfOrder ofOrder) throws Exception {
		Integer count = ofOrderDao.updateGetFlag(ofOrder);
		if (count == 1) {
			return true;
		} else {
			throw new Exception("确认领取失败");
		}
	}

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public List<VOfOrder> selectOrderDetailsById(String id) {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		return ofOrderDao.selectOrderDetailsById(map);
	}

	/**
	 * 根据订单号查询订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public OfOrder selectByOrderNo(String orderNo) {
		return ofOrderDao.selectByOrderNo(orderNo);
	}

	/**
	 * 更新订单支付状态及保存微信支付结果同步请求报文
	 * 
	 * @param orderNo
	 * @return
	 */
	@Override
	@Transactional
	public Integer updatePayFlag(OfOrder ofOrder, OrderPay orderPay) throws Exception {
		int updateRtnCnt = 0;
		updateRtnCnt = this.weixinService.insertOrderPayInfo(orderPay);
		if (updateRtnCnt < 1) {
			throw new Exception("接收微信支付结果同步请求报文 写入数据库失败");
		}
		updateRtnCnt = this.ofOrderDao.updatePayFlag(ofOrder);
		if (updateRtnCnt < 1) {
			throw new Exception("更新支付结果失败");
		}
		return updateRtnCnt;
	}

	/**
	 * 根据订单ID查询订单信息
	 * 
	 * @param id
	 * @return
	 */
	public OfOrder selectById(String id) {
		return ofOrderDao.selectById(id);
	}

	/**
	 * 删除当前用户未支付失效订单订单信息
	 * 
	 * @param map
	 * @return
	 */
	public void deleteInvalidOrder(String userId) {
		List<OfOrder> orders = ofOrderDao.selectInvalidOrder(userId);
		for (OfOrder order : orders) {
			ofOrderDao.deleteOrder(order);
		}
	}
}
