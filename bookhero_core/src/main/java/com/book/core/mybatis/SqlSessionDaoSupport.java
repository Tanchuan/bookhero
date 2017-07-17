package com.book.core.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;

public abstract class SqlSessionDaoSupport extends DaoSupport {

    private SqlSession sqlSession;

    @Resource(name="bookheroSqlSessionFactory")
    public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
    }

    public final SqlSession getSqlSession() {
        return this.sqlSession;
    }

    /**
     * {@inheritDoc}
     */
    protected void checkDaoConfig() {
        Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }

}
