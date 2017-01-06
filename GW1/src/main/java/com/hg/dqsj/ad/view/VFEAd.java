package com.hg.dqsj.ad.view;

import com.hg.dqsj.ad.entity.FEAd;

/**
 * 功能：广告视图类
 * 
 * @author 吴晓敏
 *
 */
public class VFEAd extends FEAd {
	private String typeName; // 所属类别名称
	private String linkTypeName; // 链接类别名称
	private String linkObjName; // 目标链接名称

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLinkTypeName() {
		return linkTypeName;
	}

	public void setLinkTypeName(String linkTypeName) {
		this.linkTypeName = linkTypeName;
	}

	public String getLinkObjName() {
		return linkObjName;
	}

	public void setLinkObjName(String linkObjName) {
		this.linkObjName = linkObjName;
	}

}
