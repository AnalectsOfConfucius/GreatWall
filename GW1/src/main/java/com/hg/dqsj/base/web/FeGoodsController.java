package com.hg.dqsj.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.FeGoods;
import com.hg.dqsj.base.entity.FeGoodsInfo;
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
/**
 * 商品信息管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "fegoods")
public class FeGoodsController extends BaseController{
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private FeGoodsService feGoodsService;
	@Autowired
	private MenuService menuService;
	
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
			String menuName = "";
			String menuUrl = "";
			if("1".equals(typeCode)){
				menuName = "特色商品";
				menuUrl = "specialGoods/";
			}else if("2".equals(typeCode)){
				menuName = "年卡";
				menuUrl = "annualCard/";
			}else if("3".equals(typeCode)){
				menuName = "食品";
				menuUrl = "food/";
			}else if("4".equals(typeCode)){
				menuName = "门票";
				menuUrl = "ticket/";
			}
			boolean boo = menuService.getOper(menuUrl, "SEARCH", request, session);
			if(!boo){
				result.put("msg", "无查询"+menuName+"的操作权限");
				return result;
			}
			String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); // 美食店ID
			String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
			String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数
			if (StringUtil.isNullorEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtil.isNullorEmpty(currentPageNo)) {
				currentPageNo = "1";
			}
			int curno = Integer.parseInt(currentPageNo);
			int pz = Integer.parseInt(pageSize);
			int startRowNo = pz * (curno - 1);
			if (!StringUtil.isChsEnNum(goodsName, false)) {
				result.put("msg", "只能输入由中文、英文、数字或下划线组成的用户名！");
			} else if (!NumberUtil.isInt(typeCode, true)) {
				result.put("msg", "未获得商品类别");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("startRowNo", String.valueOf(startRowNo));
				param.put("rowSize", pageSize);
				param.put("goodsName", goodsName);
				param.put("typeCode", typeCode);
				param.put("storeId", storeId);
				
				int totalCount = feGoodsService.countByCriterias(param);
				List<FeGoodsInfo> list = feGoodsService.selectByCriterias(param);
				
				result.put("currentPage", currentPageNo);
				result.put("totalCnt", totalCount);
				result.put("rows", list);
				result.put("isError", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
			String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 商品类别代码
			String menuName = "";
			String menuUrl = "";
			if("1".equals(typeCode)){
				menuName = "特色商品";
				menuUrl = "specialGoods/";
			}else if("2".equals(typeCode)){
				menuName = "年卡";
				menuUrl = "annualCard/";
			}else if("3".equals(typeCode)){
				menuName = "食品";
				menuUrl = "food/";
			}else if("4".equals(typeCode)){
				menuName = "门票";
				menuUrl = "ticket/";
			}
			boolean boo = menuService.getOper(menuUrl, "VIEW", request, session);
			if(!boo){
				result.put("msg", "无查看"+menuName+"的操作权限");
				return result;
			}
			
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
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 删除图片
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delImg")
	@ResponseBody
	public Map<String, Object> delBusImg(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try {
			Map<String, String> param = new HashMap<>();
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id")); // 图片id
			param.put("id", id);
			//param.put("updateUserId", auth.getUserId());
			//param.put("updateDate", fDate);
			//param.put("deleteFlag", "1");
			
			feGoodsService.deletePic(param);
			
			String imgUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "imgUrl")); // 图片路径
			if(!StringUtil.isNullorEmpty(imgUrl)){
				String realPath = request.getSession().getServletContext().getRealPath(imgUrl);
				List<String> filearr = new ArrayList<String>();
				filearr.add(realPath);
				deleteFiles(filearr);
			}
			result.put("msg", "图片删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，图片删除失败！");
		}
		return result;
	}
	/**
	 * 保存或更新
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		result.put("isError", "1");
		try {
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // 商品id
			String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 商品类别代码
			String menuName = "";
			boolean isfood = false;
			String menuUrl = "";
			if("1".equals(typeCode)){
				menuName = "特色商品";
				menuUrl = "specialGoods/";
			}else if("2".equals(typeCode)){
				menuName = "年卡";
				menuUrl = "annualCard/";
			}else if("3".equals(typeCode)){
				menuName = "食品";
				menuUrl = "food/";
			}else if("4".equals(typeCode)){
				menuName = "门票";
				menuUrl = "ticket/";
			}
			String op = "";
			String opstr = "";
			if(StringUtil.isNullorEmpty(id)){
				op = "添加";
				opstr = "ADD";
			}else{
				op = "修改";
				opstr = "UPDATE";
			}
			boolean boo = menuService.getOper(menuUrl, opstr, request, session);
			if(!boo){
				result.put("msg", "无"+op+menuName+"的操作权限");
				return result;
			}
			String goodsName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsName")); // 商品名称
			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); //美食店ID
			String sellPricestr = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "sellPrice")); //销售价格
			String goodsBrief = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsBrief")); //商品简介
			String goodsContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsContent")); //商品内容
			String goodsOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsOrder")); //排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注
			String objType = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "objType")); // 所属类别
			
			String msg = "";
			
			if(!StringUtil.isChsEnNum(goodsName, true)){
				result.put("msg", "请输入不带有特殊字符的名称");
			}else if(!validate(id,goodsName)){
				result.put("msg", menuName+"名称已存在");
			}else if(!NumberUtil.isInt(typeCode, true)){
				result.put("msg", "请选择正确的商品类别");
			}else if(!UUIdUtil.matchUUIDOrBlank(storeId, isfood)){
				result.put("msg", "非法请求，食品店id获得错误");
			}else if(!StringUtil.isMoney(sellPricestr, true)){
				result.put("msg", "请输入正确的销售价格");
			}else if(!StringUtil.isParagraph(goodsBrief, false)){
				result.put("msg", "请不要输入带有特殊字符的商品简介");
			}else if(!NumberUtil.isInt(goodsOrder, false)){
				result.put("msg", "请输入正确的排序顺序");
			}else if(!StringUtil.isParagraph(remark, false)){
				result.put("msg", "请不要输入带有特殊字符的备注");
			}else{
				int order = 0;
				if(!StringUtil.isNullorEmpty(goodsOrder)){
					order = Integer.parseInt(goodsOrder);
				}
				Double sellPrice = NumberUtil.round(NumberUtil.strToDouble(sellPricestr), 2);
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String fDate = DateUtil.getFullTime();
				String deleteFlag = "0";
				FeGoods goods = new FeGoods();
				
				if(StringUtil.isNullorEmpty(id)){
					goods.setId(UUIdUtil.getUUID());
				}else{
					goods.setId(id);
				}
				goods.setGoodsName(goodsName);
				goods.setTypeCode(typeCode);
				goods.setSellPrice(sellPrice);
				goods.setGoodsBrief(goodsBrief);
				goods.setGoodsContent(goodsContent.getBytes("UTF-8"));
				goods.setGoodsOrder(order);
				goods.setRemark(remark);
				goods.setCreateUserId(auth.getUserId());
				goods.setCreateDate(fDate);
				goods.setUpdateUserId(auth.getUserId());
				goods.setUpdateDate(fDate);
				goods.setDeleteFlag(deleteFlag);
				
				if("1".equals(typeCode)){
					objType = "14";//特色商品
					msg = "特色商品";
				}else if("2".equals(typeCode)){
					objType = "14";//年卡
					msg = "年卡";
				}else if("3".equals(typeCode)){
					objType = "12";//食品
					msg = "食品";
				}else if("4".equals(typeCode)){
					msg = "门票";
				}
				List<FePic> plist = new ArrayList<FePic>();
				String[] picUrlArr = request.getParameterValues("picUrl");
				if(null!=picUrlArr && picUrlArr.length>0){
					String[] mplarr = request.getParameterValues("mainPicFlag");
					String[] orderarr = request.getParameterValues("picOrder");
					
					for (int i = 0; i < picUrlArr.length; i++) {
						String picUrl = picUrlArr[i];
						String[] parr = picUrl.split("/");
						String picName = parr[parr.length-1];
						String mainPicFlag = "0";
						if(mplarr.length>i){
							mainPicFlag = mplarr[i];
						}
						String po = "0";
						int picOrder = 0;
						if(orderarr.length>i){
							po = orderarr[i];
							if(!NumberUtil.isInt(po, false)){
								result.put("msg", "请输入正确的图片排序顺序");
								return result;
							}
							if(!StringUtil.isNullorEmpty(po)){
								picOrder = NumberUtil.strToInt(po);
							}
						}
						FePic pic = new FePic();
						pic.setId(UUIdUtil.getUUID());
						pic.setObjType(objType);
						pic.setObjId(goods.getId());
						pic.setPicUrl(picUrl);
						pic.setPicName(picName);
						pic.setPicOrder(picOrder);
						pic.setMainPicFlag(mainPicFlag);
						pic.setRemark("");
						pic.setCreateUserId(auth.getUserId());
						pic.setCreateDate(fDate);
						pic.setUpdateUserId(auth.getUserId());
						pic.setUpdateDate(fDate);
						pic.setDeleteFlag(deleteFlag);
						plist.add(pic);
					}
				}
				
				if(StringUtil.isNullorEmpty(id)){
					feGoodsService.save(goods, plist);
					result.put("msg", msg+"添加成功");
				}else{
					feGoodsService.update(goods, plist);
					result.put("msg", msg+"修改成功");
				}
				result.put("isError", "0");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员！");
			e.printStackTrace();
		}
		return result;
	}
	private boolean validate(String id,String goodsName){
		Map<String, String> param = new HashMap<>();
		param.put("isvali","1");
		param.put("id",id);
		param.put("gName",goodsName);
		List<FeGoodsInfo> list = feGoodsService.selectByCriterias(param);
		if(null!=list && list.size()>0){
			return false;
		}
		return true;
	}
	/**
	 * 删除商品信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, String> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		Map<String, String> result = new HashMap<>();
		result.put("isError", "1");
		
		try {
			String typeCode = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "typeCode")); // 商品类别代码
			String menuName = "";
			String menuUrl = "";
			if("1".equals(typeCode)){
				menuName = "特色商品";
				menuUrl = "specialGoods/";
			}else if("2".equals(typeCode)){
				menuName = "年卡";
				menuUrl = "annualCard/";
			}else if("3".equals(typeCode)){
				menuName = "食品";
				menuUrl = "food/";
			}else if("4".equals(typeCode)){
				menuName = "门票";
				menuUrl = "ticket/";
			}
			boolean boo = menuService.getOper(menuUrl, "DELETE", request, session);
			if(!boo){
				result.put("msg", "无删除"+menuName+"的操作权限");
				return result;
			}
			
			String goodsId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "goodsId")); // ID
			
			if (!UUIdUtil.matchUUIDOrBlank(goodsId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Map<String, String> param = new HashMap<>();
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String updateUserId = auth.getUserId();
				String updateDate = DateUtil.getFullTime();
				
				param.put("objId",goodsId);
				//商品图片
				List<FePic> gplist = feGoodsService.selectPicById(param);
				
				param.put("id",goodsId);
				param.put("updateUserId",updateUserId);
				param.put("updateDate",updateDate);
				param.put("deleteFlag","1");
				param.put("typeCodeFlag", typeCode);
				feGoodsService.delete(param);
				
				if(null!=gplist && gplist.size()>0){
					List<String> filearr = new ArrayList<String>();
					for (int i = 0; i < gplist.size(); i++) {
						String mPath = request.getSession().getServletContext().getRealPath(gplist.get(i).getPicUrl());
						String bpath = mPath.replace("_1", "");
						filearr.add(mPath);
						filearr.add(bpath);
					}
					deleteFiles(filearr);
				}
				
				result.put("isError", "0");
				result.put("msg", "删除信息成功");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员");
            logger.error(e.getMessage());
		}
		return result;
	}
}
