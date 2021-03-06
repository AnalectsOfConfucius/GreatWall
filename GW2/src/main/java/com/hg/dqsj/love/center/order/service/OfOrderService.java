package com.hg.dqsj.love.center.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hg.core.weixin.entity.OrderPay;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.view.VOfOrder;

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

	/**
	 * 根据订单ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public List<VOfOrder> selectOrderDetailsById(String id);

	/**
	 * 根据订单号查询订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public OfOrder selectByOrderNo(String orderNo);

	/**
	 * 更新订单支付状态及保存微信支付结果同步请求报文
	 * 
	 * @param orderNo
	 * @return
	 */
	@Transactional
	public Integer updatePayFlag(OfOrder ofOrder, OrderPay orderPay) throws Exception;

	/**
	 * 根据订单ID查询订单信息
	 * 
	 * @param id
	 * @return
	 */
	public OfOrder selectById(String id);
}
