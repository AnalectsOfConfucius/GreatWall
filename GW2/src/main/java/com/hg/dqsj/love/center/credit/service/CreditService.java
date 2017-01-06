package com.hg.dqsj.love.center.credit.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.dqsj.love.center.credit.entity.CreditMoney;
import com.hg.dqsj.love.center.credit.entity.CreditUser;

/**
 * 功能：资讯信息SERVICE接口
 * 
 * @author joe
 *
 */
public interface CreditService {

	//查询个人积分
	public CreditUser selectCreditUser(Map<String,String> param);
	//查询个人获得积分明细
	public  Map<String, Object>  selectCreditGet(HttpServletRequest request);
	//查询个人消费积分明细
	public  Map<String, Object>  selectCreditUse(HttpServletRequest request);
	//积分抵金额
	public CreditMoney selectCreditMoney(Map<String,String> param);
}
