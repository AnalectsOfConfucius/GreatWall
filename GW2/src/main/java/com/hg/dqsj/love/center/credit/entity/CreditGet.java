package com.hg.dqsj.love.center.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：获得积分明细POJO类
 * 
 * @author joe
 *
 */
public class CreditGet extends BaseInfoEntity {
	private String userId; // 用户ID
	private Integer creditNum; //获取的积分
	private String getFlag;//获得积分标识
	private String giveCreditId;//消费送积分ID
	private String  remark ;//备注
	private String receiveTime;//领取时间
	
	
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCreditNum() {
		return creditNum;
	}
	public void setCreditNum(Integer creditNum) {
		this.creditNum = creditNum;
	}
	public String getGetFlag() {
		return getFlag;
	}
	public void setGetFlag(String getFlag) {
		this.getFlag = getFlag;
	}
	public String getGiveCreditId() {
		return giveCreditId;
	}
	public void setGiveCreditId(String giveCreditId) {
		this.giveCreditId = giveCreditId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
