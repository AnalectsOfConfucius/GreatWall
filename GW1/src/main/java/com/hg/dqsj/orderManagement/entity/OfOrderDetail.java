package com.hg.dqsj.orderManagement.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：订单详情POJO类
 * 
 * @author 吴晓敏
 *
 */
public class OfOrderDetail extends BaseInfoEntity {
	private String orderId; // 订单ID
	private String serviceId; // 服务点ID
	private String getGoodsUserName; // 取餐人姓名
	private String getGoodsUserPhone; // 取餐人电话
	private String getGoodsDate; // 取餐日
	private String getGoodsTime; // 取餐时间
	private String getGoodsRemark; // 取餐备注

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getGetGoodsUserName() {
		return getGoodsUserName;
	}

	public void setGetGoodsUserName(String getGoodsUserName) {
		this.getGoodsUserName = getGoodsUserName;
	}

	public String getGetGoodsUserPhone() {
		return getGoodsUserPhone;
	}

	public void setGetGoodsUserPhone(String getGoodsUserPhone) {
		this.getGoodsUserPhone = getGoodsUserPhone;
	}

	public String getGetGoodsDate() {
		return getGoodsDate;
	}

	public void setGetGoodsDate(String getGoodsDate) {
		this.getGoodsDate = getGoodsDate;
	}

	public String getGetGoodsTime() {
		return getGoodsTime;
	}

	public void setGetGoodsTime(String getGoodsTime) {
		this.getGoodsTime = getGoodsTime;
	}

	public String getGetGoodsRemark() {
		return getGoodsRemark;
	}

	public void setGetGoodsRemark(String getGoodsRemark) {
		this.getGoodsRemark = getGoodsRemark;
	}
}
