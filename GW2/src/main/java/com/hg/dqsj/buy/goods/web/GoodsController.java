package com.hg.dqsj.buy.goods.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FEAdService;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.base.service.InfoService;
import com.hg.dqsj.base.view.VFEAd;
import com.hg.dqsj.base.view.VFEInfo;
import com.hg.dqsj.buy.goods.entity.GoodsInfo;
import com.hg.dqsj.buy.goods.entity.GuestRoomInfo;
import com.hg.dqsj.buy.goods.entity.Store;
import com.hg.dqsj.buy.goods.service.GoodsService;
import com.hg.dqsj.buy.goods.service.GuestRoomService;
import com.hg.dqsj.buy.goods.service.StoreService;
import com.hg.dqsj.love.center.credit.entity.CreditMoney;
import com.hg.dqsj.love.center.credit.entity.CreditUser;
import com.hg.dqsj.love.center.credit.service.CreditService;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.entity.OfOrderDetail;
import com.hg.dqsj.love.center.order.entity.OfOrderGoods;

/**
 * 购买管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
public class GoodsController {
	
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FeGoodsService feGoodsService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GuestRoomService guestRoomService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private FEAdService feAdService; // 广告SERVICE接口
	@Autowired
	private InfoService infoService; // 资讯信息SERVICE接口
	@Autowired
	private CreditService creditService; // 积分

	/**
	 * 门票列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "entranceTicket", method = RequestMethod.GET)
	public String entranceTicket(HttpServletRequest request, ModelMap model) {
		Map<String, String> param = new HashMap<>();
		//所有门票
		param.put("typeCode", "4");
		List<FeGoodsInfo> list = feGoodsService.selectByCriterias(param);
		model.put("ticket",list);
		//广告
		List<VFEAd> ads = feAdService.selectByTypeCode("entranceTicket");
		model.put("ads", ads);
		//积分抵金额
		getUserCredit(request,model);
		
		return "buy/ticket-buy";
	}
	//用户积分，积分抵金额
	private void getUserCredit(HttpServletRequest request, ModelMap model){
		Map<String, String> param = new HashMap<>();
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		//用户积分
		param.put("userId", auth.getUserId());
		CreditUser uc = creditService.selectCreditUser(param);
		int useCredit = 0;
		if(null!=uc && null!=uc.getCreditNum()){
			useCredit = uc.getCreditNum();
		}
		//积分抵金额
		CreditMoney cm = creditService.selectCreditMoney(param);
		Double useMoney = 0.0;//金额
		if(null!=cm){
			if(null != cm.getUsableCondition() && cm.getUsableCondition()>0 
					&& null != cm.getIsWorthMoney() && cm.getIsWorthMoney()>0){
				double credit = cm.getUsableCondition();
				double money = cm.getIsWorthMoney();
				useMoney = NumberUtil.round((money/credit*useCredit), 2);
			}
		}
		model.put("useCredit",useCredit);
		model.put("useMoney",useMoney);
	}
	/**
	 * 购买门票页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toConfirmTicket", method = RequestMethod.GET)
	public String toConfirmTicket(HttpServletRequest request, ModelMap model) {
		String ticketListStr = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "ticketList")); // 门票
		String orderPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderPrice")); //总金额
		String useDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "useDate")); //入园时间
		if(!StringUtil.isNullorEmpty(ticketListStr)){
			List<GoodsInfo> goodsList = JSON.parseArray(ticketListStr, GoodsInfo.class);
			model.put("goodsList", goodsList);
		}
		model.put("goods", ticketListStr);
		model.put("orderPrice", orderPrice);
		model.put("useDate", useDate);
		return "buy/ticket-submit";
	}
	/**
	 * 添加订单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrder(HttpServletRequest request, ModelMap model) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("isError", "1");
		try {
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String ticketListStr = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goods")); // 门票
			String orderPriceStr = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderPrice")); //总金额
			Double orderPrice = NumberUtil.strToDouble(orderPriceStr);
			
			String orderTypeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderTypeCode")); //商品类型
			String linkUserPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkUserPhone")); //联系电话
			if(null==auth){
				result.put("msg", "获得用户信息失败，请重新登录");
			}else if(StringUtil.isNullorEmpty(ticketListStr)){
				result.put("msg", "未得到商品数据");
			}else if(StringUtil.isNullorEmpty(orderTypeCode)){
				result.put("msg", "非法请求，订单类型为空");
			}else if(!StringUtil.isMobilePhone(linkUserPhone, true)){
				result.put("msg", "请输入手机号码");
			}else{
				String invalidEndTime = ""; //到期时间
				String fdate = DateUtil.getFullTime();
				if(!"2".equals(orderTypeCode)){
					invalidEndTime = DateUtil.beforeAfterDate(fdate, 1);//当前时间加1小时
				}
				String orderNo = goodsService.getOrderNo();
				String orderId = UUIdUtil.getUUID();
				OfOrder order = new OfOrder();
				order.setId(orderId);
				order.setOrderNo(orderNo);
				order.setOrderTypeCode(orderTypeCode);
				order.setUserId(auth.getUserId());
				order.setOrderPrice(orderPrice);
				order.setOrderTime(fdate);
				order.setInvalidEndTime(invalidEndTime);
				order.setPayFlag("0");
				order.setGetFlag("0");
				order.setEvalFlag("0");
				order.setRowLockNum(0);
				order.setCreateUserId(auth.getUserId());
				order.setCreateDate(fdate);
				order.setUpdateUserId(auth.getUserId());
				order.setUpdateDate(fdate);
				order.setDeleteFlag("0");
				
				OfOrderDetail detail = new OfOrderDetail();
				detail.setId(UUIdUtil.getUUID());
				detail.setOrderId(orderId);
				detail.setLinkUserPhone(linkUserPhone);
				if("1".equals(orderTypeCode)){
					String useDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "useDate")); //使用时间
					if(!DateUtil.isDate(useDate, "yyyy-MM-dd", true)){
						result.put("msg", "请选择入园时间");
						return result;
					}
					detail.setUseDate(useDate);
				}else if("2".equals(orderTypeCode)){
					String linkUserName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "linkUserName")); //联系人
					String checkInDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "checkInDate")); //入住时间
					String checkOutDate = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "checkOutDate")); //离开时间
					if(!StringUtil.isChsEnNum(linkUserName, true)){
						result.put("msg", "请输入不带有特殊字符的联系人名称");
						return result;
					}else if(!DateUtil.isDate(checkInDate, "yyyy-MM-dd", true)){
						result.put("msg", "请选择入住日期");
						return result;
					}else if(!DateUtil.isDate(checkOutDate, "yyyy-MM-dd", true)){
						result.put("msg", "请选择离开日期");
						return result;
					}
					
					detail.setLinkUserName(linkUserName);
					detail.setCheckInDate(checkInDate);
					detail.setCheckOutDate(checkOutDate);
				}else if("5".equals(orderTypeCode)){
					String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); //店铺ID
					if(!UUIdUtil.matchUUIDOrBlank(storeId, true)){
						result.put("msg", "非法请求，店铺id获得错误");
						return result;
					}
					detail.setStoreId(storeId);
				}
				detail.setCreateUserId(auth.getUserId());
				detail.setCreateDate(fdate);
				detail.setUpdateUserId(auth.getUserId());
				detail.setUpdateDate(fdate);
				detail.setDeleteFlag("0");
				
				List<OfOrderGoods> glist = JSON.parseArray(ticketListStr, OfOrderGoods.class);
				for (OfOrderGoods ofOrderGoods : glist) {
					ofOrderGoods.setId(UUIdUtil.getUUID());
					ofOrderGoods.setOrderId(orderId);
					ofOrderGoods.setCreateUserId(auth.getUserId());
					ofOrderGoods.setCreateDate(fdate);
					ofOrderGoods.setUpdateUserId(auth.getUserId());
					ofOrderGoods.setUpdateDate(fdate);
					ofOrderGoods.setDeleteFlag("0");
					orderPrice = NumberUtil.mul(ofOrderGoods.getGoodsNum(), ofOrderGoods.getGoodsPrice());
				}
				//订单总价
				order.setOrderPrice(orderPrice);
				
				goodsService.insertOrder(order, detail, glist);
				
				result.put("orderId", orderId);
				result.put("orderNo", orderNo);
				result.put("orderTime", fdate);
				result.put("isError", "0");
				result.put("msg", "添加订单成功");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "系统错误，请联系管理员");
		}
		
		return result;
	}
	
	/**
	 * 年卡列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "annualCard", method = RequestMethod.GET)
	public String annualCard(HttpServletRequest request, ModelMap model) {
		//广告
		List<VFEAd> ads = feAdService.selectByTypeCode("annualCard");
		model.put("ads", ads);
		return "buy/annual-card";
	}
	/**
	 * 立即购买年卡
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBuySubmit", method = RequestMethod.GET)
	public String toBuySubmit(HttpServletRequest request, ModelMap model) {
		String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品Id
		String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
		String goodsNum = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsNum")); // 商品数量
		String sellPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "sellPrice")); // 单价
		Double total = NumberUtil.mul(Double.parseDouble(goodsNum), Double.parseDouble(sellPrice));
		Double orderPrice = NumberUtil.round(total, 2); //总金额
		String orderTime = DateUtil.getFullTime(); //购卡时间
		//积分抵金额
		getUserCredit(request,model);
		model.put("goodsId", goodsId);
		model.put("goodsName", goodsName);
		model.put("goodsPrice", sellPrice);
		model.put("goodsNum", goodsNum);
		model.put("orderPrice", orderPrice);
		model.put("orderTime", orderTime);
		return "buy/order-form";
	}
	/**
	 * 客房列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hotel", method = RequestMethod.GET)
	public String hotel(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> param = new HashMap<>();
			//酒店
			param.put("storeType", "2");
			List<Store> slist = storeService.selectStore(param);
			if(null!=slist && slist.size()>0){
				param.put("id", slist.get(0).getId());
				Store store = storeService.selectById(param);
				if("0".equals(store.getIsOpen())){
					store.setStoreContentStr(new String(store.getStoreContent(),"UTF-8"));
					model.put("detail", store);
					return "buy/hotel-introduce";
				}else{
					//广告
					List<VFEAd> ads = feAdService.selectByTypeCode("hotel");
					model.put("ads", ads);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "buy/hotel-rooms";
	}
	/**
	 * 酒店地图
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hotelMap", method = RequestMethod.GET)
	public String hotelMap(HttpServletRequest request, ModelMap model) {
		
		return "buy/hotel-map";
	}
	/**
	 * 加载客房列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectGuestRoom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectGuestRoom(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
			if (StringUtil.isNullorEmpty(pageSize)) {
				pageSize = "6";
			}
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "0";
			}
			
			Map<String, String> param = new HashMap<>();
			param.put("startRowNo", currentPageNo);
			param.put("rowSize", pageSize);
			
			int totalCount = guestRoomService.getCount(param);
			List<GuestRoomInfo> list = guestRoomService.selectGuestRoom(param);
			
			result.put("currentPage", currentPageNo);
			result.put("totalCnt", totalCount);
			result.put("rows", list);
			result.put("isError", "0");
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 客房详情
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toRoomDetail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, ModelMap model) {
		try {
			
			String roomId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roomId")); //客房id
			
			if (!UUIdUtil.matchUUIDOrBlank(roomId, true)) {
				model.put("msg", "非法请求，获得客房id错误！");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("id", roomId);
				//客房信息
				GuestRoomInfo room = guestRoomService.selectById(param);
				if(null!=room){
					room.setGuestRoomContentStr(new String(room.getGuestRoomContent(),"UTF-8"));
				}
				model.put("info", room);
				//图片
				param.put("objId", roomId);
				List<FePic> plist = feGoodsService.selectPicById(param);
				model.put("plist", plist);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.put("msg", "系统错误，请联系管理员！");
		}
		return "buy/hotel-details";
	}
	/**
	 * 食品店、特色商品列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "groceryStore", method = RequestMethod.GET)
	public String groceryStore(HttpServletRequest request, ModelMap model) {
		Map<String, String> param = new HashMap<>();
		//食品店
		param.put("storeType", "1");
		List<Store> slist = storeService.selectStore(param);
		model.put("slist", slist);
		//广告
		List<VFEAd> ads = feAdService.selectByTypeCode("groceryFood");
		model.put("ads", ads);
		
		return "buy/foodBeverages/store-list";
	}
	/**
	 * 食品店信息页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "foodStoreInfo", method = RequestMethod.GET)
	public String foodStoreInfo(HttpServletRequest request, ModelMap model) {
		try {
			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); //食品店id
			Map<String, String> param = new HashMap<>();
			param.put("id", storeId);
			Store store = storeService.selectById(param);
			if(null!=store){
				if("0".equals(store.getIsOpen())){
					store.setStoreContentStr(new String(store.getStoreContent(),"UTF-8"));
					model.put("detail", store);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "buy/foodBeverages/store-introduce";
	}
	/**
	 * 食品店详情页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "storeDetail", method = RequestMethod.GET)
	public String storeDetail(HttpServletRequest request, ModelMap model) {
		String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); //食品店id
		Map<String, String> param = new HashMap<>();
		//食品店
		param.put("id", storeId);
		Store store = storeService.selectById(param);
		model.put("store", store);
		//图片
		param.put("objId", storeId);
		List<FePic> plist = feGoodsService.selectPicById(param);
		model.put("plist", plist);
		//所有食品
		param.put("storeId", storeId);
		param.put("monthDate", DateUtil.beforeAfterDate(DateUtil.getFullTime(), -24*30));
		List<FeGoodsInfo> glist = feGoodsService.selectByCriterias(param);
		model.put("glist", glist);
		
		return "buy/foodBeverages/food-list";
	}
	/**
	 * 食品，特色商品提交订单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFoodBeveragesBuy", method = RequestMethod.GET)
	public String toFoodBuy(HttpServletRequest request, ModelMap model) {
		String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // 食品店Id
		String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品Id
		String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
		String goodsPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsPrice")); // 商品价格
		String orderTypeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderTypeCode")); // 订单类型
		//积分抵金额
		getUserCredit(request,model);
		model.put("storeId", storeId);
		model.put("goodsId", goodsId);
		model.put("goodsName", goodsName);
		model.put("goodsPrice", goodsPrice);
		model.put("orderTypeCode", orderTypeCode);
		return "buy/foodBeverages/foodBeverages-buy";
	}
	/**
	 * 食品，特色商品 支付页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFoodBeveragesPay", method = RequestMethod.GET)
	public String toFoodPay(HttpServletRequest request, ModelMap model) {
		String orderId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderId")); // 订单id
		String orderNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderNo")); // 订单编号
		String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品Id
		String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
		String goodsNum = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsNum")); // 商品数量
		String orderPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderPrice")); //订单总价
		String userPhone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userPhone")); //手机号码
		String credit = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "credit")); //积分抵金额
		
		model.put("orderId", orderId);
		model.put("orderNo", orderNo);
		model.put("goodsId", goodsId);
		model.put("goodsName", goodsName);
		model.put("goodsNum", goodsNum);
		model.put("orderPrice", orderPrice);
		model.put("userPhone", userPhone);
		model.put("credit", credit);
		model.put("orderDate", DateUtil.getFullDate_());
		return "buy/foodBeverages/foodBeverages-form";
	}
	/**
	 * 特色商品列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "specialGoods", method = RequestMethod.GET)
	public String specialGoods(HttpServletRequest request, ModelMap model) {
		//广告
		List<VFEAd> ads = feAdService.selectByTypeCode("goods");
		model.put("ads", ads);
		
		return "buy/foodBeverages/shop-list";
	}
}
