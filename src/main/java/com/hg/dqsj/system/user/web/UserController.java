package com.hg.dqsj.system.user.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.MD5Util;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
import com.hg.dqsj.system.role.entity.Role;
import com.hg.dqsj.system.role.entity.UserRole;
import com.hg.dqsj.system.role.service.RoleService;
import com.hg.dqsj.system.user.entity.User;
import com.hg.dqsj.system.user.service.UserService;

/**
 * 用户管理Controller类
 * 
 * @author mj
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			List<Role> rlist = roleService.selectAllRole();
			model.put("rlist", rlist);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return "common/error/500";
		}

		return "system/admin/list";
	}

	/**
	 * 查询用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			boolean boo = menuService.getOper("user/", "SEARCH", request, session);
			if(!boo){
				result.put("msg", "无查询系统管理员的操作权限");
				return result;
			}
			String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName")); // 用户名称
			String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); // 用户手机号码
			String adminFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adminFlag")); // 管理员标识
			String userFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userFlag")); // 用户标识
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

			if (!StringUtil.isChsEnNum(userName, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的用户名！");
			} else if (!NumberUtil.isLong(userPhone, false)) {
				result.put("isError", "1");
				result.put("msg", "只能输入由数字组成的手机号码！");
			} else if (!NumberUtil.isInt(currentPageNo, false)) {
				result.put("isError", "1");
				result.put("msg", "非法请求！");
			} else {

				Map<String, String> m = new HashMap<>();
				m.put("startRowNo", String.valueOf(startRowNo));
				m.put("endRowNo", pageSize);

				m.put("userName", userName);
				m.put("userPhone", userPhone);
				m.put("adminFlag", adminFlag);
				m.put("userFlag", userFlag);

				int totalCount = userService.getUserCount(m);
				List<User> lsUser = this.userService.selectUserByIdAndNameAndPhone(m);

				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", lsUser);

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询用户信息失败！");
		}

		return result;
	}

	/**
	 * 查询用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectUserRoleByUserId")
	@ResponseBody
	public Map<String, Object> selectUserRoleByUserId(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户Id
			Map<String, String> param = new HashMap<>();
			param.put("userId", userId);
			List<UserRole> list = userService.selectUserRoleByUserId(param);
			result.put("rows", list);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询用户角色信息失败！");
		}

		return result;
	}

	/**
	 * 请求新增用户信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(@RequestParam(value = "filepath", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			boolean boo = menuService.getOper("user/", "ADD", request, session);
			if(!boo){
				result.put("msg", "无添加系统管理员的操作权限");
				return result;
			}
			String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName")); // 用户名
			String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); // 手机号码
			String userTel = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userTel")); // 固话号码
			String userEmail = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userEmail")); // 邮箱
			String userGender = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userGender")); // 性别
			// String userPicUrl =
			// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
			// "userPicUrl")); // 用户头像地址
			String userBirthday = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userBirthday")); // 生日
			String userFlag = "0"; // 用户标识（0:有密码账户；1：无密码账户；）
			// String isLocked =
			// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
			// "isLocked")); // 是否激活
			String adminFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adminFlag")); // 管理员标识
			String userOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注

			if (!StringUtil.isChsEnNum(userName, true)) {
				result.put("msg", "请输入由中文、英文、数字或下划线组成的用户名");
			} else if (selectUserByName(null, userName)) {
				result.put("msg", "用户名已存在");
			} else if (!StringUtil.isMobilePhone(userPhone, false)) {
				result.put("msg", "请输入正确的手机号码");
			} else if (!StringUtil.isTelphone(userTel, false)) {
				result.put("msg", "请输入正确的固话号码");
			} else if (!StringUtil.isEMail(userEmail, false)) {
				result.put("msg", "请输入正确的邮箱地址");
			} else if (!"0".equals(userGender) && !"1".equals(userGender) && !"2".equals(userGender)) {
				result.put("msg", "非法请求，性别格式错误");
			} else if (!StringUtil.isBirthday(userBirthday, false)) {
				result.put("msg", "非法请求，生日格式错误");
			} else if (!"0".equals(userFlag) && !"1".equals(userFlag)) {
				result.put("msg", "非法请求，用户标识格式错误");
			} else if (!"0".equals(adminFlag) && !"1".equals(adminFlag)) {
				result.put("msg", "非法请求，管理员标识格式错误");
			} else if (!NumberUtil.isInt(userOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else if (!StringUtil.isParagraph(remark, false)) {
				result.put("msg", "请不要输入有特殊字符的备注");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				User user = new User();
				String strCurrentDateTime = DateUtil.getFullTime();

				user.setId(UUIdUtil.getUUID());
				user.setUserName(userName);
				user.setUserPassWord(MD5Util.getMD5("123456"));
				user.setUserPhone(userPhone);
				user.setUserTel(userTel);
				user.setUserEmail(userEmail);
				user.setUserGender(userGender);
				// 头像
				String picUrl = "";
				if (null != file) {
					picUrl = uploadFile(file, request);
				}
				user.setUserPicUrl(picUrl);
				user.setUserBirthday(userBirthday);
				user.setIsLocked("0");
				user.setAdminFlag(adminFlag);
				user.setUserOrder(NumberUtil.strToInt(userOrder));
				user.setRemark(remark);
				user.setCreateDate(strCurrentDateTime);
				user.setCreateUserId(auth.getUserId());
				user.setUpdateDate(strCurrentDateTime);
				user.setUpdateUserId(auth.getUserId());
				user.setDeleteFlag("0");

				this.userService.insertUser(user);

				result.put("isError", "0");
				result.put("msg", "新建用户信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，新建用户信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 请求修改后管用户信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestParam(value = "filepath", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			boolean boo = menuService.getOper("user/", "UPDATE", request, session);
			if(!boo){
				result.put("msg", "无修改系统管理员的操作权限");
				return result;
			}
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID
			String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName")); // 用户名
			String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); // 手机号码
			String userTel = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userTel")); // 固话号码
			String userEmail = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userEmail")); // 邮箱
			String userGender = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userGender")); // 性别
			String userPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPicUrl")); // 用户头像地址
			String userBirthday = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userBirthday")); // 生日
			String userFlag = "0"; // 用户标识（0:有密码账户；1：无密码账户；）
			// String isLocked =
			// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
			// "isLocked")); // 是否激活
			String adminFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "adminFlag")); // 管理员标识
			String userOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userOrder")); // 排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注
			String picFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "picFlag")); // 图片修改1-修改

			if (!UUIdUtil.matchUUIDOrBlank(userId, true)) {
				result.put("msg", "非法请求！");
			} else if (!StringUtil.isChsEnNum(userName, true)) {
				result.put("msg", "请输入由中文、英文、数字或下划线组成的用户名");
			} else if (selectUserByName(userId, userName)) {
				result.put("msg", "用户名已存在");
			} else if (!StringUtil.isMobilePhone(userPhone, false)) {
				result.put("msg", "请输入正确的手机号码");
			} else if (!StringUtil.isTelphone(userTel, false)) {
				result.put("msg", "请输入正确的固话号码");
			} else if (!StringUtil.isEMail(userEmail, false)) {
				result.put("msg", "请输入正确的邮箱地址");
			} else if (!"0".equals(userGender) && !"1".equals(userGender) && !"2".equals(userGender)) {
				result.put("msg", "非法请求，性别格式错误");
			} else if (!StringUtil.isBirthday(userBirthday, false)) {
				result.put("msg", "非法请求，生日格式错误");
			} else if (!"0".equals(userFlag) && !"1".equals(userFlag)) {
				result.put("msg", "非法请求，用户标识格式错误");
			} else if (!"0".equals(adminFlag) && !"1".equals(adminFlag)) {
				result.put("msg", "非法请求，管理员标识格式错误");
			} else if (!NumberUtil.isInt(userOrder, false)) {
				result.put("msg", "请输入正确的排序顺序");
			} else if (!StringUtil.isParagraph(remark, false)) {
				result.put("msg", "请不要输入有特殊字符的备注");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				User user = new User();

				user.setId(userId);
				user.setUserName(userName);
				user.setUserPhone(userPhone);
				user.setUserTel(userTel);
				user.setUserEmail(userEmail);
				user.setUserGender(userGender);
				user.setUserBirthday(userBirthday);
				user.setUserPicUrl(userPicUrl);
				user.setIsLocked("0");
				user.setAdminFlag(adminFlag);
				user.setUserOrder(NumberUtil.strToInt(userOrder));
				user.setRemark(remark);
				user.setUpdateDate(DateUtil.getFullTime());
				user.setUpdateUserId(auth.getUserId());

				String picpath = "";
				if ("1".equals(picFlag)) {
					String picUrl = "";
					if (null != file) {
						picUrl = uploadFile(file, request);
					}
					user.setUserPicUrl(picUrl);
					Map<String, String> param = new HashMap<String, String>();
					param.put("userId", userId);
					List<User> lis = userService.selectUserByIdAndNameAndPhone(param);
					if (null != lis && lis.size() > 0) {
						picpath = lis.get(0).getUserPicUrl();
					}
				}

				this.userService.updateUser(user);
				if ("1".equals(picFlag)) {
					if (!StringUtil.isNullorEmpty(picpath)) {
						List<String> filearr = new ArrayList<String>();
						String path = request.getSession().getServletContext().getRealPath(picpath);
						filearr.add(path);
						deleteFiles(filearr);
					}
				}
				result.put("isError", "0");
				result.put("msg", "修改用户信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，修改用户信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	private boolean selectUserByName(String userId, String userName) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("userName", userName);
		User user = userService.selectUserByName(param);
		if (null != user) {
			return true;
		}
		return false;
	}

	/**
	 * 请求逻辑删除用户信息
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			boolean boo = menuService.getOper("user/", "DELETE", request, session);
			if(!boo){
				result.put("msg", "无删除系统管理员的操作权限");
				return result;
			}
			
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID

			if (!UUIdUtil.matchUUIDOrBlank(userId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);

				this.userService.deleteUser(userId, auth.getUserId());

				result.put("isError", "0");
				result.put("msg", "删除用户信息成功");
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，删除用户信息失败！请联系管理员");
			logger.error(ex.getMessage());
		}
		return result;
	}

	/**
	 * 密码修改页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "uptPwPage", method = RequestMethod.GET)
	public String uptPwPage(HttpServletRequest request, ModelMap model) {
		return "system/admin/uptPassWord";
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "password/change", method = RequestMethod.POST)
	public Map<String, String> changePassword(HttpServletRequest request, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String oldPassword = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "oldPassword")); // 原密码
			String newPassword = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "newPassword")); // 新密码
			String confirmPassword = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "confirmPassword")); // 确认密码

			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);

			if (StringUtil.isNullorEmpty(oldPassword)) {
				result.put("msg", "请输入原密码！");
			} else if (StringUtil.isNullorEmpty(newPassword)) {
				result.put("msg", "请输入新密码！");
			} else if (StringUtil.isNullorEmpty(confirmPassword)) {
				result.put("msg", "请输入确认密码！");
			} else if (!newPassword.equals(confirmPassword)) {
				result.put("msg", "新密码与确认密码不一致！");
			} else {
				User user = this.userService.selectUserByPw(auth.getUserId(), MD5Util.getMD5(oldPassword));
				if (user != null) {
					user.setUserPassWord(MD5Util.getMD5(newPassword));
					user.setUpdateDate(DateUtil.getFullTime());
					user.setUpdateUserId(user.getId());
					this.userService.changePassword(user);

					result.put("isError", "0");
					result.put("msg", "修改密码成功！");
				} else {
					result.put("msg", "原密码不正确，修改密码失败！");
				}
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，修改密码失败！请联系管理员");
			logger.error(ex.getMessage());
		}

		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "password/reset", method = RequestMethod.POST)
	public Map<String, String> resetPassword(HttpServletRequest request, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String userId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userId")); // 用户ID
			String newPassword = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "newPassword")); // 新密码
			String confirmPassword = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "confirmPassword")); // 确认密码

			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);

			if (!UUIdUtil.matchUUIDOrBlank(userId, true)) {
				result.put("msg", "非法请求！");
			} else if (StringUtil.isNullorEmpty(newPassword)) {
				result.put("msg", "请输入新密码！");
			} else if (StringUtil.isNullorEmpty(confirmPassword)) {
				result.put("msg", "请输入确认密码！");
			} else if (!newPassword.equals(confirmPassword)) {
				result.put("msg", "新密码与确认密码不一致！");
			} else if (!"2".equals(auth.getUser().getAdminFlag())) {
				result.put("msg", "您没有重置密码的权限！");
			} else {
				User user = new User();
				user.setId(userId);
				user.setUserPassWord(MD5Util.getMD5(newPassword));
				user.setUpdateDate(DateUtil.getFullTime());
				user.setUpdateUserId(user.getId());
				this.userService.changePassword(user);

				result.put("isError", "0");
				result.put("msg", "修改密码成功！");
			}
		} catch (Exception ex) {
			result.put("msg", "系统错误，修改密码失败！请联系管理员");
			logger.error(ex.getMessage());
		}

		return result;
	}

}
