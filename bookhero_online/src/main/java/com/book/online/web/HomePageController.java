package com.book.online.web;

import com.alibaba.fastjson.JSONObject;
import com.book.core.dao.EventDao;
import com.book.core.model.Event;
import com.book.core.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class HomePageController {

    @Resource
    private EventDao eventDao;

    /**
     * web首页
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
            Map<String, Object> cond = Maps.newHashMap();
            cond.put("startTimeLower", todayStart);
            cond.put("startTimeUpper", lastDayOf4thWeek);
            cond.put("orderBy", "start_time");
            List<Event> events = eventDao.selectByCond(cond);
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
//        String result = "{'weeks':[{'days':[{'date':1500127539558,'events':[]},{'date':1500100200000,'events':[{'startTime':1500084000000,'endTime':1500093000000,'topic':'《笨办法学python》','place':'朝阳区国贸恒惠路文化体育广场胜利联盟（长方形低层建筑）一楼创客汇咖啡','url':'https://mp.weixin.qq.com/s?__biz=MzA3OTY5NDczOA==&mid=2651088875&idx=1&sn=227bf69556835a808b20a42ec4f61406&chksm=845f5916b328d000d2f34b8dd991d7d65d0ace2ab324e37679022f0f0e0e8e30b53e2c2425a1&mpshare=1&scene=1&srcid=0713gs3u7TmDP9roU8gynOlA&key=227c90f337be412abac6ea70d097a861e81e7e3fdda80ca116d50861d200650ee386efc6c3c33a813eb9a71d21b24ff9e38c8056d166ac31107329c63c7ea61165be84d4c9ded087337490203603f779&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'},{'startTime':1500100200000,'endTime':1500111000000,'topic':'《万历十五年》','place':'北京市朝阳区北四环中路6号华亭嘉园A-1F','url':'https://mp.weixin.qq.com/s?__biz=MjM5OTkwOTA2MQ==&mid=2651303780&idx=1&sn=ddd1ca3ec8d4af930579434c2ff72c19&chksm=bcc74cce8bb0c5d89315e05159562ec45d189187fcd04fa88fee51f929207ab958d9cde7e600&mpshare=1&scene=1&srcid=0713DXTndYdTL4PuvZj19aCG&key=889821a349a20841c29cd9405a02c94fde1b8f0cfbc60f8322ab59c14b7e134fafe61d265cd2c468f91efccc490c958791d76ae5eabf21911b328ad8930e5e03b2e43bf4357affd9a5b7a879e1607f31&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'}]},{'date':1500184800000,'events':[{'startTime':1500184800000,'endTime':1500195600000,'topic':'《政治哲学导论》','place':'706青年空间小会议室 （北京市海淀区五道口华清嘉园15号楼20层）','url':'https://mp.weixin.qq.com/s?__biz=MjM5MzAyMTgwNA==&mid=2650230185&idx=3&sn=3c88bbae5199d6791086d1e30fc2efae&chksm=be9e87aa89e90ebc67bf56288216f21d908967c9edff18a185b3e5a2ed1ac70a443c237d67fc&mpshare=1&scene=1&srcid=0713cQKhPbr9i0LTPvdAfzHE&key=baf732038d89126b9b3754266909ec723ad231694123f4edbd72d2265b6776ede180bcb0b53ac8ff28281f0d777db1d71722e5fa0d694d1ac8dedae376a32f2f5506222c13c8a0786d5f579180233cda&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'}]}]},{'days':[{'date':1500375600000,'events':[{'startTime':1500375600000,'endTime':1500382800000,'topic':'《非暴力沟通》','place':'北京海淀区海淀中街16号中关村公馆1层C101号（距海淀黄庄地铁约471米）微度咖啡','url':'http://www.hdb.com/party/mv39b.html?hdb_pos=search'}]},{'date':1500703200000,'events':[{'startTime':1500703200000,'endTime':1500714000000,'topic':'简·奥斯汀','place':'知更小院（北京市朝阳区新荣家园B座4H）','url':'https://mp.weixin.qq.com/s?__biz=MzAwNjY4MjA4Mg==&mid=2651640634&idx=2&sn=1505f228e858b4d99911aac669a3704c&chksm=80f1d7b7b7865ea135ee71e8ac2e1e4629dce369883c6d1802d8c27266cc0198ba2395a82c97&mpshare=1&scene=1&srcid=0713N1Fta05Na3pvnw0pE2u7&key=ae110083100918b9f8357bc7d560ab35eddce6b8736a4449fc032a9e51da8062b6c8ff6a90d9ec989f5dd82d9f51904ee45cb47113cd7165ba433b15eb3bda50945f717df76541cd0f000fbfe949777d&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'}]}]},{'days':[{'date':1501293600000,'events':[{'startTime':1501308000000,'endTime':1501315200000,'topic':'韦力（著名藏书家）','place':'@读易洞 书房（北京市朝阳区万科青青商业街）','url':'https://mp.weixin.qq.com/s?__biz=MjM5NDA0OTAzNA==&mid=2652217050&idx=1&sn=2bdd14b8809b82f600f020da2e7b3f77&chksm=bd6c83bf8a1b0aa933dec6fb6ec56d3e1588d4a0b1e505b5204a02d0711c50b716c2ade88e21&mpshare=1&scene=1&srcid=0713PJuOgJ5AZN8R5cpOeCSg&key=c969774f949c279b31e9c4f777355cb0358ab3e8ac4f9b8afc7f48ec64bf32d83664ece7f35904473f67fa937231f8ef5d76fc17aa2043e36733f2d5e396c6e62decf45ea96991eea3ae2e4d450b3dea&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'}]},{'date':1501380000000,'events':[{'startTime':1501380000000,'endTime':1501389000000,'topic':'《游戏设计艺术》','place':'海淀区五道口桥咖啡','url':'https://mp.weixin.qq.com/s?__biz=MzA3OTY5NDczOA==&mid=2651088878&idx=1&sn=971f2fac992cec8063ead4d754af9553&chksm=845f5913b328d0051e59f7f4e0c71a35653334280e6817723b6c914608a9434be72af45cb8a0&mpshare=1&scene=1&srcid=0713Vi2QSc3vgm3BpcBp1RI1&key=889821a349a208419251aafc5b59b593755528d82d51967ebfd5bd88d0207c48346d3eeea2feca458497b4306d4a3f0f4d0ff61bc886562dd8da01e1f97fc0a4e1220b296d347649596407eadb804f42&ascene=0&uin=MjQ2NTk4NjU1&devicetype=iMac+Macmini7%2C1+OSX+OSX+10.11.6+build(15G1510)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=RHdohYuCTSTGSCnkUVfE%2B0%2BjZF62pn1I9qOjRBNGWQ0%3D'}]}]}]}";
//        Object weeks = JSONObject.parse(result);
//        model.put("data", weeks);
        return "mobile/index/main";
    }

    private Map<String, Object> acquireEventsByTimeSpan(List<Event> events, Date start, Date end, int order) {

        Map<String, Object> result = Maps.newHashMap();
        result.put("order", order);
        if(null != events && !events.isEmpty() && null != start && null != end){
            List<Event> resultEvents = Lists.newArrayList();
            events.stream().forEach(e -> {
                if(!e.getStartTime().before(start) && !e.getStartTime().after(end)){
                    resultEvents.add(e);
                }
            });
            result.put("events", resultEvents);
        }
        return result;
    }

}
