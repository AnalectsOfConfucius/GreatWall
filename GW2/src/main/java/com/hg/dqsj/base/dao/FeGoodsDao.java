package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.EvalInfo;
import com.hg.dqsj.base.entity.FeGoods;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;


public interface FeGoodsDao {
	//列表
	public List<FeGoodsInfo> selectByCriterias(Map<String,String> param);
	//总条数
	public int countByCriterias(Map<String,String> param);
	//详情
	public FeGoodsInfo selectById(Map<String,String> param);
	//保存
	public void save(FeGoods feGoods);
	//修改
	public void update(FeGoods feGoods);
	//逻辑删除
	public void updateDeleteFlagById(Map<String,String> param);
	
	//图片
	//查询
	public List<FePic> selectPicById(Map<String,String> param);
	//保存
	public void savePic(FePic fePic);
	//逻辑删除
	public void updateDeleteFlagPic(Map<String,String> param);
	//删除
	public void deletePic(Map<String,String> param);
	//评价
	//查询
	public List<EvalInfo> selectEval(Map<String,String> param);
	//查询评价总条数
	public int selectEvalCount(Map<String,String> param);
}
