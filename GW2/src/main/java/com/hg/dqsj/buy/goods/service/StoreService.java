package com.hg.dqsj.buy.goods.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.buy.goods.entity.Store;

public interface StoreService {
	//查询列表
	public List<Store> selectStore(Map<String,String> param);
	//查询总条数
	public int getCount(Map<String,String> param);
	//查询详情
	public Store selectById(Map<String,String> param);
	
}
