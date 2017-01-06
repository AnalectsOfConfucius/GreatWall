package com.hg.dqsj.orderManagement.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.entity.OfRefund;
import com.hg.dqsj.orderManagement.view.VOfOrder;
import com.hg.dqsj.orderManagement.view.VOfRefund;

/**
 * 功能：退款SERVICE接口
 * 
 * @author joe
 *
 */
public interface OfRefundService {

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request);

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfRefund  selectVById(String id);

	/**
	 * 更新领取状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateGetFlag(OfRefund OfRefund);
	/**
	 * 更新到账状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateAccountFlag(OfRefund  ofOrder);

}
