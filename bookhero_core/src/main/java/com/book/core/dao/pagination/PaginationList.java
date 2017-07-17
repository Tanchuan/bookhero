package com.book.core.dao.pagination;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * <p>
 * Title: 带分页信息的List实现类，作为分页结果返回
 * </p>
 *
 * 
 * @author bjtanchuan
 * @version 1.0
 */

public class PaginationList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 8566365879882741188L;

    private PaginationInfo paginationInfo = null;
    
	public PaginationList() {
        super();
    }

    public PaginationList(Collection<? extends T> c) {
        super(c);
    }

    public PaginationList(int initialCapacity) {
        super(initialCapacity);
    }
    
	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public Integer getCurrentPage() {
		return this.paginationInfo.getCurrentPage();
	}

	public Integer getRecordPerPage() {
		return this.paginationInfo.getRecordPerPage();
	}

	public Integer getTotalPage() {
		return this.paginationInfo.getTotalPage();
	}

	public Integer getTotalRecord() {
		return this.paginationInfo.getTotalRecord();
	}

    @Override
    public String toString() {
        return "PaginationList [paginationInfo=" + paginationInfo + ", list=" + super.toString() + "]";
    }

}