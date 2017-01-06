package com.hg.dqsj.orderManagement.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：订单商品POJO类
 * 
 * @author 吴晓敏
 *
 */
public class OfOrderGoods extends BaseInfoEntity {
	private String orderId; // 订单ID
	private String goodsId; // 商品ID
	private Double goodsNum; // 商品数量
	private Double goodsPrice; // 商品单价
	private Integer refundGoodsNum;//已退款数量
	private String remark; // 备注

	
	public Integer getRefundGoodsNum() {
		return refundGoodsNum;
	}

	public void setRefundGoodsNum(Integer refundGoodsNum) {
		this.refundGoodsNum = refundGoodsNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Double getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
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
