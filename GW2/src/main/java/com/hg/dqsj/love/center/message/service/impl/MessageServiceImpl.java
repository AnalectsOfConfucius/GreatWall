package com.hg.dqsj.love.center.message.service.impl;

import com.hg.core.util.StringUtil;
import com.hg.dqsj.love.center.message.dao.MessageDao;
import com.hg.dqsj.love.center.message.entity.Message;
import com.hg.dqsj.love.center.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@Override
	public List<Message> selectMessageByCriterias(String currentPageNo, String isReply,
												  String userName, String userPhone, String evalLevel, String pageSize) {
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
	public List<Message> selectMessageByCriterias(String userId) {
		Map<String, Object> selectConditionMap = new HashMap<String, Object>();
		selectConditionMap.put("userId", userId);
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

	@Override
	public void insertMessage(Message message) {
		this.messageDao.insertMessage(message);
	}

}
