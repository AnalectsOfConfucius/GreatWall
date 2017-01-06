package com.hg.dqsj.love.center.order.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.entity.OfOrderDetail;
import com.hg.dqsj.love.center.order.view.VOfOrder;

/**
 * 功能：订单DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface OfOrderDao {
	/**
	 * 保存订单
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer save(OfOrder ofOrder);

	/**
	 * 保存订单详情
	 * 
	 * @param ofOrderDetail
	 * @return
	 */
	public void insertOrderDetail(OfOrderDetail detail);

	/**
	 * 根据订单号查询订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public OfOrder selectByOrderNo(String orderNo);

	/**
	 * 根据订单ID查询订单信息
	 * 
	 * @param id
	 * @return
	 */
	public OfOrder selectById(String id);

	/**
	 * 根据查询条件查询订单信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VOfOrder> selectByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询订单信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countByCriterias(Map<String, Object> map);

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfOrder selectVById(String id);

	/**
	 * 更新支付状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updatePayFlag(OfOrder ofOrder);

	/**
	 * 更新领取状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateGetFlag(OfOrder ofOrder);

	/**
	 * 更新评价状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateEvalFlag(OfOrder ofOrder);

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VOfOrder> selectOrderDetailsById(Map<String, String> map);

	/**
	 * 查询当前用户未支付失效订单订单详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<OfOrder> selectInvalidOrder(String userId);

	/**
	 * 删除订单
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer deleteOrder(OfOrder ofOrder);

}
