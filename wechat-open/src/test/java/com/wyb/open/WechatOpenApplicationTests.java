package com.wyb.open;

import com.wyb.open.api.WxMpConfigStorage;
import com.wyb.open.api.WxMpService;
import com.wyb.open.api.impl.WxMpInMemoryConfigStorage;
import com.wyb.open.api.impl.WxMpServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatOpenApplicationTests {

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
