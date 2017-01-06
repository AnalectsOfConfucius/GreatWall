package com.hg.dqsj.love.center.cart.web;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.cart.entity.ShoppingCart;
import com.hg.dqsj.love.center.cart.entity.ShoppingCartGoods;
import com.hg.dqsj.love.center.cart.service.ShoppingCartService;
import com.hg.dqsj.love.center.credit.entity.CreditMoney;
import com.hg.dqsj.love.center.credit.entity.CreditUser;
import com.hg.dqsj.love.center.credit.service.CreditService;

@Controller
@RequestMapping(value = "shoppingCart")
public class ShoppingCartController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private CreditService creditService; // 积分
	/**
	 * 跳转购物车页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String pay(HttpServletRequest request, ModelMap model) {
		setData(request,model,false);
		return "love/center/cart/cart";
	}
	/**
	 * 跳转删除购物车页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toDelete", method = RequestMethod.GET)
	public String openGarden(HttpServletRequest request, ModelMap model) {
		setData(request,model,true);
		return "love/center/cart/cart-edit";
	}
	/**
	 * 查询购物车数据
	 * @param request
	 * @param model
	 */
	private void setData(HttpServletRequest request,ModelMap model,boolean isdel){
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		Map<String, String> param = new HashMap<>();
		param.put("userId", auth.getUserId());
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		if(isdel){
			param.put("typeCode", "1");//年卡
			list = shoppingCartService.selectShoppingCart(param);
			model.put("cardList", list);
			
			param.put("othCode", "1");//其他商品
			list = shoppingCartService.selectShoppingCart(param);
		}else{
			list = shoppingCartService.selectShoppingCart(param);
		}
		model.put("goodsList", list);
	}
	/**
	 * 添加购物车商品
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addCart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addCart(HttpServletRequest request, ModelMap model) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("isError", "1");
		try {
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String userId = auth.getUserId();
			String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品id
			String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 商品类型
			String goodsNum = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsNum")); // 商品数量
			if(!UUIdUtil.matchUUIDOrBlank(goodsId, true)){
				result.put("msg", "非法请求，获得商品id错误");
			}else if(!NumberUtil.isInt(goodsNum, true)){
				result.put("msg", "请输入正确格式的数量");
			}else{
				String fdate = DateUtil.getFullTime();
				ShoppingCartGoods cart = new ShoppingCartGoods();
				cart.setId(UUIdUtil.getUUID());
				cart.setUserId(userId);
				cart.setTypeCode(typeCode);
				cart.setGoodsId(goodsId);
				cart.setGoodsNum(Integer.parseInt(goodsNum));
				cart.setRemark("");
				cart.setCreateUserId(userId);
				cart.setCreateDate(fdate);
				cart.setUpdateUserId(userId);
				cart.setUpdateDate(fdate);
				cart.setDeleteFlag("0");
				shoppingCartService.insertCart(cart);
				
				result.put("isError", "0");
				result.put("msg", "添加到购物车成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 删除购物车商品
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteGoods(HttpServletRequest request, ModelMap model) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("isError", "1");
		try {
			String goodsIds = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsIds")); // 商品id
			if(!StringUtil.isNullorEmpty(goodsIds)){
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				Map<String,Object> param = new HashMap<String, Object>();
				String[] ids = goodsIds.split(",");
				param.put("ids", ids);
				param.put("updateDate", DateUtil.getFullTime());
				param.put("updateUserId", auth.getUserId());
				param.put("deleteFlag", "1");
				shoppingCartService.delete(param);
			}
			result.put("isError", "0");
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 跳转提交购物车页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toSubmit", method = RequestMethod.GET)
	public String toSubmit(HttpServletRequest request, ModelMap model) {
		try {
			String goods = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goods")); // 商品
			String orderPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderPrice")); // 总金额
			if(!StringUtil.isNullorEmpty(goods)){
				List<ShoppingCart> glist = JSON.parseArray(goods, ShoppingCart.class);
				for (ShoppingCart shoppingCart : glist) {
					Double total = shoppingCart.getGoodsNum()*shoppingCart.getSellPrice();
					shoppingCart.setGoodsTotal(total);
				}
				String typeCode = glist.get(0).getTypeCode();
				if("1".equals(typeCode)){//年卡
					typeCode = "3";
				}else if("2".equals(typeCode)){//食品
					typeCode = "5";
				}else if("3".equals(typeCode)){//特色商品
					typeCode = "4";
				}
				model.put("typeCode", typeCode);
				model.put("goodsList", glist);
			}
			model.put("orderPrice", orderPrice);
			model.put("goods", goods);
			//积分抵金额
			getUserCredit(request,model);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "love/center/cart/cart-order-submit";
	}
	/**
	 * 跳转确认购物车页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toConfirm", method = RequestMethod.GET)
	public String toConfirm(HttpServletRequest request, ModelMap model) {
		try {
			String goods = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goods")); // 商品
			String orderPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "orderPrice")); // 总金额
			String credit = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "credit")); // 抵金额
			if(!StringUtil.isNullorEmpty(goods)){
				List<ShoppingCart> glist = JSON.parseArray(goods, ShoppingCart.class);
				for (ShoppingCart shoppingCart : glist) {
					Double total = shoppingCart.getGoodsNum()*shoppingCart.getSellPrice();
					shoppingCart.setGoodsTotal(total);
				}
				model.put("goodsList", glist);
			}
			model.put("orderPrice", orderPrice);
			model.put("credit", credit);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "love/center/cart/cart-order-confirm";
	}
	//用户积分，积分抵金额
	private void getUserCredit(HttpServletRequest request, ModelMap model){
		Map<String, String> param = new HashMap<>();
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		//用户积分
		param.put("userId", auth.getUserId());
		CreditUser uc = creditService.selectCreditUser(param);
		int useCredit = 0;
		Double useMoney = 0.0;//金额
		if(null!=uc){
			if(null!=uc.getCreditNum()){
				useCredit = uc.getCreditNum();
			}
			//积分抵金额
			CreditMoney cm = creditService.selectCreditMoney(param);
			if(null!=cm){
				if(null != cm.getUsableCondition() && cm.getUsableCondition()>0 
						&& null != cm.getIsWorthMoney() && cm.getIsWorthMoney()>0){
					double credit = cm.getUsableCondition();
					double money = cm.getIsWorthMoney();
					useMoney = NumberUtil.round((money/credit*useCredit), 2);
				}
			}
		}
		model.put("useCredit",useCredit);
		model.put("useMoney",useMoney);
	}
}
