/*
 * @(#)QqInMemoryConfigStorage    Created on 2019/4/1
 * Copyright (c) 2019 ZDSoft Networks, Inc. All rights reserved.
 * $$ Id$$
 */
package com.wyb.mp.qq.api.impl;


import com.wyb.mp.qq.api.QqConfigStorage;

/**
 * QQ 配置信息 包括定死的信息 和 变化的accessToken
 *
 * @author Kunzite
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/4/1 15:29 $$
 */
public class QqInMemoryConfigStorage implements QqConfigStorage {

    // 防止多线程修改这些参数 要设置为线程安全
    protected volatile String appId;
    protected volatile String secret;
    protected volatile String accessToken;
    protected volatile String refreshToken;
    protected volatile long expiresTime;// 过期时间

    public QqInMemoryConfigStorage(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return false;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public long getExpiresTime() {
        return expiresTime;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
}
