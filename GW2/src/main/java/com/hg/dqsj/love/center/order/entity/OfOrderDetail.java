package com.hg.dqsj.love.center.order.entity;

import com.hg.core.entity.BaseInfoEntity;

public class OfOrderDetail extends BaseInfoEntity{
	private String orderId;//订单ID
	private String storeId;//店铺ID
	private String useDate;//使用时间
	private String linkUserName;//联系人名称
	private String linkUserPhone;//联系人手机号
	private String checkInDate;//入住日期
	private String checkOutDate;//离开日期
	private String remark;//备注
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getLinkUserName() {
		return linkUserName;
	}
	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}
	public String getLinkUserPhone() {
		return linkUserPhone;
	}
	public void setLinkUserPhone(String linkUserPhone) {
		this.linkUserPhone = linkUserPhone;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
