package com.hg.dqsj.vote.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.RequestUtils;
import com.hg.dqsj.vote.dao.VoteOptionDao;
import com.hg.dqsj.vote.entity.VoteOption;
import com.hg.dqsj.vote.service.VoteOptionService;
import com.hg.dqsj.vote.view.VofVoteOption;

/**
 * 功能：投票SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class VoteOptionServiceImpl implements VoteOptionService {
	private Logger logger = Logger.getLogger(getClass());
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
	public Map<String, Object> selectByVoteId(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		Map<String, Object> result = new HashMap<>();
		String filterKey = (String)map.get("filterKey");
		String isLimitUser= (String)map.get("isLimitUser");
		System.out.println(">>>>>>>>>>>>>>>>>>"+filterKey);
		System.out.println(">>>>>>>>>>>>>>>>>>"+isLimitUser);
		String currentRecords = (map.get("currentRecords") == null || map.get("currentRecords").equals("")) ? "0" : map.get("currentRecords").toString();//开始行数
		List<VofVoteOption> details = voteOptionDao.selectByVoteId(map);
		List<VofVoteOption> details2 =new ArrayList<>();
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("totalRecords", 0); // 总记录数
		}else if ( details.size()==1){
			details.get(0).setRank(1);
			details.get(0).setToPrev(0);
			result.put("details", details);
			result.put("totalRecords", 1); // 总记录数
		} else {
			
			Collections.sort(details,new Comparator<VofVoteOption>(){
				 public int compare(VofVoteOption arg0, VofVoteOption arg1) {
					 return arg1.getVotedCount().compareTo(arg0.getVotedCount());				          
		            }
			});
			
			for (int i = 0; i < details.size(); i++) {
				details.get(i).setRank(i+1);
				Integer prev =  0;
				if (i!=0) {
                    prev =  details.get(i-1).getVotedCount()-details.get(i).getVotedCount();
				}
				details.get(i).setToPrev(prev);
			}				
			if (filterKey.equals("2")) {
				for (int i = 0; i < 6; i++) {
					if (details.size()>=(Integer.valueOf(currentRecords)+i+1)) {
						details2.add(details.get(Integer.valueOf(currentRecords)+i));
					}
					
				}
				result.put("details", details2);
			}else{
				if (isLimitUser.equals("1")) {
					Collections.sort(details,new Comparator<VofVoteOption>(){
						 public int compare(VofVoteOption arg0, VofVoteOption arg1) {
							 return arg0.getVoteOrder().compareTo(arg1.getVoteOrder());				       
				            }
					});
					for (int i = 0; i < 6; i++) {
						if (details.size()>=(Integer.valueOf(currentRecords)+i+1)) {
							details2.add(details.get(Integer.valueOf(currentRecords)+i));
						}
						
					}
					result.put("details", details2);
				}else{
					Collections.sort(details,new Comparator<VofVoteOption>(){
						 public int compare(VofVoteOption arg0, VofVoteOption arg1) {
							try{
							SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date time0 =sd.parse(arg0.getUpdateDate().toString());
							Date time1= sd.parse(arg1.getUpdateDate().toString());
							System.out.println(">>>>>>>>>>>>>>>time0"+time0.getTime());
							System.out.println(">>>>>>>>>>>>>>>time1"+time1.getTime());
							if (time0.getTime()<time1.getTime()) {
			                	return 1;
							}else if (time0.getTime()==time1.getTime()) {
								return 0;
							}else{
								return -1;
							}
							}catch(Exception e) {
								logger.error(e.getMessage());	
								System.out.println(">>>>>>>>>>>>>>>时间转换出错");
							}
							return 1;    
				            }
					});
					for (int i = 0; i < 6; i++) {
						if (details.size()>=(Integer.valueOf(currentRecords)+i+1)) {
							details2.add(details.get(Integer.valueOf(currentRecords)+i));
						}
						
					}
					result.put("details", details2);
				}
			}
			Integer totalRecords = voteOptionDao.countByVoteId(map);
			result.put("totalRecords", totalRecords); // 总记录数
		}
		return result;
	}
	/**
	 *查询投票选项数量
	 */
	@Override
	public Integer countByVoteId(Map<String, Object> map) {
		return voteOptionDao.countByVoteId(map);
	}
	/**
	 * 新增投票选项
	 */
	@Override
	public void saveOption(VoteOption option) {
		voteOptionDao.saveOption(option);
	}
    
	
}
