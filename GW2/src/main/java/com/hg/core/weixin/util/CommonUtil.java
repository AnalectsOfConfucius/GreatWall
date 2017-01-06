package com.hg.core.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hg.core.weixin.entity.JsapiTicket;
import com.hg.core.weixin.entity.Token;

//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;


/**
 * 通用工具类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class CommonUtil {

//	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
//
//	// // 获取access_token的接口地址（GET） 限200（次/天）
//	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//	// 刷新access_token
//	public final static String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
//
//	// 菜单创建（POST） 限100（次/天）
//	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
//
//	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
//
//	/**
//	 * 发起https请求并获取结果
//	 *
//	 * @param requestUrl 请求地址
//	 * @param requestMethod 请求方式（GET、POST）
//	 * @param outputStr 提交的数据
//	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
//	 */
//	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
//		HttpsURLConnection httpsUrlConn = null;
//		InputStream inputStream = null;
//		InputStreamReader inputStreamReader = null;
//		BufferedReader bufferedReader = null;
//
//		JSONObject jsonObject = null;
//		StringBuffer buffer = new StringBuffer();
//		try {
//			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm = { new MyX509TrustManager() };
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//			URL url = new URL(requestUrl);
//			httpsUrlConn = (HttpsURLConnection) url.openConnection();
//			httpsUrlConn.setSSLSocketFactory(ssf);
//
//			httpsUrlConn.setDoOutput(true);
//			httpsUrlConn.setDoInput(true);
//			httpsUrlConn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			httpsUrlConn.setRequestMethod(requestMethod);
//
//			if ("GET".equalsIgnoreCase(requestMethod)) {
//				httpsUrlConn.connect();
//			}
//
//			// 当有数据需要提交时
//			if (null != outputStr) {
//				OutputStream outputStream = httpsUrlConn.getOutputStream();
//				// 注意编码格式，防止中文乱码
//				outputStream.write(outputStr.getBytes("UTF-8"));
//				outputStream.close();
//			}
//
//			// 将返回的输入流转换成字符串
//			inputStream = httpsUrlConn.getInputStream();
//			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			bufferedReader = new BufferedReader(inputStreamReader);
//
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//
//			jsonObject = JSONObject.fromObject(buffer.toString());
//		} catch (ConnectException ce) {
//			log.error("连接超时：" + ce.getMessage());
//		} catch (Exception e) {
//			log.error("https请求异常：" + e.getMessage());
//		} finally {
//			// 释放资源
//			if (null != bufferedReader) {
//				try {
//					bufferedReader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (null != inputStreamReader) {
//				try {
//					inputStreamReader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (null != inputStream) {
//				try {
//					inputStream.close();
//					inputStream = null;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			if (null != httpsUrlConn) {
//				httpsUrlConn.disconnect();
//			}
//		}
//		return jsonObject;
//	}
//
//	/**
//	 * 获取接口访问凭证
//	 *
//	 * @param appid 凭证
//	 * @param appsecret 密钥
//	 * @return
//	 */
//	public static Token getAccessToken(String appid, String appsecret) {
//		Token token = null;
//		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
//		// 发起GET请求获取凭证
//		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
//
//		if (null != jsonObject) {
//			try {
//				token = new Token();
//				token.setAccessToken(jsonObject.getString("access_token"));
//				token.setExpiresIn(jsonObject.getInt("expires_in"));
//			} catch (JSONException e) {
//				token = null;
//				// 获取token失败
//				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
//						jsonObject.getString("errmsg"));
//			}
//		}
//		return token;
//	}
//
//	/**
//	 * 获取接口访问凭证
//	 *
//	 * @param appid 凭证
//	 * @param appsecret 密钥
//	 * @return
//	 */
//	public static Token getRefreshToken(String appid, String refreshToken) {
//		Token token = null;
//		String requestUrl = refresh_token_url.replace("APPID", appid).replace("REFRESH_TOKEN", refreshToken);
//		// 发起GET请求获取凭证
//		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
//
//		if (null != jsonObject) {
//			try {
//				token = new Token();
//				token.setAccessToken(jsonObject.getString("access_token"));
//				token.setExpiresIn(jsonObject.getInt("expires_in"));
//				token.setRefreshToken(jsonObject.getString("refresh_token"));
//				token.setOpenId(jsonObject.getString("openid"));
//				token.setScope(jsonObject.getString("scope"));
//			} catch (JSONException e) {
//				token = null;
//				// 获取token失败
//				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
//						jsonObject.getString("errmsg"));
//			}
//		}
//		return token;
//	}
//
//	/**
//	 * 获取调用微信JS接口的临时票据
//	 *
//	 * @return
//	 */
//	public static JsapiTicket getJsapiTicket(String accessToken) {
//		JsapiTicket jsapiTicket = null;
//		System.out.println(">>>getJsapiTicket start");
//		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
//		System.out.println(">>>getJsapiTicket end");
//		// 发起GET请求获取凭证
//		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
//
//		if (null != jsonObject) {
//			try {
//				if (0 == jsonObject.getInt("errcode")) {
//					jsapiTicket = new JsapiTicket();
//					jsapiTicket.setErrcode("0");
//					jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
//					jsapiTicket.setTicket(jsonObject.getString("ticket"));
//					jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
//				}
//				System.out.println("获取jsapi_ticket成功 errcode:" + jsonObject.getInt("errcode") + " errmsg:{}" + jsonObject.getString("errmsg"));
//			} catch (JSONException e) {
//				jsapiTicket = null;
//				System.out.println("获取jsapi_ticket失败 errcode:" + jsonObject.getInt("errcode") + " errmsg:{}" + jsonObject.getString("errmsg"));
//				// 获取jsapi_ticket失败
//				log.error("获取jsapi_ticket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
//						jsonObject.getString("errmsg"));
//			}
//		} else {
//			System.out.println(">>>jsonObject is null");
//		}
//		return jsapiTicket;
//	}
//
//	/**
//	 * URL编码（utf-8）
//	 *
//	 * @param source
//	 * @return
//	 */
//	public static String urlEncodeUTF8(String source) {
//		String result = source;
//		try {
//			result = java.net.URLEncoder.encode(source, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 根据内容类型判断文件扩展名
//	 *
//	 * @param contentType 内容类型
//	 * @return
//	 */
//	public static String getFileExt(String contentType) {
//		String fileExt = "";
//		if ("image/jpeg".equals(contentType) || contentType.contains("image/jpeg;")) {
//			fileExt = ".jpg";
//		} else if ("audio/mpeg".equals(contentType) || contentType.contains("audio/mpeg;")) {
//			fileExt = ".mp3";
//		} else if ("audio/amr".equals(contentType) || contentType.contains("audio/amr;")) {
//			fileExt = ".amr";
//		} else if ("video/mp4".equals(contentType) || contentType.contains("video/mp4;")) {
//			fileExt = ".mp4";
//		} else if ("video/mpeg4".equals(contentType) || contentType.contains("video/mpeg4;")) {
//			fileExt = ".mp4";
//		}
//		return fileExt;
//	}
//
//	/**
//	 * 编码
//	 *
//	 * @param bstr
//	 * @return String
//	 */
//	public static String encode(byte[] bstr) {
////		return new sun.misc.BASE64Encoder().encode(bstr);
//		return new Base64().encodeToString(bstr);
//	}
//
//	/**
//	 * 解码
//	 *
//	 * @param str
//	 * @return string
//	 */
//	public static byte[] decode(String str) {
//
//		byte[] bt = null;
////		try {
////			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
////			bt = decoder.decodeBuffer(str);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		try {
//			bt = new Base64().decode(str);
//		} catch (Exception e) {
//			log.error("解码出错：" + e.getMessage());
//		}
//		return bt;
//
//	}
//
//	public static void main(String args[]) {
//		// Map<String, Object> test = new HashMap<String, Object>();
//		// test.put("111", "111");
//		// test.put("222", "222");
//		// test.put("333", "333");
//		// Map<String, String> filter = new HashMap<String, String>();
//		// filter.put("group_id", "wwww");
//		//
//		// test.put("filter", filter);
//		// JSONObject jsonMenu = JSONObject.fromObject(test);
//		// System.out.println("jsonMenu: " + jsonMenu.toString());
//
//		System.out.println(urlEncodeUTF8("http://v.duomi.me/hangguan/oauth2"));
//
//	}
}