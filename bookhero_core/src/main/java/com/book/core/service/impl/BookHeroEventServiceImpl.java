package com.book.core.service.impl;

import com.book.core.dao.BookHeroEventDao;
import com.book.core.model.BookHeroEvent;
import com.book.core.service.BookHeroEventService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookHeroEventServiceImpl implements BookHeroEventService {

    @Resource
    private BookHeroEventDao bookHeroEventDao;

    @Override
    public List<BookHeroEvent> getBookHeroEventBy(Date begin, Date end, String orderBy) {
        if (null != begin && null != end) {
            Map<String, Object> cond = Maps.newHashMap();
            cond.put("startTimeLower", begin);
            cond.put("startTimeUpper", end);
            if(!Strings.isNullOrEmpty(orderBy)) {
                cond.put("orderBy", orderBy);
            }
            return bookHeroEventDao.selectByCond(cond);
        }
        return null;
    }

    @Override
    public boolean insert(BookHeroEvent event) {
        if(null != event){
            return bookHeroEventDao.insert(event) > 0;
        } else {
            return false;
        }
    }
}
