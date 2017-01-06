package com.hg.dqsj.love.center.refund.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：退款商品POJO类
 * 
 * @author joe
 *
 */
public class OfRefundGoods extends BaseInfoEntity {
	private String refundId; // 退款ID
	private String goodsId; // 商品ID
	private Integer goodsNum; // 商品数量
	private Double goodsPrice; // 商品单价
	private String remark; // 备注

	

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
