package com.hg.dqsj.base.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.base.entity.FEInfo;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 功能：资讯信息SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface InfoService {
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
	public FEInfo selectById(String id);

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String filepath) throws UnsupportedEncodingException;

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEInfo selectVById(String id);

	/**
	 * 更新删除标识
	 * 
	 * @param typeCode
	 * @param feInfo
	 * @return
	 */
	public Integer updateDeleteFlagById(String typeCode, FEInfo feInfo);

	/**
	 * 根据【类别代码】和【资讯映射类型】查询资讯信息
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public VFEInfo selectVByInfoMapTypeAndTypeCode(HttpServletRequest request, String infoMapType, String typeCode);

	/**
	 * 根据【类别代码】和【资讯映射类型】查询资讯信息（多条）
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public List<VFEInfo> selectVByInfoMapTypeAndTypeCode(String infoMapType, String typeCode);
}
