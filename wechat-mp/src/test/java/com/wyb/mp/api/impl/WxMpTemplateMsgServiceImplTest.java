package com.wyb.mp.api.impl;

import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.template.WxMpTemplate;
import com.wyb.mp.bean.template.WxMpTemplateData;
import com.wyb.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangyb
 */
public class WxMpTemplateMsgServiceImplTest {
    protected WxMpService wxService;

    @Before
    public void initService() {
        wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204"));
    }

    @Test
    public void testAddTemplate() throws Exception {
        String result = this.wxService.getWxMpTemplateMsgService().addTemplate("TM00658");
        Assert.assertNotNull(result);
        System.err.println(result);
    }

    @Test
    public void testGetAllPrivateTemplate() throws Exception {
        List<WxMpTemplate> result = this.wxService.getWxMpTemplateMsgService().getAllPrivateTemplate();
        Assert.assertNotNull(result);
        System.err.println(result);
    }

    @Test()
    public void testSendTemplateMsg() throws WxErrorException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS");
//        TestConfigStorage configStorage = (TestConfigStorage) this.wxService
//                .getWxMpConfigStorage();
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .touser("odnet5jB84uLoCcaPjv-eYtx0pHA")
                .template_id("nLIIqH4xGuzJU5Dur7TONi856oyYmD85yOqnKs3DHlk")
                .url(" ")
                .build();

        templateMessage.addData(new WxMpTemplateData("first", dateFormat.format(new Date()), "#FF00FF"))
                .addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
        String msgId = this.wxService.getWxMpTemplateMsgService().sendTemplateMsg(templateMessage);
        Assert.assertNotNull(msgId);
        System.out.println(msgId);
    }
}
