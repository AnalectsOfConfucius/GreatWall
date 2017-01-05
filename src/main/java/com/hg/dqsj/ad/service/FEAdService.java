package com.hg.dqsj.ad.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hg.dqsj.ad.entity.FEAd;
import com.hg.dqsj.ad.view.VFEAd;

/**
 * 功能：广告SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEAdService {
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
	public FEAd selectById(String id);

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String filepath) throws Exception;

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEAd selectVById(String id);

	/**
	 * 更新删除标识
	 * 
	 * @param feAd
	 * @return
	 */
	public Integer updateDeleteFlagById(FEAd feAd);

	/**
	 * 根据【广告类别】和【广告目标】更新删除标识
	 * 
	 * @param linkTypeId
	 *            广告类别
	 * @param linkObjId
	 *            广告目标
	 * @return
	 */
	public Integer updateDeleteFlagByLinkTypeIdAndLinkObjId(String linkTypeId, String linkObjId);

	/**
	 * 加载目标链接信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> loadObjLinkDetailByCriterias(HttpServletRequest request);
}
