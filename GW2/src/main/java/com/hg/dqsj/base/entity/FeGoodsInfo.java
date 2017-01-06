package com.hg.dqsj.base.entity;

public class FeGoodsInfo extends FeGoods{
	
	private String storeName;//美食店
	private String isOpen;//是否开业
	private String storeBrief;//店铺简介
	private String picUrl;//图片路径
	private Integer monthlySales;//月销量

	public Integer getMonthlySales() {
		return monthlySales;
	}

	public void setMonthlySales(Integer monthlySales) {
		this.monthlySales = monthlySales;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getStoreBrief() {
		return storeBrief;
	}

	public void setStoreBrief(String storeBrief) {
		this.storeBrief = storeBrief;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}
