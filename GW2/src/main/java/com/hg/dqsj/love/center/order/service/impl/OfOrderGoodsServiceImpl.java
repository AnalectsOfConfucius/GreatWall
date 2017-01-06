package com.hg.dqsj.love.center.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.love.center.order.dao.OfOrderGoodsDao;
import com.hg.dqsj.love.center.order.service.OfOrderGoodsService;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;

/**
 * 功能：订单商品SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class OfOrderGoodsServiceImpl implements OfOrderGoodsService {
	@Autowired
	private OfOrderGoodsDao ofOrderGoodsDao; // 订单商品DAO接口

	/**
	 * 根据订单ID查询商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGoodsByOrderId(String orderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		return ofOrderGoodsDao.selectGoodsByOrderId(map);
	}

	/**
	 * 根据订单ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGuestRoomByOrderId(String orderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		return ofOrderGoodsDao.selectGuestRoomByOrderId(map);
	}
}
