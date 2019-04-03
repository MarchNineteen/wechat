package com.wyb.demo.bean.entity;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


/**
 * Created by Kunzite on 2016/9/14.
 */
public class WeixinBasicEntity implements Serializable {
    private static final long serialVersionUID = -1684424864037105661L;

    private String errmsg;
    private String errcode;
    private boolean success;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public boolean isSuccess() {
        success = true;
        if (StringUtils.isNoneBlank(this.errcode) && StringUtils.isNoneBlank(this.errmsg)) {
            success = false;
        }
        return success;
    }

}
