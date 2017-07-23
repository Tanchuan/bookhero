package com.book.online.web;

import com.book.core.dao.BookHeroEventDao;
import com.book.core.model.BookHeroEvent;
import com.book.core.service.BookHeroEventService;
import com.book.core.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class HomePageController {
    
    @Resource
    private BookHeroEventService bookHeroEventService;

    /**
     * web首页
     * 展示从今天起四周内的活动
     * @return
     */
    @RequestMapping(value={"/web/home/index.html","/"}, method= RequestMethod.GET)
    public String index(HttpServletRequest req, ModelMap model) {
        final String apiName = "主页接口";

        try {
            Date todayStart = DateUtils.getDateStart(new Date());
            Date todayEnd = DateUtils.addDays(new Date(todayStart.getTime() - 1), 1);
            Date lastDayOfThisWeek = DateUtils.getLastDayOfWeekCn(todayEnd);
            Date lastDayOf2ndWeek = DateUtils.getLastDayOfWeekCn(DateUtils.addDays(lastDayOfThisWeek, 1));
            Date lastDayOf3rdWeek = DateUtils.getLastDayOfWeekCn(DateUtils.addDays(lastDayOf2ndWeek, 1));
            Date lastDayOf4thWeek = DateUtils.getLastDayOfWeekCn(DateUtils.addDays(lastDayOf3rdWeek, 1));

            List<BookHeroEvent> events = bookHeroEventService.getBookHeroEventBy(todayStart,
                    lastDayOf4thWeek, "start_time");
            if(null != events && !events.isEmpty()){
                List<Map<String, Object>> weeks = Lists.newArrayList();
                weeks.add(acquireEventsByTimeSpan(events, todayStart, lastDayOfThisWeek, 0));
                weeks.add(acquireEventsByTimeSpan(events, lastDayOfThisWeek, lastDayOf2ndWeek, 1));
                weeks.add(acquireEventsByTimeSpan(events, lastDayOf2ndWeek, lastDayOf3rdWeek, 2));
                weeks.add(acquireEventsByTimeSpan(events, lastDayOf3rdWeek, lastDayOf4thWeek, 3));
                Map<String, Object> data =Maps.newHashMap();
                data.put("weeks", weeks);
                model.put("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mobile/index/main";
    }

    private List<Map> setDays(List<Map> days, List<BookHeroEvent> dayEvents) {
        Map<String, Object> day = Maps.newHashMap();
        day.put("startTime", DateUtils.getDateStart(dayEvents.get(0).getStartTime()));
        day.put("events", new ArrayList<>(dayEvents));
        days.add(day);
        dayEvents.clear();
        return days;
    }

    private List<Map> setWeeks(List<Map> weeks, List<Map> days) {
        Map<String, Object> week = Maps.newHashMap();
        Date weekStart = DateUtils.getDateStart((Date)days.get(0).get("startTime"), Calendar.WEEK_OF_MONTH);
        week.put("order", DateUtils.weeksNumFromNow(weekStart));
        week.put("startTime", weekStart);
        week.put("days", new ArrayList<>(days));
        weeks.add(week);
        days.clear();
        return weeks;
    }
    private Map<String, Object> acquireEventsByTimeSpan(List<BookHeroEvent> events, Date start, Date end, int order) {

        Map<String, Object> result = Maps.newHashMap();
        result.put("order", order);
        if(null != events && !events.isEmpty() && null != start && null != end){
            List<BookHeroEvent> resultEvents = Lists.newArrayList();
            events.stream().forEach(e -> {
                if(!e.getStartTime().before(start) && !e.getStartTime().after(end)){
                    resultEvents.add(e);
                }
            });
            result.put("days", classifyEventByDate(resultEvents));
        }
        return result;
    }

    private List<Map<String, Object>> classifyEventByDate(List<BookHeroEvent> events) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if(null != events && !events.isEmpty()){
            Map<Date, List<BookHeroEvent>> eventMap = Maps.newHashMap();
            //按日期归类成活动列表
            for(BookHeroEvent event : events){
                Date eventStartTime = DateUtils.getDateStart(event.getStartTime());
                List<BookHeroEvent> dailyEvents = eventMap.get(eventStartTime);
                if(null == dailyEvents){
                    dailyEvents = Lists.newArrayList();
                }
                dailyEvents.add(event);
                eventMap.put(eventStartTime, dailyEvents);
            }
            //装配成数据model
            for(Map.Entry<Date, List<BookHeroEvent>> entry : eventMap.entrySet()){
                Map<String, Object> dailyEventsMap = Maps.newHashMap();
                dailyEventsMap.put("date", entry.getKey());
                dailyEventsMap.put("events", entry.getValue());
                result.add(dailyEventsMap);
            }
            //排序
            result = result.stream().sorted((r1, r2) -> {
                if(r1 == null) {
                    return -1;
                }
                if(null == r2){
                    return 1;
                }
                Date date1 = (Date) r1.get("date");
                Date date2 = (Date) r2.get("date");
                return date1.compareTo(date2);
            }).collect(Collectors.toList());

        }
        return result;
    }

}
