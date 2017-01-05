package com.hg.dqsj.base.service;

import com.hg.dqsj.base.entity.FEInfoMap;

/**
 * 功能：资讯映射信息SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEInfoMapService {
	/**
	 * 根据类别ID查询一条详细信息
	 * 
	 * @param typeId
	 * @return
	 */
	public FEInfoMap selectSingleByTypeId(String typeId);
	/**
	 * 根据类别ID和资讯ID查询详细信息
	 * 
	 * @param typeId
	 * @param infoId
	 * @return
	 */
	public FEInfoMap selectByTypeIdAndInfoId(String typeId, String infoId);

	/**
	 * 保存
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer save(FEInfoMap feInfoMap);

	/**
	 * 更新
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer update(FEInfoMap feInfoMap);
	
	/**
	 * 更新删除标识
	 * 
	 * @param feInfoMap
	 * @return
	 */
	public Integer updateDeleteFlagById(FEInfoMap feInfoMap);
}
