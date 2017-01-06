package com.hg.dqsj.orderManagement.view;

import com.hg.dqsj.orderManagement.entity.OfRefundGoods;

/**
 * 功能：退款商品详细信息类
 * 
 * @author joe
 *
 */
public class VOfRefundGoods extends OfRefundGoods {
	private String goodsName; // 商品名称

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
