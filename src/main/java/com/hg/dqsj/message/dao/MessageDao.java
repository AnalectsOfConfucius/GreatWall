package com.hg.dqsj.message.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.message.entity.Message;
import com.hg.dqsj.message.view.VMessage;

/**
 *
 * @author Junxing
 */
public interface MessageDao {

	public List<Message> selectMessageByCriterias(Map<String, Object> selectConditionMap);

	public Integer countMessageByCriterias(Map<String, Object> selectConditionMap);

	public Message selectMessageById(String id);

	public void updateMessage(Message message);

	public void deleteMessage(Message message);

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
