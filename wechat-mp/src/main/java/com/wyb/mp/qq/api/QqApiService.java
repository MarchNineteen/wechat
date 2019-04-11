/*
 * @(#)QqApiService    Created on 2019/4/1
 * Copyright (c) 2019 ZDSoft Networks, Inc. All rights reserved.
 * $$ Id$$
 */
package com.wyb.mp.qq.api;

import com.wyb.mp.qq.bean.result.QqOAuth2AccessToken;
import com.wyb.mp.qq.bean.result.QqUserInfo;

public interface QqApiService {
    /**
     * 获取qq Authorization Code 10分钟内过期
     * @param redirectUri
     * @param scope
     * @param display
     * @return
     */
    String qqAuth2buildAuthorizationUrl(String redirectUri, String state, String scope, String display);

    /**
     * Authorization Code 获取 Access Token
     * @param grantType
     * @param code
     * @param redirectUri
     * @return
     */
    QqOAuth2AccessToken qqAuth2getAccessToken(String grantType, String code, String redirectUri);

    /**
     * accessToken获取openID
     * @param accessToken
     * @return
     */
    String qqAuth2getOpenID(String accessToken);

    QqUserInfo getUserInfo(QqOAuth2AccessToken auth2AccessToken);
}
