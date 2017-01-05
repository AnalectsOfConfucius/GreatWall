package com.hg.dqsj.vote.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.vote.entity.Vote;

/**
 * 功能：投票DAO接口
 * 
 * @author joe
 *
 */
public interface VoteDao {
	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Vote> selectByCriterias(Map<String, String> map);
    //新增投票基础内容
	public void saveBase(Vote vote);
	//修改投票基础内容
	public void updateBase(Vote vote);
	//修改投票规则
	public void updateRule(Vote vote);
	//查询投票选项
	public List<Vote> selectAllVote(Map<String,String> param);
	//结束投票
	public void shutDown(Map<String,String> param);
}
