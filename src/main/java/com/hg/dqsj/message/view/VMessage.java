package com.hg.dqsj.message.view;

import com.hg.dqsj.message.entity.Message;

/**
 * 功能：留言视图类
 * 
 * @author 吴晓敏
 *
 */
public class VMessage extends Message {
	private String userPicUrl; // 用户头像地址
	private String dateDifference; // 时间差

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getDateDifference() {
		return dateDifference;
	}

	public void setDateDifference(String dateDifference) {
		this.dateDifference = dateDifference;
	}

}
