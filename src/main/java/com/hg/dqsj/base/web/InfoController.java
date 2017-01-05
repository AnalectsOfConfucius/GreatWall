package com.hg.dqsj.base.web;

import java.util.HashMap;
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
import com.hg.dqsj.base.entity.FEInfo;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEInfo;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * 资讯信息管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "info")
public class InfoController extends BaseController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE

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
			if (menuService.isHasOperator(request, "search")) {
				result = infoService.selectByCriterias(request); // 查询结果
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请重试！");
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
			if (menuService.isHasOperator(request, "update")) {
				result = infoService.saveOrUpdate(request, file != null ? uploadFile(file, request) : null);
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1");
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
				result.put("msg", "非法请求，获得ID错误！");
			} else {
				VFEInfo detail = infoService.selectVById(id);
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
					result.put("msg", "非法请求，获得ID错误！");
				} else {
					String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode"));
					FEInfo feInfo = infoService.selectById(id);
					if (feInfo != null) {
						feInfo.setUpdateDate(DateUtil.getFullTime());
						feInfo.setUpdateUserId(auth.getUserId());
						feInfo.setDeleteFlag("1");
						infoService.updateDeleteFlagById(typeCode, feInfo);
						result.put("isError", "0"); // 是否报错[0:否][1:是]
						result.put("msg", "删除成功！");
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
	 * 根据【资讯映射类型】和【类别代码】查询详细信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detailByInfoMapTypeAndTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailByInfoMapTypeAndTypeCode(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1"); // 是否报错[0:否][1:是]
		try {
			if (menuService.isHasOperator(request, "view")) {
				String infoMapType = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "infoMapType"));
				String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode"));

				if (!"1".equals(infoMapType) && !"2".equals(infoMapType)) {
					result.put("msg", "请输入正确的【资讯映射类型】！");
				} else if (!StringUtil.isLetter(typeCode, true)) {
					result.put("msg", "请输入正确的【类别代码】！");
				} else {
					VFEInfo detail = infoService.selectVByInfoMapTypeAndTypeCode(request, infoMapType, typeCode);
					result.put("detail", detail);
					result.put("isError", "0"); // 是否报错[0:否][1:是]
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查看权限！");
			}
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请重试！");
			logger.error(e.getMessage());
		}
		return result;
	}
}
