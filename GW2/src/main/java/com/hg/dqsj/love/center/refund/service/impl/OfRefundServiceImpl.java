package com.hg.dqsj.love.center.refund.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.RequestUtils;
import com.hg.dqsj.love.center.refund.dao.OfRefundDao;
import com.hg.dqsj.love.center.refund.entity.OfRefund;
import com.hg.dqsj.love.center.refund.service.OfRefundGoodsService;
import com.hg.dqsj.love.center.refund.service.OfRefundService;
import com.hg.dqsj.love.center.refund.view.VOfRefund;
import com.hg.dqsj.love.center.refund.view.VOfRefundGoods;



/**
 * 功能：退款SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class OfRefundServiceImpl implements OfRefundService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private OfRefundDao ofRefunDao; // 退款DAO接口
	@Autowired
	private OfRefundGoodsService ofRefundGoodsService; // 退款商品SERVICE接口
	/**
	 * 根据退款号查询信息
	 * @param orderNo
	 * @return
	 */
	public OfRefund selectByRefundNo(String RefundNo){
		return ofRefunDao.selectByRefundNo(RefundNo);
	}
	/**
	 * 随机生成退款号
	 * 
	 * @return
	 */
	public String getRefundNo() {
		int count = 10; // 最大查询次数
		while (count > 0) {
			String currentDateStr = DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS"); // 当前时间
			int random = (int) (Math.random() * 10000000); // 7位随机数
			String result = "R"+currentDateStr + random;
			OfRefund ofRefund = ofRefunDao.selectByRefundNo(result);
			count--;
			if (ofRefund == null) {
				return result;
			}
		}
		return null;
	}
	/**
	 * 新增退款
	 * 
	 * @param ofOrder
	 * @return
	 */
	public void saveRefund(OfRefund refund){
		ofRefunDao.saveRefund( refund);
	}


	/**
	 * 生成查询条件
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createCriteria(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		map.put("startRowNo", (map.get("currentRecords") == null || map.get("currentRecords").equals("")) ? "0" : map.get("currentRecords").toString()); // 开始行数
		map.put("rowSize", "3"); // 查询范围
		return map;
	}

	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectByCriterias(HttpServletRequest request) {
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<VOfRefund> details = null;
			 details = ofRefunDao.selectByCriterias(map);				
		if (details == null || details.size() <= 0) {
			result.put("details", details);		
			result.put("totalRecords", 0); // 总记录数
		} else {
			for (VOfRefund detail : details) {
				completeRefundDetail(detail);
			}
			Integer totalCount = ofRefunDao.countByCriterias(map);
			result.put("oneVo", details.get(0));
			result.put("details", details);
			result.put("totalRecords", totalCount); // 总记录数
		}
		return result;
	}

	/**
	 * 完善订单详细信息
	 * 
	 * @param detail
	 */
	private void completeRefundDetail(VOfRefund detail) {
		String refundState = ""; // 退款状态
		String unit ="";//商品单位
		List<VOfRefundGoods> fGoods = null;//退款商品列表
		List<String> productInfo = new ArrayList<>();//退款商品详情字符串
		if (null!=detail.getPlatformFlag()) {
			if (detail.getPlatformFlag().equals("1")) {
				detail.setPlatformFlag("微信退款");
			} else if (detail.getPlatformFlag().equals("2")) {
				detail.setPlatformFlag("支付宝转账");
			} else {
				detail.setPlatformFlag("");
			}
		}else{
			detail.setPlatformFlag("");
		}
			
		
		if (detail.getRefundFlag().equals("0")) {
			refundState = "退款中";	
		} else if (detail.getRefundFlag().equals("1")) {
			refundState = "退款失败";
		} else if (detail.getRefundFlag().equals("2")) {
				refundState = "退款完成 ";			
		} else {
			refundState = "退款中";
		}
		detail.setRefundState(refundState);
		if (detail.getOrderTypeCode().equals("2")) {
			fGoods = ofRefundGoodsService.selectGuestRoomByRefundId(detail.getId());
			unit="套";
			detail.setOrderTypeCode("民生大千世界游乐园客房");
		} else {
			fGoods = ofRefundGoodsService.selectGoodsByRefundId(detail.getId());
			if (detail.getOrderTypeCode().equals("1")) {
				detail.setOrderTypeCode("民生大千世界游乐园门票");
				unit="张";
			}else if (detail.getOrderTypeCode().equals("3")) {
				unit="张";
				detail.setOrderTypeCode("民生大千世界游乐园年卡");
			}else if (detail.getOrderTypeCode().equals("4")) {
				detail.setOrderTypeCode("特色商品");
				unit="件";
			}else if (detail.getOrderTypeCode().equals("5")) {
				detail.setOrderTypeCode("美食");
				unit="份";
			}else{
				unit="件";
				detail.setOrderTypeCode("民生大千世界游乐园商品");
			}
		}
		if (fGoods!=null) {
			if (fGoods.size()>0) {
				for(VOfRefundGoods fg : fGoods){
					String proinfo = "";
					String createDate = fg.getCreateDate().substring(0, 10);
					proinfo += fg.getGoodsName()+","+createDate+","+fg.getGoodsNum()+unit;
					productInfo.add(proinfo);
					System.out.println(">>>>>>>>>>退款商品字符串"+proinfo);
				}
			}
		}	
		detail.setProductInfo(productInfo);
	}

	/**
	 * 根据退款ID查询订单详细信息
	 * 
	 * @param id
	 * @return
	 */
	public VOfRefund selectVById(String id) {
		VOfRefund detail = ofRefunDao.selectVById(id);
		completeRefundDetail(detail);
		return detail;
	}

	/**
	 * 更新退款状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	@Transactional
	public Integer updateGetFlag(OfRefund ofRefund) {
		return ofRefunDao.updateGetFlag(ofRefund);
	}
	/**
	 * 更新到账状态
	 * 
	 * @param ofOrder
	 * @return
	 */
	public Integer updateAccountFlag(OfRefund  ofRefund ){
		return ofRefunDao.updateAccountFlag(ofRefund);
	}

}
