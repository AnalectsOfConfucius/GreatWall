package com.hg.dqsj.goods.guestRoom.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.ad.service.FEAdService;
import com.hg.dqsj.base.dao.FeGoodsDao;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.goods.guestRoom.dao.GuestRoomDao;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoom;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoomInfo;
import com.hg.dqsj.goods.guestRoom.service.GuestRoomService;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

@Service
public class GuestRoomServiceImpl implements GuestRoomService {
	@Autowired
	private GuestRoomDao dao;
	@Autowired
	private FeGoodsDao goodsDao;
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口

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

	@Override
	@Transactional
	public void save(GuestRoom guestRoom, List<FePic> plist) {
		dao.save(guestRoom);
		if (null != plist && plist.size() > 0) {
			for (FePic fePic : plist) {
				goodsDao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void update(GuestRoom guestRoom, List<FePic> plist) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("objId", guestRoom.getId());
		dao.update(guestRoom);
		goodsDao.deletePic(param);
		if (null != plist && plist.size() > 0) {
			for (FePic fePic : plist) {
				goodsDao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Map<String, String> param) {
		dao.delete(param);
		param.put("objId", param.get("id"));
		goodsDao.updateDeleteFlagPic(param);

		SMType type = smTypeService.selectByTypeCode("hotel");
		if (type == null) {
			return;
		}
		feAdService.updateDeleteFlagByLinkTypeIdAndLinkObjId(type.getId(), param.get("id"));
	}

}
