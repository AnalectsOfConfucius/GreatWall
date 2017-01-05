package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.base.dao.FEInfoMapDao;
import com.hg.dqsj.base.entity.FEInfoMap;
import com.hg.dqsj.base.service.FEInfoMapService;

/**
 * 功能：资讯信息SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class FEInfoMapServiceImpl implements FEInfoMapService {
	@Autowired
	private FEInfoMapDao feInfoMapDao; // 资讯映射DAO接口

	/**
	 * 根据类别ID查询一条详细信息
	 * 
	 * @param typeId
	 * @return
	 */
	public FEInfoMap selectSingleByTypeId(String typeId) {
		return feInfoMapDao.selectSingleByTypeId(typeId);
	}

	/**
	 * 根据类别ID和资讯ID查询详细信息
	 * 
	 * @param typeId
	 * @param infoId
	 * @return
	 */
	public FEInfoMap selectByTypeIdAndInfoId(String typeId, String infoId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("typeId", typeId);
		map.put("infoId", infoId);
		return feInfoMapDao.selectByTypeIdAndInfoId(map);
	}

	/**
	 * 保存
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer save(FEInfoMap feInfoMap) {
		return feInfoMapDao.save(feInfoMap);
	}

	/**
	 * 更新
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer update(FEInfoMap feInfoMap) {
		return feInfoMapDao.update(feInfoMap);
	}

	/**
	 * 更新删除标识
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer updateDeleteFlagById(FEInfoMap feInfoMap) {
		return feInfoMapDao.update(feInfoMap);
	}
}
