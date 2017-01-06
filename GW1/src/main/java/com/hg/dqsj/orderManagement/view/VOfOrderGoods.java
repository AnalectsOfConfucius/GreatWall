package com.hg.dqsj.orderManagement.view;

import com.hg.dqsj.orderManagement.entity.OfOrderGoods;

/**
 * 功能：订单商品详细信息类
 * 
 * @author 吴晓敏
 *
 */
public class VOfOrderGoods extends OfOrderGoods {
	private String goodsName; // 商品名称

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
