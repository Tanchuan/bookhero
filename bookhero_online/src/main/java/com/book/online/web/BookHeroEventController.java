package com.book.online.web;

import com.book.core.model.BookHeroEvent;
import com.book.core.model.result.ResultData;
import com.book.core.model.result.ResultInfo;
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
    public ResultInfo create(HttpServletRequest req) {
        final String apiName = "活动添加接口";
        try {
            String title = req.getParameter("title");
            String startTimeStr = req.getParameter("startTime");//每期活动开始时间
            String endTimeStr = req.getParameter("endTime");//每期活动结束时间
            String startDateStr = req.getParameter("startDate");//活动持续期间起始日期
            String endDateStr = req.getParameter("endDate");//活动持续期间截止日期
            String address = req.getParameter("address");
            String eventUrl = req.getParameter("eventUrl");
            String note = req.getParameter("note");
            String club = req.getParameter("club");
            String img = req.getParameter("img");
            String logo = req.getParameter("logo");
            String repeatFrequencysStr = req.getParameter("repeatFrequency");

            Date startTime = DateUtils.formatToDate(startTimeStr, "yyyy-MM-dd hh:mm:ss");
            Date endTime = DateUtils.formatToDate(endTimeStr, "yyyy-MM-dd hh:mm:ss");
            BookHeroEvent event = new BookHeroEvent(title, startTime, endTime, address, eventUrl);
            if(bookHeroEventService.insert(event)){
                return ResultInfo.CONSTANT.OK;
            } else {
                //TODO 日志报错
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.CONSTANT.SYS_ERR;
    }


}
