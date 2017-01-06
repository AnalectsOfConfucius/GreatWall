package com.hg.dqsj.vote.view;

import com.hg.dqsj.vote.entity.VoteOption;

public class VofVoteOption extends VoteOption {
	private Integer rank; //排名
	private Integer toPrev;//距离上一名差多少票
	
	
	public Integer getToPrev() {
		return toPrev;
	}

	public void setToPrev(Integer toPrev) {
		this.toPrev = toPrev;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	

	
    
}
