package com.hg.dqsj.credit.web;

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
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.credit.entity.Credit;
import com.hg.dqsj.credit.entity.CreditMoney;
import com.hg.dqsj.credit.entity.UserCredit;
import com.hg.dqsj.credit.service.CreditService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

@Controller
@RequestMapping(value = "credit")
public class CreditController {
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;
	@Autowired
	private CreditService creditService;
	@Autowired
	private MenuService menuService;// 菜单信息SERVICE

	/**
	 * 列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {

		return "credit/list";
	}

	/**
	 * 查询列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");

		if (menuService.isHasOperator(request, "search")) {
			String startAmount = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "startAmount")); // 开始金额
			String endAmount = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "endAmount")); // 结束金额
			String isForever = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isForever")); // 永久有效
																										// 0：否；1：是
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
			Map<String, String> param = new HashMap<>();
			param.put("startRowNo", String.valueOf(startRowNo));
			param.put("endRowNo", pageSize);
			param.put("startAmount", startAmount);
			param.put("endAmount", endAmount);
			param.put("isForever", isForever);

			int totalCount = creditService.getCount(param);

			List<Credit> list = creditService.selectCredit(param);

			result.put("currentPage", currentPageNo);
			result.put("totalCnt", totalCount);
			result.put("rows", list);
		} else {
			result.put("isError", "1");
			result.put("msg", "你未拥有当前菜单的查询权限！");
		}

		return result;
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, String> add(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			if (menuService.isHasOperator(request, "add")) {
				String uuid = UUIdUtil.getUUID();
				String consumeAmount = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "consumeAmount"));// 消费金额
				String giveCredit = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "giveCredit"));// 赠送积分
				String isForever = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isForever"));// 永久有效
																											// 0：否；1：是
				String validEndDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "validEndDate"));// 有效截止日
				String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));// 备注
				if (!NumberUtil.isFloatNumber(consumeAmount, true)) {
					result.put("msg", "请输入正确的消费金额");
				} else if (!NumberUtil.isFloatNumber(giveCredit, true)) {
					result.put("msg", "请输入正确的赠送积分");
				} else if (!DateUtil.isDate(validEndDate, "yyyy-MM-dd", false)) {
					result.put("msg", "请输入正确的赠送积分");
				} else if (!"0".equals(isForever) && !"1".equals(isForever)) {
					result.put("msg", "非法请求，永久有效选择有错误");
				} else if (!StringUtil.isParagraph(remark, false)) {
					result.put("msg", "请不要输入带有特殊字符的备注");
				} else {
					Double amt = NumberUtil.round(NumberUtil.strToDouble(consumeAmount), 2);
					Double cred = NumberUtil.round(NumberUtil.strToDouble(giveCredit), 2);
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();
					String deleteFlag = "0";
					Credit credit = new Credit();
					credit.setId(uuid);
					credit.setConsumeAmount(amt);
					credit.setGiveCredit(cred);
					credit.setIsForever(isForever);
					credit.setValidEndDate(validEndDate);
					credit.setRemark(remark);
					credit.setCreateUserId(auth.getUserId());
					credit.setCreateDate(fDate);
					credit.setUpdateUserId(auth.getUserId());
					credit.setUpdateDate(fDate);
					credit.setDeleteFlag(deleteFlag);
					creditService.insertCredit(credit);

					result.put("isError", "0");
					result.put("msg", "新增消费送积分信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的添加权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，新增消费送积分信息失败！请联系管理员");
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, String> update(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		try {
			if (menuService.isHasOperator(request, "update")) {
				String creditId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditId"));// id
				String consumeAmount = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "consumeAmount"));// 消费金额
				String giveCredit = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "giveCredit"));// 赠送积分
				String isForever = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isForever"));// 永久有效
																											// 0：否；1：是
				String validEndDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "validEndDate"));// 有效截止日
				String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));// 备注
				if (!UUIdUtil.matchUUIDOrBlank(creditId, true)) {
					result.put("msg", "非法请求，消费送积分id获得错误");
				} else if (!NumberUtil.isFloatNumber(consumeAmount, true)) {
					result.put("msg", "请输入正确的消费金额");
				} else if (!NumberUtil.isFloatNumber(giveCredit, true)) {
					result.put("msg", "请输入正确的赠送积分");
				} else if (!DateUtil.isDate(validEndDate, "yyyy-MM-dd", false)) {
					result.put("msg", "请输入正确的赠送积分");
				} else if (!"0".equals(isForever) && !"1".equals(isForever)) {
					result.put("msg", "非法请求，永久有效选择有错误");
				} else if (!StringUtil.isParagraph(remark, false)) {
					result.put("msg", "请不要输入带有特殊字符的备注");
				} else {
					Double amt = NumberUtil.round(NumberUtil.strToDouble(consumeAmount), 2);
					Double cred = NumberUtil.round(NumberUtil.strToDouble(consumeAmount), 2);
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();
					String deleteFlag = "0";

					Credit credit = new Credit();
					credit.setId(creditId);
					credit.setConsumeAmount(amt);
					credit.setGiveCredit(cred);
					credit.setIsForever(isForever);
					credit.setValidEndDate(validEndDate);
					credit.setRemark(remark);
					credit.setCreateUserId(auth.getUserId());
					credit.setCreateDate(fDate);
					credit.setUpdateUserId(auth.getUserId());
					credit.setUpdateDate(fDate);
					credit.setDeleteFlag(deleteFlag);
					creditService.updateCredit(credit);

					result.put("isError", "0");
					result.put("msg", "修改消费送积分信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，修改消费送积分信息失败！请联系管理员");
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
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, String> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		try {
			if (menuService.isHasOperator(request, "delete")) {
				String creditId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditId")); // id
				if (!UUIdUtil.matchUUIDOrBlank(creditId, true)) {
					result.put("msg", "非法请求，获得消费送积分id错误");
				} else {
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();

					Map<String, String> param = new HashMap<String, String>();
					param.put("creditId", creditId);
					param.put("updateUserId", auth.getUserId());
					param.put("updateDate", fDate);
					param.put("deleteFlag", "1");

					creditService.deleteCredit(param);

					result.put("isError", "0");
					result.put("msg", "删除消费送积分信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的删除权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，删除消费送积分信息失败！请联系管理员");
		}
		return result;
	}

	/**
	 * 积分抵金额列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "creditMoney", method = RequestMethod.GET)
	public String creditMoney(HttpServletRequest request, ModelMap model) {
		return "credit/creditMoney";
	}

	/**
	 * 查询列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectCreditMoney")
	@ResponseBody
	public Map<String, Object> selectCreditMoney(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			if (menuService.isHasOperator(request, "search")) {
				// String typeCode =
				// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
				// "typeCode")); // 类别
				// String currentPageNo =
				// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
				// "currentPageNo")); // 当前页数
				// String pageSize =
				// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
				// "pageSize")); // 当前页数
				/*
				 * if(StringUtil.isNullorEmpty(pageSize)){ pageSize = "10"; }
				 * if(StringUtil.isNullorEmpty(currentPageNo)){ currentPageNo =
				 * "1"; }
				 */
				/*
				 * int curno = Integer.parseInt(currentPageNo); int pz =
				 * Integer.parseInt(pageSize); int startRowNo = pz*(curno-1);
				 * 
				 * param.put("startRowNo", String.valueOf(startRowNo));
				 * param.put("endRowNo", pageSize); param.put("typeCode",
				 * typeCode);
				 */
				Map<String, String> param = new HashMap<>();
				// int totalCount = creditService.getCreditMoneyCount(param);

