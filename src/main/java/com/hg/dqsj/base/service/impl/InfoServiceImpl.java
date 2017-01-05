package com.hg.dqsj.base.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.ad.service.FEAdService;
import com.hg.dqsj.base.dao.FEInfoDao;
import com.hg.dqsj.base.entity.FEInfo;
import com.hg.dqsj.base.entity.FEInfoMap;
import com.hg.dqsj.base.service.FEInfoMapService;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.type.dao.SMTypeDao;
import com.hg.dqsj.system.type.entity.SMType;

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
	@Autowired
	private SMTypeDao smTypeDao; // 系统类别DAO接口
	@Autowired
	private FEInfoMapService feInfoMapService; // 资讯映射信息SERVICE接口
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1"); // 是否报错[0:否][1:是]

		String infoTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoTitle")); // 标题
		String isRelease = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isRelease")); // 是否发布
		String isRecommend = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isRecommend")); // 是否推荐

		if (!StringUtil.isChsEnNum(infoTitle, false)) {
			result.put("msg", "请不要输入带有特殊字符的【标题】！");
		} else if (!StringUtil.isNullorEmpty(isRecommend) && !"0".equals(isRecommend) && !"1".equals(isRecommend)) {
			result.put("msg", "请选择正确的【是否推荐】！");
		} else if (!StringUtil.isNullorEmpty(isRelease) && !"0".equals(isRelease) && !"1".equals(isRelease)) {
			result.put("msg", "请选择正确的【是否发布】！");
		} else {
			List<VFEInfo> details = feInfoDao.selectByCriterias(map);
			if (details == null || details.size() <= 0) {
				result.put("details", details);
				result.put("currentPageNo", 0); // 当前页码
				result.put("totalCount", 0); // 总记录数
			} else {
				for (VFEInfo vfeInfo : details) {
					if (!StringUtil.isNullorEmpty(vfeInfo.getIsRelease())) {
						vfeInfo.setIsReleaseStr(vfeInfo.getIsRelease().equals("0") ? "否" : "是");
					}
					if (!StringUtil.isNullorEmpty(vfeInfo.getIsRecommend())) {
						vfeInfo.setIsRecommendStr(vfeInfo.getIsRecommend().equals("0") ? "否" : "是");
					}
				}
				Integer totalCount = feInfoDao.countByCriterias(map);
				result.put("details", details);
				result.put("currentPageNo", map.get("currentPageNo")); // 当前页码
				result.put("totalCount", totalCount); // 总记录数
			}
			result.put("isError", "0"); // 是否报错[0:否][1:是]
		}

		return result;
	}

	/**
	 * 生成查询条件
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createCriteria(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
		String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
		if (StringUtil.isNullorEmpty(pageSize)) {
			pageSize = "10";
		}
		if (StringUtil.isNullorEmpty(currentPageNo)) {
			currentPageNo = "1";
		}
		int curno = Integer.parseInt(currentPageNo);
		int pz = Integer.parseInt(pageSize);
		int startRowNo = pz * (curno - 1);
		map.put("startRowNo", String.valueOf(startRowNo)); // 开始行数
		map.put("rowSize", pageSize); // 查询范围
		return map;
	}

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public FEInfo selectById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return feInfoDao.selectById(map);
	}

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Transactional
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String filepath) throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1"); // 是否报错[0:否][1:是]

		String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 类别代码
		String infoMapType = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoMapType")); // 资讯映射类型

		// 【类别代码】和【资讯映射类型】不能为空
		if (StringUtil.isNullorEmpty(typeCode) || StringUtil.isNullorEmpty(infoMapType)) {
			result.put("msg", "【类别代码】和【资讯映射类型】不能为空!");
			return result;
		}

		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id")); // 资讯信息ID
		String infoTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoTitle")); // 资讯信息标题
		String infoPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoPicUrl")); // 封面图片
		String infoBrief = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoBrief")); // 资讯信息简介
		String infoContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoContent")); // 资讯信息内容
		String isRecommend = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isRecommend")); // 是否推荐
		String isRelease = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isRelease")); // 是否发布
		String infoMapOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoMapOrder")); // 排序
		if (!StringUtil.isNullorEmpty(id) && !UUIdUtil.matchUUID(id)) {
			result.put("msg", "操作失败，请重试！");
		} else if (StringUtil.isNullorEmpty(infoTitle)) {
			result.put("msg", "【标题】不能为空！");
		} else if (infoTitle.length() > 32) {
			result.put("msg", "【标题】长度不能大于32！");
		} else if (!StringUtil.isChsEnNum(infoTitle, true)) {
			result.put("msg", "请不要输入带有特殊字符的【标题】！");
		} else if (StringUtil.isNullorEmpty(infoPicUrl) && StringUtil.isNullorEmpty(filepath)) {
			result.put("msg", "【封面图片】不能为空！");
		} else if (!"0".equals(isRecommend) && !"1".equals(isRecommend)) {
			result.put("msg", "请选择正确的【是否推荐】！");
		} else if (!"0".equals(isRelease) && !"1".equals(isRelease)) {
			result.put("msg", "请选择正确的【是否发布】！");
		} else if (!NumberUtil.isInt(infoMapOrder, false)) {
			result.put("msg", "请输入正确的【排序】！");
		} else if (StringUtil.isNullorEmpty(infoBrief)) {
			result.put("msg", "【简介】不能为空！");
		} else if (!StringUtil.isParagraph(infoBrief, true)) {
			result.put("msg", "请不要输入带有特殊字符的【简介】！");
		} else if (StringUtil.isNullorEmpty(infoContent)) {
			result.put("msg", "【内容】不能为空！");
		} else if (StringUtil.isParagraph(infoContent, true)) {
			result.put("msg", "请不要输入带有特殊字符的【内容】！");
		} else {
			// 根据类别代码获取类别信息
			SMType smType = smTypeDao.selectByTypeCode(typeCode);
			if (smType == null) {
				smType = new SMType();
				smType.setId(UUIdUtil.getUUID());
				smType.setTypeCode(typeCode);
				smType.setCreateDate(DateUtil.getFullTime());
				smType.setCreateUserId(auth.getUserId());
				smType.setUpdateDate(DateUtil.getFullTime());
				smType.setUpdateUserId(auth.getUserId());
				smType.setDeleteFlag("0");
				smTypeDao.save(smType);
			}

			/**
			 * 保存或更新资讯信息
			 */
			FEInfo feInfo = null;
			if (StringUtil.isNullorEmpty(id)) {
				feInfo = new FEInfo();
				feInfo.setId(UUIdUtil.getUUID());
				feInfo.setInfoTitle(infoTitle);
				feInfo.setInfoPicUrl(filepath != null ? filepath : infoPicUrl);
				feInfo.setInfoBrief(infoBrief);
				feInfo.setInfoContent(infoContent.getBytes("utf-8"));
				feInfo.setIsRecommend(isRecommend);
				feInfo.setIsRelease(isRelease);
				feInfo.setReleaseDate(isRelease.equals("1") ? DateUtil.getFullDate_() : "");
				feInfo.setCreateDate(DateUtil.getFullTime());
				feInfo.setCreateUserId(auth.getUserId());
				feInfo.setUpdateDate(DateUtil.getFullTime());
				feInfo.setUpdateUserId(auth.getUserId());
				feInfo.setDeleteFlag("0");
				feInfoDao.save(feInfo);
			} else {
				feInfo = selectById(id);
				if (feInfo == null) {
					feInfo = new FEInfo();
					feInfo.setId(UUIdUtil.getUUID());
					feInfo.setInfoTitle(infoTitle);
					feInfo.setInfoPicUrl(filepath != null ? filepath : infoPicUrl);
					feInfo.setInfoBrief(infoBrief);
					feInfo.setInfoContent(infoContent.getBytes("utf-8"));
					feInfo.setIsRecommend(isRecommend);
					feInfo.setIsRelease(isRelease);
					feInfo.setReleaseDate((isRelease.equals("1") && StringUtil.isNullorEmpty(feInfo.getReleaseDate())) ? DateUtil.getFullDate_() : "");
					feInfo.setCreateDate(DateUtil.getFullTime());
					feInfo.setCreateUserId(auth.getUserId());
					feInfo.setUpdateDate(DateUtil.getFullTime());
					feInfo.setUpdateUserId(auth.getUserId());
					feInfo.setDeleteFlag("0");
					feInfoDao.save(feInfo);
				} else {
					feInfo.setInfoTitle(infoTitle);
					feInfo.setInfoPicUrl(filepath != null ? filepath : infoPicUrl);
					feInfo.setInfoBrief(infoBrief);
					feInfo.setInfoContent(infoContent.getBytes("utf-8"));
					feInfo.setIsRecommend(isRecommend);
					feInfo.setIsRelease(isRelease);
					feInfo.setReleaseDate(isRelease.equals("1") ? DateUtil.getFullDate_() : "");
					feInfo.setUpdateDate(DateUtil.getFullTime());
					feInfo.setUpdateUserId(auth.getUserId());
					feInfoDao.update(feInfo);
				}
			}

			/**
			 * 保存或更新资讯映射信息
			 */
			// 资讯映射类型为[1:一对一]
			FEInfoMap feInfoMap = feInfoMapService.selectByTypeIdAndInfoId(smType.getId(), feInfo.getId());
			if (infoMapType.equals("1")) {
				if (feInfoMap == null) {
					feInfoMap = new FEInfoMap();
					feInfoMap.setId(UUIdUtil.getUUID());
					feInfoMap.setInfoMapType("1");
					feInfoMap.setTypeId(smType.getId());
					feInfoMap.setInfoId(feInfo.getId());
					feInfoMap.setInfoMapOrder(NumberUtil.strToInt(infoMapOrder));
					feInfoMap.setCreateDate(DateUtil.getFullTime());
					feInfoMap.setCreateUserId(auth.getUserId());
					feInfoMap.setUpdateDate(DateUtil.getFullTime());
					feInfoMap.setUpdateUserId(auth.getUserId());
					feInfoMap.setDeleteFlag("0");
					feInfoMapService.save(feInfoMap);
				} else {
					feInfoMap.setInfoMapType("1");
					feInfoMap.setTypeId(smType.getId());
					feInfoMap.setInfoId(feInfo.getId());
					feInfoMap.setInfoMapOrder(NumberUtil.strToInt(infoMapOrder));
					feInfoMap.setUpdateDate(DateUtil.getFullTime());
					feInfoMap.setUpdateUserId(auth.getUserId());
					feInfoMapService.update(feInfoMap);
				}
			} else {
				if (feInfoMap == null) {
					feInfoMap = new FEInfoMap();
					feInfoMap.setId(UUIdUtil.getUUID());
					feInfoMap.setInfoMapType("2");
					feInfoMap.setTypeId(smType.getId());
					feInfoMap.setInfoId(feInfo.getId());
					feInfoMap.setInfoMapOrder(NumberUtil.strToInt(infoMapOrder));
					feInfoMap.setCreateDate(DateUtil.getFullTime());
					feInfoMap.setCreateUserId(auth.getUserId());
					feInfoMap.setUpdateDate(DateUtil.getFullTime());
					feInfoMap.setUpdateUserId(auth.getUserId());
					feInfoMap.setDeleteFlag("0");
					feInfoMapService.save(feInfoMap);
				} else {
					feInfoMap.setInfoMapType("2");
					feInfoMap.setTypeId(smType.getId());
					feInfoMap.setInfoId(feInfo.getId());
					feInfoMap.setInfoMapOrder(NumberUtil.strToInt(infoMapOrder));
					feInfoMap.setUpdateDate(DateUtil.getFullTime());
					feInfoMap.setUpdateUserId(auth.getUserId());
					feInfoMapService.update(feInfoMap);
				}
			}
			result.put("isError", "0"); // 是否报错[0:否][1:是]
			result.put("id", feInfo.getId()); // 资讯ID
		}
		return result;
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
			if (vfeInfo != null) {
				if (vfeInfo.getInfoContent() != null) {
					vfeInfo.setInfoContentStr(new String(vfeInfo.getInfoContent(), "utf-8"));
				}
				if (!StringUtil.isNullorEmpty(vfeInfo.getIsRelease())) {
					vfeInfo.setIsReleaseStr(vfeInfo.getIsRelease().equals("0") ? "否" : "是");
				}
				if (!StringUtil.isNullorEmpty(vfeInfo.getIsRecommend())) {
					vfeInfo.setIsRecommendStr(vfeInfo.getIsRecommend().equals("0") ? "否" : "是");
				}
			}
			return vfeInfo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 更新删除标识
	 * 
	 * @param typeCode
	 * @param feInfo
	 * @return
	 */
	@Transactional
	public Integer updateDeleteFlagById(String typeCode, FEInfo feInfo) {
		SMType smType = smTypeDao.selectByTypeCode(typeCode);
		if (smType == null) {
			return 0;
		}
		FEInfoMap feInfoMap = feInfoMapService.selectByTypeIdAndInfoId(smType.getId(), feInfo.getId());
		if (feInfoMap == null) {
			return 0;
		}
		feInfoMap.setUpdateDate(DateUtil.getFullTime());
		feInfoMap.setUpdateUserId(feInfo.getUpdateUserId());
		feInfoMap.setDeleteFlag("1");
		feInfoMapService.updateDeleteFlagById(feInfoMap);
		feAdService.updateDeleteFlagByLinkTypeIdAndLinkObjId(smType.getId(), feInfo.getId());
		return feInfoDao.updateDeleteFlagById(feInfo);
	}

	/**
	 * 根据【类别代码】和【资讯映射类型】查询资讯信息 （单条）
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public VFEInfo selectVByInfoMapTypeAndTypeCode(HttpServletRequest request, String infoMapType, String typeCode) {
		if (StringUtil.isNullorEmpty(infoMapType) || StringUtil.isNullorEmpty(typeCode)) {
			return null;
		}

		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		SMType smType = smTypeDao.selectByTypeCode(typeCode);
		if (smType == null) {
			smType = new SMType();
			smType.setId(UUIdUtil.getUUID());
			smType.setTypeCode(typeCode);
			smType.setCreateDate(DateUtil.getFullTime());
			smType.setCreateUserId(auth.getUserId());
			smType.setUpdateDate(DateUtil.getFullTime());
			smType.setUpdateUserId(auth.getUserId());
			smType.setDeleteFlag("0");
			smTypeDao.save(smType);
		}

		FEInfoMap feInfoMap = feInfoMapService.selectSingleByTypeId(smType.getId());
		if (feInfoMap == null) {
			FEInfo feInfo = new FEInfo();
			feInfo.setId(UUIdUtil.getUUID());
			feInfo.setIsRelease("0");
			feInfo.setCreateDate(DateUtil.getFullTime());
			feInfo.setCreateUserId(auth.getUserId());
			feInfo.setUpdateDate(DateUtil.getFullTime());
			feInfo.setUpdateUserId(auth.getUserId());
			feInfo.setDeleteFlag("0");
			feInfoDao.save(feInfo);

			feInfoMap = new FEInfoMap();
			feInfoMap.setId(UUIdUtil.getUUID());
			feInfoMap.setInfoMapType(infoMapType);
			feInfoMap.setTypeId(smType.getId());
			feInfoMap.setInfoId(feInfo.getId());
			feInfoMap.setInfoMapOrder(1);
			feInfoMap.setCreateDate(DateUtil.getFullTime());
			feInfoMap.setCreateUserId(auth.getUserId());
			feInfoMap.setUpdateDate(DateUtil.getFullTime());
			feInfoMap.setUpdateUserId(auth.getUserId());
			feInfoMap.setDeleteFlag("0");
			feInfoMapService.save(feInfoMap);
		}

		return selectVById(feInfoMap.getInfoId());
	}

	/**
	 * 根据【类别代码】和【资讯映射类型】查询资讯信息（多条）
	 * 
	 * @param request
	 * @param infoMapType
	 * @param typeCode
	 * @return
	 */
	public List<VFEInfo> selectVByInfoMapTypeAndTypeCode(String infoMapType, String typeCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("infoMapType", infoMapType);
		map.put("typeCode", typeCode);
		return feInfoDao.selectByCriterias(map);
	}
}
