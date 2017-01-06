package com.hg.dqsj.love.center.order.view;

import java.util.List;

import com.hg.dqsj.love.center.order.entity.OfOrder;

/**
 * 功能：订单详细信息类
 * 
 * @author 吴晓敏
 *
 */
public class VOfOrder extends OfOrder {
	private String orderTypeCodeStr; // 订单类型
	private String orderState; // 订单状态
	private String useDate; // 使用时间（入园时间）
	private String isRefundFlag; // 是否可退款标识[0:否][1:是]
	private String linkUserName; // 联系人名称
	private String linkUserPhone; // 联系人手机号
	private String checkInDate; // 入住日期
	private String checkOutDate; // 离开日期
	private Double goodsNum; // 商品数量
	private Integer refundGoodsNum;//退款商品数量
	private Double goodsPrice; // 商品价格
	private String goodsInfo; // 商品信息
	private String goodsId; // 商品ID
	private String goodsName; // 商品名称
	private String guestRoomId; // 客房ID
	private String guestRoomName; // 客房名称
	private String deductCredit; // 扣除积分
	private String isWorthMoney; // 抵消金额
	private String payTime; // 付款时间
	private List<VOfOrderGoods> goodsDetails; // 订单商品信息
	
	

	public Integer getRefundGoodsNum() {
		return refundGoodsNum;
	}

	public void setRefundGoodsNum(Integer refundGoodsNum) {
		this.refundGoodsNum = refundGoodsNum;
	}

	public String getOrderTypeCodeStr() {
		return orderTypeCodeStr;
	}

	public void setOrderTypeCodeStr(String orderTypeCodeStr) {
		this.orderTypeCodeStr = orderTypeCodeStr;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getIsRefundFlag() {
		return isRefundFlag;
	}

	public void setIsRefundFlag(String isRefundFlag) {
		this.isRefundFlag = isRefundFlag;
	}

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}

	public String getLinkUserPhone() {
		return linkUserPhone;
	}

	public void setLinkUserPhone(String linkUserPhone) {
		this.linkUserPhone = linkUserPhone;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Double getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGuestRoomId() {
		return guestRoomId;
	}

	public void setGuestRoomId(String guestRoomId) {
		this.guestRoomId = guestRoomId;
	}

	public String getGuestRoomName() {
		return guestRoomName;
	}

	public void setGuestRoomName(String guestRoomName) {
		this.guestRoomName = guestRoomName;
	}

	public String getDeductCredit() {
		return deductCredit;
	}

	public void setDeductCredit(String deductCredit) {
		this.deductCredit = deductCredit;
	}

	public String getIsWorthMoney() {
		return isWorthMoney;
	}

	public void setIsWorthMoney(String isWorthMoney) {
		this.isWorthMoney = isWorthMoney;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public List<VOfOrderGoods> getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(List<VOfOrderGoods> goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

}
