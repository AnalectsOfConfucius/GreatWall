package com.hg.dqsj.orderManagement.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.view.VOfOrder;

/**
 * 功能：订单DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface OfOrderDao {
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
	 * 更新取餐状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateGetFlag(OfOrder ofOrder);

}
