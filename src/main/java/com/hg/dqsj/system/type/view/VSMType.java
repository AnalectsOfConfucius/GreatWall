package com.hg.dqsj.system.type.view;

import com.hg.dqsj.system.type.entity.SMType;

/**
 * 功能：系统类别视图类
 * 
 * @author 吴晓敏
 *
 */
public class VSMType extends SMType {
	private String typeFlagStr; // 类别标识名称

	public String getTypeFlagStr() {
		return typeFlagStr;
	}

	public void setTypeFlagStr(String typeFlagStr) {
		this.typeFlagStr = typeFlagStr;
	}
}
