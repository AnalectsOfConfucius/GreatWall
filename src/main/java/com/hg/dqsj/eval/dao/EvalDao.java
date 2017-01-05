package com.hg.dqsj.eval.dao;

import com.hg.dqsj.eval.entity.Eval;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface EvalDao {

    public List<Eval> selectByCriterias(Map map);

    public Eval selectById(Map map);

    public Integer countByCriterias(Map map);

    public void delete(Eval eval);
}
