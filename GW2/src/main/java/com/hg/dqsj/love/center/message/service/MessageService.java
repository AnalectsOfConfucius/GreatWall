package com.hg.dqsj.love.center.message.service;

import com.hg.dqsj.love.center.message.entity.Message;

import java.util.List;

public interface MessageService {

	List<Message> selectMessageByCriterias(String currentPageNo,
										   String isReply, String userName, String userPhone, String evalLevel, String pageSize);

	Integer countMessageByCriterias(String isReply, String userName,
									String userPhone);

	Message selectMessageById(String id);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	void insertMessage(Message message);

	List<Message> selectMessageByCriterias(String userId);

}
