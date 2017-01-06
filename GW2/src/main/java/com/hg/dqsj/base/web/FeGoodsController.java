package com.hg.dqsj.base.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.EvalInfo;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
/**
 * 商品信息管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "fegoods")
public class FeGoodsController{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FeGoodsService feGoodsService;
	
	
	/**
	 * 加载列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 商品类别代码
			
			String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // 美食店ID
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
			if (StringUtil.isNullorEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "0";
			}
			
			if (!StringUtil.isChsEnNum(goodsName, false)) {
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的用户名！");
			} else if (!NumberUtil.isInt(typeCode, true)) {
				result.put("msg", "未获得商品类别");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("startRowNo", currentPageNo);
				param.put("rowSize", pageSize);
				param.put("goodsName", goodsName);
				param.put("typeCode", typeCode);
				param.put("storeId", storeId);
				param.put("monthDate", DateUtil.beforeAfterDate(DateUtil.getFullTime(), -24*30));
				
				int totalCount = feGoodsService.countByCriterias(param);
				List<FeGoodsInfo> list = feGoodsService.selectByCriterias(param);
				
				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", list);
				result.put("isError", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 详情
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品id
			
			if (!UUIdUtil.matchUUIDOrBlank(goodsId, true)) {
				result.put("msg", "非法请求，获得商品id错误！");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("id", goodsId);
				//商品信息
				FeGoodsInfo goods = feGoodsService.selectById(param);
				if(null!=goods){
					goods.setGoodsContentStr(new String(goods.getGoodsContent(),"UTF-8"));
				}
				result.put("info", goods);
				//图片
				if(!"4".equals(goods.getTypeCode())){
					param.put("objId", goodsId);
					List<FePic> plist = feGoodsService.selectPicById(param);
					result.put("plist", plist);
				}
				
				result.put("isError", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 跳转详情
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toDetail", method = RequestMethod.GET)
	public String toDetail(HttpServletRequest request, ModelMap model) {
		try {
			String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品id
			
			Map<String, String> param = new HashMap<>();
			param.put("id", goodsId);
			param.put("monthDate", DateUtil.beforeAfterDate(DateUtil.getFullTime(), -24*30));
			//商品信息
			FeGoodsInfo goods = feGoodsService.selectById(param);
			if(null!=goods){
				goods.setGoodsContentStr(new String(goods.getGoodsContent(),"UTF-8"));
			}
			model.put("info", goods);
			//图片
			if(!"4".equals(goods.getTypeCode())){
				param.put("objId", goodsId);
				List<FePic> plist = feGoodsService.selectPicById(param);
				model.put("plist", plist);
			}
			
			if("2".equals(goods.getTypeCode())){//年卡
				return "buy/annual-card-details";
			}else if("3".equals(goods.getTypeCode())){//食品
				return "buy/foodBeverages/food-details";
			}else if("1".equals(goods.getTypeCode())){//特色商品
				return "buy/foodBeverages/shop-list-details";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}
	/**
	 * 加载列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectEval", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectEval(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			String orderId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderId")); // 订单ID
			String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品id
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
			if (StringUtil.isNullorEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "0";
			}
			
			Map<String, String> param = new HashMap<>();
			param.put("startRowNo", currentPageNo);
			param.put("rowSize", pageSize);
			param.put("orderId", orderId);
			param.put("goodsId", goodsId);
			
			int totalCount = feGoodsService.selectEvalCount(param);
			List<EvalInfo> list = feGoodsService.selectEval(param);
			
			result.put("currentPage", currentPageNo);
			result.put("totalCnt", totalCount);
			result.put("rows", list);
			result.put("isError", "0");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
}
