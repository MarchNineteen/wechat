/*
 * @(#)QqOAuth2AccessToken    Created on 2019/4/1
 * Copyright (c) 2019 ZDSoft Networks, Inc. All rights reserved.
 * $$ Id$$
 */
package com.wyb.mp.qq.bean.result;


import com.wyb.mp.qq.util.json.QqGsonBuilder;

/**
 * @author Kunzite
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/4/1 15:20 $$
 */
public class QqOAuth2AccessToken {

    private String accessToken;// 授权令牌，Access_Token。
    private int expiresIn; // 该access token的有效期，单位为秒。
    private String refreshToken;// 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数
    private String openId;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public static QqOAuth2AccessToken fromJson(String json) {
        return QqGsonBuilder.create().fromJson(json, QqOAuth2AccessToken.class);
    }

    @Override
    public String toString() {
        return QqGsonBuilder.create().toJson(this);
    }
}
