package com.hg.dqsj.love.center.eval.service.impl;

import com.hg.dqsj.love.center.eval.dao.EvalDao;
import com.hg.dqsj.love.center.eval.entity.Eval;
import com.hg.dqsj.love.center.eval.entity.EvalPic;
import com.hg.dqsj.love.center.eval.service.EvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
@Service
public class EvalServiceImpl implements EvalService {

    @Autowired
    private EvalDao evalDao;

    @Override
    public List<Eval> selectByUserId(String userId) {
        List<Eval> evals = this.evalDao.selectByUserId(userId);
        Iterator iterator = evals.iterator();
        while (iterator.hasNext()) {
            Eval eval = (Eval) iterator.next();
            List<EvalPic> evalPics = this.evalDao.selectEvalPicByEvalId(eval.getId());
            eval.setEvalPics(evalPics);
        }
        return evals;
    }

    @Override
    @Transactional
    public void save(Eval eval) {
       /* List<EvalPic> evalPics = eval.getEvalPics();
        for (int i = 0; i < evalPics.size(); i++) {
            this.evalDao.insertEvalPic(evalPics.get(i));
        }*/
        this.evalDao.save(eval);
    }

    @Override
    public Integer selectByOrderId(String id) {
        return this.evalDao.selectByOrderId(id);
    }
}
