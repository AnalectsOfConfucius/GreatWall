package com.hg.dqsj.vote.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.vote.dao.VoteOptionDao;
import com.hg.dqsj.vote.entity.VoteOption;
import com.hg.dqsj.vote.service.VoteOptionService;

/**
 * 功能：资讯信息SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class VoteOptionServiceImpl implements VoteOptionService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private VoteOptionDao voteOptionDao; // DAO接口
	//删除投票选项
	public void deleteOption(Map<String,String> param){
		voteOptionDao.deleteOption(param);
	}
	//修改投票选项
	public void updateOption(VoteOption option){
		voteOptionDao.updateOption(option);
	}
	/**
	 * 查找投票选项
	 */
	@Override
	public List<VoteOption> selectByVoteId(Map<String, String> param) {
		return voteOptionDao.selectByVoteId(param);
	}
	/**
	 * 新增投票选项
	 */
	@Override
	public void saveOption(VoteOption option) {
		voteOptionDao.saveOption(option);
	}
	/**
	 *查询投票选项数量
	 */
	@Override
	public Integer countByVoteId(Map<String, Object> map) {
		return voteOptionDao.countByVoteId(map);
	}
	
}
