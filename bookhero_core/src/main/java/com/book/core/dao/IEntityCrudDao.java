package com.book.core.dao;


import com.book.core.dao.pagination.PaginationInfo;
import com.book.core.dao.pagination.PaginationList;

import java.util.List;
import java.util.Map;

public interface IEntityCrudDao<Entity> {

    public boolean insertEntity(Entity entity);

    public boolean deleteEntity(Entity entity);

    public boolean updateEntity(Map<String, Object> condition, Map<String, Object> updateValue);

    public List<Entity> selectEntityByCond(Map<String, Object> condtion);

    public int selectTotalEntityByCond(Map<String, Object> condtion);

    public PaginationList<Entity> selectEntityListByCond(Map<String, Object> cond, PaginationInfo paginationInfo);

    public long querySeqId();

    public String querySeqId(String prefix);
}
