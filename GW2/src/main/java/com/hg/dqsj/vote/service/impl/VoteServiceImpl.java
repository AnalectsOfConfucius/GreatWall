package com.hg.dqsj.vote.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.vote.dao.VoteDao;
import com.hg.dqsj.vote.entity.VTUserDetail;
import com.hg.dqsj.vote.entity.Vote;
import com.hg.dqsj.vote.service.VoteService;

/**
 * 功能：投票SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class VoteServiceImpl implements VoteService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private VoteDao voteDao; // 投票DAO接口
	//结束投票
	public void shutDown(Map<String,String> param){
		voteDao.shutDown(param);
	}
        /**
          * 查询所有投票
          */
		public List<Vote> selectAllVote(Map<String,String> param){
			return voteDao.selectAllVote(param);
		}
	/**
	 * 新增投票基础内容
	 * 
	 */
	@Override
		public void saveBase(Vote vote){
			voteDao.saveBase(vote);
		}
	/**
	* 修改投票基础内容
	*
	*/
	@Override
	public void updateBase(Vote vote) {
		voteDao.updateBase(vote);
	}
	/**
	 * 新增用户投票详情
	 * 
	 */
	@Override
		public void saveUserDetail(VTUserDetail ud){
			voteDao.saveUserDetail(ud);
		}
	//修改投票规则
	public void updateRule(Vote vote){
		voteDao.updateRule(vote);
	}
	
}
