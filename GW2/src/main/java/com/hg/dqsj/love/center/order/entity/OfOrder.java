package com.hg.dqsj.love.center.order.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：订单POJO类
 * 
 * @author 吴晓敏
 *
 */
public class OfOrder extends BaseInfoEntity {
	private String orderNo; // 订单号
	private String orderTypeCode; // 订单类型代码[1:门票][2:酒店][3:年卡][4:商品][5:美食]
	private String userId; // 用户Id
	private Double orderPrice; // 订单总金额
	private String orderQRCode; // 消费码
	private String orderTime; // 下单时间
	private String invalidEndTime; // 失效截止时间
	private String payFlag; // 支付状态标识
	private String getFlag; // 取餐状态标识
	private String evalFlag; // 评价状态标识
	private Integer rowLockNum; // 行锁号
	private String remark; // 备注
	private String storeId;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderQRCode() {
		return orderQRCode;
	}

	public void setOrderQRCode(String orderQRCode) {
		this.orderQRCode = orderQRCode;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getInvalidEndTime() {
		return invalidEndTime;
	}

	public void setInvalidEndTime(String invalidEndTime) {
		this.invalidEndTime = invalidEndTime;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getGetFlag() {
		return getFlag;
	}

	public void setGetFlag(String getFlag) {
		this.getFlag = getFlag;
	}

	public String getEvalFlag() {
		return evalFlag;
	}

	public void setEvalFlag(String evalFlag) {
		this.evalFlag = evalFlag;
	}

	public Integer getRowLockNum() {
		return rowLockNum;
	}

	public void setRowLockNum(Integer rowLockNum) {
		this.rowLockNum = rowLockNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
