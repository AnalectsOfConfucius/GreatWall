package com.hg.dqsj.eval.view;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class EvalDetailDTO {

    private String id;
    private String goodsName;
    private double evalTotalScore;
    private String serviceTotalScore;
    private String tasteTotalScore;
    private String environmentTotalScore;
    private String evalComment;
    private String evalUserIp;
    private String remark;
    private List<EvalPicDTO> evalPicDTOs;

    public List<EvalPicDTO> getEvalPicDTOs() {
        return evalPicDTOs;
    }

    public void setEvalPicDTOs(List<EvalPicDTO> evalPicDTOs) {
        this.evalPicDTOs = evalPicDTOs;
    }

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

    public String getEvalComment() {
        return evalComment;
    }

    public void setEvalComment(String evalComment) {
        this.evalComment = evalComment;
    }

    public String getEvalUserIp() {
        return evalUserIp;
    }

    public void setEvalUserIp(String evalUserIp) {
        this.evalUserIp = evalUserIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String updateDate;

}
