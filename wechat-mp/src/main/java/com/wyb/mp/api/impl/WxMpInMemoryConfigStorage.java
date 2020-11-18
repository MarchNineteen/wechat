package com.wyb.mp.api.impl;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.wyb.common.bean.WxAccessToken;
import com.wyb.mp.api.WxMpConfigStorage;
import com.wyb.mp.enums.TicketType;

/**
 * @author Kunzite
 */
public class WxMpInMemoryConfigStorage implements WxMpConfigStorage, Serializable {

    private static final long serialVersionUID = -1969107891472873549L;

    // 防止多线程修改这些参数 要设置为线程安全
    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String accessToken = "39_UEQjtoHbuI5zJCqfa1vPPUfTfJZuK_FkXtqm-lg0eVnOX27gIkwTf8aKkgthipEpb7nYP6TvuLE4xHb9P74wYRcvAxLJ9OQGcNmf0wG_iMbwyh9JYUxbLnSrxkQf1AHsDCiw3elELv-UvGjVETFcAEAKZL";
    protected volatile String aesKey;
    protected volatile long expiresTime;// 过期时间
    protected volatile boolean autoRefreshToken;

    protected volatile String jsapiTicket;
    protected volatile long jsapiTicketExpiresTime;

    protected volatile String sdkTicket;
    protected volatile long sdkTicketExpiresTime;

    protected volatile String cardApiTicket;
    protected volatile long cardApiTicketExpiresTime;

    protected Lock accessTokenLock = new ReentrantLock();
    protected Lock jsapiTicketLock = new ReentrantLock();
    protected Lock sdkTicketLock = new ReentrantLock();
    protected Lock cardApiTicketLock = new ReentrantLock();

    public WxMpInMemoryConfigStorage() {
    }

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
    public void updateAccessToken(WxAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        // 预留200秒时间
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getTicket(TicketType type) {
        switch (type) {
        case SDK:
            return this.sdkTicket;
        case JSAPI:
            return this.jsapiTicket;
        case WX_CARD:
            return this.cardApiTicket;
        default:
            return null;
        }
    }

    public void setTicket(TicketType type, String ticket) {
        switch (type) {
        case JSAPI:
            this.jsapiTicket = ticket;
            break;
        case WX_CARD:
            this.cardApiTicket = ticket;
            break;
        case SDK:
            this.sdkTicket = ticket;
            break;
        default:
        }
    }

    @Override
    public Lock getTicketLock(TicketType type) {
        switch (type) {
        case SDK:
            return this.sdkTicketLock;
        case JSAPI:
            return this.jsapiTicketLock;
        case WX_CARD:
            return this.cardApiTicketLock;
        default:
            return null;
        }
    }

    @Override
    public boolean isTicketExpired(TicketType type) {
        switch (type) {
        case SDK:
            return System.currentTimeMillis() > this.sdkTicketExpiresTime;
        case JSAPI:
            return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
        case WX_CARD:
            return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
        default:
            return false;
        }
    }

    @Override
    public synchronized void updateTicket(TicketType type, String ticket, int expiresInSeconds) {
        switch (type) {
        case JSAPI:
            this.jsapiTicket = ticket;
            // 预留200秒的时间
            this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
            break;
        case WX_CARD:
            this.cardApiTicket = ticket;
            // 预留200秒的时间
            this.cardApiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
            break;
        case SDK:
            this.sdkTicket = ticket;
            // 预留200秒的时间
            this.sdkTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
            break;
        default:
        }
    }

    @Override
    public void expireTicket(TicketType type) {
        switch (type) {
        case JSAPI:
            this.jsapiTicketExpiresTime = 0;
            break;
        case WX_CARD:
            this.cardApiTicketExpiresTime = 0;
            break;
        case SDK:
            this.sdkTicketExpiresTime = 0;
            break;
        default:
        }
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

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    public void setAutoRefreshToken(boolean autoRefreshToken) {
        this.autoRefreshToken = autoRefreshToken;
    }

    public Lock getAccessTokenLock() {
        return accessTokenLock;
    }

    public void setAccessTokenLock(Lock accessTokenLock) {
        this.accessTokenLock = accessTokenLock;
    }
}
