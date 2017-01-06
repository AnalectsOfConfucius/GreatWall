package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：资讯POJO类
 * 
 * @author 吴晓敏
 *
 */
public class FEInfo extends BaseInfoEntity {
	private String infoTitle; // 资讯信息标题
	private String infoPicUrl; // 资讯信息封面图
	private String infoBrief; // 资讯信息简介
	private byte[] infoContent; // 资讯信息内容
	private String isRecommend; // 是否推荐
	private String isRelease; // 是否发布
	private String releaseDate; // 发布日期
	private String remark; // 备注

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoPicUrl() {
		return infoPicUrl;
	}

	public void setInfoPicUrl(String infoPicUrl) {
		this.infoPicUrl = infoPicUrl;
	}

	public String getInfoBrief() {
		return infoBrief;
	}

	public void setInfoBrief(String infoBrief) {
		this.infoBrief = infoBrief;
	}

	public byte[] getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(byte[] infoContent) {
		this.infoContent = infoContent;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
