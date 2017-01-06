package com.hg.dqsj.love.center.order.service;

import java.util.List;

import com.hg.dqsj.love.center.order.view.VOfOrderGoods;

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
}