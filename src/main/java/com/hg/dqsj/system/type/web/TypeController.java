package com.hg.dqsj.system.type.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;

/**
 * 类别管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "type")
public class TypeController {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private SMTypeService smTypeService; // 系统类别SERVICE接口

	/**
	 * 类别列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		return "system/type/type_list";
	}

	/**
	 * 加载类别详情列表
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
			Pager pager = smTypeService.selectByCriterias(request); // 查询结果
			if (pager != null) {
				result.put("details", pager.getItems()); // 类别详细信息
				result.put("currentPage", pager.getCurrentPage()); // 当前页码
				result.put("totalCount", pager.getTotalCount()); // 总记录数
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
		String typeFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeFlag"));
		String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode"));
		String typeName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeName"));
		String feAction = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "feAction"));
		String typeOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeOrder"));
		String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));
		try {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			if (StringUtil.isNullorEmpty(typeCode)) {
				result.put("msg", "类别代码不能为空！");
			} else if (typeCode.length() > 16) {
				result.put("msg", "类别代码长度不能大于16！");
			} else if (smTypeService.isTypeCodeExsit(id, typeCode)) {
				result.put("msg", "类别代码已存在！");
			} else if (!StringUtil.isNullorEmpty(typeName) && typeName.length() > 16) {
				result.put("msg", "类别名称长度不能大于16！");
			} else if (!NumberUtil.isInt(typeOrder, false)) {
				result.put("msg", "请输入正确的排序!");
			} else {
				SMType bean = null;
				if (!StringUtil.isNullorEmpty(id)) {
					bean = smTypeService.selectById(id);
					if (bean == null) {
						bean = new SMType();
						bean.setId(UUIdUtil.getUUID());
						bean.setTypeFlag(typeFlag);
						bean.setTypeCode(typeCode);
						bean.setTypeName(typeName);
						bean.setFeAction(feAction);
						bean.setTypeOrder(NumberUtil.strToInt(typeOrder));
						bean.setRemark(remark);
						bean.setCreateDate(DateUtil.getFullTime());
						bean.setCreateUserId(auth.getUserId());
						bean.setUpdateDate(DateUtil.getFullTime());
						bean.setUpdateUserId(auth.getUserId());
						bean.setDeleteFlag("0");
						smTypeService.save(bean);
					} else {
						bean.setTypeFlag(typeFlag);
						bean.setTypeCode(typeCode);
						bean.setTypeName(typeName);
						bean.setFeAction(feAction);
						bean.setTypeOrder(NumberUtil.strToInt(typeOrder));
						bean.setRemark(remark);
						bean.setUpdateDate(DateUtil.getFullTime());
						bean.setUpdateUserId(auth.getUserId());
						smTypeService.update(bean);
					}
				} else {
					bean = new SMType();
					bean.setId(UUIdUtil.getUUID());
					bean.setTypeFlag(typeFlag);
					bean.setTypeCode(typeCode);
					bean.setTypeName(typeName);
					bean.setFeAction(feAction);
					bean.setTypeOrder(NumberUtil.strToInt(typeOrder));
					bean.setRemark(remark);
					bean.setCreateDate(DateUtil.getFullTime());
					bean.setCreateUserId(auth.getUserId());
					bean.setUpdateDate(DateUtil.getFullTime());
					bean.setUpdateUserId(auth.getUserId());
					bean.setDeleteFlag("0");
					smTypeService.save(bean);
				}
				result.put("isError", "0"); // 是否报错[0:否][1:是]
				result.put("id", bean.getId());
			}
		} catch (Exception e) {
			result.put("msg", "保存失败，请联系管理员！");
			e.printStackTrace();
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
			SMType smType = smTypeService.selectById(id);
			result.put("detail", smType);
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请联系管理员！");
			e.printStackTrace();
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
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			SMType smType = smTypeService.selectById(id);
			smType.setUpdateDate(DateUtil.getFullTime());
			smType.setUpdateUserId(auth.getUserId());
			smType.setDeleteFlag("1");
			smTypeService.updateDeleteFlagById(smType);
			result.put("isError", "0"); // 是否报错[0:否][1:是]
		} catch (Exception e) {
			result.put("isError", "1"); // 是否报错[0:否][1:是]
			result.put("msg", "操作失败，请联系管理员！");
			e.printStackTrace();
		}
		return result;
	}
}
