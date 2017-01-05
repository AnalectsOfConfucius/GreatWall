package com.hg.dqsj.goods.store.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.goods.store.entity.Store;
import com.hg.dqsj.goods.store.service.StoreService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

/**
 * 店铺信息管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "store")
public class StoreController extends BaseController {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private StoreService storeService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private FeGoodsService feGoodsService;
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 店铺列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "store/list";
	}

	/**
	 * 加载列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {

			boolean boo = menuService.getOper("store/", "SEARCH", request, session);
			if (!boo) {
				result.put("msg", "无查询店铺列表的操作权限");
				return result;
			}
			String storeType = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeType")); // 店铺类别
			String storeName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeName")); // 店铺名称
			String storeNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeNo")); // 店铺代号
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
			if (!StringUtil.isChsEnNum(storeName, false)) {
				result.put("msg", "请不要输入带有特殊字符的店铺名称");
			} else if (!StringUtil.isChsEnNum(storeNo, false)) {
				result.put("msg", "请不要输入带有特殊字符的店铺名称");
			} else if (!NumberUtil.isInt(storeType, false)) {
				result.put("msg", "店铺类别格式错误");
			} else {
				Map<String, String> param = new HashMap<>();
				param.put("startRowNo", String.valueOf(startRowNo));
				param.put("rowSize", pageSize);
				param.put("storeName", storeName);
				param.put("storeNo", storeNo);
				param.put("storeType", storeType);

				int totalCount = storeService.getCount(param);
				List<Store> list = storeService.selectStore(param);

				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", list);
				result.put("isError", "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}

	/**
	 * 详情
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			boolean boo = menuService.getOper("store/", "VIEW", request, session);
			if (!boo) {
				result.put("msg", "无查看店铺的操作权限");
				return result;
			}

			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // 店铺id

			if (!UUIdUtil.matchUUIDOrBlank(storeId, true)) {
				result.put("msg", "非法请求，获得店铺id错误！");
			} else {
				Map<String, String> param = new HashMap<>();
				param.put("id", storeId);
				// 店铺信息
				Store store = storeService.selectById(param);
				if (null != store) {
					store.setStoreContentStr(new String(store.getStoreContent(), "UTF-8"));
				}
				result.put("info", store);
				// 图片
				param.put("objId", storeId);
				List<FePic> plist = feGoodsService.selectPicById(param);
				result.put("plist", plist);

				result.put("isError", "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}

	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // 店铺id

			String op = "";
			String opstr = "";
			if (StringUtil.isNullorEmpty(id)) {
				op = "添加";
				opstr = "ADD";
			} else {
				op = "修改";
				opstr = "UPDATE";
			}
			boolean boo = menuService.getOper("store/", opstr, request, session);
			if (!boo) {
				result.put("msg", "无" + op + "店铺的操作权限");
				return result;
			}
			String storeType = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeType")); // 店铺类别
			if (!NumberUtil.isInt(storeType, true) && StringUtil.isNullorEmpty(id)) {
				result.put("msg", "请选择正确的店铺类别");
				return result;
			}
			String typename = "";
			String objType = "";
			if ("1".equals(storeType)) {
				typename = "食品店";
				objType = "11";
			} else if ("2".equals(storeType)) {
				Map<String, String> param = new HashMap<>();
				param.put("storeType", storeType);
				List<Store> list = storeService.selectStore(param);
				if (null != list && list.size() > 0) {
					result.put("msg", "只能有一条酒店记录，酒店已存在");
					return result;
				}
				typename = "酒店";
				objType = "15";
			}
			String storeName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeName")); // 店铺名称
			String storeNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeNo")); // 店铺代号
			String isOpen = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isOpen")); // 是否开业
			String longitude = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "longitude")); // 经度
			String latitude = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "latitude")); // 纬度
			String storeBrief = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeBrief")); // 店铺简介
			String storeContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeContent")); // 店铺内容
			String storeOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!StringUtil.isChsEnNum(storeName, true)) {
				result.put("msg", "请输入不带有特殊字符的名称");
			} else if (!validate(id, storeName)) {
				result.put("msg", typename + "名称已存在");
			} else if (!StringUtil.isChsEnNum(storeNo, true)) {
				result.put("msg", "请输入不带有特殊字符的店铺代号");
			} else if (!"0".equals(isOpen) && !"1".equals(isOpen)) {
				result.put("msg", "请选择正确的‘是否开业’");
			} else if (!NumberUtil.isFloatNumber(longitude, false)) {
				result.put("msg", "请选择正确的精度");
			} else if (!NumberUtil.isFloatNumber(latitude, false)) {
				result.put("msg", "请选择正确的纬度");
			} else if (!NumberUtil.isInt(storeOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else if (!StringUtil.isParagraph(storeBrief, false)) {
				result.put("msg", "请不要输入带有特殊字符的店铺简介");
			} else if (!StringUtil.isParagraph(remark, false)) {
				result.put("msg", "请不要输入带有特殊字符的备注");
			} else {

				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String fDate = DateUtil.getFullTime();
				String deleteFlag = "0";
				Store store = new Store();

				if (StringUtil.isNullorEmpty(id)) {
					store.setId(UUIdUtil.getUUID());
				} else {
					store.setId(id);
				}
				store.setStoreName(storeName);
				store.setStoreNo(storeNo);
				store.setStoreType(storeType);
				store.setIsOpen(isOpen);
				store.setLongitude(longitude);
				store.setLatitude(latitude);
				store.setStoreBrief(storeBrief);
				store.setStoreContent(storeContent.getBytes("UTF-8"));
				if (!StringUtil.isNullorEmpty(storeOrder)) {
					store.setStoreOrder(Integer.parseInt(storeOrder));
				}
				store.setRemark(remark);
				store.setCreateUserId(auth.getUserId());
				store.setCreateDate(fDate);
				store.setUpdateUserId(auth.getUserId());
				store.setUpdateDate(fDate);
				store.setDeleteFlag(deleteFlag);

				List<FePic> plist = new ArrayList<FePic>();
				String[] picUrlArr = request.getParameterValues("picUrl");
				if (null != picUrlArr && picUrlArr.length > 0) {
					String[] mplarr = request.getParameterValues("mainPicFlag");
					String[] orderarr = request.getParameterValues("picOrder");

					for (int i = 0; i < picUrlArr.length; i++) {
						String picUrl = picUrlArr[i];
						String[] parr = picUrl.split("/");
						String picName = parr[parr.length - 1];
						String mainPicFlag = "0";
						if (mplarr.length > i) {
							mainPicFlag = mplarr[i];
						}
						String po = "0";
						int picOrder = 0;
						if (orderarr.length > i) {
							po = orderarr[i];
							if (!NumberUtil.isInt(po, false)) {
								result.put("msg", "请输入正确的图片排序顺序");
								return result;
							}
							if (!StringUtil.isNullorEmpty(po)) {
								picOrder = NumberUtil.strToInt(po);
							}
						}
						FePic pic = new FePic();
						pic.setId(UUIdUtil.getUUID());
						pic.setObjType(objType);
						pic.setObjId(store.getId());
						pic.setPicUrl(picUrl);
						pic.setPicName(picName);
						pic.setPicOrder(picOrder);
						pic.setMainPicFlag(mainPicFlag);
						pic.setRemark("");
						pic.setCreateUserId(auth.getUserId());
						pic.setCreateDate(fDate);
						pic.setUpdateUserId(auth.getUserId());
						pic.setUpdateDate(fDate);
						pic.setDeleteFlag(deleteFlag);
						plist.add(pic);
					}
				}

				if (StringUtil.isNullorEmpty(id)) {
					storeService.save(store, plist);
					result.put("msg", "店铺添加成功");
				} else {
					storeService.update(store, plist);
					result.put("msg", "店铺修改成功");
				}
				result.put("isError", "0");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员！");
			e.printStackTrace();
		}
		return result;
	}

	private boolean validate(String id, String storeName) {
		Map<String, String> param = new HashMap<>();
		param.put("isvali", "1");
		param.put("id", id);
		param.put("sName", storeName);
		List<Store> list = storeService.selectStore(param);
		if (null != list && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 删除店铺信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, String> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			boolean boo = menuService.getOper("store/", "DELETE", request, session);
			if (!boo) {
				result.put("msg", "无删除店铺的操作权限");
				return result;
			}

			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // ID

			if (!UUIdUtil.matchUUIDOrBlank(storeId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Map<String, String> param = new HashMap<>();
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String updateUserId = auth.getUserId();
				String updateDate = DateUtil.getFullTime();

				param.put("objId", storeId);
				// 商品图片
				List<FePic> gplist = feGoodsService.selectPicById(param);

				param.put("id", storeId);
				param.put("updateUserId", updateUserId);
				param.put("updateDate", updateDate);
				param.put("deleteFlag", "1");
				storeService.delete(param);

				if (null != gplist && gplist.size() > 0) {
					List<String> filearr = new ArrayList<String>();
					for (int i = 0; i < gplist.size(); i++) {
						String mPath = request.getSession().getServletContext().getRealPath(gplist.get(i).getPicUrl());
						String bpath = mPath.replace("_1", "");
						filearr.add(mPath);
						filearr.add(bpath);
					}
					deleteFiles(filearr);
				}

				result.put("isError", "0");
				result.put("msg", "删除信息成功");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 餐饮店广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "groceryStore/ad", method = RequestMethod.GET)
	public String groceryStoreAd(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("groceryFood");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}

	/**
	 * 酒店广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hotel/ad", method = RequestMethod.GET)
	public String hotelAd(HttpServletRequest request, ModelMap model) {
		SMType type = smTypeService.selectByTypeCode("hotel");
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		if (type != null) {
			model.put("typeId", type.getId());
		}
		model.put("types", types);
		return "base/ad-list";
	}
}
