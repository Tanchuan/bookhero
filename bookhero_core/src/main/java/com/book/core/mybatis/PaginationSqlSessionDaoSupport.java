package com.book.core.mybatis;

import com.book.core.dao.PaginationCrudDao;
import com.book.core.dao.pagination.PaginationInfo;
import com.book.core.dao.pagination.PaginationList;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationSqlSessionDaoSupport<Domain> extends SqlSessionDaoSupport
        implements PaginationCrudDao<Domain> {
    
    Class<?> cls ;
    String selectMapperId ;
    String insertMapperId ;
    String countMapperId ;
    String updateMapperId ;
    String deleteMapperId ;
    String nameSpace;

    public PaginationSqlSessionDaoSupport() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type)
                    .getActualTypeArguments();
            this.cls = (Class<?>) parameterizedType[0];
            this.selectMapperId = cls.getSimpleName() + ".selectByCond";
            this.insertMapperId = cls.getSimpleName() + ".insert";
            this.countMapperId  = cls.getSimpleName() + ".countByCond";
            this.updateMapperId = cls.getSimpleName() + ".updateByCond";
            this.deleteMapperId = cls.getSimpleName() + ".deleteByCond";
            this.nameSpace = cls.getSimpleName();
        }
    }

    @Override
    public PaginationList<Domain> pageSelectByCond(Map<String, Object> cond,
                                                   PaginationInfo paginationInfo) {
        PaginationList<Domain> paginationList = new PaginationList<Domain>();
        Assert.notNull(cond, "cond should not be a map rather than NULL");
        
        
        List<Domain> result = null;
      if (null != paginationInfo) {
          int pageNo = paginationInfo.getCurrentPage();
          pageNo = pageNo <= 0 ? 1 : pageNo;
          int pageSize = paginationInfo.getRecordPerPage();
          int totalCount = countByCond(cond);
          
          if(totalCount == 0) {
              // 不再查了，直接返回
              paginationInfo.setTotalPage(0);
              paginationInfo.setTotalRecord(0);
              paginationList.setPaginationInfo(paginationInfo);
              return paginationList;
          }
          
          int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);
          paginationInfo.setTotalPage(totalPage);
          paginationInfo.setTotalRecord(totalCount);
          pageNo = pageNo <= totalPage ? pageNo : totalPage;
          int skipResults = (pageNo - 1) * pageSize;
          skipResults = skipResults >= 0 ? skipResults : 0;
          
          cond.put("paginationInfo", paginationInfo);
          
          result = selectByCond(cond);
          
          cond.remove("paginationInfo");
          
      } else {
          
          result = selectByCond(cond);
          
          paginationInfo = new PaginationInfo();
          paginationInfo.setCurrentPage(1);
          paginationInfo.setRecordPerPage(result.size());
          paginationInfo.setTotalPage(1);
          paginationInfo.setTotalRecord(result.size());
      }
      
      paginationList.addAll(result);
      paginationList.setPaginationInfo(paginationInfo);
      return paginationList;
    }

    @Override
    public int insert(Domain domain) {
        if (domain == null) {
            return 0;
        }
        return getSqlSession().insert(cls.getSimpleName() + ".insert", domain);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Domain> selectByCond(Map<String, Object> cond) {
        List<Domain> list = new ArrayList<Domain>();
        if (cond != null) {
            Map<String, Object> input = new HashMap<String, Object>();
            input.put("cond", cond);
            list = (List<Domain>)getSqlSession().selectList(selectMapperId, input);
        }
        return list;
    }

    @Override
    public int countByCond(Map<String, Object> cond) {
        if (cond == null) {
            return 0;
        }
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("cond", cond);
        Integer count = (Integer)getSqlSession().selectOne(countMapperId, input);
        return (null == count) ? 0 : count;
    }

    @Override
    public int updateByCond(Map<String, Object> updateValue, Map<String, Object> cond) {
        if(updateValue == null || updateValue.isEmpty() || cond == null || cond.isEmpty()) {
            return 0;
        }
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("updateValue", updateValue);
        input.put("cond", cond);
        return getSqlSession().update(updateMapperId, input);
    }

    @Override
    public int deleteByCond(Map<String, Object> cond) {
        if (cond == null || cond.isEmpty()) {
            return 0;
        }
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("cond", cond);
        return getSqlSession().delete(deleteMapperId, input);
    }

    public String getNameSpace() {
        return nameSpace;
    }
}
