package com.hg.dqsj.love.center.eval.web;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.core.util.UUIdUtil;
import com.hg.dqsj.base.entity.Auth;
import com.hg.dqsj.love.center.eval.entity.Eval;
import com.hg.dqsj.love.center.eval.entity.EvalPic;
import com.hg.dqsj.love.center.eval.service.EvalService;
import com.hg.dqsj.love.center.order.entity.OfOrder;
import com.hg.dqsj.love.center.order.service.OfOrderGoodsService;
import com.hg.dqsj.love.center.order.service.OfOrderService;
import com.hg.dqsj.love.center.order.view.VOfOrderGoods;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
@RequestMapping(value = "eval")
public class EvalController {

    public static final String messageMenuId = "";

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private EvalService evalService;
    @Autowired
    private OfOrderService ofOrderService;
    @Autowired
    private OfOrderGoodsService ofOrderGoodsService;

    @RequestMapping(value = "")
    public String selectList(HttpServletRequest request,
                                 HttpServletResponse reponse, ModelMap model) {
        try {
            Auth auth = (Auth) sessionProvider.getAttribute(request,
                    SessionValidFilter.AUTH_KEY);
            String userId = auth.getUserId();
            // 处理逻辑
            List<Eval> evals = this.evalService.selectByUserId(userId);
            model.put("evals", evals);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "love/center/eval/my-comment";
    }

    @RequestMapping(value = "order")
    public String enter(HttpServletRequest request,
                        HttpServletResponse reponse, ModelMap model) {
        try {
            String orderId = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "orderId"));
            Auth auth = (Auth) sessionProvider.getAttribute(request,
                    SessionValidFilter.AUTH_KEY);
            String userId = auth.getUserId();
            // 处理逻辑
            OfOrder ofOrder = ofOrderService.selectById(orderId);
            model.put("ofOrder", ofOrder);
            int count = this.evalService.selectByOrderId(orderId);
            if (count > 0) {
//                return "";
            }
            if (ofOrder.getOrderTypeCode().equals("5")) {
                return "love/center/eval/en-food";
            } else if (ofOrder.getOrderTypeCode().equals("4")) {
                List<VOfOrderGoods> ofOrderGoods = ofOrderGoodsService.selectGoodsByOrderId(orderId);
                model.put("ofOrderGoods", ofOrderGoods);
                return "love/center/eval/en-order";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }


    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request, ModelMap model) {

        Map<String, Object> result = new HashMap<>();
        result.put("isError", "1");

        try {
            String orderId = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "orderId"));
            String evalTotalScore = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "evalTotalScore"));
            String serviceTotalScore = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "serviceTotalScore"));
            String tasteTotalScore = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "tasteTotalScore"));
            String environmentTotalScore = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "environmentTotalScore"));
            String evalComment = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "evalComment"));
            String goodsId = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "goodsId"));
            String storeId = StringUtil.trimBlank(RequestUtils
                    .getQueryParam(request, "storeId"));

            Auth auth = (Auth) sessionProvider.getAttribute(request,
                    SessionValidFilter.AUTH_KEY);
            String userId = auth.getUserId();

            ArrayList<String> picUrls = new ArrayList<String>();

//            if (files != null) {
//                for (int i = 0; i < files.length; i++) {
//                    picUrls.add(uploadFile(files[i], request, userId));
//                }
//            }

            if (evalTotalScore == null || evalTotalScore.trim().equals("")) {
                result.put("msg", "非法请求，总评分输入非法");
            } else {
                // 处理逻辑
                OfOrder ofOrder = ofOrderService.selectById(orderId);
                if (ofOrder.getOrderTypeCode().equals("5")) {
                    String dateStr = DateUtil.getFullTime();
                    Eval eval = new Eval();
                    eval.setId(UUIdUtil.getUUID());
                    eval.setStoreId(storeId);
                    eval.setEvalUserId(userId);
                    eval.setEvalComment(evalComment);
                    eval.setEnvironmentTotalScore(Integer.valueOf(environmentTotalScore));
                    eval.setOrderId(orderId);
                    eval.setEvalTotalScore(Double.valueOf(evalTotalScore));
                    eval.setServiceTotalScore(Integer.valueOf(serviceTotalScore));
                    eval.setTasteTotalScore(Integer.valueOf(tasteTotalScore));
                    eval.setUpdateDate(dateStr);
                    eval.setUpdateUserId(userId);
                    eval.setDeleteFlag("0");
                    eval.setCreateUserId(userId);
                    eval.setCreateDate(dateStr);
//                List<EvalPic> evalPics = new ArrayList<>();
//                for (int i = 0; i < picUrls.size(); i++) {
//                    EvalPic evalPic = new EvalPic();
//                    evalPic.setId(UUIdUtil.getUUID());
//                    evalPic.setPicUrl(picUrls.get(i));
//                    evalPic.setUpdateDate(dateStr);
//                    evalPic.setUpdateUserId(userId);
//                    evalPic.setEvalId(eval.getId());
//                    evalPic.setDeleteFlag("0");
//                    evalPic.setCreateUserId(userId);
//                    evalPic.setCreateDate(dateStr);
//                    evalPics.add(evalPic);
//                }
//                eval.setEvalPics(evalPics);
                    this.evalService.save(eval);
                } else if (ofOrder.getOrderTypeCode().equals("4")) {
                    String dateStr = DateUtil.getFullTime();
                    Eval eval = new Eval();
                    eval.setGoodsId(goodsId);
                    eval.setId(UUIdUtil.getUUID());
                    eval.setEvalUserId(userId);
                    eval.setOrderId(orderId);
                    eval.setEvalTotalScore(Double.valueOf(evalTotalScore));
                    eval.setUpdateDate(dateStr);
                    eval.setUpdateUserId(userId);
                    eval.setDeleteFlag("0");
                    eval.setCreateUserId(userId);
                    eval.setCreateDate(dateStr);
                    List<EvalPic> evalPics = new ArrayList<>();
                /*for (int i = 0; i < picUrls.size(); i++) {
                    EvalPic evalPic = new EvalPic();
                    evalPic.setId(UUIdUtil.getUUID());
                    evalPic.setPicUrl(picUrls.get(i));
                    evalPic.setUpdateDate(dateStr);
                    evalPic.setUpdateUserId(userId);
                    evalPic.setEvalId(eval.getId());
                    evalPic.setDeleteFlag("0");
                    evalPic.setCreateUserId(userId);
                    evalPic.setCreateDate(dateStr);
                    evalPics.add(evalPic);
                }
                eval.setEvalPics(evalPics);*/
                    this.evalService.save(eval);
                }
                result.put("isError", "0");
                result.put("msg", "评价成功!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    protected String uploadFile(MultipartFile file, HttpServletRequest request, String userId) {
        Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        // 设置存储的相对目录
        String path = new StringBuffer("").append("/static/upload/").append(userId).append("/").append(year).append("/").append(month).append("/").toString();
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

}
