package com.wyb.open.api.impl;

import com.wyb.open.api.WxMpConfigStorage;
import com.wyb.open.common.bean.WxMpCommonAccessToken;


import java.io.Serializable;

/**
 * @author wangyb
 */
public class WxMpInMemoryConfigStorage implements WxMpConfigStorage, Serializable {

    private static final long serialVersionUID = -1969107891472873549L;

    // 防止多线程修改这些参数 要设置为线程安全
    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String accessToken;
    protected volatile String aesKey;
    protected volatile long expiresTime;// 过期时间
    protected volatile boolean autoRefreshToken;

    public WxMpInMemoryConfigStorage(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public void updateAccessToken(WxMpCommonAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        // 预留200秒时间
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
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
    public String getToken() {
        return token;
    }

    @Override
    public String getAesKey() {
        return aesKey;
    }

    @Override
    public long getExpiresTime() {
        return -1;
    }

    @Override
    public boolean getAutoRefreshToken() {
        return autoRefreshToken;
    }

}
