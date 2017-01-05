package com.hg.dqsj.system.type.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：系统类别POJO类
 * 
 * @author 吴晓敏
 *
 */
public class SMType extends BaseInfoEntity {
	private String typeFlag; // 类别标识
	private String typeCode; // 类别代码
	private String typeName; // 类别名称
	private String feAction; // 前台链接
	private Integer typeOrder; // 排序
	private String remark; // 备注

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFeAction() {
		return feAction;
	}

	public void setFeAction(String feAction) {
		this.feAction = feAction;
	}

	public Integer getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
