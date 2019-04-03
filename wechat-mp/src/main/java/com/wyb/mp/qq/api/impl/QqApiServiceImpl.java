/*
 * @(#)QqApiServiceImpl    Created on 2019/4/1
 * Copyright (c) 2019 ZDSoft Networks, Inc. All rights reserved.
 * $$ Id$$
 */
package com.wyb.mp.qq.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.wyb.mp.qq.bean.result.QqOAuth2AccessToken;
import com.wyb.common.util.http.HttpUtil;
import com.wyb.common.util.http.URIUtil;
import com.wyb.mp.qq.api.QqApiService;
import com.wyb.mp.qq.api.QqConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * qq第三方接口
 *
 * @author Kunzite
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/4/1 14:56 $$
 */
public class QqApiServiceImpl implements QqApiService {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    // auth2授权的url连接. 获取code
    public static final String QQ_CONNECT_OAUTH2_AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=%s&display=%s";
    // 用code换取oauth2的accessToken
    public static final String QQ_OAUTH2_ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
    // accessToken获取OpenId
    public static final String QQ_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    protected QqConfigStorage qqConfigStorage = new QqInMemoryConfigStorage("101562739", "ea2fb63dd20d7c5d9a6a50f84ca1735b");

    @Override
    public String qqAuth2buildAuthorizationUrl(String redirectUri, String state, String scope, String display) {
        return String.format(QQ_CONNECT_OAUTH2_AUTHORIZE_URL, qqConfigStorage.getAppId(), URIUtil.encodeURIComponent(redirectUri), state, scope, display);
    }

    @Override
    public QqOAuth2AccessToken qqAuth2getAccessToken(String grantType, String code, String redirectUri) {
        String sendUrl = String.format(QQ_OAUTH2_ACCESS_TOKEN_URL, grantType, qqConfigStorage.getAppId(), qqConfigStorage.getSecret(), code, redirectUri);
        try {
            QqOAuth2AccessToken token = new QqOAuth2AccessToken();
            String result = HttpUtil.get(sendUrl, null);
            Matcher m = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$").matcher(result);
            if (m.find()) {
                token.setAccessToken(m.group(1));
                token.setExpiresIn(Integer.parseInt(m.group(2)));
                token.setRefreshToken(m.group(3));
            } else {
                Matcher m2 = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)$").matcher(result);
                if (m2.find()) {
                    token.setAccessToken(m.group(1));
                    token.setExpiresIn(Integer.parseInt(m.group(2)));
                }
            }
            token.setOpenId(qqAuth2getOpenID(token.getAccessToken()));
            return token;
        } catch (Exception e) {
            log.info("qqAuth2getAccessToken() error, message: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public String qqAuth2getOpenID(String accessToken) {
        String sendUrl = String.format(QQ_OPEN_ID_URL, accessToken);
        try {
            String result = HttpUtil.get(sendUrl, null);
            result = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getString("openid");
        } catch (Exception e) {
            log.info("qqAuth2getOpenID() error, accessToken:{}, message: {}", accessToken, e.getMessage());
            return null;
        }
    }


    public QqConfigStorage getQqConfigStorage() {
        return qqConfigStorage;
    }

    public void setQqConfigStorage(QqConfigStorage qqConfigStorage) {
        this.qqConfigStorage = qqConfigStorage;
    }
}
