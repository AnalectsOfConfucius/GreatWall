package com.hg.dqsj.ad.service.impl;

import java.util.ArrayList;
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
import com.hg.dqsj.ad.dao.FEAdDao;
import com.hg.dqsj.ad.entity.FEAd;
import com.hg.dqsj.ad.service.FEAdService;
import com.hg.dqsj.ad.view.AdObjLinkDetail;
import com.hg.dqsj.ad.view.VFEAd;
import com.hg.dqsj.base.entity.FEInfo;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoomInfo;
import com.hg.dqsj.goods.guestRoom.service.GuestRoomService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

/**
 * 功能：广告SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class FEAdServiceImpl implements FEAdService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEAdDao feAdDao; // 广告DAO接口
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口
	@Autowired
	private FeGoodsService feGoodsService; // 商品SERVICE接口
	@Autowired
	private GuestRoomService guestRoomService; // 客房SERVICE接口

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

		String adTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adTitle")); // 广告标题
		String typeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeId")); // 广告所属
		String linkTypeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkTypeId")); // 广告类型

		if (!StringUtil.isChsEnNum(adTitle, false)) {
			result.put("msg", "请不要输入带有特殊字符的【广告标题】！");
		} else if (!StringUtil.isNullorEmpty(typeId) && !UUIdUtil.matchUUID(typeId)) {
			result.put("msg", "请选择正确的【广告所属】！");
		} else if (!StringUtil.isNullorEmpty(linkTypeId) && !UUIdUtil.matchUUID(linkTypeId)) {
			result.put("msg", "请选择正确的【广告类型】！");
		} else {
			List<VFEAd> details = feAdDao.selectByCriterias(map);

			for (VFEAd detail : details) {
				completeDetail(detail);
			}

			if (details == null || details.size() <= 0) {
				result.put("details", details);
				result.put("currentPageNo", 0); // 当前页码
				result.put("totalCount", 0); // 总记录数
			} else {
				Integer totalCount = feAdDao.countByCriterias(map);
				result.put("details", details);
				result.put("currentPageNo", map.get("currentPageNo")); // 当前页码
				result.put("totalCount", totalCount); // 总记录数
			}
			result.put("isError", "0"); // 是否报错[0:否][1:是]
		}
		return result;
	}

	/**
	 * 完善详细信息
	 * 
	 * @param detail
	 */
	private void completeDetail(VFEAd detail) {
		if (detail == null || StringUtil.isNullorEmpty(detail.getLinkTypeId()) || StringUtil.isNullorEmpty(detail.getLinkObjId())) {
			return;
		}

		SMType smType = smTypeService.selectById(detail.getLinkTypeId());
		if (smType != null) {
			// 【类别标识】为[1:资讯]
			if (smType.getTypeFlag().equals("1")) {
				FEInfo info = infoService.selectById(detail.getLinkObjId());
				detail.setLinkObjName(info != null ? info.getInfoTitle() : "");
			}
			// 【类别标识】为[2.美食]
			else if ("2".equals(smType.getTypeFlag())) {
				Map<String, String> param = new HashMap<>();
				param.put("id", detail.getLinkObjId());
				FeGoodsInfo goods = feGoodsService.selectById(param); // 商品信息
				detail.setLinkObjName(goods != null ? goods.getGoodsName() : "");
			}
			// 【类别标识】为[3:特色商品]
			else if ("3".equals(smType.getTypeFlag())) {
				Map<String, String> param = new HashMap<>();
				param.put("id", detail.getLinkObjId());
				FeGoodsInfo goods = feGoodsService.selectById(param); // 商品信息
				detail.setLinkObjName(goods != null ? goods.getGoodsName() : "");
			}
			// 【类别标识】为[4:酒店客房]
			else if ("4".equals(smType.getTypeFlag())) {
				Map<String, String> param = new HashMap<>();
				param.put("id", detail.getLinkObjId());
				GuestRoomInfo room = guestRoomService.selectById(param); // 客房信息
				detail.setLinkObjName(room != null ? room.getGuestRoomName() : "");
			}
			// 【类别标识】为[5:年卡]
			else if ("5".equals(smType.getTypeFlag())) {
				Map<String, String> param = new HashMap<>();
				param.put("id", detail.getLinkObjId());
				FeGoodsInfo goods = feGoodsService.selectById(param); // 商品信息
				detail.setLinkObjName(goods != null ? goods.getGoodsName() : "");
			}
		}
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
	public FEAd selectById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return feAdDao.selectById(map);
	}

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, String filepath) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1"); // 是否报错[0:否][1:是]
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id")); // 广告ID
		String adTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adTitle")); // 广告标题
		String typeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeId")); // 广告所属ID
		String linkTypeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkTypeId")); // 广告类型ID
		String linkObjId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkObjId")); // 广告名称ID
		String adMiniPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adMiniPicUrl")); // 封面图片
		String adOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adOrder")); // 排序
		String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

		if (!StringUtil.isNullorEmpty(id) && !UUIdUtil.matchUUID(id)) {
			result.put("msg", "操作失败，请重试！");
		} else if (StringUtil.isNullorEmpty(adTitle)) {
			result.put("msg", "【广告标题】不能为空！");
		} else if (adTitle.length() > 32) {
			result.put("msg", "【广告标题】长度不能大于32！");
		} else if (!StringUtil.isChsEnNum(adTitle, true)) {
			result.put("msg", "请不要输入带有特殊字符的【广告标题】！");
		} else if (StringUtil.isNullorEmpty(typeId)) {
			result.put("msg", "【广告所属】不能为空！");
		} else if (!UUIdUtil.matchUUID(typeId)) {
			result.put("msg", "请选择正确的【广告所属】！");
		} else if (StringUtil.isNullorEmpty(linkTypeId)) {
			result.put("msg", "【广告类型】不能为空！");
		} else if (!UUIdUtil.matchUUID(linkTypeId)) {
			result.put("msg", "请选择正确的【广告类型】！");
		} else if (StringUtil.isNullorEmpty(linkObjId)) {
			result.put("msg", "【广告名称】不能为空！");
		} else if (!UUIdUtil.matchUUID(linkObjId)) {
			result.put("msg", "请选择正确的【广告名称】！");
		} else if (StringUtil.isNullorEmpty(adMiniPicUrl) && StringUtil.isNullorEmpty(filepath)) {
			result.put("msg", "【封面图片】不能为空！");
		} else if (!NumberUtil.isInt(adOrder, false)) {
			result.put("msg", "请输入正确的【排序】!");
		} else if (!StringUtil.isChsEnNum(remark, false)) {
			result.put("msg", "请不要输入带有特殊字符的【备注】！");
		} else if (!UUIdUtil.matchUUID(typeId) || !UUIdUtil.matchUUID(linkTypeId) || !UUIdUtil.matchUUID(linkObjId)) {
			result.put("msg", "操作失败，请重试！");
		} else {
			SMType smType = smTypeService.selectById(linkTypeId);
			if (smType == null) {
				result.put("msg", "操作失败，请重试！");
				return result;
			}
			String adUrl = StringUtil.isNullorEmpty(smType.getFeAction()) ? "" : smType.getFeAction() + linkObjId; // 广告URL
			/**
			 * 保存或更新广告
			 */
			FEAd feAd = null;
			if (StringUtil.isNullorEmpty(id)) {
				feAd = new FEAd();
				feAd.setId(UUIdUtil.getUUID());
				feAd.setAdTitle(adTitle);
				feAd.setTypeId(typeId);
				feAd.setLinkTypeId(linkTypeId);
				feAd.setLinkObjId(linkObjId);
				feAd.setAdMiniPicUrl(filepath != null ? filepath : adMiniPicUrl);
				feAd.setAdUrl(adUrl);
				feAd.setAdOrder(NumberUtil.strToInt(adOrder));
				feAd.setRemark(remark);
				feAd.setCreateDate(DateUtil.getFullTime());
				feAd.setCreateUserId(auth.getUserId());
				feAd.setUpdateDate(DateUtil.getFullTime());
				feAd.setUpdateUserId(auth.getUserId());
				feAd.setDeleteFlag("0");
				if (feAdDao.save(feAd) != 1) {
					result.put("msg", "操作失败，请重试！");
					throw new Exception();
				}
			} else {
				feAd = selectById(id);
				if (feAd == null) {
					feAd = new FEAd();
					feAd.setId(UUIdUtil.getUUID());
					feAd.setAdTitle(adTitle);
					feAd.setTypeId(typeId);
					feAd.setLinkTypeId(linkTypeId);
					feAd.setLinkObjId(linkObjId);
					feAd.setAdMiniPicUrl(filepath != null ? filepath : adMiniPicUrl);
					feAd.setAdUrl(adUrl);
					feAd.setAdOrder(NumberUtil.strToInt(adOrder));
					feAd.setRemark(remark);
					feAd.setCreateDate(DateUtil.getFullTime());
					feAd.setCreateUserId(auth.getUserId());
					feAd.setUpdateDate(DateUtil.getFullTime());
					feAd.setUpdateUserId(auth.getUserId());
					feAd.setDeleteFlag("0");
					if (feAdDao.save(feAd) != 1) {
						result.put("msg", "操作失败，请重试！");
						throw new Exception();
					}
				} else {
					feAd.setAdTitle(adTitle);
					feAd.setTypeId(typeId);
					feAd.setLinkTypeId(linkTypeId);
					feAd.setLinkObjId(linkObjId);
					feAd.setAdMiniPicUrl(filepath != null ? filepath : adMiniPicUrl);
					feAd.setAdUrl(adUrl);
					feAd.setAdOrder(NumberUtil.strToInt(adOrder));
					feAd.setRemark(remark);
					feAd.setUpdateDate(DateUtil.getFullTime());
					feAd.setUpdateUserId(auth.getUserId());
					if (feAdDao.update(feAd) != 1) {
						result.put("msg", "操作失败，请重试！");
						throw new Exception();
					}
				}
			}
			result.put("isError", "0"); // 是否报错[0:否][1:是]
			result.put("id", feAd.getId()); // 广告ID
		}
		return result;
	}

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VFEAd selectVById(String id) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			VFEAd vfeAd = feAdDao.selectVById(map);
			completeDetail(vfeAd);
			return vfeAd;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 更新删除标识
	 * 
	 * @param feAd
	 * @return
	 */
	@Transactional
	public Integer updateDeleteFlagById(FEAd feAd) {
		return feAdDao.updateDeleteFlagById(feAd);
	}

	/**
	 * 根据【广告类别】和【广告目标】更新删除标识
	 * 
	 * @param linkTypeId
	 *            广告类别
	 * @param linkObjId
	 *            广告目标
	 * @return
	 */
	@Transactional
	public Integer updateDeleteFlagByLinkTypeIdAndLinkObjId(String linkTypeId, String linkObjId) {
		Map<String, String> map = new HashMap<>();
		map.put("linkTypeId", linkTypeId);
		map.put("linkObjId", linkObjId);
		return feAdDao.updateDeleteFlagByLinkTypeIdAndLinkObjId(map);
	}

	/**
	 * 加载广告类型信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> loadObjLinkDetailByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<AdObjLinkDetail> details = new ArrayList<>();
		Integer totalCount = 0;
		try {
			String linkTypeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkTypeId"));
			if (!UUIdUtil.matchUUIDOrBlank(linkTypeId, true)) {
				result.put("details", details);
				result.put("currentPageNo", 0); // 当前页码
				result.put("totalCount", 0); // 总记录数
				result.put("isError", "1"); // 是否报错[0:否][1:是]
				result.put("msg", "非法操作，请重试！");
			}
			SMType smType = smTypeService.selectById(linkTypeId);
			if (smType != null) {
				// 【类别标识】为[1:资讯]
				if ("1".equals(smType.getTypeFlag())) {
					details = feAdDao.selectInfosByCriterias(map);
					totalCount = feAdDao.countInfosByCriterias(map);
				}
				// 【类别标识】为[2.美食]
				else if ("2".equals(smType.getTypeFlag())) {
					map.put("typeCode", "3");
					details = feAdDao.selectGoodsByCriterias(map);
					totalCount = feAdDao.countGoodsByCriterias(map);
				}
				// 【类别标识】为[3:特色商品]
				else if ("3".equals(smType.getTypeFlag())) {
					map.put("typeCode", "1");
					details = feAdDao.selectGoodsByCriterias(map);
					totalCount = feAdDao.countGoodsByCriterias(map);
				}
				// 【类别标识】为[4:酒店客房]
				else if ("4".equals(smType.getTypeFlag())) {
					details = feAdDao.selectGuestRoomByCriterias(map);
					totalCount = feAdDao.countGuestRoomByCriterias(map);
				}
				// 【类别标识】为[5:年卡]
				else if ("5".equals(smType.getTypeFlag())) {
					map.put("typeCode", "2");
					details = feAdDao.selectGoodsByCriterias(map);
					totalCount = feAdDao.countGoodsByCriterias(map);
				}
			}

			if (details == null || details.size() <= 0) {
				result.put("details", details);
				result.put("currentPageNo", 0); // 当前页码
				result.put("totalCount", 0); // 总记录数
			} else {
				result.put("details", details);
				result.put("currentPageNo", map.get("currentPageNo")); // 当前页码
				result.put("totalCount", totalCount); // 总记录数
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
