package com.hg.dqsj.orderManagement.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hg.dqsj.orderManagement.entity.OfOrder;
import com.hg.dqsj.orderManagement.view.VOfOrder;

/**
 * 功能：订单SERVICE接口
 * 
 * @author 吴晓敏
 *
 */
public interface OfOrderService {

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request);

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfOrder selectVById(String id);

	/**
	 * 更新领取状态
	 * 
	 * @param ofOrder
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public boolean updateGetFlag(OfOrder ofOrder) throws Exception;

}
