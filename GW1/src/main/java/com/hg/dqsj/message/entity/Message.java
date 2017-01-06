package com.hg.dqsj.message.entity;

import com.hg.core.entity.BaseInfoEntity;

/**
 *
 * @author Junxing
 */
public class Message extends BaseInfoEntity {

    /********** attribute ***********/
    private String userId;
    
    private String userName;
    
    private String userPhone;
    
    private String isReply;
    
    private String evalLevel;
    
    private String messageContent;
    
    private String messageDate;
    
    private String replyDate;
    
    private String replyUserId;
    
    private String remark;
    
    private String replyContent;
    
    /********** get/set ***********/
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    
    public String getIsReply() {
        return isReply;
    }

    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }
    
    public String getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(String evalLevel) {
        this.evalLevel = evalLevel;
    }
    
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
    
    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }
    
    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    
      
}