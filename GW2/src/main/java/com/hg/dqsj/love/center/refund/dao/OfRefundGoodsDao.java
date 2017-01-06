package com.hg.dqsj.love.center.refund.dao;

import java.util.List;

import com.hg.dqsj.love.center.refund.entity.OfRefundGoods;
import com.hg.dqsj.love.center.refund.view.VOfRefundGoods;


/**
 * 功能：退款商品DAO接口
 * 
 * @author joe
 *
 */
public interface OfRefundGoodsDao {
	/**
	 * 新增退款商品
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void saveRefundGoods(OfRefundGoods goods);
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
}
