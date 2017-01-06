package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.view.VFEInfo;

/**
 * 功能：资讯DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEInfoDao {
	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VFEInfo> selectByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countByCriterias(Map<String, Object> map);

	/**
	 * 根据【类别代码】查询资讯信息 （单条）
	 * 
	 * @param map
	 * @return
	 */
	public VFEInfo selectVSingleByTypeCode(Map<String, String> map);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param map
	 * @return
	 */
	public VFEInfo selectVById(Map<String, String> map);
}
