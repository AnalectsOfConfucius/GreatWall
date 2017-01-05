package com.hg.dqsj.goods.store.entity;

import com.hg.core.entity.BaseInfoEntity;

public class Store extends BaseInfoEntity{
	private String storeName;//店铺名称
	private String storeNo;//店铺代号
	private String storeType;//店铺类别
	private String isOpen;//是否开业
	private String longitude;//经度
	private String latitude;//纬度
	private String storeBrief;//店铺简介
	private byte[] storeContent;//店铺内容
	private String storeContentStr;//店铺内容
	private int storeOrder;//排序
	private String remark;//备注
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getStoreBrief() {
		return storeBrief;
	}
	public void setStoreBrief(String storeBrief) {
		this.storeBrief = storeBrief;
	}
	public byte[] getStoreContent() {
		return storeContent;
	}
	public void setStoreContent(byte[] storeContent) {
		this.storeContent = storeContent;
	}
	public String getStoreContentStr() {
		return storeContentStr;
	}
	public void setStoreContentStr(String storeContentStr) {
		this.storeContentStr = storeContentStr;
	}
	public int getStoreOrder() {
		return storeOrder;
	}
	public void setStoreOrder(int storeOrder) {
		this.storeOrder = storeOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
