package com.hg.dqsj.vote.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.vote.dao.VoteDao;
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
			 List<Vote> list = voteDao.selectAllVote(param);
			 for(Vote vo : list){
				 Timestamp currentTime = new Timestamp(System.currentTimeMillis());
					Timestamp startTime =Timestamp.valueOf(vo.getVoteStartTime());
					Timestamp endTime= Timestamp.valueOf(vo.getVoteEndTime());
					String flag = vo.getStateFlag();
					if (flag.equals("1")) {
						if (startTime.getTime()<currentTime.getTime() && currentTime.getTime()<endTime.getTime()) {
							//活动进行中
							vo.setStateStr("投票进行中···");
						}else if(startTime.getTime()>currentTime.getTime()){
							//已结束或未开始
							vo.setStateStr("投票未开始");
						}else if(currentTime.getTime()>endTime.getTime()){
							//已结束或未开始
							vo.setStateStr("投票已结束");
						}else {
							vo.setStateStr("投票进行中···");
						}
					}else if (flag.equals("2")) {
							vo.setStateStr("投票已结束");
						}else {
							vo.setStateStr("投票进行中···");
						}
					
					
			 }
			 return list;
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
	//修改投票规则
	public void updateRule(Vote vote){
		voteDao.updateRule(vote);
	}
	
}
