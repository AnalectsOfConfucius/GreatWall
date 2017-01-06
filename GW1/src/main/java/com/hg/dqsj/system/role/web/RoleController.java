package com.hg.dqsj.system.role.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.role.entity.Operate;
import com.hg.dqsj.system.role.entity.Role;
import com.hg.dqsj.system.role.entity.RoleAuth;
import com.hg.dqsj.system.role.entity.RoleMenu;
import com.hg.dqsj.system.role.entity.RoleOperate;
import com.hg.dqsj.system.role.service.RoleService;

/**
 * 功能：角色管理Controller类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "role")
public class RoleController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	/**
	 * 显示角色列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		// int totalCount = 0;
		// try {
		// List<Role> lsRole = this.roleService.selectAllRole();
		// if (lsRole != null && lsRole.size() > 0) {
		// totalCount = lsRole.size();
		// Pager p = new Pager();
		// p.setCurrentPage(1);
		// p.setPageSize(10);
		// p.setTotalCount(totalCount);
		// p.setItems(lsRole);
		// @SuppressWarnings("unchecked")
		// List<Role> rtnRoleList = (List<Role>) p.doPagination();
		// model.addAttribute("lsRole", rtnRoleList);
		// model.addAttribute("currentPage", p.getCurrentPage());
		// }
		// } catch (Exception ex) {
		// logger.error(ex.getMessage());
		// return "common/error/500";
		// }
		// model.addAttribute("totalCnt", totalCount);
		return "system/role/list";
	}

	/**
	 * 查询角色列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			String roleName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleName")); // 角色名称
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数

			if (!StringUtil.isChsEnNum(roleName, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的角色名称！");
			} else if (!NumberUtil.isInt(currentPageNo, false)) {
				result.put("isError", "1");
				result.put("msg", "非法请求！");
			} else {

				Map<String, String> m = new HashMap<>();
				m.put("roleName", roleName);

				List<Role> lsRole = this.roleService.selectRoleByIdAndName(m);
				if (lsRole != null && lsRole.size() > 0) {

					int totalCount = lsRole.size();
					Pager p = new Pager();
					p.setCurrentPage(NumberUtil.strToInt(currentPageNo));
					p.setTotalCount(totalCount);
					p.setItems(lsRole);

					@SuppressWarnings("unchecked")
					List<Role> rtnRoleList = (List<Role>) p.doPagination();

					result.put("currentPage", p.getCurrentPage());
					result.put("totalCnt", totalCount);
					result.put("rows", rtnRoleList);

				}

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询角色信息失败！");
		}

		return result;
	}

	/**
	 * 请求新增角色信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String roleName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleName")); // 角色名称
			String roleOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!StringUtil.isChsEnNum(roleName, true)) {
				result.put("msg", "请输入由中文、英文、数字或下划线组成的角色名称");
			} else if (!NumberUtil.isInt(roleOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else if (!StringUtil.isChsEnNum(remark, false)) {
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的备注");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Role role = new Role();
				String strCurrentDateTime = DateUtil.getFullTime();

				role.setId(UUIdUtil.getUUID());
				role.setRoleName(roleName);
				role.setRoleOrder(NumberUtil.strToInt(roleOrder));
				role.setRemark(remark);
				role.setCreateDate(strCurrentDateTime);
				role.setCreateUserId(auth.getUserId());
				role.setUpdateDate(strCurrentDateTime);
				role.setUpdateUserId(auth.getUserId());
				role.setDeleteFlag("0");

				this.roleService.insertRole(role);

				result.put("isError", "0");
				result.put("msg", "新建角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "新建角色信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求修改菜单信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色ID
			String roleName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleName")); // 角色名称
			String roleOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!UUIdUtil.matchUUIDOrBlank(roleId, true)) {
				result.put("msg", "非法请求！");
			} else if (!StringUtil.isChsEnNum(roleName, true)) {
				result.put("msg", "请输入由中文、英文、数字或下划线组成的角色名称");
			} else if (!NumberUtil.isInt(roleOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else if (!StringUtil.isChsEnNum(remark, false)) {
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的备注");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Role role = new Role();

				role.setId(roleId);
				role.setRoleName(roleName);
				role.setRoleOrder(NumberUtil.strToInt(roleOrder));
				role.setRemark(remark);
				role.setUpdateDate(DateUtil.getFullTime());
				role.setUpdateUserId(auth.getUserId());

				this.roleService.updateRole(role);

				result.put("isError", "0");
				result.put("msg", "修改角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "修改角色信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求逻辑删除角色信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色ID

			if (!UUIdUtil.matchUUIDOrBlank(roleId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);

				this.roleService.deleteRole(roleId, auth.getUserId());

				result.put("isError", "0");
				result.put("msg", "删除角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "删除角色信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 查询角色权限
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectRoleAuth")
	@ResponseBody
	public Map<String, Object> selectRoleAuth(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色Id

			if (!UUIdUtil.matchUUIDOrBlank(roleId, true)) {
				result.put("isError", "1");
				result.put("msg", "非法请求！");
			} else {

				Map<String, String> param = new HashMap<>();
				param.put("roleId", roleId);

				List<RoleAuth> ralist = this.roleService.selectRoleAuth(param);
				Map<String, String> mo = new HashMap<String, String>();
				if (null != ralist && ralist.size() > 0) {
					for (int i = 0; i < ralist.size(); i++) {
						RoleAuth ra = ralist.get(i);
						List<RoleMenu> rmlist = ra.getMlist();
						if (null != rmlist && rmlist.size() > 0) {
							for (int j = 0; j < rmlist.size(); j++) {
								RoleMenu rm = rmlist.get(j);
								List<Operate> olist = rm.getOlist();
								if (null != olist && olist.size() > 0) {
									for (int k = 0; k < olist.size(); k++) {
										Operate op = olist.get(k);
										mo.put(rm.getMenuId() + "+" + op.getOperateId(), "1");
									}
								}
							}
						}
					}
				}
				List<RoleMenu> mlist = this.roleService.selectMenu(param);
				List<Operate> olist = this.roleService.selectOperate(param);
				if (null != mlist && mlist.size() > 0) {
					for (RoleMenu roleMenu : mlist) {
						// if(null!=olist && olist.size()>0){
						// for (Operate op : olist) {
						// String ishav =
						// mo.get(roleMenu.getMenuId()+"+"+op.getOperateId());
						// if("1".equals(ishav)){
						// roleMenu.setIscheckm("1");
						// op.setIschecko("1");
						// }else{
						// op.setIschecko("0");
						// }
						// }
						// }

						roleMenu.setOlist(olist);
					}
				}

				result.put("rows", mlist);
				result.put("operCheck", mo);
				result.put("roleId", roleId);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询权限信息失败！");
		}

		return result;
	}

	/**
	 * 角色权限设置
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "roleAuthSet")
	@ResponseBody
	public Map<String, Object> roleAuthSet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色Id

			String brows = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "brows"));

			if (brows == null || brows.isEmpty()) {
				result.put("msg", "请选择角色操作权限");
			} else {
				List<RoleOperate> ralist = JSON.parseArray(brows, RoleOperate.class);
				if (ralist == null || ralist.isEmpty()) {
					result.put("msg", "非法请求");
				} else {
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String userId = auth.getUserId();
					String date = DateUtil.getFullTime();
					for (RoleOperate roleOperate : ralist) {
						roleOperate.setId(UUIdUtil.getUUID());
						roleOperate.setCreateUserId(userId);
						roleOperate.setCreateDate(date);
						roleOperate.setUpdateUserId(userId);
						roleOperate.setUpdateDate(date);
						roleOperate.setDeleteFlag("0");
					}
					Map<String, String> param = new HashMap<String, String>();
					param.put("roleId", roleId);
					roleService.insertRoleOperate(param, ralist);
				}
				result.put("msg", "角色权限添加成功！");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，角色权限添加失败！");
		}

		return result;
	}
}
