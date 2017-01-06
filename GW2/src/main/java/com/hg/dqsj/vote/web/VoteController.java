package com.hg.dqsj.vote.web;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.vote.entity.VTUserDetail;
import com.hg.dqsj.vote.entity.Vote;
import com.hg.dqsj.vote.entity.VoteOption;
import com.hg.dqsj.vote.service.VoteOptionService;
import com.hg.dqsj.vote.service.VoteService;

import net.sourceforge.jtds.jdbc.DateTime;



/**
 * 投票管理
 * 
 * @author joe
 *
 */
@Controller
@RequestMapping(value = "vote")
public class VoteController {
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionProvider session;

	@Autowired
	private VoteService voteService;

	@Autowired
	private VoteOptionService voteOptionService;

	/**
	 * 投票详情页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(@RequestParam(value = "voteId" , defaultValue="") String voteId,
			HttpServletRequest request, ModelMap model) {
		
		try {	
			if (null!=voteId && voteId!="") {
		Map<String,String> param = new HashMap<String,String>();
		param.put("voteId", voteId);
		List<Vote> list = voteService.selectAllVote(param);	
		if(null!=list && list.size()>0){
			Vote vote = list.get(0);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			Timestamp startTime =Timestamp.valueOf(vote.getVoteStartTime());
			Timestamp endTime= Timestamp.valueOf(vote.getVoteEndTime());
			if (startTime.getTime()<currentTime.getTime() && currentTime.getTime()<endTime.getTime()) {
				//活动进行中
				model.addAttribute("status", "1");
			}else{
				//已结束或未开始
				model.addAttribute("status", "0");
			}
			
			model.addAttribute("voteId", voteId);
			model.addAttribute("vote", vote);
		  }
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(">>>>>获取投票详情错误！");
		}
		return "vote/voteDetails";
	}
	/**
	 * 获取投票选项列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "optionList", method = RequestMethod.GET)
	public String optionList(@RequestParam(value = "voteId" , defaultValue="") String voteId,
			@RequestParam(value = "voteTitle" , defaultValue="") String voteTitle,
			@RequestParam(value = "isLimitUser" , defaultValue="1") String isLimitUser,
			HttpServletRequest request, ModelMap model) {
		try {
			String title = URLDecoder.decode(voteTitle,"UTF-8");
			model.addAttribute("title", title);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("voteId", voteId);
		model.addAttribute("isLimitUser", isLimitUser);
		return "vote/voteOption";
	}
	/**
	 * 加载投票选项列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getOption", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadDetails(HttpServletRequest request, ModelMap model) {
		Map<String, Object> result = new HashMap<>();
		try{	
			result = voteOptionService.selectByVoteId(request); // 查询结果
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("isError", "1");
			result.put("msg", "系统错误，请联系管理员！");
		}
		return result;
	}
	
	/**
	 * 获取投票列表
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
			result.put("msg", "网络错误，请稍后再试！");
		}
		return result;
	}
	/**
	 * 给选项投票
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "doVote",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doVote(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String uuid = UUIdUtil.getUUID();
			String id = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "id")); // 投票选项id
			String voteId= StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId")); // 投票id
			
			if(id==null || voteId==null){
				result.put("msg", "网络错误，请稍后重试！");
		     }
			else{
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			String createUserId = auth.getUserId();
			String createDate = DateUtil.getFullTime();
			String voteUserId = auth.getUserId();
			String updateUserId = auth.getUserId();
			String updateDate = DateUtil.getFullTime();
			String deleteFlag = "0";
			
			VTUserDetail vd = new VTUserDetail();
			vd.setId(uuid);
			vd.setVoteId(voteId);
		    vd.setVoteOptionId(id);
		    vd.setVoteUserId(voteUserId);
            vd.setCreateDate(createDate);
            vd.setCreateUserId(createUserId);
            vd.setUpdateDate(updateDate);
            vd.setUpdateUserId(updateUserId);
            vd.setDeleteFlag(deleteFlag);
 
			
			voteService.saveUserDetail(vd);;
			
			result.put("isError", "0");
			result.put("msg", "投票成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "网络错误，请稍后再试！");
		}
		return result;
	}
	/**
	 * 参与投票页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String join(@RequestParam(value = "voteId" , defaultValue="") String voteId,			
			@RequestParam(value = "voteTitle" , defaultValue="") String voteTitle,			
			HttpServletRequest request, ModelMap model) {
		try {
			String title = URLDecoder.decode(voteTitle,"UTF-8");
			model.addAttribute("title", title);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("voteId", voteId);
		return "vote/voteJoin";
	}
	/**
	 * 参与投票
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveOption",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOption(HttpServletRequest request, HttpServletResponse reponse, ModelMap model) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("isError", "1");
		try{
			String uuid = UUIdUtil.getUUID();
			String name = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "name")); // 用户名
			String phone = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "phone")); // 手机号		
			String voteId = StringUtil.trimBlank(RequestUtils.getQueryParam(request, "voteId"));//投票id
			Map<String, Object> map = new HashMap<>();
			map.put("voteOptionNo", phone);
           Integer count = voteOptionService.countByVoteId(map);
           if (count>0) {
        	   result.put("msg", "该手机号码已存在！");
		     }else if(!StringUtil.isChsEnNum(name, true)){
				result.put("msg", "请输入不带有特殊字符的用户名");
		     }else if(voteId==null){
		    	result.put("msg", "网络错误，参与失败！请稍后再试！");
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
            vote.setVoteOptionNo(phone);
            vote.setVoteOptionTitle(name);
            vote.setCreateDate(createDate);
            vote.setCreateUserId(createUserId);
            vote.setUpdateDate(updateDate);
            vote.setUpdateUserId(updateUserId);
            vote.setDeleteFlag(deleteFlag);
            
			String picUrl = "";
			/*if(null!=file){
				picUrl = uploadFile(file, request);
			}*/
			vote.setVoteOptionPicUrl(picUrl);
			
			voteOptionService.saveOption(vote);
			
			result.put("isError", "0");
			result.put("msg", "提交参与成功!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", "网络错误，参与失败！请稍后再试！");
		}
		return result;
	}
	
	
	
}
