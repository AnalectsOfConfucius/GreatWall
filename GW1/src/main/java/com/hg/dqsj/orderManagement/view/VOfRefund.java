package com.hg.dqsj.orderManagement.view;

import com.hg.dqsj.orderManagement.entity.OfRefund;

/**
 * 功能：退款详细信息类
 * 
 * @author joe
 *
 */
public class VOfRefund extends OfRefund {
	private String refundState; //退款状态
	private String orderNo; //订单编号
	private String userName; // 用户名
	private String useDate; // 使用时间
	private String linkUserName; // 联系人名称
	private String linkUserPhone; // 联系人手机号
	
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
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

	
}
