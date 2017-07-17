package com.book.core.dao;

import java.util.List;
import java.util.Map;

/**
 * crud接口
 * @author bjtanchuan
 *
 * @param <Domain> 库表对应实体类
 */
public interface CrudDao<Domain> {

    public int insert(Domain domain);

    public List<Domain> selectByCond(Map<String, Object> cond);

    public int countByCond(Map<String, Object> cond);

    public int updateByCond(Map<String, Object> updateValue, Map<String, Object> cond);

    public int deleteByCond(Map<String, Object> cond);
}
