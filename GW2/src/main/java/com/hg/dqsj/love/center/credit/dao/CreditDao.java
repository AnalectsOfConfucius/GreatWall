package com.hg.dqsj.love.center.credit.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FEInfo;
import com.hg.dqsj.base.view.VFEInfo;
import com.hg.dqsj.love.center.credit.entity.CreditConsume;
import com.hg.dqsj.love.center.credit.entity.CreditGet;
import com.hg.dqsj.love.center.credit.entity.CreditMoney;
import com.hg.dqsj.love.center.credit.entity.CreditUser;
import com.hg.dqsj.vote.entity.VTUserDetail;
import com.hg.dqsj.vote.entity.Vote;

/**
 * 功能：投票DAO接口
 * 
 * @author joe
 *
 */
public interface CreditDao {
	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Vote> selectByCriterias(Map<String, String> map);

	//查询个人积分
	public CreditUser selectCreditUser(Map<String,String> param);
	//查询个人获得积分明细
	public List<CreditGet> selectCreditGet(Map<String,Object> param);
	//查询个人消费积分明细
	public List<CreditConsume> selectCreditUse(Map<String,Object> param);
	//查询个人获得积分明细数量
	public Integer selectCreditGetCount(Map<String,Object> param);
	//查询个人消费积分明细数量
	public Integer selectCreditUseCount(Map<String,Object> param);
	//积分抵金额
	public CreditMoney selectCreditMoney(Map<String,String> param);
	
	
}
