package com.hg.dqsj.love.center.cart.entity;

import com.hg.core.entity.BaseInfoEntity;

public class ShoppingCartGoods extends BaseInfoEntity{
	private String userId;//用户ID
	private String typeCode;//商品类别代码
	private String goodsId;//商品ID
	private int goodsNum;//商品总量
	private String remark;//备注
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
