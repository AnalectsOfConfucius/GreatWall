package com.hg.dqsj.love.center.message.dao;


import com.hg.dqsj.love.center.message.entity.Message;

import java.util.List;
import java.util.Map;


/**
 *
 * @author Junxing
 */
public interface MessageDao {
	
	public List<Message> selectMessageByCriterias(Map<String, Object> selectConditionMap);

	public Integer countMessageByCriterias(
			Map<String, Object> selectConditionMap);

	public Message selectMessageById(String id);

	public void updateMessage(Message message);
	
	public void deleteMessage(Message message);

	public void insertMessage(Message message);

}
