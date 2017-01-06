package com.hg.dqsj.system.menu.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：系统菜单信息POJO类
 * 
 * @author Administrator
 *
 */
public class Menu extends BaseInfoEntity {
	private String menuName;// 菜单名称
	private String parentId;// 父类菜单ID
	private String menuUrl;// 菜单URL地址
	private String picUrl;// 菜单图标名称
	private String menuCss;// 样式CSS
	private Integer menuOrder;// 排序号
	private String remark; // 备注
	
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
