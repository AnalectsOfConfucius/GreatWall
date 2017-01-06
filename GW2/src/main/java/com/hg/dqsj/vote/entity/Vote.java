package com.hg.dqsj.vote.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：投票POJO类
 * 
 * @author joe
 *
 */
public class Vote extends BaseInfoEntity {
	private String voteTitle; // 投票标题
	private String voteDescribe; //投票描述
	private String votePicUrl; // 封面图
	private String voteBackPicUrl;//详情页背景图
	private String voteStartTime; // 投票开始时间
	private String voteEndTime; // 投票结束时间
	private String stateFlag; // 投票状态标识
	private String userTypeFlag;//用户类型
	private String isLimitUser;//限制用户[0:对用户开放][1:不对用户开放]
	private String  voteRule ;//规则说明
	private String  remark ;//备注
	private Integer voteOrder;//投票排序
	private Integer periodHour;//用户投票周期设置
	private Integer voteNumber;//周期内投票数设置
	private Integer optionCount;//该投票的选项数量
	private Integer votedCount;//该投票的总投票数
	private byte[] voteContent;//投票详情介绍
	
	
	public String getVoteBackPicUrl() {
		return voteBackPicUrl;
	}
	public void setVoteBackPicUrl(String voteBackPicUrl) {
		this.voteBackPicUrl = voteBackPicUrl;
	}
	public Integer getVoteOrder() {
		return voteOrder;
	}
	public void setVoteOrder(Integer voteOrder) {
		this.voteOrder = voteOrder;
	}
	public Integer getVotedCount() {
		return votedCount;
	}
	public void setVotedCount(Integer votedCount) {
		this.votedCount = votedCount;
	}
	public Integer getOptionCount() {
		return optionCount;
	}
	public void setOptionCount(Integer optionCount) {
		this.optionCount = optionCount;
	}
	public String getVoteTitle() {
		return voteTitle;
	}
	public void setVoteTitle(String voteTitle) {
		this.voteTitle = voteTitle;
	}
	public String getVoteDescribe() {
		return voteDescribe;
	}
	public void setVoteDescribe(String voteDescribe) {
		this.voteDescribe = voteDescribe;
	}
	public String getVotePicUrl() {
		return votePicUrl;
	}
	public void setVotePicUrl(String votePicUrl) {
		this.votePicUrl = votePicUrl;
	}
	public String getVoteStartTime() {
		return voteStartTime;
	}
	public void setVoteStartTime(String voteStartTime) {
		this.voteStartTime = voteStartTime;
	}
	public String getVoteEndTime() {
		return voteEndTime;
	}
	public void setVoteEndTime(String voteEndTime) {
		this.voteEndTime = voteEndTime;
	}
	public String getStateFlag() {
		return stateFlag;
	}
	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}
	public String getUserTypeFlag() {
		return userTypeFlag;
	}
	public void setUserTypeFlag(String userTypeFlag) {
		this.userTypeFlag = userTypeFlag;
	}
	public String getIsLimitUser() {
		return isLimitUser;
	}
	public void setIsLimitUser(String isLimitUser) {
		this.isLimitUser = isLimitUser;
	}
	public String getVoteRule() {
		return voteRule;
	}
	public void setVoteRule(String voteRule) {
		this.voteRule = voteRule;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPeriodHour() {
		return periodHour;
	}
	public void setPeriodHour(Integer periodHour) {
		this.periodHour = periodHour;
	}
	public Integer getVoteNumber() {
		return voteNumber;
	}
	public void setVoteNumber(Integer voteNumber) {
		this.voteNumber = voteNumber;
	}
	public byte[] getVoteContent() {
		return voteContent;
	}
	public void setVoteContent(byte[] voteContent) {
		this.voteContent = voteContent;
	}
	
	
	
}
