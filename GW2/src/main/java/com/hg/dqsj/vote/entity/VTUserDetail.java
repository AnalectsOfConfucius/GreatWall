package com.hg.dqsj.vote.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 * 功能：用户投票详情POJO类
 * 
 * @author joe
 *
 */
public class VTUserDetail extends BaseInfoEntity {
	private String voteUserId; // 投票用户ID
	private String voteId; //投票id
	private String voteOptionId; // 投票选项id	
	private String  remark ;//备注
	public String getVoteUserId() {
		return voteUserId;
	}
	public void setVoteUserId(String voteUserId) {
		this.voteUserId = voteUserId;
	}
	public String getVoteId() {
		return voteId;
	}
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}
	public String getVoteOptionId() {
		return voteOptionId;
	}
	public void setVoteOptionId(String voteOptionId) {
		this.voteOptionId = voteOptionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
