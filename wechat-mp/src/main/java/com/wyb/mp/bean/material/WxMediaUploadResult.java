package com.wyb.mp.bean.material;

import java.io.Serializable;

import lombok.Data;

import com.wyb.mp.util.json.WxMpGsonBuilder;

/**
 *
 */
@Data
public class WxMediaUploadResult implements Serializable {
    private static final long serialVersionUID = 330834334738622341L;

    private String url;
    private String type;
    private String mediaId;
    private String thumbMediaId;
    private long createdAt;

    public static WxMediaUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMediaUploadResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
