package com.book.core.model.result;


import com.book.core.common.CommonConstant;

import java.io.Serializable;

public class ResultInfo implements Serializable{
	
	private static final long serialVersionUID = 8036561402266912292L;

    /* 四个够多了，别的自己new吧 */
    public static class CONSTANT {
        public static final ResultInfo OK = new ResultInfo(
                CommonConstant.OK_CODE, CommonConstant.OK_DESC);
        public static final ResultInfo PARAM_ERR = new ResultInfo(
                CommonConstant.PARAM_ERROR_CODE, CommonConstant.PARAM_ERROR_DESC);
        public static final ResultInfo NO_LOGIN = new ResultInfo(
                CommonConstant.USER_NOT_LOGIN_CODE, CommonConstant.USER_NOT_LOGIN_DESC);
        public static final ResultInfo SYS_ERR = new ResultInfo(
                CommonConstant.SYS_ERROR_CODE, CommonConstant.SYS_ERROR_DESC);
    }

	private int retcode;
	
	private String retdesc = "";
	
	public ResultInfo() {

	}
	
	public ResultInfo(int retcode, String retdesc) {

		this.retcode = retcode;
		this.retdesc = retdesc;
	}
	
	public int getRetcode() {

		return this.retcode;
	}
	
	public void setRetcode(int retcode) {

		this.retcode = retcode;
	}

	public String getRetdesc() {

		return this.retdesc;
	}

	public void setRetdesc(String retdesc) {

		this.retdesc = retdesc;
	}	
}