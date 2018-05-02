package com.xa3ti.blackcat.remote.vo;

import java.io.Serializable;

/**
 * Created by jackson.liu on 2017/2/23.
 */
public class ValidataVo implements Serializable {
    private String errCode;
    private String message;
    private Object info;

    public ValidataVo(String errCode, String message, Object info) {
        this.errCode = errCode;
        this.message = message;
        this.info = info;
    }
    public ValidataVo() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
