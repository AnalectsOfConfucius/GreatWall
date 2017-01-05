package com.hg.dqsj.goods.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.base.dao.FeGoodsDao;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.goods.store.dao.StoreDao;
import com.hg.dqsj.goods.store.entity.Store;
import com.hg.dqsj.goods.store.service.StoreService;
@Service
public class StoreServiceImpl implements StoreService {
	@Autowired
	private StoreDao dao;
	@Autowired
	private FeGoodsDao goodsDao;
	
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

	@Override
	@Transactional
	public void save(Store store,List<FePic> plist) {
		dao.save(store);
		if(null!=plist && plist.size()>0){
			for (FePic fePic : plist) {
				goodsDao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void update(Store store,List<FePic> plist) {
		dao.update(store);
		Map<String,String> param = new HashMap<String, String>();
		param.put("objId", store.getId());
		goodsDao.deletePic(param);
		if(null!=plist && plist.size()>0){
			for (FePic fePic : plist) {
				goodsDao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Map<String, String> param) {
		dao.delete(param);
		param.put("objId", param.get("id"));
		goodsDao.updateDeleteFlagPic(param);
	}

}
