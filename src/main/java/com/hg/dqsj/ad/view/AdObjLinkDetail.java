package com.hg.dqsj.ad.view;

/**
 * 功能：广告目标连接类
 * 
 * @author 吴晓敏
 *
 */
public class AdObjLinkDetail {
	private String objId; // 目标ID
	private String objParentName; // 目标所属名称
	private String objName; // 目标名称
	private String objBrief; // 目标简介
	private String objFEAction; // 目标前台访问链接

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjParentName() {
		return objParentName;
	}

	public void setObjParentName(String objParentName) {
		this.objParentName = objParentName;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getObjBrief() {
		return objBrief;
	}

	public void setObjBrief(String objBrief) {
		this.objBrief = objBrief;
	}

	public String getObjFEAction() {
		return objFEAction;
	}

	public void setObjFEAction(String objFEAction) {
		this.objFEAction = objFEAction;
	}

}
