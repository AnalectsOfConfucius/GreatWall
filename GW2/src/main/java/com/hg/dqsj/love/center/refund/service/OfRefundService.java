package com.hg.dqsj.love.center.refund.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.dqsj.love.center.refund.entity.OfRefund;
import com.hg.dqsj.love.center.refund.view.VOfRefund;


/**
 * 功能：退款SERVICE接口
 * 
 * @author joe
 *
 */
public interface OfRefundService {
	/**
	 * 根据退款号查询信息
	 * @param orderNo
	 * @return
	 */
	public OfRefund selectByRefundNo(String RefundNo);
	/**
	 * 随机生成退款号
	 * 
	 * @return
	 */
	public String getRefundNo() ;

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
	/**
	 * 新增退款
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void saveRefund(OfRefund refund);

}
