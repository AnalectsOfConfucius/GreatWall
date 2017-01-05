package com.hg.dqsj.vote.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.vote.entity.VoteOption;

/**
 * 功能：投票DAO接口
 * 
 * @author joe
 *
 */
public interface VoteOptionDao {
	/**
	 * 根据查询条件查询投票选项
	 * 
	 * @param map
	 * @return
	 */
	public List<VoteOption> selectByVoteId(Map<String, String> map);
	/**
	 * 根据查询条件查询投票选项数目
	 * 
	 * @param map
	 * @return
	 */
	public Integer countByVoteId(Map<String, Object> param);
	
	/**
	 *新增投票选项
	 * 	
	 */
	public void saveOption(VoteOption option);
	//删除投票选项
	public void deleteOption(Map<String,String> param);
	//修改投票选项
	public void updateOption(VoteOption option);
}
