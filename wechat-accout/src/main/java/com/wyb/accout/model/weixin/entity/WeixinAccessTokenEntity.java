package com.wyb.accout.model.weixin.entity;

/**
 * Created by Kunzite on 2016/9/13.
 */
public class WeixinAccessTokenEntity extends WeixinBasicEntity {

    private String accesstoken;
    private int expiresin;
    private int currenttimes;
    private String refreshToken;
    private String openId;
    private String scope;

    public WeixinAccessTokenEntity() {
        super();
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }

    public int getCurrenttimes() {
        return currenttimes;
    }

    public void setCurrenttimes(int currenttimes) {
        this.currenttimes = currenttimes;
    }

    /**
     * 判断token是否在有效时间内，true是，false不是
     *
     * @param currenttimes
     * @return
     */
    public boolean isEffective(int currenttimes) {
        if ((currenttimes - this.currenttimes + 600000) > expiresin) {
            return false;
        }
        return true;
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
}
