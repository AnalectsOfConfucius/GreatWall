package com.hg.dqsj.system.role.entity;

import java.util.List;

/**
 * 功能：系统菜单信息POJO类
 * 
 * @author Administrator
 *
 */
public class RoleMenu{
	private String menuId;// 菜单名称
	private String menuName;// 菜单名称
	private String parentId;// 父类菜单ID
	private String menuUrl;// 菜单URL地址
	private String picUrl;// 菜单图标名称
	private String menuCss;// 样式CSS
	private Integer menuOrder;// 排序号
	private String remark; // 备注
	private String ischeckm; // 是否选中
	private List<Operate> olist;//操作列表
	
	public List<Operate> getOlist() {
		return olist;
	}

	public void setOlist(List<Operate> olist) {
		this.olist = olist;
	}

	public String getIscheckm() {
		return ischeckm;
	}

	public void setIscheckm(String ischeckm) {
		this.ischeckm = ischeckm;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}
	
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}
	
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getMenuCss() {
		return menuCss;
	}
	
	public void setMenuCss(String menuCss) {
		this.menuCss = menuCss;
	}
	
	public Integer getMenuOrder() {
		return menuOrder;
	}
	
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
