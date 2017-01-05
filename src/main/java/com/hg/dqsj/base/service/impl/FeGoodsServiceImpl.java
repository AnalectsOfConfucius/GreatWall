package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.util.StringUtil;
import com.hg.dqsj.ad.service.FEAdService;
import com.hg.dqsj.base.dao.FeGoodsDao;
import com.hg.dqsj.base.entity.FeGoods;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

@Service
public class FeGoodsServiceImpl implements FeGoodsService {
	@Autowired
	private FeGoodsDao dao;
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口

	@Override
	public List<FeGoodsInfo> selectByCriterias(Map<String, String> param) {
		return dao.selectByCriterias(param);
	}

	@Override
	public int countByCriterias(Map<String, String> param) {
		return dao.countByCriterias(param);
	}

	@Override
	public FeGoodsInfo selectById(Map<String, String> param) {
		return dao.selectById(param);
	}

	@Override
	@Transactional
	public void save(FeGoods feGoods, List<FePic> plist) {
		dao.save(feGoods);
		if (null != plist && plist.size() > 0) {
			for (FePic fePic : plist) {
				dao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void update(FeGoods feGoods, List<FePic> plist) {
		dao.update(feGoods);
		Map<String, String> param = new HashMap<String, String>();
		param.put("objId", feGoods.getId());
		dao.deletePic(param);
		if (null != plist && plist.size() > 0) {
			for (FePic fePic : plist) {
				dao.savePic(fePic);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Map<String, String> param) {
		dao.updateDeleteFlagById(param);
		param.put("objId", param.get("id"));
		dao.updateDeleteFlagPic(param);

		String typeCodeFlag = param.get("typeCodeFlag");
		String typeCode = null;
		if ("1".equals(typeCodeFlag)) {
			typeCode = "goods";
		} else if ("2".equals(typeCodeFlag)) {
			typeCode = "annualCard";
		} else if ("3".equals(typeCodeFlag)) {
			typeCode = "groceryFood";
		}

		if (StringUtil.isNullorEmpty(typeCode)) {
			return;
		}
		SMType type = smTypeService.selectByTypeCode(typeCode);
		if (type == null) {
			return;
		}
		feAdService.updateDeleteFlagByLinkTypeIdAndLinkObjId(type.getId(), param.get("id"));
	}

	@Override
	public List<FePic> selectPicById(Map<String, String> param) {
		return dao.selectPicById(param);
	}

	@Override
	public void deletePic(Map<String, String> param) {
		dao.deletePic(param);
	}

}
