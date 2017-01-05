package com.hg.dqsj.goods.guestRoom.entity;

public class GuestRoomInfo extends GuestRoom{
	private String guestRoomContentStr;//客房内容
	private String storeName;//店铺名称
	private String storeNo;//店铺代号
	private String isOpen;//是否开业
	
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
