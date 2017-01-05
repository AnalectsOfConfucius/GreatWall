package com.hg.dqsj.message.service;

import java.util.List;

import com.hg.dqsj.message.entity.Message;
import com.hg.dqsj.message.view.VMessage;

public interface MessageService {

	List<Message> selectMessageByCriterias(String currentPageNo, String isReply, String userName, String userPhone, String evalLevel, String pageSize);

	Integer countMessageByCriterias(String isReply, String userName, String userPhone);

	Message selectMessageById(String id);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	/**
	 * 获取最新留言
	 * 
	 * @return
	 */
	public List<VMessage> selectFewMessageByCriterias();

	/**
	 * 获取未回复留言
	 * 
	 * @return
	 */
	public Integer countNoReply();
}
