package com.hg.dqsj.buy.goods.service;

import java.util.List;

import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.entity.OfOrderDetail;
import com.hg.dqsj.love.center.order.entity.OfOrderGoods;

public interface GoodsService {
	/**
	 * 保存订单
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void insertOrder(OfOrder ofOrder,OfOrderDetail detail,List<OfOrderGoods> glist);
	/**
	 * 随机生成订单号
	 * 
	 * @return
	 */
	public String getOrderNo();
}
