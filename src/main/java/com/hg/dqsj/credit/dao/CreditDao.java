package com.hg.dqsj.credit.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.credit.entity.Credit;
import com.hg.dqsj.credit.entity.CreditMoney;
import com.hg.dqsj.credit.entity.UserCredit;


public interface CreditDao {
	//消费送积分列表
	public List<Credit> selectCredit(Map<String,String> param);
	//总条数
	public int getCount(Map<String,String> param);
	//新增
	public void insertCredit(Credit credit);
	//修改
	public void updateCredit(Credit credit);
	//删除
	public void deleteCredit(Map<String,String> param);
	
	//积分抵金额设置列表
	public List<CreditMoney> selectCreditMoney(Map<String,String> param);
	//积分抵金额设置总条数
	public int getCreditMoneyCount(Map<String,String> param);
	//积分抵金额设置新增
	public void insertCreditMoney(CreditMoney creditMoney);
	//积分抵金额设置修改
	public void updateCreditMoney(CreditMoney creditMoney);
	//积分抵金额设置删除
	public void deleteCreditMoney(Map<String,String> param);
	
	//用户积分列表
	public List<UserCredit> selectUserCredit(Map<String,String> param);
	//用户积分总条数
	public int getUserCreditCount(Map<String,String> param);
	
}
