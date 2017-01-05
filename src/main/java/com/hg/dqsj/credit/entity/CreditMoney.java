package com.hg.dqsj.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

public class CreditMoney extends BaseInfoEntity{
	private String typeCode;//类别代码
	private Double usableCondition;//可用条件
	private Double isWorthMoney;//可抵金额
	private String creditComment;//积分规则
	private byte[] creditCont;//积分规则
	private String remark;//备注
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Double getUsableCondition() {
		return usableCondition;
	}
	public void setUsableCondition(Double usableCondition) {
		this.usableCondition = usableCondition;
	}
	public Double getIsWorthMoney() {
		return isWorthMoney;
	}
	public void setIsWorthMoney(Double isWorthMoney) {
		this.isWorthMoney = isWorthMoney;
	}
	public String getCreditComment() {
		return creditComment;
	}
	public void setCreditComment(String creditComment) {
		this.creditComment = creditComment;
	}
	public byte[] getCreditCont() {
		return creditCont;
	}
	public void setCreditCont(byte[] creditCont) {
		this.creditCont = creditCont;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
