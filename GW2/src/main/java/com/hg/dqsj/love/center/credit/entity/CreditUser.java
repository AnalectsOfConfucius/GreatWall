package com.hg.dqsj.love.center.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：用户积分POJO类
 * 
 * @author joe
 *
 */
public class CreditUser extends BaseInfoEntity {
	private String userId; // 用户ID
	
	private Integer creditNum; //总积分
	private Integer rowLockNum; // 行锁号
	private byte[] creditRule;//积分规则
	private String  remark ;//备注
	
	
	public byte[] getCreditRule() {
		return creditRule;
	}
	public void setCreditRule(byte[] creditRule) {
		this.creditRule = creditRule;
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
