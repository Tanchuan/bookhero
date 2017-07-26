package com.book.core.model.result;

import java.io.Serializable;

public class ResultData<T> extends ResultInfo implements Serializable {

    private static final long serialVersionUID = 6300337442870893234L;
    protected T data = null;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultData() {
    }

    public ResultData(int retcode, String retdesc) {
        super(retcode, retdesc);
    }
    
    public ResultData(ResultInfo info){
    	super(info.getRetcode(),info.getRetdesc());
    }

    public ResultData(int retcode, String retdesc, T data) {
        super(retcode, retdesc);
        this.data = data;
    }
    
    public ResultData(ResultInfo info, T data) {
    	super(info.getRetcode(), info.getRetdesc());
        this.data = data;
    } 

	@Override
	public String toString() {
		return "ResultData [data=" + data + "]";
	}
    
}
