package com.hg.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 基础控制器
 * 
 * @author
 * 
 */
@Controller
public class BaseController {
	protected static final String PAGE_SIZE = "5";
	protected static final String UPLOAD_ROOT_PATH = "/static/upload/";
	protected static final String DOWNLOAD_ROOT_PATH = "/static/download/";
	// protected static final String BASE_URL = "http://mall.71jewel.com/";

	/**
	 * Logger for Service class
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Long getCurrentUserId() {
		return 1l;
	}

	/**
	 * 上传文件-返回成功上传存储的文件名
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected String uploadFile(MultipartFile file, HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(getCurrentUserId()).append("/").append(year).append("/").append(month).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		if (StringUtils.isNotEmpty(fileName)) {
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			fileName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
			System.out.println(realPath);
			File targetFile = new File(realPath, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存文件
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new StringBuffer(path).append(fileName).toString();
	}

	/**
	 * 上传文件-返回成功上传存储的文件名
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected List<Map<String, String>> uploadFileList4Map(MultipartHttpServletRequest request) {

		MultiValueMap<String, MultipartFile> multiValueMap = request.getMultiFileMap();
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(getCurrentUserId()).append("/").append(year).append("/").append(month).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = "";
		List<Map<String, String>> nameList = null;
		if ((null != multiValueMap) && !multiValueMap.isEmpty()) {
			nameList = new ArrayList<Map<String, String>>();
			for (String key : multiValueMap.keySet()) {
				List<MultipartFile> fileList = multiValueMap.get(key);
				if ((null != fileList) && (fileList.size() > 0)) {
					for (int i = 0; i < fileList.size(); i++) {
						MultipartFile file = fileList.get(i);
						fileName = file.getOriginalFilename();
						if (StringUtils.isNotEmpty(fileName)) {
							String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
							String saveName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
							System.out.println(realPath);
							File targetFile = new File(realPath, saveName);
							if (!targetFile.exists()) {
								targetFile.mkdirs();
							}
							// 保存文件
							try {
								file.transferTo(targetFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							String storPath = ((new StringBuffer(path).append(saveName)).toString()).replace(File.separatorChar, '/');
							Map<String, String> nameMap = new HashMap<String, String>();
							nameMap.put("key_" + fileName.replace(".", "_"), storPath);

							nameList.add(nameMap);
						}
					}
				}
			}
		}
		return nameList;
	}

	/**
	 * 上传文件-返回成功上传存储的文件名
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected List<String> uploadFile(MultipartHttpServletRequest request) {

		MultiValueMap<String, MultipartFile> multiValueMap = request.getMultiFileMap();
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(getCurrentUserId()).append("/").append(year).append("/").append(month).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = "";
		List<String> nameList = null;
		if ((null != multiValueMap) && !multiValueMap.isEmpty()) {
			nameList = new ArrayList<String>();
			for (String key : multiValueMap.keySet()) {
				List<MultipartFile> fileList = multiValueMap.get(key);
				if ((null != fileList) && (fileList.size() > 0)) {
					for (int i = 0; i < fileList.size(); i++) {
						MultipartFile file = fileList.get(i);
						fileName = file.getOriginalFilename();
						if (StringUtils.isNotEmpty(fileName)) {
							String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
							String saveName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
							System.out.println(realPath);
							File targetFile = new File(realPath, saveName);
							if (!targetFile.exists()) {
								targetFile.mkdirs();
							}
							// 保存文件
							try {
								file.transferTo(targetFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							nameList.add(((new StringBuffer(path).append(saveName)).toString()).replace(File.separatorChar, '/'));
						}
					}
				}
			}
		}
		return nameList;
	}

	/**
	 * 上传文件-返回成功上传存储的File文件对象
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected File uploadFile2(MultipartFile file, HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(getCurrentUserId()).append("/").append(year).append("/").append(month).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		File targetFile = null;
		if (StringUtils.isNotEmpty(fileName)) {
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			fileName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
			System.out.println(realPath);
			targetFile = new File(realPath, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存文件
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return targetFile;
	}

	/**
	 * 上传文件-返回成功上传存储的文件名
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected List<String> uploadFile(MultipartHttpServletRequest request, int width, int height) {

		MultiValueMap<String, MultipartFile> multiValueMap = request.getMultiFileMap();
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(getCurrentUserId()).append("/").append(year).append("/").append(month).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = "";
		List<String> nameList = null;
		if ((null != multiValueMap) && !multiValueMap.isEmpty()) {
			nameList = new ArrayList<String>();
			for (String key : multiValueMap.keySet()) {
				List<MultipartFile> fileList = multiValueMap.get(key);
				if ((null != fileList) && (fileList.size() > 0)) {
					for (int i = 0; i < fileList.size(); i++) {
						MultipartFile file = fileList.get(i);
						fileName = file.getOriginalFilename();
						if (StringUtils.isNotEmpty(fileName)) {
							String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
							String saveName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
							String saveName_1 = new StringBuffer("").append(new Date().getTime()).append("_1").append(fileType).toString();
							System.out.println(realPath);
							File targetFile = new File(realPath, saveName);
							File targetFile_1 = new File(realPath, saveName_1);

							if (!targetFile.exists()) {
								targetFile.mkdirs();
							}
							// 保存文件
							try {
								file.transferTo(targetFile);
								// 复制
								FileInputStream fi = new FileInputStream(targetFile);
								FileOutputStream fo = new FileOutputStream(targetFile_1);
								FileChannel in = fi.getChannel();// 得到对应的文件通道
								FileChannel out = fo.getChannel();// 得到对应的文件通道
								in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
								fi.close();
								in.close();
								fo.close();
								out.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							nameList.add(((new StringBuffer(path).append(saveName_1)).toString()).replace(File.separatorChar, '/'));

							try {
								ScaleImageUtils.resize(width, height, realPath + "\\" + saveName_1, targetFile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return nameList;
	}

	protected boolean deleteFiles(List<String> filearr) {
		try {
			for (int i = 0; i < filearr.size(); i++) {
				File file = new File(filearr.get(i));
				file.delete();
				System.out.println(filearr.get(i) + "删除成功");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
