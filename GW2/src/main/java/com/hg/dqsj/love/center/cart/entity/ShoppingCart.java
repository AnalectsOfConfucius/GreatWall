package com.hg.dqsj.love.center.cart.entity;

import com.hg.core.entity.BaseInfoEntity;

public class ShoppingCart extends BaseInfoEntity{
	private String userId;//用户ID
	private String typeCode;//商品类别代码
	private String goodsId;//商品ID
	private int goodsNum;//商品总量
	private String remark;//备注
	private String storeId;//食品店id
	private String goodsName;//商品名称
	private String picUrl;//商品图片路径
	private Double sellPrice;//销售价
	private Double goodsTotal;//商品总价
	private Double totalPrice;//总价
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getGoodsTotal() {
		return goodsTotal;
	}
	public void setGoodsTotal(Double goodsTotal) {
		this.goodsTotal = goodsTotal;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
