package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.entity.FEInfo;
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
	 * 根据ID查询详细信息
	 * 
	 * @param map
	 * @return
	 */
	public FEInfo selectById(Map<String, String> map);

	/**
	 * 保存
	 * 
	 * @param feInfo
	 * @return
	 */
	public Integer save(FEInfo feInfo);

	/**
	 * 更新
	 * 
	 * @param feInfo
	 * @return
	 */
	public Integer update(FEInfo feInfo);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param map
	 * @return
	 */
	public VFEInfo selectVById(Map<String, String> map);

	/**
	 * 更新删除标识
	 * 
	 * @param feInfo
	 * @return
	 */
	public Integer updateDeleteFlagById(FEInfo feInfo);
}
