package com.wyb.open.api;

import com.wyb.open.common.bean.WxMpCommonAccessToken;

/**
 * 微信配置接口
 *
 * @author Kunzite
 */
public interface WxMpConfigStorage {

    /**
     * 获取accessToken
     */
    String getAccessToken();

    /**
     * accessToken是否过期
     */
    boolean isAccessTokenExpired();

    /**
     * 强制将access token过期掉.
     */
    void expireAccessToken();

    /**
     * 应该是线程安全的.
     *
     * @param accessToken 要更新的WxAccessToken对象
     */
    void updateAccessToken(WxMpCommonAccessToken accessToken);

    /**
     * 应该是线程安全的.
     *
     * @param accessToken      新的accessToken值
     * @param expiresInSeconds 过期时间，以秒为单位
     */
    void updateAccessToken(String accessToken, int expiresInSeconds);

    String getAppId();

    String getSecret();

    String getToken();

    String getAesKey();

    long getExpiresTime();

    /**
     * 是否自动刷新token.
     */
    boolean getAutoRefreshToken();
}
