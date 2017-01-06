package com.hg.dqsj.vote.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.vote.entity.VoteOption;


/**
 * 功能：投票选项SERVICE接口
 * 
 * @author joe
 *
 */
public interface VoteOptionService {


	//查询投票选项
	public List<VoteOption> selectByVoteId(Map<String,String> param);
	
	//新增投票选项
	public void saveOption(VoteOption option);
	//修改投票选项
	public void updateOption(VoteOption option);
	//删除投票选项
	public void deleteOption(Map<String,String> param);
	/**
	 *查询投票选项数量
	 */
	public Integer countByVoteId(Map<String, Object> map);
}
