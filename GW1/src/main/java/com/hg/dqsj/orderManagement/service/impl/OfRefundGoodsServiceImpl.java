package com.hg.dqsj.orderManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.orderManagement.dao.OfOrderGoodsDao;
import com.hg.dqsj.orderManagement.dao.OfRefundGoodsDao;
import com.hg.dqsj.orderManagement.entity.OfRefundGoods;
import com.hg.dqsj.orderManagement.service.OfOrderGoodsService;
import com.hg.dqsj.orderManagement.service.OfRefundGoodsService;
import com.hg.dqsj.orderManagement.view.VOfOrderGoods;
import com.hg.dqsj.orderManagement.view.VOfRefundGoods;

/**
 * 功能：退款商品SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class OfRefundGoodsServiceImpl implements OfRefundGoodsService {
	@Autowired
	private OfRefundGoodsDao ofRefundGoodsDao; // 订单商品DAO接口
	/**
	 * 根据退款ID查询商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfRefundGoods> selectGoodsByRefundId(String orderId) {
		return ofRefundGoodsDao.selectGoodsByRefundId(orderId);
	}
	/**
	 * 根据退款ID查询所有商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OfRefundGoods> selectByRefundId(String refundId){
		return ofRefundGoodsDao.selectByRefundId(refundId);
	}
	/**
	 * 根据退款ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfRefundGoods> selectGuestRoomByRefundId(String orderId) {
		return ofRefundGoodsDao.selectGuestRoomByRefundId(orderId);
	}
	
}
