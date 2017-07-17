package com.book.core.dao;


import com.book.core.dao.pagination.PaginationInfo;
import com.book.core.dao.pagination.PaginationList;

import java.util.Map;

/**
 * 分页查询接口
 * @author bjtanchuan
 *
 * @param <Domain> 库表对应实体类
 */
public interface PaginationCrudDao<Domain> extends CrudDao<Domain>{

    public PaginationList<Domain> pageSelectByCond(Map<String, Object> cond, PaginationInfo paginationInfo);

}
