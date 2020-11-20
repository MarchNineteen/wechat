package com.wyb.common.exception;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.wyb.common.WxType;
import com.wyb.common.util.json.WxGsonBuilder;

/**
 * @author Kunzite
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/3/26 16:31 $$
 */
@Data
@Builder
public class WxError implements Serializable {
    private static final long serialVersionUID = 1306862281857558620L;

    /**
     * 微信错误代码.
     */
    private int errorCode;

    /**
     * 微信错误信息. （如果可以翻译为中文，就为中文）
     */
    private String errorMsg;

    /**
     * 微信接口返回的错误原始信息（英文）.
     */
    private String errorMsgEn;

    private String json;

    public static WxError fromJson(String json) {
        final WxError wxError = WxGsonBuilder.create().fromJson(json, WxError.class);

        final String msg = WxMpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
        if (msg != null) {
            wxError.setErrorMsg(msg);
        }

        return wxError;
    }

    public static WxError fromJson(String json, WxType type) {
        final WxError wxError = WxGsonBuilder.create().fromJson(json, WxError.class);
        if (StringUtils.isNotEmpty(wxError.getErrorMsg())) {
            wxError.setErrorMsgEn(wxError.getErrorMsg());
        }

        if (type == null) {
            return wxError;
        }

        if (type == WxType.MP) {
            final String msg = WxMpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
            if (msg != null) {
                wxError.setErrorMsg(msg);
            }
        }
        else if (type == WxType.CP) {
            final String msg = WxCpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
            if (msg != null) {
                wxError.setErrorMsg(msg);
            }
        }
        else if (type == WxType.MiniApp) {
            final String msg = WxMaErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
            if (msg != null) {
                wxError.setErrorMsg(msg);
            }
        }

        return wxError;
    }

    @Override
    public String toString() {
        if (this.json != null) {
            return this.json;
        }
        return "错误: Code=" + this.errorCode + ", Msg=" + this.errorMsg;
    }
}
