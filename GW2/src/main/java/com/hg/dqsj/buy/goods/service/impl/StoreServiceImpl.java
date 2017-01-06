package com.hg.dqsj.buy.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.base.dao.FeGoodsDao;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.buy.goods.dao.StoreDao;
import com.hg.dqsj.buy.goods.entity.Store;
import com.hg.dqsj.buy.goods.service.StoreService;
@Service
public class StoreServiceImpl implements StoreService {
	@Autowired
	private StoreDao dao;
	
	@Override
	public List<Store> selectStore(Map<String, String> param) {
		return dao.selectStore(param);
	}

	@Override
	public int getCount(Map<String, String> param) {
		return dao.getCount(param);
	}

	@Override
	public Store selectById(Map<String, String> param) {
		return dao.selectById(param);
	}

}
