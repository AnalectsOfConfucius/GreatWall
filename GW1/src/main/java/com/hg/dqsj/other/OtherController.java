package com.hg.dqsj.other;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by StoryInStone on 2017/1/6.
 */
@Controller
@RequestMapping(value = "/other")
public class OtherController {

    @RequestMapping(value = "/jqyx", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, ModelMap model) {
        return "jqyx/jingquyingxiao";
    }

    @RequestMapping(value = "/tdyd", method = RequestMethod.GET)
    public String tdyd(HttpServletRequest request, ModelMap model) {
        return "tdyd/tuanduiyuding";
    }


}
