package com.hg.dqsj.credit.entity;

import com.hg.core.entity.BaseInfoEntity;

public class UserCredit extends BaseInfoEntity{
	private String userName;//用户名
	private String userPhone;//手机号码
	private String userId;//用户ID
	private Double creditTotal;//总积分
	private int rowLockNum;//行锁号
	
	private String getFlag;//获得积分标识
	//消费送积分
	private String giveCreditId;//消费送积分ID
	private Double consumeAmount;//消费金额
	private Double giveCredit;//赠送积分
	//活动送积分
	private String newsId;//活动送积分ID
	private String newsTypeCode;//消息类别代码
	private String newsTitle;//标题
	private String newsComment;//内容
	private String credit;//赠送积分数
	private String receiveFlag;//接收标识
	private String sendFlag;//发送标识
	
	private Double creditNum;//获取的积分
	private String isReceive;//是否已领取
	private String receiveTime;//领取时间
	private String remark;//备注
	
	public String getReceiveFlag() {
		return receiveFlag;
	}
	public void setReceiveFlag(String receiveFlag) {
		this.receiveFlag = receiveFlag;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getCreditTotal() {
		return creditTotal;
	}
	public void setCreditTotal(Double creditTotal) {
		this.creditTotal = creditTotal;
	}
	public int getRowLockNum() {
		return rowLockNum;
	}
	public void setRowLockNum(int rowLockNum) {
		this.rowLockNum = rowLockNum;
	}
	public String getGetFlag() {
		return getFlag;
	}
	public void setGetFlag(String getFlag) {
		this.getFlag = getFlag;
	}
	public String getGiveCreditId() {
		return giveCreditId;
	}
	public void setGiveCreditId(String giveCreditId) {
		this.giveCreditId = giveCreditId;
	}
	public Double getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public Double getGiveCredit() {
		return giveCredit;
	}
	public void setGiveCredit(Double giveCredit) {
		this.giveCredit = giveCredit;
	}
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getNewsTypeCode() {
		return newsTypeCode;
	}
	public void setNewsTypeCode(String newsTypeCode) {
		this.newsTypeCode = newsTypeCode;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsComment() {
		return newsComment;
	}
	public void setNewsComment(String newsComment) {
		this.newsComment = newsComment;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public Double getCreditNum() {
		return creditNum;
	}
	public void setCreditNum(Double creditNum) {
		this.creditNum = creditNum;
	}
	public String getIsReceive() {
		return isReceive;
	}
	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
