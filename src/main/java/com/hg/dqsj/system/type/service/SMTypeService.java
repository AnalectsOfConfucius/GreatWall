package com.hg.dqsj.system.type.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hg.core.util.Pager;
import com.hg.dqsj.system.type.entity.SMType;

/**
 * 功能：系统类别SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface SMTypeService {

	/**
	 * 根据查询条件查询所有类别详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Pager selectByCriterias(HttpServletRequest request);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectById(String id);

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
	 * 类别代码是否存在
	 * 
	 * @param id
	 * @param typeCode
	 * @return
	 */
	public boolean isTypeCodeExsit(String id, String typeCode);

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
	
	/**
	 * 根据类别代码获取类别信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectByTypeCode(String typeCode);
}
