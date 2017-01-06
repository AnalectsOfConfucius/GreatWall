package com.hg.dqsj.orderManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.orderManagement.dao.OfOrderGoodsDao;
import com.hg.dqsj.orderManagement.entity.OfOrderGoods;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.view.VOfOrderGoods;

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
	 * 查询该订单ID所有商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OfOrderGoods> selectByOrderId(String orderId){
		return ofOrderGoodsDao.selectByOrderId(orderId);
	}

	/**
	 * 根据订单ID查询商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGoodsByOrderId(String orderId) {
		return ofOrderGoodsDao.selectGoodsByOrderId(orderId);
	}

	/**
	 * 根据订单ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfOrderGoods> selectGuestRoomByOrderId(String orderId) {
		return ofOrderGoodsDao.selectGuestRoomByOrderId(orderId);
	}
	/**
	 * 更新已退商品数量
	 * 
	 * @param orderId
	 * @return
	 */
	public void uptRefundGoodsNum(OfOrderGoods ofOrderGoods){
		ofOrderGoodsDao.uptRefundGoodsNum(ofOrderGoods);
	}
}
