package com.hg.dqsj.orderManagement.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：退款POJO类
 * 
 * @author joe
 *
 */
public class OfRefund extends BaseInfoEntity {
	private String orderId; // 退款ID
	private String refundNo;//退款编号
	private Double refundTotalAmount; // 退款总金额
	private String refundFlag; // 退款状态标识 [0:退款中][1:退款失败][2:退款完成]
	private String refundDate; // 退款申请时间
	private String confirmDate; //退款确认时间
	private String failReason; // 退款失败原因
	private String accountFlag; // 到账状态标识[0:未到账][1:已到账]
	private String accountDate; // 到账时间
	private Integer rowLockNum; // 行锁号
	private String remark; // 备注
	
	
	
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public Integer getRowLockNum() {
		return rowLockNum;
	}
	public void setRowLockNum(Integer rowLockNum) {
		this.rowLockNum = rowLockNum;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getRefundTotalAmount() {
		return refundTotalAmount;
	}
	public void setRefundTotalAmount(Double refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getAccountFlag() {
		return accountFlag;
	}
	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
