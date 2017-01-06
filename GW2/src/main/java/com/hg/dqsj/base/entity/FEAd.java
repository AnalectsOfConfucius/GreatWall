package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：广告POJO类
 * 
 * @author 吴晓敏
 *
 */
public class FEAd extends BaseInfoEntity {
	private String adTitle; // 标题
	private String typeId; // 所属类别ID
	private String linkTypeId; // 链接类别ID
	private String linkObjId; // 链接目标ID
	private String adMiniPicUrl; // 广告缩略图片
	private String adUrl; // 广告URL
	private Integer adOrder; // 排序
	private String remark; // 备注

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(String linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public String getLinkObjId() {
		return linkObjId;
	}

	public void setLinkObjId(String linkObjId) {
		this.linkObjId = linkObjId;
	}

	public String getAdMiniPicUrl() {
		return adMiniPicUrl;
	}

	public void setAdMiniPicUrl(String adMiniPicUrl) {
		this.adMiniPicUrl = adMiniPicUrl;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public Integer getAdOrder() {
		return adOrder;
	}

	public void setAdOrder(Integer adOrder) {
		this.adOrder = adOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
