package com.hg.dqsj.orderManagement.view;

import com.hg.dqsj.orderManagement.entity.OfOrder;

/**
 * 功能：订单详细信息类
 * 
 * @author 吴晓敏
 *
 */
public class VOfOrder extends OfOrder {
	private String orderState; // 订单状态
	private String orderTypeCodeStr; // 订单类型
	private String userName; // 用户名
	private String useDate; // 使用时间
	private String linkUserName; // 联系人名称
	private String linkUserPhone; // 联系人手机号
	private String checkInDate; // 入住日期
	private String checkOutDate; // 离开日期

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderTypeCodeStr() {
		return orderTypeCodeStr;
	}

	public void setOrderTypeCodeStr(String orderTypeCodeStr) {
		this.orderTypeCodeStr = orderTypeCodeStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

}
