package com.hg.dqsj.orderManagement.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.orderManagement.dao.OfOrderDao;
import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.service.OfOrderService;
import com.hg.dqsj.orderManagement.view.VOfOrder;

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

	/**
	 * 生成查询条件
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createCriteria(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		map.put("startRowNo", (map.get("currentRecords") == null || map.get("currentRecords").equals("")) ? "0" : map.get("currentRecords").toString()); // 开始行数
		map.put("rowSize", "10"); // 查询范围
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
			result.put("currentPageNo", 0); // 当前页码
			result.put("totalCount", 0); // 总记录数
		} else {
			for (VOfOrder detail : details) {
				completeOrderDetail(detail);
			}
			Integer totalCount = ofOrderDao.countByCriterias(map);
			result.put("details", details);
			result.put("currentPageNo", map.get("currentPageNo")); // 当前页码
			result.put("totalCount", totalCount); // 总记录数
		}
		return result;
	}

	/**
	 * 完善订单详细信息
	 * 
	 * @param detail
	 */
	private void completeOrderDetail(VOfOrder detail) {
		String orderState = null; // 订单状态

		if ("0".equals(detail.getPayFlag())) {
			orderState = "待支付";
			if (!"2".equals(detail.getOrderTypeCode()) && !StringUtil.isNullorEmpty(detail.getInvalidEndTime())
					&& DateUtil.strToDate(detail.getInvalidEndTime(), "yyyy-MM-dd HH:mm:ss").before(new Date())) {
				orderState = "已取消";
			}
			// 客房
			if ("2".equals(detail.getOrderTypeCode())) {
				orderState = "已预订";
			}
		} else if ("0".equals(detail.getGetFlag())) {
			orderState = "已支付 待领取";
		} else if ("0".equals(detail.getEvalFlag())) {
			orderState = "已领取 待评价";
		} else {
			orderState = "已完成";
		}

		if (!StringUtil.isNullorEmpty(detail.getOrderTypeCode())) {
			String orderTypeCode = detail.getOrderTypeCode();
			String orderTypeCodeStr = null;

			if ("1".equals(orderTypeCode)) {
				orderTypeCodeStr = "门票";
			} else if ("2".equals(orderTypeCode)) {
				orderTypeCodeStr = "客房";
			} else if ("3".equals(orderTypeCode)) {
				orderTypeCodeStr = "年卡";
			} else if ("4".equals(orderTypeCode)) {
				orderTypeCodeStr = "特色商品";
			} else if ("5".equals(orderTypeCode)) {
				orderTypeCodeStr = "美食";
			}

			detail.setOrderTypeCodeStr(orderTypeCodeStr);
		}

		detail.setOrderState(orderState); // 订单状态
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
			throw new Exception("确认领取失败，请重试！");
		}
	}
}
