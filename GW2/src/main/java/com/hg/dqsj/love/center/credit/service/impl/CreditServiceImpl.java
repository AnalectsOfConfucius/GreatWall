package com.hg.dqsj.love.center.credit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.RequestUtils;
import com.hg.dqsj.love.center.credit.dao.CreditDao;
import com.hg.dqsj.love.center.credit.entity.CreditConsume;
import com.hg.dqsj.love.center.credit.entity.CreditGet;
import com.hg.dqsj.love.center.credit.entity.CreditMoney;
import com.hg.dqsj.love.center.credit.entity.CreditUser;
import com.hg.dqsj.love.center.credit.service.CreditService;

/**
 * 功能：投票SERVICE实现类
 * 
 * @author joe
 *
 */
@Service
public class CreditServiceImpl implements CreditService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private CreditDao creditDao; // 积分DAO接口
	/**
	 * 查询个人获得积分明细
	 */
	
	public Map<String, Object> selectCreditGet(HttpServletRequest request){
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<CreditGet> details = creditDao.selectCreditGet(map);
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("totalRecords", 0); // 总记录数
		} else {
			for(CreditGet cg : details){
				if (cg!=null) {
					if (null!=cg.getReceiveTime()) {
						cg.setReceiveTime(cg.getReceiveTime().substring(0, 10));
					}
					if (null!=cg.getGetFlag()) {
						if (cg.getGetFlag().equals("1")) {
							cg.setGetFlag("店铺活动");
						}else if (cg.getGetFlag().equals("2")) {
							cg.setGetFlag("生日有礼");
						}else if (cg.getGetFlag().equals("3")) {
							cg.setGetFlag("注册有礼");
						}else if (cg.getGetFlag().equals("4")) {
							cg.setGetFlag("消费送积分");
						}else{
							cg.setGetFlag("消费送积分");
						}
					}
				}
			}
			Integer totalRecords = creditDao.selectCreditGetCount(map);
			result.put("details", details);
			result.put("totalRecords", totalRecords); // 总记录数
		}
		return result;
	}
	/**
	 * 查询个人消费积分明细
	 */
	
	public Map<String, Object> selectCreditUse(HttpServletRequest request){
		Map<String, Object> map = createCriteria(request);
		Map<String, Object> result = new HashMap<>();
		List<CreditConsume> details = creditDao.selectCreditUse(map);
		if (details == null || details.size() <= 0) {
			result.put("details", details);
			result.put("totalRecords", 0); // 总记录数
		} else {
			for(CreditConsume cc : details){
				if (cc!=null) {
					if (null!=cc.getCreateDate()) {
						cc.setCreateDate(cc.getCreateDate().substring(0, 10));
					}
					if (null!=cc.getUseType()) {
						if (cc.getUseType().equals("1")) {
							cc.setUseType("购买门票");
						}else if (cc.getUseType().equals("2")) {
							cc.setUseType("预订客房");
						}else if (cc.getUseType().equals("3")) {
							cc.setUseType("购买年卡");
						}else if (cc.getUseType().equals("4")) {
							cc.setUseType("购买特色商品");
						}else if (cc.getUseType().equals("5")) {
							cc.setUseType("订餐");
						}else{
							cc.setUseType("购买门票");
						}
					}
				}
			}
			Integer totalRecords = creditDao.selectCreditUseCount(map);
			result.put("details", details);
			result.put("totalRecords", totalRecords); // 总记录数
		}
		return result;
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
		map.put("rowSize", "8"); // 查询范围
		return map;
	}
   /**
     * 查询个人积分
     */
	public CreditUser selectCreditUser(Map<String,String> param){
		return creditDao.selectCreditUser(param);
		
	}
	@Override
	public CreditMoney selectCreditMoney(Map<String, String> param) {
		return creditDao.selectCreditMoney(param);
	}
	
}
