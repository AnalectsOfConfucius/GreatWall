package com.hg.dqsj.system.role.entity;

/**
 * 功能：系统菜单信息POJO类
 * 
 * @author Administrator
 *
 */
public class Operate{
	private String operateId;// 操作Id
	private String opCode;// 操作代码
	private String opName; // 操作名称
	private String opOrder; // 排序
	private String remark; // 备注
	private String ischecko; // 是否选中
	
	public String getIschecko() {
		return ischecko;
	}
	public void setIschecko(String ischecko) {
		this.ischecko = ischecko;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpOrder() {
		return opOrder;
	}
	public void setOpOrder(String opOrder) {
		this.opOrder = opOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
