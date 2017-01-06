package com.hg.dqsj.love.center.refund.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.love.center.refund.dao.OfRefundGoodsDao;
import com.hg.dqsj.love.center.refund.entity.OfRefundGoods;
import com.hg.dqsj.love.center.refund.service.OfRefundGoodsService;
import com.hg.dqsj.love.center.refund.view.VOfRefundGoods;



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
	 * 新增退款商品
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void saveRefundGoods(OfRefundGoods goods){
		ofRefundGoodsDao.saveRefundGoods(goods);
	}
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
	 * 根据退款ID查询客房信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<VOfRefundGoods> selectGuestRoomByRefundId(String orderId) {
		return ofRefundGoodsDao.selectGuestRoomByRefundId(orderId);
	}
	
}
