package com.book.core.dao.impl;

import com.book.core.dao.BookHeroEventDao;
import com.book.core.model.BookHeroEvent;
import com.book.core.mybatis.PaginationSqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Author:bjtanchuan
 */
@Repository
public class BookHeroEventDaoImpl extends PaginationSqlSessionDaoSupport<BookHeroEvent> implements BookHeroEventDao {

}
