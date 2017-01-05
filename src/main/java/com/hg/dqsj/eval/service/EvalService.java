package com.hg.dqsj.eval.service;

import com.hg.dqsj.eval.entity.Eval;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface EvalService {

    List<Eval> selectList(String goodsName, String currentPageNo, String pageSize);

    Integer countEvalByCriterias(String goodsName);

    void delete(Eval eval);

    Eval selectById(String id);
}
