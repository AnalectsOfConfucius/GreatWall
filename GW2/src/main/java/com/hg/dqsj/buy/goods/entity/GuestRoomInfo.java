package com.hg.dqsj.buy.goods.entity;

import com.hg.core.entity.BaseInfoEntity;

public class GuestRoomInfo extends BaseInfoEntity{
	private String storeId;//酒店ID
	private String guestRoomName;//客房名称
	private String sellPrice;//销售价格
	private String guestRoomBrief;//客房简介
	private String guestRoomTel;//联系电话
	private String guestRoomAddress;//地址
	private byte[] guestRoomContent;//客房内容
	private int guestRoomOrder;//排序
	private String remark;//备注
	private String guestRoomContentStr;//客房内容
	private String storeName;//店铺名称
	private String storeNo;//店铺代号
	private String isOpen;//是否开业
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getGuestRoomName() {
		return guestRoomName;
	}
	public void setGuestRoomName(String guestRoomName) {
		this.guestRoomName = guestRoomName;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getGuestRoomBrief() {
		return guestRoomBrief;
	}
	public void setGuestRoomBrief(String guestRoomBrief) {
		this.guestRoomBrief = guestRoomBrief;
	}
	public String getGuestRoomTel() {
		return guestRoomTel;
	}
	public void setGuestRoomTel(String guestRoomTel) {
		this.guestRoomTel = guestRoomTel;
	}
	public String getGuestRoomAddress() {
		return guestRoomAddress;
	}
	public void setGuestRoomAddress(String guestRoomAddress) {
		this.guestRoomAddress = guestRoomAddress;
	}
	public byte[] getGuestRoomContent() {
		return guestRoomContent;
	}
	public void setGuestRoomContent(byte[] guestRoomContent) {
		this.guestRoomContent = guestRoomContent;
	}
	public int getGuestRoomOrder() {
		return guestRoomOrder;
	}
	public void setGuestRoomOrder(int guestRoomOrder) {
		this.guestRoomOrder = guestRoomOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGuestRoomContentStr() {
		return guestRoomContentStr;
	}
	public void setGuestRoomContentStr(String guestRoomContentStr) {
		this.guestRoomContentStr = guestRoomContentStr;
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
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
}
