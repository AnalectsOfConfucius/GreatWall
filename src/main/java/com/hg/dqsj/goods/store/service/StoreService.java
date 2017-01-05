package com.hg.dqsj.goods.store.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.goods.store.entity.Store;

public interface StoreService {
	//查询列表
	public List<Store> selectStore(Map<String,String> param);
	//查询总条数
	public int getCount(Map<String,String> param);
	//查询详情
	public Store selectById(Map<String,String> param);
	//添加
	public void save(Store store,List<FePic> plist);
	//修改
	public void update(Store store,List<FePic> plist);
	//逻辑删除
	public void delete(Map<String,String> param);
}
