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
import com.hg.dqsj.orderManagement.dao.OfRefundDao;
import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.entity.OfRefund;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.service.OfOrderService;
import com.hg.dqsj.orderManagement.service.OfRefundGoodsService;
import com.hg.dqsj.orderManagement.service.OfRefundService;
import com.hg.dqsj.orderManagement.view.VOfOrder;
import com.hg.dqsj.orderManagement.view.VOfRefund;

/**
 * 功能：退款SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class OfRefundServiceImpl implements OfRefundService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private OfRefundDao ofRefunDao; // 订单DAO接口
	@Autowired
	private OfRefundGoodsService ofRefunGoodsService; // 订单商品SERVICE接口

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
		List<VOfRefund> details = ofRefunDao.selectByCriterias(map);
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("currentPageNo", 0); // 当前页码
			result.put("totalCount", 0); // 总记录数
		} else {
			for (VOfRefund detail : details) {
				completeOrderDetail(detail);
			}
			Integer totalCount = ofRefunDao.countByCriterias(map);
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
	private void completeOrderDetail(VOfRefund detail) {
		String refundState = null; // 退款状态

		if (detail.getRefundFlag().equals("0")) {
			refundState = "退款中";	
		} else if (detail.getRefundFlag().equals("1")) {
			refundState = "退款失败";
		} else if (detail.getRefundFlag().equals("2")) {
			if (detail.getAccountFlag().equals('0')) {
				refundState = "退款完成  未到账";
			}else if (detail.getAccountFlag().equals('1')) {
				refundState = "退款完成  已到账";
			}else {
				refundState = "退款完成";
			}
		} else {
			refundState = "退款中";
		}

			detail.setRefundState(refundState);
	}

	/**
	 * 根据退款ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfRefund selectVById(String id) {
		VOfRefund detail = ofRefunDao.selectVById(id);
		completeOrderDetail(detail);
		return detail;
	}

	/**
	 * 更新退款状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	@Transactional
	public Integer updateGetFlag(OfRefund ofRefund) {
		return ofRefunDao.updateGetFlag(ofRefund);
	}
	/**
	 * 更新到账状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateAccountFlag(OfRefund  ofRefund ){
		return ofRefunDao.updateAccountFlag(ofRefund);
	}

}
