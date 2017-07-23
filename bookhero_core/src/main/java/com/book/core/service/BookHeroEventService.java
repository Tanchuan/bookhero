package com.book.core.service;

import com.book.core.model.BookHeroEvent;

import java.util.Date;
import java.util.List;

/**
 * 读书会活动服务
 */
public interface BookHeroEventService {

    List<BookHeroEvent> getBookHeroEventBy(Date begin, Date end, String orderBy);

}
