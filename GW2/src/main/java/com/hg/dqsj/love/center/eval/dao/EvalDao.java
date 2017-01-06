package com.hg.dqsj.love.center.eval.dao;

import com.hg.dqsj.love.center.eval.entity.Eval;
import com.hg.dqsj.love.center.eval.entity.EvalPic;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface EvalDao {

    List<Eval> selectByUserId(String id);

    List<EvalPic> selectEvalPicByEvalId(String id);

    void insertEvalPic(EvalPic evalPic);

    void save(Eval eval);

    Integer selectByOrderId(String id);


}
