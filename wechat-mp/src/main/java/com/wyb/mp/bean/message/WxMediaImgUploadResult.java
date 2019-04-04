package com.wyb.mp.bean.message;

import java.io.Serializable;

import lombok.Data;

import com.wyb.mp.util.json.WxMpGsonBuilder;

/**
 * @author Kunzite
 */
@Data
public class WxMediaImgUploadResult implements Serializable {
    private static final long serialVersionUID = 1996392453428768829L;
    private String url;

    public static WxMediaImgUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMediaImgUploadResult.class);
    }

}
