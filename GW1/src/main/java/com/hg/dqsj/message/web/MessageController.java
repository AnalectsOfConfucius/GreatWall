package com.hg.dqsj.message.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.message.entity.Message;
import com.hg.dqsj.message.service.MessageService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 *
 * @author 卢俊星
 */
@Controller
@RequestMapping(value = "message")
public class MessageController extends BaseController {

	public static final String messageMenuId = "";

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "enter")
	public String list(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		return "message/messageList";

	}

	@RequestMapping(value = "selectMessageList")
	@ResponseBody
	public Map<String, Object> selectMessageList(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			if (menuService.isHasOperator(request, "search")) {
				String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo"));
				String isReply = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isReply"));
				String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName"));
				String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone"));
				String evalLevel = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "evalLevel"));
				String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize"));

				if (!NumberUtil.isInt(currentPageNo, false)) {
					result.put("msg", "非法请求，请求页码非法");
				} else if (!NumberUtil.isInt(isReply, false)) {
					result.put("msg", "非法请求，留言回复状态输入非法");
				} else if (!StringUtil.isChsEnNum(userName, false)) {
					result.put("msg", "非法请求，留言者用户名输入非法");
				} else if (!StringUtil.isMobilePhone(userPhone, false)) {
					result.put("msg", "非法请求，留言者手机号码输入非法");
				} else if (!NumberUtil.isInt(pageSize, false)) {
					result.put("msg", "非法请求，当页显示留言条数输入非法");
				} else {
					// 处理逻辑
					List<Message> messageList = this.messageService.selectMessageByCriterias(currentPageNo, isReply, userName, userPhone, evalLevel, pageSize);
					if (messageList == null || messageList.size() <= 0) {
						result.put("messageList", messageList);
						result.put("currentPageNo", 0); // 当前页码
						result.put("totalCount", 0); // 总记录数
					} else {
						Integer totalCount = this.messageService.countMessageByCriterias(isReply, userName, userPhone);
						result.put("messageList", messageList);
						result.put("currentPageNo", currentPageNo); // 当前页码
						result.put("totalCount", totalCount); // 总记录数
					}
					result.put("isError", "0");
				}
			} else {
				result.put("msg", "你未拥有当前菜单的查询权限！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "操作失败，请重试！");
		}

		return result;

	}

	@RequestMapping(value = "selectMessageById")
	@ResponseBody
	public Map<String, Object> selectMessageById(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");

		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
			if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
				result.put("msg", "非法请求，当页显示留言条数输入非法");
			} else {
				// 处理逻辑
				Message message = this.messageService.selectMessageById(id);
				result.put("message", message);
				result.put("isError", "0");
			}
		} catch (Exception e) {
			result.put("isError", 1);
			logger.error(e.getMessage());
		}

		return result;

	}

	@RequestMapping(value = "updateMessage")
	@ResponseBody
	public Map<String, Object> updateMessage(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		if (menuService.isHasOperator(request, "update")) {
			Auth auth = (Auth) sessionProvider.getAttribute(request, SessionValidFilter.AUTH_KEY);

			try {
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));
				String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userName"));
				String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone"));
				String messageContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "messageContent"));
				String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));
				String evalLevel = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "evalLevel"));
				String replyContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "replyContent"));
				String isReply = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isReply"));
				String flag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "flag"));

				if (flag.equals("reply")) {
					if (!StringUtil.isParagraph(replyContent, true)) {
						result.put("msg", "回复的内容不能为空");
						return result;
					}
				}

				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "id输入非法");
				} else if (!StringUtil.isChsEnNum(userName, true)) {
					result.put("msg", "留言者用户名输入非法");
				} else if (!StringUtil.isMobilePhone(userPhone, true)) {
					result.put("msg", "留言者手机号码输入非法");
				} else if (!StringUtil.isParagraph(messageContent, true)) {
					result.put("msg", "留言内容输入非法");
				} else if (!StringUtil.isParagraph(remark, false)) {
					result.put("msg", "备注输入非法");
				} else if (!isReply.equals("1") && !isReply.equals("0")) {
					result.put("msg", "非法请求，回复标识输入非法");
				} else {
					// 处理逻辑
					Message message = new Message();
					message.setEvalLevel(evalLevel);
					message.setId(id);
					message.setMessageContent(messageContent);
					message.setReplyContent(replyContent);
					message.setRemark(remark);
					message.setUpdateDate(DateUtil.getFullTime());
					message.setUpdateUserId(auth.getUserId());
					message.setUserName(userName);
					message.setUserPhone(userPhone);
					message.setIsReply(isReply);
					this.messageService.updateMessage(message);
					result.put("isError", "0");
				}

			} catch (Exception e) {
				result.put("isError", 1);
				logger.error(e.getMessage());
			}
		} else {
			result.put("msg", "你未拥有当前菜单的编辑权限！");
		}

		return result;

	}

	@RequestMapping(value = "deleteMessage")
	@ResponseBody
	public Map<String, Object> deleteMessage(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		if (menuService.isHasOperator(request, "delete")) {
			Auth auth = (Auth) sessionProvider.getAttribute(request, SessionValidFilter.AUTH_KEY);

			try {
				String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id"));

				if (!UUIdUtil.matchUUIDOrBlank(id, true)) {
					result.put("msg", "非法请求，id输入非法");
				} else {
					// 处理逻辑
					Message message = new Message();
					message.setId(id);
					message.setUpdateDate(DateUtil.getFullTime());
					String userId = auth.getUserId();
					message.setUpdateUserId(userId);
					this.messageService.deleteMessage(message);
					result.put("isError", "0");
				}
			} catch (Exception e) {
				result.put("isError", 1);
				logger.error(e.getMessage());
			}
		} else {
			result.put("msg", "你未拥有当前菜单的删除权限！");
		}
		return result;
	}
}