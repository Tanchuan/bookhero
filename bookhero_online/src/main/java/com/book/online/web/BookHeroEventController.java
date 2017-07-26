package com.book.online.web;

import com.book.core.model.BookHeroEvent;
import com.book.core.model.result.ResultData;
import com.book.core.service.BookHeroEventService;
import com.book.core.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class BookHeroEventController {
    
    @Resource
    private BookHeroEventService bookHeroEventService;

    /**
     * 创建添加活动页面
     * @return
     */
    @RequestMapping(value={"/web/bookhero/event/create.html"}, method= RequestMethod.GET)
    public String createPage(HttpServletRequest req, ModelMap model) {
        final String apiName = "活动添加页面";
        final String ftlName = "";

        return ftlName;
    }

    @RequestMapping(value={"/web/bookhero/event/create"}, method= RequestMethod.POST)
    @ResponseBody
    public ResultData<Map<String, Object>> create(HttpServletRequest req, ModelMap model) {
        final String apiName = "活动添加接口";
        final String ftlName = "";

        return null;
    }


}
