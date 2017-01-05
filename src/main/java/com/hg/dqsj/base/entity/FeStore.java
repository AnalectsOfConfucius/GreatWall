package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

public class FeStore extends BaseInfoEntity{
	private String storeName;//店铺名称
	private String storeNo;//店铺代号
	private String storeType;//店铺类别
	private String isOpen;//是否开业
	private String longitude;//经度
	private String latitude;//纬度
	private byte[] storeBrief;//店铺简介
	private String storeBriefStr;//店铺简介
	private String storeContent;//店铺内容
	private String storeOrder;//排序
	private String remark;//备注
	
	public String getStoreBriefStr() {
		return storeBriefStr;
	}
	public void setStoreBriefStr(String storeBriefStr) {
		this.storeBriefStr = storeBriefStr;
	}
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
	public byte[] getStoreBrief() {
		return storeBrief;
	}
	public void setStoreBrief(byte[] storeBrief) {
		this.storeBrief = storeBrief;
	}
	public String getStoreContent() {
		return storeContent;
	}
	public void setStoreContent(String storeContent) {
		this.storeContent = storeContent;
	}
	public String getStoreOrder() {
		return storeOrder;
	}
	public void setStoreOrder(String storeOrder) {
		this.storeOrder = storeOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
