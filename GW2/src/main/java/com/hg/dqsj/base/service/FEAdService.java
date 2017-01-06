package com.hg.dqsj.base.service;

import java.util.List;

import com.hg.dqsj.base.view.VFEAd;

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
	 * @param typeCode
	 * @return
	 */
	public List<VFEAd> selectByTypeCode(String typeCode);
}
