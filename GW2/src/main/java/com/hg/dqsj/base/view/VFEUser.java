package com.hg.dqsj.base.view;

import com.hg.dqsj.base.entity.FEUser;

public class VFEUser extends FEUser {
	private Integer creditNum; // 总积分
	private String userGenderStr; // 性别

	public Integer getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(Integer creditNum) {
		this.creditNum = creditNum;
	}

	public String getUserGenderStr() {
		return userGenderStr;
	}

	public void setUserGenderStr(String userGenderStr) {
		this.userGenderStr = userGenderStr;
	}
}
