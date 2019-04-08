package com.wyb.mp.api.impl;


import com.wyb.common.bean.WxAccessToken;
import com.wyb.common.exception.WxError;
import com.wyb.common.exception.WxErrorException;
import com.wyb.common.util.http.HttpClientUtil;
import com.wyb.mp.api.WxMpService;

import java.util.concurrent.locks.Lock;

/**
 * @author Kunzite
 */
public class WxMpServiceImpl extends BaseWxMpServiceImpl {

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
        try {
            lock.lock();
            // 判断accessToken是否过期或者强制要求刷新
            if (this.getWxMpConfigStorage().isAccessTokenExpired() || forceRefresh) {
                String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL,
                        this.getWxMpConfigStorage().getAppId(), this.getWxMpConfigStorage().getSecret());
                String resultContent = HttpClientUtil.doGet(url);
                WxError error = WxError.fromJson(resultContent);
                if (error.getErrorCode() != 0) {
                    throw new WxErrorException(error);
                }
                WxAccessToken wxAccessToken = WxAccessToken.fromJson(resultContent);
                this.getWxMpConfigStorage().updateAccessToken(wxAccessToken.getAccessToken(), wxAccessToken.getExpiresIn());
            }
        } finally {
            lock.unlock();
        }
        return this.getWxMpConfigStorage().getAccessToken();
    }
}
