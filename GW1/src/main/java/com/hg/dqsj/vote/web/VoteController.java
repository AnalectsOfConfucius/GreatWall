package com.hg.dqsj.vote.web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.core.file.BaseController;
import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.vote.entity.Vote;
import com.hg.dqsj.vote.entity.VoteOption;
import com.hg.dqsj.vote.service.VoteOptionService;
import com.hg.dqsj.vote.service.VoteService;



/**
 * 投票管理
 * 
 * @author joe
 *
 */
@Controller
@RequestMapping(value = "vote")
public class VoteController extends BaseController {

	@Autowired
	private SessionProvider session;

	@Autowired
	private VoteService voteService;

	@Autowired
	private VoteOptionService voteOptionService;

	/**
	 * 发起投票设置界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		
		return "vote/voteList";
	}

	/**
	 * 发起投票设置界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/setVote", method = RequestMethod.GET)
	public String setVote(@RequestParam(value = "fromList" ,defaultValue="0") String fromList,
			@RequestParam(value = "isSee" ,defaultValue="0") String isSee,
			@RequestParam(value = "voteId" , defaultValue="") String voteId,
			HttpServletRequest request, ModelMap model) {
		if (fromList.equals("1") && !voteId.equals("") ) {
			Map<String,String> param = new HashMap<String,String>();
			param.put("voteId", voteId);
			List<Vote> list = voteService.selectAllVote(param);	
			if(null!=list && list.size()>0){
				Vote vote = list.get(0);
				model.addAttribute("voteId", voteId);
				model.addAttribute("vote", vote);
				model.addAttribute("isExist", "1");
				model.addAttribute("fromList", "1");
				model.addAttribute("isSee",isSee);
			}
		}
		return "vote/voteForm";
	}
	/**
	 * 获取投票选项列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "voteList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voteList(HttpServletRequest request, ModelMap model) {
		String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
		String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try{
		if(StringUtil.isNullorEmpty(pageSize)){
			pageSize = "8";
		}
		if(StringUtil.isNullorEmpty(currentPageNo)){
			currentPageNo = "1";
		}

		Map<String,String> param = new HashMap<String,String>();
		
		List<Vote> list = voteService.selectAllVote(param);
		if(null!=list && list.size()>0){
			int totalCount = list.size();
			Pager p = new Pager();
			p.setCurrentPage(Integer.parseInt(currentPageNo));
			p.setTotalCount(totalCount);
			p.setPageSize(Integer.parseInt(pageSize));
			p.setItems(list);
			
			@SuppressWarnings("unchecked")
			List<VoteOption> alist = (List<VoteOption>) p.doPagination();
			
			result.put("currentPage", p.getCurrentPage());
			result.put("totalCnt", totalCount);
			result.put("rows", alist);
		}
		} catch (Exception e) {
			logger.error(e.getMessage());
	        result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	/**
	 * 结束投票
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "shutDown")
	@ResponseBody
	public Map<String, Object> shutDown(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try {
			String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId")); // 投票id
			
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			String stateFlag = "2";
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("id", voteId);
			param.put("updateUserId", updateUserId);
			param.put("updateDate", updateDate);
			param.put("stateFlag", stateFlag);
			voteService.shutDown(param);
			
		
			result.put("isError", "0");
			result.put("msg", "删除广告信息成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，删除广告信息失败！请联系管理员");
		}
		return result;
	}
	/**
	 * 新增投票基础信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveBase",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBase(@RequestParam(value = "filepath" , required = false) MultipartFile file, 
			@RequestParam(value = "backpath" , required = false) MultipartFile backFile, 
			HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String uuid = UUIdUtil.getUUID();
			String voteTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteTitle")); // 投票标题
			String voteDescribe = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteDescribe")); // 投票描述
			String voteStartTime = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteStartTime")); // 开始时间
			String voteEndTime = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteEndTime")); //结束时间
			String voteOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOrder")); //排序
			//String voteContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteContent")); //详细信息
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = sd.parse(voteStartTime);
			Date endTime = sd.parse(voteEndTime);
			if(!StringUtil.isChsEnNum(voteTitle, true)){
				result.put("msg", "请输入不带有特殊字符的投票标题");
			}else if(startTime.getTime()>endTime.getTime()){
				result.put("msg", "开始时间不能大于结束时间");
		     }else if(!NumberUtil.isInt(voteOrder, true)){
				result.put("msg", "排序必须为数字");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String createUserId = auth.getUserId();
			String createDate = DateUtil.getFullTime();
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			String deleteFlag = "0";
			String stateFlag = "1" ;
			
			Vote vote = new Vote();
			vote.setId(uuid);
            vote.setVoteTitle(voteTitle);
            vote.setVoteDescribe(voteDescribe);
            vote.setStateFlag(stateFlag);
           // vote.setVoteContent(voteContent.getBytes("UTF-8"));
            vote.setVoteOrder(Integer.valueOf(voteOrder));
            vote.setVoteStartTime(voteStartTime);
            vote.setVoteEndTime(voteEndTime);
            vote.setCreateDate(createDate);
            vote.setCreateUserId(createUserId);
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);
            vote.setDeleteFlag(deleteFlag);
            //封面图
			String picUrl = "";
			if(null!=file){
				picUrl = uploadFile(file, request);
			}
			vote.setVotePicUrl(picUrl);
			//背景图
			String voteBackPicUrl = "";
			if(null!=backFile){
				voteBackPicUrl = uploadFile(backFile, request);
			}
			vote.setVoteBackPicUrl(voteBackPicUrl);
			
			voteService.saveBase(vote);
			
			result.put("isError", "0");
			result.put("voteId", uuid);
			result.put("msg", "保存投票信息成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，保存信息失败！请联系管理员");
		}
		return result;
	}
	/**
	 * 修改投票基础信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateBase",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBase(@RequestParam(value = "filepath" , required = false) MultipartFile file, 
			@RequestParam(value = "backpath" , required = false) MultipartFile backFile, 
			HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String voteTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteTitle")); // 投票标题
			String voteDescribe = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteDescribe")); // 投票描述
			String voteStartTime = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteStartTime")); // 开始时间
			String voteEndTime = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteEndTime")); //结束时间
			//String voteContent = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteContent")); //详细信息
			String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId"));//投票id
			String votePicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "votePicUrl"));//封面图
			String voteBackPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteBackPicUrl"));//背景图
			String voteOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOrder")); //排序
			String hasDeletePic1 = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "hasDeletePic1"));//是否删除封面图片
			String hasDeletePic2 = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "hasDeletePic2"));//是否删除背景图片
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = sd.parse(voteStartTime);
			Date endTime = sd.parse(voteEndTime);
			if(!StringUtil.isChsEnNum(voteTitle, true)){
				result.put("msg", "请输入不带有特殊字符的投票标题");
			}else if(startTime.getTime()>endTime.getTime()){
				result.put("msg", "开始时间不能大于结束时间");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			
			Vote vote = new Vote();
			vote.setId(voteId);
            vote.setVoteTitle(voteTitle);
            vote.setVoteDescribe(voteDescribe);
            vote.setVoteOrder(Integer.valueOf(voteOrder));
            //vote.setVoteContent(voteContent.getBytes("UTF-8"));
            vote.setVoteStartTime(voteStartTime);
            vote.setVoteEndTime(voteEndTime);
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);
            
            //封面图
			String picUrl = "";
			if(null!=file){
				picUrl = uploadFile(file, request);
			}
			if (picUrl.equals("") && !hasDeletePic1.equals("1")) {
				vote.setVotePicUrl(votePicUrl);
			}else{
			vote.setVotePicUrl(picUrl);
			}
			
			//背景图
			String backPicUrl = "";
			if(null!=backFile){
				backPicUrl = uploadFile(backFile, request);
			}
			if (backPicUrl.equals("") && !hasDeletePic2.equals("1")) {
				vote.setVoteBackPicUrl(voteBackPicUrl);
			}else{
				vote.setVoteBackPicUrl(backPicUrl);
			}
			
			
			voteService.updateBase(vote);
			
			result.put("isError", "0");
			result.put("msg", "修改投票信息成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，修改信息失败！请联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改投票规则信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateRule",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRule(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String userTypeFlag = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "userTypeFlag"));// 投票用户类型
			String periodHour = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "periodHour")); // 用户投票周期设置
			String voteNumber = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteNumber")); //周期内投票数设置		
			String voteRule = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteRule"));//投票规则描述
			String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId"));//投票id
			String isLimitUser = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "isLimitUser"));//投票id
			if(!NumberUtil.isInt(periodHour, false)){
				result.put("msg", "投票周期设置请输入数字");
			}else if(!NumberUtil.isInt(voteNumber, false)){
				result.put("msg", "投票周期内票数设置请输入数字");
		     }else if(!isLimitUser.equals("0") && !isLimitUser.equals("1")){
				result.put("msg", "请选择是否对用户开放");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			
			Vote vote = new Vote();
			vote.setId(voteId);
           // vote.setUserTypeFlag(userTypeFlag);
			vote.setIsLimitUser(isLimitUser);
            vote.setPeriodHour(Integer.valueOf(periodHour));
            vote.setVoteNumber(Integer.valueOf(voteNumber));
            vote.setVoteRule(voteRule);
            
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);            
			
			voteService.updateRule(vote);
			
			result.put("isError", "0");
			result.put("msg", "修改投票规则信息成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，修改信息失败！请联系管理员");
		}
		return result;
	}
	/**
	 * 新增投票选项
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveOption",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOption(@RequestParam(value = "filepath" , required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String uuid = UUIdUtil.getUUID();
			String voteOptionNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOptionNo")); // 投票选项编号
			String voteOptionTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOptionTitle")); // 投票选项标题		
			String voteOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOrder")); // 投票选项排序
			String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId"));//投票id
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));//选项描述
			Map<String, Object> map = new HashMap<>();
			map.put("voteOptionNo", voteOptionNo);
			map.put("voteId", voteId);
		    Integer count = voteOptionService.countByVoteId(map);
		    if (count>0) {
		    	result.put("msg", "该选项编号已存在，请换一个！");
			}else if(!StringUtil.isChsEnNum(voteOptionTitle, true)){
				result.put("msg", "请输入不带有特殊字符的选项标题");
		     }else if(!NumberUtil.isInt(voteOrder, true)){
				result.put("msg", "选项排序必须为数字");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String createUserId = auth.getUserId();
			String createDate = DateUtil.getFullTime();
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			String deleteFlag = "0";
			
			VoteOption vote = new VoteOption();
			vote.setId(uuid);
			vote.setVoteId(voteId);
			vote.setRemark(remark);
			vote.setVoteOrder(Integer.valueOf(voteOrder));
            vote.setVoteOptionNo(voteOptionNo);
            vote.setVoteOptionTitle(voteOptionTitle);
            vote.setCreateDate(createDate);
            vote.setCreateUserId(createUserId);
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);
            vote.setDeleteFlag(deleteFlag);
            
			String picUrl = "";
			if(null!=file){
				picUrl = uploadFile(file, request);
			}
			vote.setVoteOptionPicUrl(picUrl);
			
			voteOptionService.saveOption(vote);
			
			result.put("isError", "0");
			result.put("msg", "新增投票选项成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，保存信息失败！请联系管理员");
		}
		return result;
	}
	/**
	 * 修改投票选项
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateOption",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOption(@RequestParam(value = "filepath" , required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String o_voteOptionNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "o_voteOptionNo")); // 原投票选项编号
			String voteOptionNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOptionNo")); // 投票选项编号
			String voteOptionTitle = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOptionTitle")); // 投票选项标题		
			String voteOrder = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOrder")); // 投票选项排序
			String optionId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "optionId"));//投票选项id
			String remark = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "remark"));//选项描述
		    String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId"));//投票id
			String voteOptionPicUrl = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteOptionPicUrl"));//选项图片
			String hasDeletePic = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "hasDeletePic3"));//是否删除选项图片
			Map<String, Object> map = new HashMap<>();
			map.put("voteOptionNo", voteOptionNo);
			map.put("voteId", voteId);
		    Integer count = voteOptionService.countByVoteId(map);
		    if (count>0 && !o_voteOptionNo.equals(voteOptionNo)) {
		    	result.put("msg", "该选项编号已存在，请换一个！");
			}else if(!StringUtil.isChsEnNum(voteOptionTitle, true)){
				result.put("msg", "请输入不带有特殊字符的选项标题");
		     }else if(!NumberUtil.isInt(voteOrder, true)){
				result.put("msg", "选项排序必须为数字");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			
			VoteOption vote = new VoteOption();
			vote.setId(optionId);
			vote.setRemark(remark);
			vote.setVoteOrder(Integer.valueOf(voteOrder));
            vote.setVoteOptionNo(voteOptionNo);
            vote.setVoteOptionTitle(voteOptionTitle);
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);
            
			String picUrl = "";
			if(null!=file){
				picUrl = uploadFile(file, request);
			}
			if (picUrl.equals("") && !hasDeletePic.equals("1") ) {
				vote.setVoteOptionPicUrl(voteOptionPicUrl);
			}else{
			    vote.setVoteOptionPicUrl(picUrl);
			}
			voteOptionService.updateOption(vote);
			
			result.put("isError", "0");
			result.put("msg", "修改投票选项成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，修改信息失败！请联系管理员");
		}
		return result;
	}
	/**
	 * 获取投票选项列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getOption", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> serviceTime(HttpServletRequest request, ModelMap model) {
		String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId")); //投票id
		String currentPageNo = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPageNo")); // 当前页数
		String pageSize = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "pageSize")); // 当前页数

		Map<String, Object> result = new HashMap<>();
		result.put("isError", "0");
		try{
		if(StringUtil.isNullorEmpty(pageSize)){
			pageSize = "8";
		}
		if(StringUtil.isNullorEmpty(currentPageNo)){
			currentPageNo = "1";
		}

		Map<String,String> param = new HashMap<String,String>();
		param.put("voteId",voteId);

		List<VoteOption> list = voteOptionService.selectByVoteId(param);
		if(null!=list && list.size()>0){
			int totalCount = list.size();
			Pager p = new Pager();
			p.setCurrentPage(Integer.parseInt(currentPageNo));
			p.setTotalCount(totalCount);
			p.setItems(list);
			
			@SuppressWarnings("unchecked")
			List<VoteOption> alist = (List<VoteOption>) p.doPagination();
			
			result.put("currentPage", p.getCurrentPage());
			result.put("totalCnt", totalCount);
			result.put("rows", alist);
		}
		} catch (Exception e) {
			logger.error(e.getMessage());
	        result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	
	/**
	 * 删除投票选项
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delOption")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try {
			String optionId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "optionId")); // 投票id
			
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("id", optionId);
			param.put("updateUserId", updateUserId);
			param.put("updateDate", updateDate);
			param.put("deleteFlag", "1");
			
			List<VoteOption> lis = voteOptionService.selectByVoteId(param);
			
			voteOptionService.deleteOption(param);
			
			if(null!=lis && lis.size()>0){
				String picpath = lis.get(0).getVoteOptionPicUrl();
				if(!StringUtil.isNullorEmpty(picpath)){
					List<String> filearr = new ArrayList<String>();
					String path = request.getSession().getServletContext().getRealPath(picpath);
					filearr.add(path);
					deleteFiles(filearr);
				}
			}
			result.put("isError", "0");
			result.put("msg", "删除广告信息成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "系统错误，删除广告信息失败！请联系管理员");
		}
		return result;
	}
}
