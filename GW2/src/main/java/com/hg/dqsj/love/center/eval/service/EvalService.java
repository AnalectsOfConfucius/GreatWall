package com.hg.dqsj.love.center.eval.service;

import com.hg.dqsj.love.center.eval.entity.Eval;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface EvalService {

    List<Eval> selectByUserId(String userId);

    void save(Eval eval);

    Integer selectByOrderId(String id);

}
