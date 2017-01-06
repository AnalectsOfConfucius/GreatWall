package com.hg.dqsj.buy.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.buy.goods.dao.GuestRoomDao;
import com.hg.dqsj.buy.goods.entity.GuestRoomInfo;
import com.hg.dqsj.buy.goods.service.GuestRoomService;
@Service
public class GuestRoomServiceImpl implements GuestRoomService{
	@Autowired
	private GuestRoomDao dao;
	
	@Override
	public List<GuestRoomInfo> selectGuestRoom(Map<String, String> param) {
		return dao.selectGuestRoom(param);
	}

	@Override
	public int getCount(Map<String, String> param) {
		return dao.getCount(param);
	}

	@Override
	public GuestRoomInfo selectById(Map<String, String> param) {
		return dao.selectById(param);
	}

}
