package com.wyb.mp.bean.result;

import java.io.Serializable;

import lombok.Data;

import com.wyb.mp.util.json.WxMpGsonBuilder;

/**
 * <pre>
 * 群发消息一发送就返回的结果
 *
 * 真正的群发消息是否发送成功要看
 * http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C
 *
 * </pre>
 */
@Data
public class WxMpMassSendResult implements Serializable {
    private static final long serialVersionUID = 5286349095081207203L;

    private String errorCode;
    private String errorMsg;
    private String msgId;
    private String msgDataId;

    public static WxMpMassSendResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMassSendResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
