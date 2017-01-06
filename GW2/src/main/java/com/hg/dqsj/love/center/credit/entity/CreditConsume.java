package com.hg.dqsj.love.center.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：消费积分明细POJO类
 * 
 * @author joe
 *
 */
public class CreditConsume extends BaseInfoEntity {
	private String userId; // 用户ID
	private Double deductCredit; //扣除积分
	private Double isWorthMoney; //抵消金额
	private String orderId;//订单ID
	private String  remark ;//备注
	private String useType;//消费方式
	
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getDeductCredit() {
		return deductCredit;
	}
	public void setDeductCredit(Double deductCredit) {
		this.deductCredit = deductCredit;
	}
	public Double getIsWorthMoney() {
		return isWorthMoney;
	}
	public void setIsWorthMoney(Double isWorthMoney) {
		this.isWorthMoney = isWorthMoney;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
