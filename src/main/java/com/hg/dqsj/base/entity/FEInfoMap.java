package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

public class FEInfoMap extends BaseInfoEntity {
	private String infoMapType; // 资讯映射类型
	private String typeId; // 类别ID
	private String infoId; // 资讯信息ID
	private Integer infoMapOrder; // 排序
	private String remark; // 备注

	public String getInfoMapType() {
		return infoMapType;
	}

	public void setInfoMapType(String infoMapType) {
		this.infoMapType = infoMapType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Integer getInfoMapOrder() {
		return infoMapOrder;
	}

	public void setInfoMapOrder(Integer infoMapOrder) {
		this.infoMapOrder = infoMapOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
