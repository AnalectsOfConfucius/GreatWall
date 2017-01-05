package com.hg.dqsj.base.view;

import com.hg.dqsj.base.entity.FEInfo;

/**
 * 功能：资讯视图类
 * 
 * @author 吴晓敏
 *
 */
public class VFEInfo extends FEInfo {
	private String infoMapId; // 资讯映射ID
	private String infoMapType; // 资讯映射类型
	private String typeId; // 类别ID
	private String typeCode; // 类别代码
	private String infoMapOrder; // 资讯映射排序
	private String isRecommendStr; // 是否推荐
	private String isReleaseStr; // 是否发布
	private String infoContentStr; // 资讯信息内容

	public String getInfoMapId() {
		return infoMapId;
	}

	public void setInfoMapId(String infoMapId) {
		this.infoMapId = infoMapId;
	}

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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getInfoMapOrder() {
		return infoMapOrder;
	}

	public void setInfoMapOrder(String infoMapOrder) {
		this.infoMapOrder = infoMapOrder;
	}

	public String getIsRecommendStr() {
		return isRecommendStr;
	}

	public void setIsRecommendStr(String isRecommendStr) {
		this.isRecommendStr = isRecommendStr;
	}

	public String getIsReleaseStr() {
		return isReleaseStr;
	}

	public void setIsReleaseStr(String isReleaseStr) {
		this.isReleaseStr = isReleaseStr;
	}

	public String getInfoContentStr() {
		return infoContentStr;
	}

	public void setInfoContentStr(String infoContentStr) {
		this.infoContentStr = infoContentStr;
	}

}
