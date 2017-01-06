/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.hg.dqsj.plupload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hg.core.file.BaseController;

/**
 * uploadify文件上传组件服务端
 * 
 * @author
 *
 */
@Controller
@RequestMapping(value = "/plupload")
public class uploadifyServer extends BaseController {

	/**
	 * 处理图片文件上传操作(支持同时上传多个)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 定义返回json格式的Map数据
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// List<String> nameList = uploadFile(request,
			// UPLOAD_ROOT_PATH,140,95);
			List<String> nameList = uploadFile(request);

			resultMap.put("nameList", nameList);
			if ((null != nameList) && (nameList.size() > 0)) {
				resultMap.put("code", "0000");
				resultMap.put("msg", "上传成功");
			} else {
				resultMap.put("code", "0001");
				resultMap.put("msg", "上传失败");
			}
		} catch (Exception e) {
			resultMap.put("code", "1111");
			resultMap.put("msg", "上传失败");
		}

		return resultMap;
	}
}
