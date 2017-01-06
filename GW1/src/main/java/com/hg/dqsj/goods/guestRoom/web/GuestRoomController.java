package com.hg.dqsj.goods.guestRoom.web;

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
import com.hg.dqsj.base.entity.FePic;
import com.hg.dqsj.base.service.FeGoodsService;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoom;
import com.hg.dqsj.goods.guestRoom.entity.GuestRoomInfo;
import com.hg.dqsj.goods.guestRoom.service.GuestRoomService;
import com.hg.dqsj.goods.store.entity.Store;
import com.hg.dqsj.goods.store.service.StoreService;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.menu.service.MenuService;
/**
 * 客房信息管理Controller类
 * 
 * @author hlz
 *
 */
@Controller
@RequestMapping(value = "guestRoom")
public class GuestRoomController extends BaseController{
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	private MenuService menuService;
	@Autowired
	private GuestRoomService guestRoomService;
	@Autowired
	private FeGoodsService feGoodsService;
	@Autowired
	private StoreService storeService;
	/**
	 * 住房列表页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		Map<String, String> param = new HashMap<>();
		param.put("storeType", "2");
		List<Store> list = storeService.selectStore(param);
		if(null!=list && list.size()>0){
			
			model.put("store", list.get(0));
		}
		return "store/guestRoom";
	}
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
			
			boolean boo = menuService.getOper("guestRoom/", "SEARCH", request, session);
			if(!boo){
				result.put("msg", "无查询客房列表的操作权限");
				return result;
			}
			String guestRoomName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomName")); //客房名称
			String startPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "startPrice")); //开始价格
			String endPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "endPrice")); //结束价格
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
			if (!StringUtil.isChsEnNum(guestRoomName, false)) {
				result.put("msg", "请不要输入带有特殊字符的客房名称");
			} else if (!StringUtil.isMoney(startPrice, false)) {
				result.put("msg", "请输入正确格式的价格区间");
			} else if (!StringUtil.isMoney(endPrice, false)) {
				result.put("msg", "请输入正确格式的价格区间");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("startRowNo", String.valueOf(startRowNo));
				param.put("rowSize", pageSize);
				param.put("guestRoomName", guestRoomName);
				param.put("startPrice", startPrice);
				param.put("endPrice", endPrice);
				
				int totalCount = guestRoomService.getCount(param);
				List<GuestRoomInfo> list = guestRoomService.selectGuestRoom(param);
				
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
			boolean boo = menuService.getOper("guestRoom/", "VIEW", request, session);
			if(!boo){
				result.put("msg", "无查看客房信息的操作权限");
				return result;
			}
			
			String roomId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roomId")); //客房id
			
			if (!UUIdUtil.matchUUIDOrBlank(roomId, true)) {
				result.put("msg", "非法请求，获得客房id错误！");
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("id", roomId);
				//客房信息
				GuestRoomInfo room = guestRoomService.selectById(param);
				if(null!=room){
					room.setGuestRoomContentStr(new String(room.getGuestRoomContent(),"UTF-8"));
				}
				result.put("info", room);
				//图片
				param.put("objId", roomId);
				List<FePic> plist = feGoodsService.selectPicById(param);
				result.put("plist", plist);
				
				result.put("isError", "0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "系统错误，请联系管理员！");
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
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roomId")); // id
			
			String op = "";
			String opstr = "";
			if(StringUtil.isNullorEmpty(id)){
				op = "添加";
				opstr = "ADD";
			}else{
				op = "修改";
				opstr = "UPDATE";
			}
			boolean boo = menuService.getOper("guestRoom/", opstr, request, session);
			if(!boo){
				result.put("msg", "无"+op+"客房的操作权限");
				return result;
			}
			
			String storeId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "storeId")); //酒店ID
			String guestRoomName = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomName")); //客房名称
			String sellPrice = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "sellPrice")); //销售价格
			String guestRoomBrief = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomBrief")); //客房简介
			String guestRoomTel = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomTel")); //联系电话
			String guestRoomAddress = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomAddress")); //地址
			String guestRoomContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomContent")); //客房内容
			String guestRoomOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "guestRoomOrder")); //排序
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark")); // 备注
			
			if(!UUIdUtil.matchUUIDOrBlank(storeId, true)){
				result.put("msg", "非法请求，请选择一个酒店");
			}else if(!StringUtil.isChsEnNum(guestRoomName, true)){
				result.put("msg", "请输入不带有特殊字符的客房名称");
			}else if(!validate(id,storeId,guestRoomName)){
				result.put("msg", "客房名称已存在");
			}else if(!StringUtil.isMoney(sellPrice, true)){
				result.put("msg", "请输入正确格式的销售价格");
			}else if(!StringUtil.isParagraph(guestRoomBrief, false)){
				result.put("msg", "请不要输入带有特殊字符的客房简介");
			}else if(!StringUtil.isMobilePhone(guestRoomTel, false) && !StringUtil.isTelphone(guestRoomTel, false)){
				result.put("msg", "请输入正确的联系方式");
			}else if(!NumberUtil.isInt(guestRoomOrder, false)){
				result.put("msg", "请输入正确的排序顺序");
			}else if(!StringUtil.isParagraph(remark, false)){
				result.put("msg", "请不要输入带有特殊字符的备注");
			}else{
				
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String fDate = DateUtil.getFullTime();
				String deleteFlag = "0";
				
				GuestRoom room = new GuestRoom();
				if(StringUtil.isNullorEmpty(id)){
					room.setId(UUIdUtil.getUUID());
				}else{
					room.setId(id);
				}
				room.setStoreId(storeId);
				room.setGuestRoomName(guestRoomName);
				room.setSellPrice(sellPrice);
				room.setGuestRoomBrief(guestRoomBrief);
				room.setGuestRoomTel(guestRoomTel);
				room.setGuestRoomAddress(guestRoomAddress);
				room.setGuestRoomContent(guestRoomContent.getBytes("UTF-8"));
				if(!StringUtil.isNullorEmpty(guestRoomOrder)){
					room.setGuestRoomOrder(Integer.parseInt(guestRoomOrder));
				}
				room.setRemark(remark);
				room.setCreateUserId(auth.getUserId());
				room.setCreateDate(fDate);
				room.setUpdateUserId(auth.getUserId());
				room.setUpdateDate(fDate);
				room.setDeleteFlag(deleteFlag);
				
				String objType = "16";//所属类别
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
						pic.setObjId(room.getId());
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
					guestRoomService.save(room, plist);
					result.put("msg", "客房添加成功");
				}else{
					guestRoomService.update(room, plist);
					result.put("msg", "客房修改成功");
				}
				result.put("isError", "0");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员！");
			e.printStackTrace();
		}
		return result;
	}
	private boolean validate(String id,String storeId,String roomName){
		Map<String, String> param = new HashMap<>();
		param.put("isvali","1");
		param.put("id",id);
		param.put("storeId",storeId);
		param.put("roomName",roomName);
		List<GuestRoomInfo> list = guestRoomService.selectGuestRoom(param);
		if(null!=list && list.size()>0){
			return false;
		}
		return true;
	}
	/**
	 * 删除客房信息
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
			boolean boo = menuService.getOper("guestRoom/", "DELETE", request, session);
			if(!boo){
				result.put("msg", "无删除客房信息的操作权限");
				return result;
			}
			
			String roomId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "roomId")); // ID
			
			if (!UUIdUtil.matchUUIDOrBlank(roomId, true)) {
				result.put("msg", "非法请求！");
			} else {
				Map<String, String> param = new HashMap<>();
				Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
				String updateUserId = auth.getUserId();
				String updateDate = DateUtil.getFullTime();
				
				param.put("objId",roomId);
				//商品图片
				List<FePic> gplist = feGoodsService.selectPicById(param);
				
				param.put("id",roomId);
				param.put("updateUserId",updateUserId);
				param.put("updateDate",updateDate);
				param.put("deleteFlag","1");
				
				guestRoomService.delete(param);

				if(null!=gplist && gplist.size()>0){
					List<String> filearr = new ArrayList<String>();
					for (int i = 0; i < gplist.size(); i++) {
						String mPath = request.getSession().getServletContext().getRealPath(gplist.get(i).getPicUrl());
						//String bpath = mPath.replace("_1", "");
						filearr.add(mPath);
						//filearr.add(bpath);
					}
					deleteFiles(filearr);
				}
				
				result.put("isError", "0");
				result.put("msg", "删除客房信息成功");
			}
		} catch (Exception e) {
			result.put("msg", "系统错误，请联系管理员");
            logger.error(e.getMessage());
		}
		return result;
	}
}
