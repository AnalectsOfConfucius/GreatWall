package com.hg.dqsj.base.entity;

import java.util.List;

import com.hg.dqsj.love.center.eval.entity.Eval;
import com.hg.dqsj.love.center.eval.entity.EvalPic;

public class EvalInfo extends Eval{
	
	private String userName;//评论者
	private String userPicUrl;//用户头像地址
	
	private List<EvalPic> plist;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public List<EvalPic> getPlist() {
		return plist;
	}

	public void setPlist(List<EvalPic> plist) {
		this.plist = plist;
	}
	
}
