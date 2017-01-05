package com.hg.dqsj.ad.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.ad.entity.FEAd;
import com.hg.dqsj.ad.service.FEAdService;
import com.hg.dqsj.ad.view.VFEAd;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

/**
 * 广告管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "ad")
public class AdController extends BaseController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE

	/**
	 * 广告列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		List<SMType> types = smTypeService.selectByTypeFlagNotNull();
		model.put("types", types);
		return "ad/ad-list";
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
		try {
			/*if (menuService.isHasOperator(request, "search")) {
				result = feAdService.selectByCriterias(request); // 查询结果
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}*/
			result = feAdService.selectByCriterias(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "操作失败，请重试！");
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
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, @RequestParam(value = "filepath", required = false) MultipartFile file, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			/*if (menuService.isHasOperator(request, "update")) {
				result = feAdService.saveOrUpdate(request, file != null ? uploadFile(file, request) : null);
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}*/
			result = feAdService.saveOrUpdate(request, file != null ? uploadFile(file, request) : null);
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "保存失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 详细信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
				result.put("msg", "非法请求，获得广告ID错误！");
			} else {
				VFEAd detail = feAdService.selectVById(id);
				result.put("detail", detail);
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			if (menuService.isHasOperator(request, "delete")) {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "非法请求，获得广告ID错误！");
				} else {
					FEAd feAd = feAdService.selectById(id);
					if (feAd != null) {
						feAd.setUpdateDate(DateUtil.getFullTime());
						feAd.setUpdateUserId(auth.getUserId());
						feAd.setDeleteFlag("1");
						if (feAdService.updateDeleteFlagById(feAd) == 1) {
							result.put("isError", "0"); // 是否报错[0:否][1:是]
							result.put("msg", "删除成功！");
						} else {
							result.put("isError", "1"); // 是否报错[0:否][1:是]
							result.put("msg", "删除失败，请重试！");
						}
					} else {
						result.put("isError", "1"); // 是否报错[0:否][1:是]
						result.put("msg", "操作失败，请重试！");
					}
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的删除权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 加载目标链接信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loadObjLinkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadObjLinkDetail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		try {
			result = feAdService.loadObjLinkDetailByCriterias(request); // 查询结果
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}
}
