package com.hg.dqsj.base.view;

import java.util.List;

import com.hg.dqsj.base.entity.FeMenuInfo;

public class VFeMenuInfo extends FeMenuInfo {
	private List<FeMenuInfo> children; // 子菜单

	public List<FeMenuInfo> getChildren() {
		return children;
	}

	public void setChildren(List<FeMenuInfo> children) {
		this.children = children;
	}

}
