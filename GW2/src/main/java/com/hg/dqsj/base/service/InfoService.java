package com.hg.dqsj.base.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.dqsj.base.view.VFEInfo;

/**
 * 功能：资讯信息SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface InfoService {
	/**
	 * 根据【类别代码】查询资讯信息 （单条）
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public VFEInfo selectVSingleByTypeCode(String typeCode);

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEInfo selectVById(String id);
}
