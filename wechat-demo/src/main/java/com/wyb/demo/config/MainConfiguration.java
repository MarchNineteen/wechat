package com.wyb.demo.config;

import com.wyb.mp.api.WxMpConfigStorage;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.api.impl.WxMpInMemoryConfigStorage;
import com.wyb.mp.api.impl.WxMpServiceImpl;
import com.wyb.mp.qq.api.QqApiService;
import com.wyb.mp.qq.api.impl.QqApiServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信公众号配置
 *
 * @author Kunzite
 */
@Component
public class MainConfiguration {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.token}")
    private String token;

    @Value("${wx.aesKey}")
    private String aesKey;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.appId);
        configStorage.setSecret(this.appSecret);
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public QqApiService qqService() {
        QqApiService qqApiService = new QqApiServiceImpl();
        return qqApiService;
    }
}
