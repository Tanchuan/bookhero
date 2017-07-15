package com.book.core.dao.pagination;

import com.book.core.dao.pagination.dialect.Dialect;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 基金系统的分页拦截器<p>
 * 
 * 原来的拦截器有性能问题，重新写一个<br/>
 * 将 condition参数多包了一层map，不能适应autoMapper生成对象及AbstractCrudDao的实现类
 * @author Zhaolinq
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {
        Connection.class }) })
public class PaginationInterceptor implements Interceptor {
    private Dialect dialect = null;

    private String paginationSqlRegEx = ".*[sS]electByCond";

    public Object intercept(Invocation invocation) throws Throwable {
        //    	this.debug("intercept");

        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
                    .getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
                    .getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper
                    .getValueByFieldName(delegate, "mappedStatement");

            //            this.debug("stat id : " + mappedStatement.getId());

            if (mappedStatement.getId().matches(paginationSqlRegEx)) {
                //            	this.debug("matches");

                BoundSql boundSql = delegate.getBoundSql();
                String sql = boundSql.getSql();
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject != null) {

                    PaginationInfo paginationInfo = null;
                    if (parameterObject instanceof Map<?, ?>) {
                        Object cond = ((Map<?, ?>) parameterObject).get("cond");
                        if(cond != null && cond instanceof Map<?, ?>) {
                            paginationInfo = (PaginationInfo) ((Map<?, ?>)cond).get("paginationInfo");
                        }
                    }
                    else {
                        Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,
                                "paginationInfo");
                        if (pageField != null) {
                            paginationInfo = (PaginationInfo) ReflectHelper
                                    .getValueByFieldName(parameterObject, "paginationInfo");
                        }
                    }

                    if (paginationInfo != null) {

                        String paginationSql = this.dialect.getLimitString(sql,
                                paginationInfo.getOffset(), paginationInfo.getLimit());

                        ReflectHelper.setValueByFieldName(boundSql, "sql", paginationSql);
                    }
                }
            }
        }
        return invocation.proceed();
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
    }

    protected void debug(String msg) {
//        LogUtil.debug(this.getClass().getName(), msg);
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getPaginationSqlRegEx() {
        return paginationSqlRegEx;
    }

    public void setPaginationSqlRegEx(String paginationSqlRegEx) {
        this.paginationSqlRegEx = paginationSqlRegEx;
    }

    /**
     * 
     * <p>Title: 反射工具类</p> 
     * 
     * <p>Copyright: Copyright (c) 2011</p> 
     * 
     * <p>Company: www.netease.com</p>
     * 
     * @author 	Barney Woo
     * @date 	2011-11-22
     * @version 1.0
     */

    static class ReflectHelper {
        public static Field getFieldByFieldName(Object obj, String fieldName) {
            for (Class<?> superClass = obj
                    .getClass(); superClass != Object.class; superClass = superClass
                            .getSuperclass()) {
                try {
                    return superClass.getDeclaredField(fieldName);
                }
                catch (NoSuchFieldException e) {
                }
            }
            return null;
        }

        public static Object getValueByFieldName(Object obj, String fieldName)
                throws SecurityException, NoSuchFieldException, IllegalArgumentException,
                IllegalAccessException {
            Field field = getFieldByFieldName(obj, fieldName);
            Object value = null;
            if (field != null) {
                if (field.isAccessible()) {
                    value = field.get(obj);
                }
                else {
                    field.setAccessible(true);
                    value = field.get(obj);
                    field.setAccessible(false);
                }
            }
            return value;
        }

        public static void setValueByFieldName(Object obj, String fieldName, Object value)
                throws SecurityException, NoSuchFieldException, IllegalArgumentException,
                IllegalAccessException {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field.isAccessible()) {
                field.set(obj, value);
            }
            else {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            }
        }
    }

}
