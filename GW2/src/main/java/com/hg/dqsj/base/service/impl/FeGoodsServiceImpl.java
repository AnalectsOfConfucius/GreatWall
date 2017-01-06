package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.base.dao.FeGoodsDao;
import com.hg.dqsj.base.entity.EvalInfo;
import com.hg.dqsj.base.entity.FeGoods;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
@Service
public class FeGoodsServiceImpl implements FeGoodsService {
	@Autowired
	private FeGoodsDao dao;
	
	@Override
	public List<FeGoodsInfo> selectByCriterias(Map<String, String> param) {
		return dao.selectByCriterias(param);
	}

	@Override
	public int countByCriterias(Map<String, String> param) {
		return dao.countByCriterias(param);
	}

	@Override
	public FeGoodsInfo selectById(Map<String, String> param) {
		return dao.selectById(param);
	}

	@Override
	@Transactional
	public void save(FeGoods feGoods,List<FePic> plist) {
		dao.save(feGoods);
		if(null!=plist && plist.size()>0){
			for (FePic fePic : plist) {
				dao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void update(FeGoods feGoods,List<FePic> plist) {
		dao.update(feGoods);
		Map<String,String> param = new HashMap<String, String>();
		param.put("objId", feGoods.getId());
		dao.deletePic(param);
		if(null!=plist && plist.size()>0){
			for (FePic fePic : plist) {
				dao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Map<String,String> param) {
		dao.updateDeleteFlagById(param);
		param.put("objId", param.get("id"));
		dao.updateDeleteFlagPic(param);
	}

	@Override
	public List<FePic> selectPicById(Map<String, String> param) {
		return dao.selectPicById(param);
	}

	@Override
	public void deletePic(Map<String, String> param) {
		dao.deletePic(param);
	}

	@Override
	public List<EvalInfo> selectEval(Map<String, String> param) {
		return dao.selectEval(param);
	}

	@Override
	public int selectEvalCount(Map<String, String> param) {
		return dao.selectEvalCount(param);
	}

}
