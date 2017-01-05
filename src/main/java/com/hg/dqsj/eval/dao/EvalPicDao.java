package com.hg.dqsj.eval.dao;

import com.hg.dqsj.eval.entity.EvalPic;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/30.
 */
public interface EvalPicDao {

    List<EvalPic> selectByCriterias(Map map);

}
