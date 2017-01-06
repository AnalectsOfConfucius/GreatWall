package com.hg.dqsj.buy.goods.service;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.buy.goods.entity.GuestRoomInfo;

public interface GuestRoomService {
	//查询列表
	public List<GuestRoomInfo> selectGuestRoom(Map<String,String> param);
	//查询列表总条数
	public int getCount(Map<String,String> param);
	//详情
	public GuestRoomInfo selectById(Map<String,String> param);
	
}
