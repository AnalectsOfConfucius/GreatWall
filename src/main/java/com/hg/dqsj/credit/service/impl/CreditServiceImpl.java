package com.hg.dqsj.credit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.credit.dao.CreditDao;
import com.hg.dqsj.credit.entity.Credit;
import com.hg.dqsj.credit.entity.CreditMoney;
import com.hg.dqsj.credit.entity.UserCredit;
import com.hg.dqsj.credit.service.CreditService;

@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired
	private CreditDao dao;
	
	@Override
	public List<Credit> selectCredit(Map<String, String> param) {
		return dao.selectCredit(param);
	}

	@Override
	public int getCount(Map<String, String> param) {
		return dao.getCount(param);
	}

	@Override
	public void insertCredit(Credit credit) {
		dao.insertCredit(credit);
	}

	@Override
	public void updateCredit(Credit credit) {
		dao.updateCredit(credit);
	}

	@Override
	public void deleteCredit(Map<String,String> param) {
		dao.deleteCredit(param);
	}

	@Override
	public List<CreditMoney> selectCreditMoney(Map<String, String> param) {
		return dao.selectCreditMoney(param);
	}

	@Override
	public int getCreditMoneyCount(Map<String, String> param) {
		return dao.getCreditMoneyCount(param);
	}

	@Override
	public void insertCreditMoney(CreditMoney creditMoney) {
		dao.insertCreditMoney(creditMoney);
	}

	@Override
	public void updateCreditMoney(CreditMoney creditMoney) {
		dao.updateCreditMoney(creditMoney);
	}

	@Override
	public void deleteCreditMoney(Map<String, String> param) {
		dao.deleteCreditMoney(param);
	}

	@Override
	public List<UserCredit> selectUserCredit(Map<String, String> param) {
		return dao.selectUserCredit(param);
	}

	@Override
	public int getUserCreditCount(Map<String, String> param) {
		return dao.getUserCreditCount(param);
	}

}
