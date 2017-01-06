package com.hg.dqsj.base.entity;

import com.hg.core.entity.BaseInfoEntity;

public class FePic extends BaseInfoEntity{
	private String objType;//所属类别
	private String objId;//所属ID
	private String picUrl;//图片路径
	private String picName;//图片名称
	private int picOrder;//排序
	private String mainPicFlag;//主图片标识
	private String remark;//备注
	
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public int getPicOrder() {
		return picOrder;
	}
	public void setPicOrder(int picOrder) {
		this.picOrder = picOrder;
	}
	public String getMainPicFlag() {
		return mainPicFlag;
	}
	public void setMainPicFlag(String mainPicFlag) {
		this.mainPicFlag = mainPicFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
