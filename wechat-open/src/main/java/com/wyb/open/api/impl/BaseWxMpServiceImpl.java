package com.wyb.open.api.impl;

import com.wyb.open.api.WxMpService;
import com.wyb.open.bean.result.WxMpOAuth2AccessToken;
import com.wyb.open.common.exception.WxErrorException;
import com.wyb.open.api.WxMpConfigStorage;
import com.wyb.open.common.util.http.HttpUtil;
import com.wyb.open.common.util.http.URIUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kunzite
 */
@Slf4j
public abstract class BaseWxMpServiceImpl implements WxMpService {

    // 组合各个微信的api实现


    // 微信配置
    protected WxMpConfigStorage wxMpConfigStorage;

    private int retrySleepMillis = 1000;
    private int maxRetryTimes = 5;


    @Override
    public String getAccessToken() throws WxErrorException {
        return getAccessToken(false);
    }

    /**
     * <pre>
     * 构造oauth2授权的url连接. 即获取code
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @return url
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
