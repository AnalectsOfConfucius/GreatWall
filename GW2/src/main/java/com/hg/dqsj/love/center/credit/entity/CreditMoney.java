package com.hg.dqsj.love.center.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：积分抵金额POJO类
 * 
 * @author hlz
 *
 */
public class CreditMoney extends BaseInfoEntity {
	private Integer usableCondition; // 可用条件
	private Integer isWorthMoney; //可抵金额
	
	public Integer getUsableCondition() {
		return usableCondition;
	}
	public void setUsableCondition(Integer usableCondition) {
		this.usableCondition = usableCondition;
	}
	public Integer getIsWorthMoney() {
		return isWorthMoney;
	}
	public void setIsWorthMoney(Integer isWorthMoney) {
		this.isWorthMoney = isWorthMoney;
	}
	
}
