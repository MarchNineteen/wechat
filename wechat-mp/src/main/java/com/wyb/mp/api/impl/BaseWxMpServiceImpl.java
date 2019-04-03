package com.wyb.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wyb.mp.api.WxMpConfigStorage;
import com.wyb.mp.api.WxMpService;
import com.wyb.common.exception.WxErrorException;
import com.wyb.common.util.http.HttpUtil;
import com.wyb.common.util.http.URIUtil;
import com.wyb.mp.bean.result.WxMpOAuth2AccessToken;
import com.wyb.mp.bean.result.WxMpUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Kunzite
 */
@Slf4j
public abstract class BaseWxMpServiceImpl implements WxMpService {
    private static final JsonParser JSON_PARSER = new JsonParser();

    // 关联各个微信的api实现


    // 微信配置
    protected WxMpConfigStorage wxMpConfigStorage;

    private int retrySleepMillis = 1000;
    private int maxRetryTimes = 5;

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
//        try {
//            return SHA1.gen(this.getWxMpConfigStorage().getToken(), timestamp, nonce)
//                    .equals(signature);
//        } catch (Exception e) {
//            log.error("Checking signature failed, reason {}" + e.getMessage());
//            return false;
//
//        }
        return false;

    }

    @Override
    public String getAccessToken() throws WxErrorException {
        return getAccessToken(false);
    }

    @Override
    public String buildQrConnectUrl(String redirectURI, String scope, String state) {
        return String.format(WxMpService.QRCONNECT_URL,
                this.getWxMpConfigStorage().getAppId(), URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state));
    }

    /**
     * 构造oauth2授权的url连接. 即获取code
     */
    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return String.format(CONNECT_OAUTH2_AUTHORIZE_URL, this.getWxMpConfigStorage().getAppId(), URIUtil.encodeURIComponent(redirectURI), scope, state);
    }

    private WxMpOAuth2AccessToken getOAuth2AccessToken(String url) throws WxErrorException {
        String response = HttpUtil.get(url, null);
        return WxMpOAuth2AccessToken.fromJson(response);
    }

    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        String url = String.format(WxMpService.OAUTH2_ACCESS_TOKEN_URL, this.getWxMpConfigStorage().getAppId(), this.getWxMpConfigStorage().getSecret(), code);
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken token) throws WxErrorException {
        String url = String.format(WxMpService.OAUTH2_USERINFO_URL, token.getAccessToken(), token.getOpenId(), null);
        String response = HttpUtil.get(url, null);
        return WxMpUser.fromJson(response);
    }

    @Override
    public String[] getCallbackIP() throws WxErrorException {
        String responseContent = HttpUtil.get(WxMpService.GET_CALLBACK_IP_URL, null);
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
        JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
        String[] ipArray = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipArray[i] = ipList.get(i).getAsString();
        }
        return ipArray;
    }

    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public WxMpConfigStorage getWxMpConfigStorage() {
        return wxMpConfigStorage;
    }

    public void setWxMpConfigStorage(WxMpConfigStorage wxMpConfigStorage) {
        this.wxMpConfigStorage = wxMpConfigStorage;
    }
}