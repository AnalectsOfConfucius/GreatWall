package com.hg.dqsj.love.center.message.web;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.*;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.message.entity.Message;
import com.hg.dqsj.love.center.message.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
@Controller
@RequestMapping(value = "message")
public class MessageController {

    public static final String messageMenuId = "";

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "")
    public String enter(HttpServletRequest request, HttpServletResponse reponse,
                        ModelMap model) {
        return "love/message/online-message";
    }

    @RequestMapping(value = "commitMessage")
    @ResponseBody
    public Map<String, Object> commitMessage(HttpServletRequest request,
                                             HttpServletResponse reponse, ModelMap model) {
        Map<String, Object> result = new HashMap<>();
        result.put("isError", "1");

        Auth auth = (Auth) sessionProvider.getAttribute(request,
                SessionValidFilter.AUTH_KEY);

        try {
            String userName = StringUtil.trimBlank(RequestUtils.getQueryParam(
                    request, "userName"));
            String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(
                    request, "userPhone"));
            String evalLevel = StringUtil.trimBlank(RequestUtils.getQueryParam(
                    request, "evalLevel"));
            String messageContent = StringUtil.trimBlank(RequestUtils.getQueryParam(
                    request, "messageContent"));


            if (!StringUtil.isChsEnNum(userName, true)) {
                result.put("msg", "非法请求，留言者用户名输入非法");
            } else if (!StringUtil.isMobilePhone(userPhone, true)) {
                result.put("msg", "非法请求，留言者手机号码输入非法");
            } else if (!evalLevel.equals("1") && !evalLevel.equals("2") && !evalLevel.equals("3") && !evalLevel.equals("4") && !evalLevel.equals("5")) {
                result.put("msg", "非法请求，留言者评级不合法");
            } else if (!StringUtil.isParagraph(messageContent, true)) {
                result.put("msg", "非法请求，留言者留言信息输入非法");
            } else {
                // 处理逻辑
                Message message = new Message();
                message.setMessageDate(DateUtil.getFullTime());
                message.setEvalLevel(evalLevel);
                message.setId(UUIdUtil.getUUID());
                message.setUserId(auth.getUserId());
                message.setMessageContent(messageContent);
                message.setUpdateDate(DateUtil.getFullTime());
                message.setUpdateUserId(auth.getUserId());
                message.setUserName(userName);
                message.setUserPhone(userPhone);
                message.setCreateDate(DateUtil.getFullDate());
                message.setCreateUserId(auth.getUserId());
                message.setIsReply("0");
                message.setDeleteFlag("0");
                this.messageService.insertMessage(message);
                result.put("isError", "0");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;

    }


    @RequestMapping(value = "selectList")
    public String selectMessageList(HttpServletRequest request,
                                    HttpServletResponse reponse, ModelMap model) {
        try {
            Auth auth = (Auth) sessionProvider.getAttribute(request,
                    SessionValidFilter.AUTH_KEY);
            String userId = auth.getUserId();
            // 处理逻辑
            List<Message> messageList = this.messageService
                    .selectMessageByCriterias(userId);
            model.put("messageList", messageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "love/center/message/message";
    }

}
