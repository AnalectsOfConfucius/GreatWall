package com.hg.dqsj.buy.goods.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.DateUtil;
import com.hg.dqsj.buy.goods.service.GoodsService;
import com.hg.dqsj.love.center.order.dao.OfOrderDao;
import com.hg.dqsj.love.center.order.dao.OfOrderGoodsDao;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.entity.OfOrderDetail;
import com.hg.dqsj.love.center.order.entity.OfOrderGoods;
@Service
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private OfOrderDao orderDao;
	@Autowired
	private OfOrderGoodsDao orderGoodsDao;
	
	@Override
	public void insertOrder(OfOrder ofOrder, OfOrderDetail detail,
			List<OfOrderGoods> glist) {
		orderDao.save(ofOrder);
		orderDao.insertOrderDetail(detail);
		if(null!=glist && glist.size()>0){
			for (OfOrderGoods ofOrderGoods : glist) {
				orderGoodsDao.save(ofOrderGoods);
			}
		}
	}
	/**
	 * 随机生成订单号
	 * 
	 * @return
	 */
	@Override
	public String getOrderNo() {
		int count = 10; // 最大查询次数
		while (count > 0) {
			String currentDateStr = DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS"); // 当前时间
			int random = (int) (Math.random() * 100000000); // 8位随机数
			OfOrder ofOrder = orderDao.selectByOrderNo(currentDateStr + random);
			count--;
			if (ofOrder == null) {
				return currentDateStr + random;
			}
		}
		return null;
	}
}
