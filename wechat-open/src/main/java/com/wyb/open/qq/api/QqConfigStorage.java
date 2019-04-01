/*
 * @(#)QqConfigStorage    Created on 2019/4/1
 * Copyright (c) 2019 ZDSoft Networks, Inc. All rights reserved.
 * $$ Id$$
 */
package com.wyb.open.qq.api;

/**
 * @author wangyb
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/4/1 15:27 $$
 */
public interface QqConfigStorage {

    /**
     * 获取accessToken
     */
    String getAccessToken();

    /**
     * accessToken是否过期
     */
    boolean isAccessTokenExpired();

    String getAppId();

    String getSecret();

    /**
     * 获取refresh_token
     */
    String getRefreshToken();


    /**
     * 过期时间
     */
    long getExpiresTime();

}
