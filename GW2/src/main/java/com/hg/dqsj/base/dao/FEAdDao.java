package com.hg.dqsj.base.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.base.view.VFEAd;

/**
 * 功能：广告DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEAdDao {
	/**
	 * 根据类别代码查询所有详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VFEAd> selectByTypeCode(Map<String, Object> map);
}
