package com.hg.dqsj.goods.guestRoom.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.goods.guestRoom.entity.GuestRoom;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoomInfo;

public interface GuestRoomDao {
	//查询列表
	public List<GuestRoomInfo> selectGuestRoom(Map<String,String> param);
	//查询列表总条数
	public int getCount(Map<String,String> param);
	//详情
	public GuestRoomInfo selectById(Map<String,String> param);
	//添加
	public void save(GuestRoom guestRoom);
	//修改
	public void update(GuestRoom guestRoom);
	//删除
	public void delete(Map<String,String> param);
}
