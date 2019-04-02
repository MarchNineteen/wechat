package com.wyb.common.exception;

import com.wyb.common.util.json.WxGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyb
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
     * 微信错误信息.
     */
    private String errorMsg;

    private String json;

    public static WxError fromJson(String json) {
        final WxError wxError = WxGsonBuilder.create().fromJson(json, WxError.class);

        final String msg = WxMpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
        if (msg != null) {
            wxError.setErrorMsg(msg);
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
