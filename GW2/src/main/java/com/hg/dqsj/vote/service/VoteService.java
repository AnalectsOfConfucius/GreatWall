package com.hg.dqsj.vote.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.vote.entity.VTUserDetail;
import com.hg.dqsj.vote.entity.Vote;
import com.hg.dqsj.vote.entity.VoteOption;

/**
 * 功能：资讯信息SERVICE接口
 * 
 * @author joe
 *
 */
public interface VoteService {
	  //新增投票基础内容
	public void saveBase(Vote vote);
	 //新增投票基础内容
	public void updateBase(Vote vote);
	//修改投票规则
	public void updateRule(Vote vote);
	//结束投票
	public void shutDown(Map<String,String> param);
	//查询投票选项
	public List<Vote> selectAllVote(Map<String,String> param);
	 //新增投票
	public void saveUserDetail(VTUserDetail ud);
}
