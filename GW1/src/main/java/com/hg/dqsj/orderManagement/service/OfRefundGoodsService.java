package com.hg.dqsj.orderManagement.service;

import java.util.List;

import com.hg.dqsj.orderManagement.entity.OfRefundGoods;
import com.hg.dqsj.orderManagement.view.VOfRefundGoods;

/**
 * 功能：退款商品SERVICE接口
 * 
 * @author joe
 *
 */
public interface OfRefundGoodsService {
	/**
	 * 根据退款ID查询商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfRefundGoods> selectGoodsByRefundId(String refundId);

	/**
	 * 根据退款ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfRefundGoods> selectGuestRoomByRefundId(String refundId);
	/**
	 * 根据退款ID查询所有商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OfRefundGoods> selectByRefundId(String refundId);
}
