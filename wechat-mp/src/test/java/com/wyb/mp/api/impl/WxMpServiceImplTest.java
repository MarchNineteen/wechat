package com.wyb.mp.api.impl;

import com.wyb.mp.api.WxMpConfigStorage;
import com.wyb.mp.api.WxMpService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kunzite
 */
public class WxMpServiceImplTest {

    @Test
    public void contextLoads() {
        WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        String codeUrl = wxMpService.oauth2buildAuthorizationUrl("http://wybcs.wezoz.com/getAccessToken", "snsapi_userinfo", null);
        Assert.assertNotNull(codeUrl);
        System.out.println(codeUrl);
    }

    @Test
    public void codeChargeAccessToken() {
        WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        String codeUrl = wxMpService.oauth2buildAuthorizationUrl("http://wybcs.wezoz.com/getAccessToken", "snsapi_userinfo", null);
        Assert.assertNotNull(codeUrl);
        System.out.println(codeUrl);
    }


}
