package com.hg.dqsj.orderManagement.service;

import java.util.List;

import com.hg.dqsj.orderManagement.entity.OfOrderGoods;
import com.hg.dqsj.orderManagement.view.VOfOrderGoods;

/**
 * 功能：订单商品SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface OfOrderGoodsService {
	/**
	 * 根据订单ID查询商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGoodsByOrderId(String orderId);

	/**
	 * 根据订单ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGuestRoomByOrderId(String orderId);
	/**
	 * 查询该订单ID所有商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OfOrderGoods> selectByOrderId(String orderId);
	/**
	 * 更新已退商品数量
	 * 
	 * @param orderId
	 * @return
	 */
	public void uptRefundGoodsNum(OfOrderGoods ofOrderGoods);
}
