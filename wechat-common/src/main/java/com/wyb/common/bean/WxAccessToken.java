package com.wyb.common.bean;

import com.wyb.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * @author Kunzite
 */
public class WxAccessToken implements Serializable {
    private static final long serialVersionUID = 6791484774851818662L;

    private String accessToken;

    private int expiresIn = -1;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public static WxAccessToken fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
