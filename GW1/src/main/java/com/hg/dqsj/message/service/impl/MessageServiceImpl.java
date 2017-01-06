package com.hg.dqsj.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.DateUtil;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.message.dao.MessageDao;
import com.hg.dqsj.message.entity.Message;
import com.hg.dqsj.message.service.MessageService;
import com.hg.dqsj.message.view.VMessage;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@Override
	public List<Message> selectMessageByCriterias(String currentPageNo, String isReply, String userName, String userPhone, String evalLevel, String pageSize) {
		Map<String, Object> selectConditionMap = new HashMap<String, Object>();
		selectConditionMap.put("isReply", isReply);
		selectConditionMap.put("userName", userName);
		selectConditionMap.put("userPhone", userPhone);
		selectConditionMap.put("evalLevel", evalLevel);
		if (StringUtil.isNullorEmpty(pageSize)) {
			pageSize = "10";
		}
		if (StringUtil.isNullorEmpty(currentPageNo)) {
			currentPageNo = "1";
		}
		int curno = Integer.parseInt(currentPageNo);
		int pz = Integer.parseInt(pageSize);
		int startRowNo = pz * (curno - 1);
		selectConditionMap.put("startRowNo", String.valueOf(startRowNo)); // 开始行数
		selectConditionMap.put("rowSize", pageSize); // 查询范围

		return this.messageDao.selectMessageByCriterias(selectConditionMap);

	}

	@Override
	public Integer countMessageByCriterias(String isReply, String userName, String userPhone) {
		Map<String, Object> selectConditionMap = new HashMap<String, Object>();
		selectConditionMap.put("isReply", isReply);
		selectConditionMap.put("userName", userName);
		selectConditionMap.put("userPhone", userPhone);
		return this.messageDao.countMessageByCriterias(selectConditionMap);
	}

	@Override
	public Message selectMessageById(String id) {
		return this.messageDao.selectMessageById(id);
	}

	@Override
	public void updateMessage(Message message) {
		this.messageDao.updateMessage(message);
	}

	@Override
	public void deleteMessage(Message message) {
		this.messageDao.deleteMessage(message);
	}

	/**
	 * 获取最新留言
	 * 
	 * @return
	 */
	public List<VMessage> selectFewMessageByCriterias() {
		List<VMessage> messages = this.messageDao.selectFewMessageByCriterias();
		for (VMessage message : messages) {
			if (!StringUtil.isNullorEmpty(message.getMessageDate())) {
				message.setDateDifference(DateUtil.getDateDescription(message.getMessageDate()));
			}
			if (StringUtil.isNullorEmpty(message.getUserPicUrl())) {
				message.setUserPicUrl("/images/default_person.jpg");
			}
		}
		return messages;
	}

	/**
	 * 获取未回复留言
	 * 
	 * @return
	 */
	public Integer countNoReply() {
		return messageDao.countNoReply();
	}
}
