package com.hg.dqsj.base.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.core.file.BaseController;

/**
 * 上传管理Controller类
 * 
 * @author 吴晓敏
 *
 */
@Controller
@RequestMapping(value = "upload")
public class UploadController extends BaseController {

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "image", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImage(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		String fileName = uploadFile(file, request);
		result.put("fileName", fileName);
		return result;
	}
}
