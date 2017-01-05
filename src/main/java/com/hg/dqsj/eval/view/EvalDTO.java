package com.hg.dqsj.eval.view;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class EvalDTO {

    private String id;
    private String goodsName;
    private double evalTotalScore;
    private String serviceTotalScore;
    private String tasteTotalScore;
    private String environmentTotalScore;
    private String updateDate;

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getEvalTotalScore() {
        return evalTotalScore;
    }

    public void setEvalTotalScore(double evalTotalScore) {
        this.evalTotalScore = evalTotalScore;
    }

    public String getServiceTotalScore() {
        return serviceTotalScore;
    }

    public void setServiceTotalScore(String serviceTotalScore) {
        this.serviceTotalScore = serviceTotalScore;
    }

    public String getTasteTotalScore() {
        return tasteTotalScore;
    }

    public void setTasteTotalScore(String tasteTotalScore) {
        this.tasteTotalScore = tasteTotalScore;
    }

    public String getEnvironmentTotalScore() {
        return environmentTotalScore;
    }

    public void setEnvironmentTotalScore(String environmentTotalScore) {
        this.environmentTotalScore = environmentTotalScore;
    }
}
