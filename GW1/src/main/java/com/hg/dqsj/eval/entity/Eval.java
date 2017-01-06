package com.hg.dqsj.eval.entity;

import java.util.List;

public class Eval {


    private String id;
    private String orderId;
    private String goodsId;
    private Double evalTotalScore;
    private Long serviceTotalScore;
    private Long tasteTotalScore;
    private Long environmentTotalScore;
    private String evalUserId;
    private String evalComment;
    private String evalUserIp;
    private String remark;
    private String createDate;
    private String createUserId;
    private String updateDate;
    private String updateUserId;
    private String deleteFlag;

    private String userName;
    private String goodsName;
    private List<EvalPic> evalPics;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Double getEvalTotalScore() {
        return evalTotalScore;
    }

    public void setEvalTotalScore(Double evalTotalScore) {
        this.evalTotalScore = evalTotalScore;
    }

    public Long getServiceTotalScore() {
        return serviceTotalScore;
    }

    public void setServiceTotalScore(Long serviceTotalScore) {
        this.serviceTotalScore = serviceTotalScore;
    }

    public Long getTasteTotalScore() {
        return tasteTotalScore;
    }

    public void setTasteTotalScore(Long tasteTotalScore) {
        this.tasteTotalScore = tasteTotalScore;
    }

    public Long getEnvironmentTotalScore() {
        return environmentTotalScore;
    }

    public void setEnvironmentTotalScore(Long environmentTotalScore) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<EvalPic> getEvalPics() {
        return evalPics;
    }

    public void setEvalPics(List<EvalPic> evalPics) {
        this.evalPics = evalPics;
    }


}
