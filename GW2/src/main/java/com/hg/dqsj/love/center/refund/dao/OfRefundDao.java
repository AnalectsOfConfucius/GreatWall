package com.hg.dqsj.love.center.refund.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.love.center.refund.entity.OfRefund;
import com.hg.dqsj.love.center.refund.view.VOfRefund;



/**
 * 功能：退款DAO接口
 * 
 * @author joe
 *
 */
public interface OfRefundDao {
	/**
	 * 根据退款号查询信息
	 * @param orderNo
	 * @return
	 */
	public OfRefund selectByRefundNo(String RefundNo);
	/**
	 * 新增退款
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void saveRefund(OfRefund refund);

	/**
	 * 根据查询条件查询订单信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VOfRefund> selectByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询订单信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countByCriterias(Map<String, Object> map);

	/**
	 * 根据ID查询退款详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfRefund selectVById(String id);

	/**
	 * 更新退款状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateGetFlag(OfRefund  ofRefund);
	/**
	 * 更新到账状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateAccountFlag(OfRefund  ofRefund);


}
