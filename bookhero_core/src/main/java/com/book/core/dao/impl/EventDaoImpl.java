package com.book.core.dao.impl;

import com.book.core.dao.EventDao;
import com.book.core.model.Event;
import com.book.core.mybatis.PaginationSqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Author:bjtanchuan
 */
@Repository
public class EventDaoImpl extends PaginationSqlSessionDaoSupport<Event> implements EventDao {

}
