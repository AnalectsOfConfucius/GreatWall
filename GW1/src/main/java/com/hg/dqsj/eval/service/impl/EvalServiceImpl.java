package com.hg.dqsj.eval.service.impl;

import com.hg.core.util.StringUtil;
import com.hg.dqsj.eval.dao.EvalDao;
import com.hg.dqsj.eval.dao.EvalPicDao;
import com.hg.dqsj.eval.entity.Eval;
import com.hg.dqsj.eval.entity.EvalPic;
import com.hg.dqsj.eval.service.EvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 */
@Service
public class EvalServiceImpl implements EvalService {

    @Autowired
    private EvalDao evalDao;
   @Autowired
    private EvalPicDao evalPicDao;

    @Override
    public List<Eval> selectList(String goodsName, String currentPageNo, String pageSize) {
        Map<String, Object> selectConditionMap = new HashMap<String, Object>();
        if (StringUtil.isNullorEmpty(pageSize)) {
            pageSize = "10";
        }
        if (StringUtil.isNullorEmpty(currentPageNo)) {
            currentPageNo = "1";
        }
        int curno = Integer.parseInt(currentPageNo);
        int pz = Integer.parseInt(pageSize);
        int startRowNo = pz * (curno - 1);
        selectConditionMap.put("startRowNo", String.valueOf(startRowNo)); // 开始行数
        selectConditionMap.put("rowSize", pageSize); // 查询范围
        selectConditionMap.put("goodsName", goodsName);

        return this.evalDao.selectByCriterias(selectConditionMap);
    }

    @Override
    public Integer countEvalByCriterias(String goodsName) {
        Map<String, Object> selectConditionMap = new HashMap<String, Object>();
        selectConditionMap.put("goodsName", goodsName);
        return this.evalDao.countByCriterias(selectConditionMap);
    }

    @Override
    public void delete(Eval eval) {
        this.evalDao.delete(eval);
    }

    @Override
    public Eval selectById(String id) {
        Map<String, Object> selectConditionMap = new HashMap<String, Object>();
        selectConditionMap.put("id", id);
        Eval eval = this.evalDao.selectById(selectConditionMap);
        List<EvalPic> evalPics = this.evalPicDao.selectByCriterias(selectConditionMap);
        eval.setEvalPics(evalPics);
        return this.evalDao.selectById(selectConditionMap);
    }
}