				List<CreditMoney> list = creditService.selectCreditMoney(param);
				if (null != list && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						CreditMoney cm = list.get(i);
						if (null != cm.getCreditCont()) {
							list.get(i).setCreditComment(new String(cm.getCreditCont(), "UTF-8"));
						}
					}
				}
				// result.put("currentPage", currentPageNo);
				// result.put("totalCnt", totalCount);
				result.put("rows", list);
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询积分抵金额设置列表失败！");
		}
		return result;
	}

	/**
	 * 新增积分抵金额设置
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addCreditMoney")
	@ResponseBody
	public Map<String, String> addCreditMoney(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		try {
			if (menuService.isHasOperator(request, "add")) {
				String uuid = UUIdUtil.getUUID();
				String usableCondition = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "usableCondition"));// 可用条件
				String creditNum = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditNum"));// 积分数
				String isWorthMoney = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isWorthMoney"));// 可抵金额
				String creditComment = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditComment"));// 积分规则
				String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));// 备注
				// String nameFlag =
				// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
				// "nameFlag"));

				// String usableName = "积分";
				// String worthName = "可抵金额";

				isWorthMoney = "1";
				usableCondition = creditNum;
				Map<String, String> param = new HashMap<String, String>();
				List<CreditMoney> list = creditService.selectCreditMoney(param);
				if (null != list && list.size() > 0) {
					result.put("msg", "类别为‘积分’的记录已存在，且只能有一条");
					return result;
				}

				if (!NumberUtil.isFloatNumber(usableCondition, true)) {
					result.put("msg", "请输入正确的可用条件");
				} else if (!NumberUtil.isFloatNumber(isWorthMoney, true)) {
					result.put("msg", "请输入正确的可抵金额");
				} else if (!StringUtil.isParagraph(creditComment, false)) {
					result.put("msg", "请不要输入带有特殊字符的积分规则");
				} else if (!StringUtil.isParagraph(remark, false)) {
					result.put("msg", "请不要输入带有特殊字符的备注");
				} else {

					Double usable = NumberUtil.round(NumberUtil.strToDouble(usableCondition), 2);
					Double worth = NumberUtil.round(NumberUtil.strToDouble(isWorthMoney), 2);
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();
					String deleteFlag = "0";
					CreditMoney credit = new CreditMoney();
					credit.setId(uuid);
					credit.setUsableCondition(usable);
					credit.setIsWorthMoney(worth);
					credit.setCreditComment(creditComment);
					if (!StringUtil.isNullorEmpty(creditComment)) {
						credit.setCreditCont(creditComment.getBytes("UTF-8"));
					}
					credit.setRemark(remark);
					credit.setCreateUserId(auth.getUserId());
					credit.setCreateDate(fDate);
					credit.setUpdateUserId(auth.getUserId());
					credit.setUpdateDate(fDate);
					credit.setDeleteFlag(deleteFlag);

					creditService.insertCreditMoney(credit);

					result.put("isError", "0");
					result.put("msg", "新增积分抵金额设置信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的添加权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，新增积分抵金额设置信息失败！请联系管理员");
		}
		return result;
	}

	/**
	 * 修改积分抵金额设置
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateCreditMoney")
	@ResponseBody
	public Map<String, String> updateCreditMoney(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");

		try {
			if (menuService.isHasOperator(request, "update")) {
				String creditMoneyId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditMoneyId"));// id
				String usableCondition = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "usableCondition"));// 可用条件
				String creditNum = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditNum"));// 积分数
				String isWorthMoney = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isWorthMoney"));// 可抵金额
				String creditComment = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditComment"));// 积分规则
				String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));// 备注
				// String nameFlag =
				// StringUtil.trimBlank(RequestUtils.getQueryParam(request,
				// "nameFlag"));

				// String usableName = "积分";
				// String worthName = "可抵金额";

				isWorthMoney = "1";
				usableCondition = creditNum;
				if (!UUIdUtil.matchUUIDOrBlank(creditMoneyId, true)) {
					result.put("msg", "非法请求，获得积分抵金额id错误");
				} else if (!NumberUtil.isFloatNumber(usableCondition, true)) {
					result.put("msg", "请输入正确的可用条件");
				} else if (!NumberUtil.isFloatNumber(isWorthMoney, true)) {
					result.put("msg", "请输入正确的可抵金额");
				} else if (!StringUtil.isParagraph(creditComment, false)) {
					result.put("msg", "请不要输入带有特殊字符的积分规则");
				} else if (!StringUtil.isParagraph(remark, false)) {
					result.put("msg", "请不要输入带有特殊字符的备注");
				} else {
					Double usable = NumberUtil.round(NumberUtil.strToDouble(usableCondition), 2);
					Double worth = NumberUtil.round(NumberUtil.strToDouble(isWorthMoney), 2);
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();

					CreditMoney credit = new CreditMoney();
					credit.setId(creditMoneyId);
					credit.setUsableCondition(usable);
					credit.setIsWorthMoney(worth);
					if (!StringUtil.isNullorEmpty(creditComment)) {
						credit.setCreditCont(creditComment.getBytes("UTF-8"));
					}
					credit.setCreditComment(creditComment);
					credit.setRemark(remark);
					credit.setUpdateUserId(auth.getUserId());
					credit.setUpdateDate(fDate);

					creditService.updateCreditMoney(credit);

					result.put("isError", "0");
					result.put("msg", "修改积分抵金额设置信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的编辑权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，修改积分抵金额设置信息失败！请联系管理员");
		}
		return result;
	}

	/**
	 * 删除积分抵金额设置
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteCreditMoney")
	@ResponseBody
	public Map<String, String> deleteCreditMoney(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		try {
			if (menuService.isHasOperator(request, "delete")) {
				String creditMoneyId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "creditMoneyId")); // id

				if (!UUIdUtil.matchUUIDOrBlank(creditMoneyId, true)) {
					result.put("msg", "非法请求，获得积分抵金额设置id错误");
				} else {
					Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
					String fDate = DateUtil.getFullTime();

					Map<String, String> param = new HashMap<String, String>();
					param.put("id", creditMoneyId);
					param.put("updateUserId", auth.getUserId());
					param.put("updateDate", fDate);
					param.put("deleteFlag", "1");

					creditService.deleteCreditMoney(param);

					result.put("isError", "0");
					result.put("msg", "删除积分抵金额设置信息成功");
				}
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的删除权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，删除积分抵金额设置信息失败！请联系管理员");
		}
		return result;
	}

	/**
	 * 用户积分列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userCredit", method = RequestMethod.GET)
	public String userCredit(HttpServletRequest request, ModelMap model) {
		return "credit/userCredit";
	}

	/**
	 * 查询用户积分列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectUserCredit")
	@ResponseBody
	public Map<String, Object> selectUserCredit(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			if (menuService.isHasOperator(request, "search")) {
				String getFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "getFlag")); // 获得积分标识
				String isReceive = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isReceive")); // 是否已领取
				String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName")); // 用户名
				String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); // 手机号
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
				Map<String, String> param = new HashMap<>();
				param.put("startRowNo", String.valueOf(startRowNo));
				param.put("endRowNo", pageSize);
				param.put("getFlag", getFlag);
				param.put("isReceive", isReceive);
				param.put("userName", userName);
				param.put("userPhone", userPhone);

				int totalCount = creditService.getUserCreditCount(param);

				List<UserCredit> list = creditService.selectUserCredit(param);

				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", list);
			} else {
				result.put("isError", "1");
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，查询用户积分列表失败！");
		}
		return result;
	}
}
