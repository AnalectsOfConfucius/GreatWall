package com.hg.dqsj.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

public class Credit extends BaseInfoEntity{
	private Double consumeAmount;//消费金额
	private Double giveCredit;//赠送积分
	private String isForever;//是否永久有效
	private String validEndDate;//有效截止日
	private String remark;//备注
	
	public Double getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public Double getGiveCredit() {
		return giveCredit;
	}
	public void setGiveCredit(Double giveCredit) {
		this.giveCredit = giveCredit;
	}
	public String getIsForever() {
		return isForever;
	}
	public void setIsForever(String isForever) {
		this.isForever = isForever;
	}
	public String getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
