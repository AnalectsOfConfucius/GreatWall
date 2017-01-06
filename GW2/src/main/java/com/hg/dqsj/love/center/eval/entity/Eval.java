package com.hg.dqsj.love.center.eval.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Eval {

    private String id;
    private String orderId;
    private String goodsId;
    private String storeId;
    private double evalTotalScore;
    private int serviceTotalScore;
    private int tasteTotalScore;
    private int environmentTotalScore;
    private String evalUserId;
    private String evalComment;
    private String evalUserIp;
    private String remark;
    private String createDate;
    private String createUserId;
    private String updateDate;
    private String updateUserId;
    private String deleteFlag;
    private String goodsName;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getgoodsName() {
        return goodsName;
    }

    public void setgoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    private List<EvalPic> evalPics;

    public List<EvalPic> getEvalPics() {
        return evalPics;
    }

    public void setEvalPics(List<EvalPic> evalPics) {
        this.evalPics = evalPics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getEvalTotalScore() {
        return evalTotalScore;
    }

    public void setEvalTotalScore(double evalTotalScore) {
        this.evalTotalScore = evalTotalScore;
    }

    public int getServiceTotalScore() {
        return serviceTotalScore;
    }

    public void setServiceTotalScore(int serviceTotalScore) {
        this.serviceTotalScore = serviceTotalScore;
    }

    public int getTasteTotalScore() {
        return tasteTotalScore;
    }

    public void setTasteTotalScore(int tasteTotalScore) {
        this.tasteTotalScore = tasteTotalScore;
    }

    public int getEnvironmentTotalScore() {
        return environmentTotalScore;
    }

    public void setEnvironmentTotalScore(int environmentTotalScore) {
        this.environmentTotalScore = environmentTotalScore;
    }

    public String getEvalUserId() {
        return evalUserId;
    }

    public void setEvalUserId(String evalUserId) {
        this.evalUserId = evalUserId;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
