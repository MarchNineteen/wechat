package com.wyb.open.bean.result;

import com.wyb.open.util.json.WxMpGsonBuilder;

/**
 * 微信返回token
 *
 * @author Kunzite
 */
public class WxMpOAuth2AccessToken {

    private String accessToken;
    private int expiresIn = -1;
    private String refreshToken;
    private String openId;
    private String scope;

    /**
     * https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11513156443eZYea&version=&lang=zh_CN.
     * 本接口在scope参数为snsapi_base时不再提供unionID字段。
     */
    private String unionId;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public static WxMpOAuth2AccessToken fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpOAuth2AccessToken.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
