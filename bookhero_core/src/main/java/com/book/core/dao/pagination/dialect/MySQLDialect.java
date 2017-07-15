package com.book.core.dao.pagination.dialect;

public class MySQLDialect implements Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    public String getLimitString(String sql, boolean hasOffset) {
        return new StringBuffer(sql.length() + 20).append(trim(sql))
                                                  .append(hasOffset ? " limit ?,?" : " limit ?")
                                                  .append(SQL_END_DELIMITER).toString();
    }

    public String getLimitString(String sql, int offset, int limit) {
        sql = trim(sql);
        StringBuffer sb = new StringBuffer(sql.length() + 20);
        sb.append(sql);
        if (offset > 0) {
            sb.append(" limit ")
              .append(offset)
              .append(',')
              .append(limit)
              .append(SQL_END_DELIMITER);
        } else {
            sb.append(" limit ")
              .append(limit)
              .append(SQL_END_DELIMITER);
        }
        return sb.toString();
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getCountString(String sql) {
        String resultSql = "select count(1) from(" + sql;
        if (resultSql.toLowerCase().lastIndexOf("order by") != -1) {
            resultSql = resultSql.substring(0, resultSql.toLowerCase()
                    .lastIndexOf("order by"));
        }
        resultSql += ") as total";
        return resultSql;
    }

    private String trim(String sql) {
        sql = sql.trim();
        if (sql.endsWith(SQL_END_DELIMITER)) {
            sql = sql.substring(0,sql.length() - 1 - SQL_END_DELIMITER.length());
        }
        return sql;
    }

    public boolean supportsLimitOffset() {
        return false;
    }

}
