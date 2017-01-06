package com.hg.dqsj.love.center.order.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.love.center.order.entity.OfOrderGoods;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;

/**
 * 功能：订单商品DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface OfOrderGoodsDao {
	/**
	 * 保存订单商品
	 * 
	 * @param ofOrderGoods
	 * @return
	 */
	public Integer save(OfOrderGoods ofOrderGoods);

	/**
	 * 根据订单ID和商品ID查询商品信息
	 * 
	 * @param map
	 * @return
	 */
	public OfOrderGoods selectByOrderIdAndGoodsId(Map<String, String> map);

	/**
	 * 根据订单ID查询商品信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VOfOrderGoods> selectGoodsByOrderId(Map<String, String> map);

	/**
	 * 根据订单ID查询客房信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VOfOrderGoods> selectGuestRoomByOrderId(Map<String, String> map);
}
