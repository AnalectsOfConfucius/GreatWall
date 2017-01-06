package com.hg.dqsj.base.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.base.dao.FEInfoDao;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;

/**
 * 功能：资讯信息SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class InfoServiceImpl implements InfoService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEInfoDao feInfoDao; // 资讯DAO接口

	/**
	 * 根据【类别代码】查询资讯信息 （单条）
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public VFEInfo selectVSingleByTypeCode(String typeCode) {
		if (StringUtil.isNullorEmpty(typeCode)) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		map.put("typeCode", typeCode);
		VFEInfo vfeInfo = feInfoDao.selectVSingleByTypeCode(map);
		completeDetail(vfeInfo);
		return vfeInfo;
	}

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<VFEInfo> details = feInfoDao.selectByCriterias(map);
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("totalRecords", 0); // 总记录数
		} else {
			for (VFEInfo vfeInfo : details) {
				completeDetail(vfeInfo);
			}
			Integer totalRecords = feInfoDao.countByCriterias(map);
			result.put("details", details);
			result.put("totalRecords", totalRecords); // 总记录数
		}
		return result;
	}

	/**
	 * 根据查询条件查询所有详细信息数
	 * 
	 * @param request
	 * @return
	 */
	public Integer countByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		return feInfoDao.countByCriterias(map);
	}

	/**
	 * 生成查询条件
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createCriteria(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		map.put("startRowNo", (map.get("currentRecords") == null || map.get("currentRecords").equals("")) ? "0" : map.get("currentRecords").toString()); // 开始行数
		map.put("rowSize", "3"); // 查询范围
		return map;
	}

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEInfo selectVById(String id) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			VFEInfo vfeInfo = feInfoDao.selectVById(map);
			completeDetail(vfeInfo);
			return vfeInfo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 完善详细信息
	 * 
	 * @param detail
	 */
	private void completeDetail(VFEInfo detail) {
		try {
			if (detail != null) {
				if (detail.getInfoContent() != null) {
					detail.setInfoContentStr(new String(detail.getInfoContent(), "UTF-8"));
				}
				if (!StringUtil.isNullorEmpty(detail.getIsRelease())) {
					detail.setIsReleaseStr(detail.getIsRelease().equals("0") ? "否" : "是");
				}
				if (!StringUtil.isNullorEmpty(detail.getIsRecommend())) {
					detail.setIsRecommendStr(detail.getIsRecommend().equals("0") ? "否" : "是");
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
