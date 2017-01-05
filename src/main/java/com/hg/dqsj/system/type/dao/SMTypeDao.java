package com.hg.dqsj.system.type.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.view.VSMType;

/**
 * 功能：系统类别DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface SMTypeDao {

	/**
	 * 根据查询条件查询所有类别详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VSMType> selectByCriterias(Map<String, Object> map);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectById(String id);

	/**
	 * 根据类别代码获取类别信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectByTypeCode(String typeCode);

	/**
	 * 保存
	 * 
	 * @param smType
	 * @return
	 */
	public Integer save(SMType smType);

	/**
	 * 更新
	 * 
	 * @param smType
	 * @return
	 */
	public Integer update(SMType smType);

	/**
	 * 更新删除标识
	 * 
	 * @param smType
	 * @return
	 */
	public Integer updateDeleteFlagById(SMType smType);

	/**
	 * 查询所有类别标识不为空的信息
	 * 
	 * @return
	 */
	public List<SMType> selectByTypeFlagNotNull();
}
