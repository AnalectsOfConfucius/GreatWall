package com.hg.dqsj.system.role.web;

import java.util.ArrayList;
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

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.role.entity.UserRole;
import com.hg.dqsj.system.role.service.UserRoleService;

/**
 * 功能：角色管理Controller类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "userRole")
public class UserRoleController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SessionProvider session;
	
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 显示角色列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		
		int totalCount = 0;
		
		try {
			
			List<UserRole> list = userRoleService.selectUserRole(null);
			if (list != null && list.size() > 0) {
				
				totalCount = list.size();
				Pager p = new Pager();
				p.setCurrentPage(1);
				p.setPageSize(10);
				p.setTotalCount(totalCount);
				p.setItems(list);
				
				@SuppressWarnings("unchecked")
				List<UserRole> urlist = (List<UserRole>) p.doPagination();
		        
		        model.addAttribute("rows", urlist);
				model.addAttribute("currentPage", p.getCurrentPage());
				
			}
			
		} catch (Exception ex) {
            logger.error(ex.getMessage());
            return "common/error/500";
		}
		
		model.addAttribute("totalCnt", totalCount);
		return "system/role/list";
	}
	
	/**
	 * 查询角色列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		
		try {
			String roleName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleName")); // 权限名称
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			
			if (!StringUtil.isChsEnNum(roleName, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的权限名称！");
			} else if (!NumberUtil.isInt(currentPageNo, false)) {
				result.put("isError", "1");
				result.put("msg", "非法请求！");
			} else {
			
				Map<String, String> param = new HashMap<>();
				param.put("roleName", roleName);
				
				List<UserRole> list = userRoleService.selectUserRole(param);
				if (list != null && list.size() > 0) {
					
					int totalCount = list.size();
					Pager p = new Pager();
					p.setCurrentPage(NumberUtil.strToInt(currentPageNo));
					p.setTotalCount(totalCount);
					p.setItems(list);
					
					@SuppressWarnings("unchecked")
					List<UserRole> urlist = (List<UserRole>) p.doPagination();
					
					result.put("currentPage", p.getCurrentPage());
					result.put("totalCnt", totalCount);
			        result.put("rows", urlist);
					
				}
				
			}
			
		} catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put("isError", "1");
			result.put("msg", "系统错误，查询用户角色信息失败！");
		}
		
		return result;
	}
	
	/**
	 * 请求新增权限信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		
		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户Id
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色Id
			List<String> uuids = new ArrayList<String>();
			uuids.add(roleId);
			uuids.add(userId);
			if (!UUIdUtil.matchUUIds(uuids, true)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				UserRole userRole = new UserRole();
				String strCurrentDateTime = DateUtil.getFullTime();
				
				userRole.setId(UUIdUtil.getUUID());
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRole.setCreateDate(strCurrentDateTime);
				userRole.setCreateUserId(auth.getUserId());
				userRole.setUpdateDate(strCurrentDateTime);
				userRole.setUpdateUserId(auth.getUserId());
				userRole.setDeleteFlag("0");
				
				userRoleService.insertUserRole(userRole);
				
				result.put("isError", "0");
				result.put("msg", "新建用户角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "新建用户角色信息失败！请联系管理员");
            logger.error(ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 请求修改菜单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		
		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户Id
			String roleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roleId")); // 角色Id
			String userRoleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userRoleId")); // 用户角色Id
			List<String> uuids = new ArrayList<String>();
			uuids.add(roleId);
			uuids.add(userId);
			if (!UUIdUtil.matchUUIds(uuids, false)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				UserRole userRole = new UserRole();
				String strCurrentDateTime = DateUtil.getFullTime();
				userRole.setId(userRoleId);
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRole.setCreateDate(strCurrentDateTime);
				userRole.setCreateUserId(auth.getUserId());
				userRole.setUpdateDate(strCurrentDateTime);
				userRole.setUpdateUserId(auth.getUserId());
				userRole.setDeleteFlag("0");
				
				userRoleService.updateUserRole(userRole);
				
				result.put("isError", "0");
				result.put("msg", "修改用户角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "修改用户角色信息失败！请联系管理员");
            logger.error(ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 请求逻辑删除权限信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		
		try {
			String userRoleId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userRoleId")); // 用户角色ID
			
			if (!UUIdUtil.matchUUIDOrBlank(userRoleId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				UserRole userRole = new UserRole();
				String strCurrentDateTime = DateUtil.getFullTime();
				userRole.setId(userRoleId);
				userRole.setCreateDate(strCurrentDateTime);
				userRole.setCreateUserId(auth.getUserId());
				userRole.setUpdateDate(strCurrentDateTime);
				userRole.setUpdateUserId(auth.getUserId());
				userRole.setDeleteFlag("0");
				
				userRoleService.deleteUserRole(userRole);
				
				result.put("isError", "0");
				result.put("msg", "删除用户角色信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "删除用户角色信息失败！请联系管理员");
            logger.error(ex.getMessage());
		}
		return result;
	}
	
}
