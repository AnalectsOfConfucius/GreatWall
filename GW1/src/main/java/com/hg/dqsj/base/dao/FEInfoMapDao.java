package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FEInfoMap;

/**
 * 功能：资讯映射DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEInfoMapDao {
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
	 * @param map
	 * @return
	 */
	public FEInfoMap selectByTypeIdAndInfoId(Map<String, String> map);

	/**
	 * 根据类别ID查询多个详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<FEInfoMap> selectMultiByTypeId(Map<String, String> map);

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
