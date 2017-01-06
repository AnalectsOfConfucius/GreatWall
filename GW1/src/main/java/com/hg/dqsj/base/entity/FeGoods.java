package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

public class FeGoods extends BaseInfoEntity{
	private String goodsName;//商品名称
	private String typeCode;//商品类别代码
	private String storeId;//美食店ID
	private Double sellPrice;//销售价格
	private String goodsBrief;//商品简介
	private byte[] goodsContent;//商品内容
	private String goodsContentStr;//商品内容
	private int goodsOrder;//排序
	private String remark;//备注
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getGoodsBrief() {
		return goodsBrief;
	}
	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}
	public byte[] getGoodsContent() {
		return goodsContent;
	}
	public void setGoodsContent(byte[] goodsContent) {
		this.goodsContent = goodsContent;
	}
	public String getGoodsContentStr() {
		return goodsContentStr;
	}
	public void setGoodsContentStr(String goodsContentStr) {
		this.goodsContentStr = goodsContentStr;
	}
	public int getGoodsOrder() {
		return goodsOrder;
	}
	public void setGoodsOrder(int goodsOrder) {
		this.goodsOrder = goodsOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
