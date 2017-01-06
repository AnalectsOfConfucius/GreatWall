package com.hg.dqsj.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.dqsj.base.dao.FEAdDao;
import com.hg.dqsj.base.service.FEAdService;
import com.hg.dqsj.base.view.VFEAd;

/**
 * 功能：广告SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class FEAdServiceImpl implements FEAdService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEAdDao feAdDao; // 广告DAO接口

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<VFEAd> selectByTypeCode(String typeCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("typeCode", typeCode);
		return feAdDao.selectByTypeCode(map);
	}
}
