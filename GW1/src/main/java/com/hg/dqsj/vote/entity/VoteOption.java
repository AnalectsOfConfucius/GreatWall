package com.hg.dqsj.vote.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：投票选项POJO类
 * 
 * @author joe
 *
 */
public class VoteOption extends BaseInfoEntity {
	private String voteId; // 投票ID
	private String voteOptionNo; //投票选项编号
	private String voteOptionTitle; // 投票选项标题
	private String voteOptionPicUrl; // 投票选项封面地址
	private byte[] voteOptionContent; //详细介绍
	private Integer voteOrder;//投票排序
	private Integer votedCount;//获得票数
	private String  remark ;//备注
	
	
	public Integer getVotedCount() {
		return votedCount;
	}
	public void setVotedCount(Integer votedCount) {
		this.votedCount = votedCount;
	}
	public Integer getVoteOrder() {
		return voteOrder;
	}
	public void setVoteOrder(Integer voteOrder) {
		this.voteOrder = voteOrder;
	}
	public String getVoteId() {
		return voteId;
	}
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}
	public String getVoteOptionNo() {
		return voteOptionNo;
	}
	public void setVoteOptionNo(String voteOptionNo) {
		this.voteOptionNo = voteOptionNo;
	}
	public String getVoteOptionTitle() {
		return voteOptionTitle;
	}
	public void setVoteOptionTitle(String voteOptionTitle) {
		this.voteOptionTitle = voteOptionTitle;
	}
	public String getVoteOptionPicUrl() {
		return voteOptionPicUrl;
	}
	public void setVoteOptionPicUrl(String voteOptionPicUrl) {
		this.voteOptionPicUrl = voteOptionPicUrl;
	}
	public byte[] getVoteOptionContent() {
		return voteOptionContent;
	}
	public void setVoteOptionContent(byte[] voteOptionContent) {
		this.voteOptionContent = voteOptionContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
