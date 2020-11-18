package com.wyb.mp.api.impl;

import java.io.File;

import com.wyb.common.api.WxConsts;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.bean.message.WxMpMassNews;
import com.wyb.mp.bean.message.WxMpMassOpenIdsMessage;
import com.wyb.mp.bean.message.WxMpMassPreviewMessage;
import com.wyb.mp.bean.result.WxMpMassSendResult;
import com.wyb.mp.bean.result.WxMpMassUploadResult;
import org.junit.Before;
import org.junit.Test;

import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.material.WxMediaImgUploadResult;

import static org.junit.Assert.assertNotNull;

/**
 * @author Kunzite
 */
public class WxMpMassMessageServiceImplTest {

    private WxMpService wxService;

    @Before
    public void initService() {
        wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(
                new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204"));
    }

    @Test
    public void testMediaImgUpload() throws Exception {
        File file = new File("C:\\Users\\user\\Desktop\\avatar1.jpg");
        WxMediaImgUploadResult result = this.wxService.getWxMpMassMessageService().mediaImgUpload(file);
        assertNotNull(result);
        System.err.println(result.getUrl());
    }

    @Test
    public void testMassNewsUpload() throws Exception {
        WxMpMassNews news = new WxMpMassNews();
        WxMpMassNews.WxMpMassNewsArticle article1= new WxMpMassNews.WxMpMassNewsArticle();
        article1.setTitle("标题1");
        article1.setContent("内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1");
//        article1.setThumbMediaId(result.getUrl());
        news.addArticle(article1);

        WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
        article2.setTitle("标题2");
        article2.setContent("内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2内容2");
//        article2.setThumbMediaId(uploadMediaRes.getMediaId());
        article2.setShowCoverPic(true);
        article2.setAuthor("作者2");
        article2.setContentSourceUrl("www.baidu.com");
        article2.setDigest("摘要2");
        news.addArticle(article2);


        WxMpMassUploadResult result1 = this.wxService.getWxMpMassMessageService().massNewsUpload(news);
        assertNotNull(result1);
        System.err.println(result1.getMediaId());
    }

    @Test
    public void testTextMassOpenIdsMessageSend() throws WxErrorException {
        // 发送群发消息
        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("测试群发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:");
        massMessage.getToUsers().add("odnet5jB84uLoCcaPjv-eYtx0pHA");
        massMessage.getToUsers().add("odnet5uBEqWpT2onpGljv8l7_LZ8");

        WxMpMassSendResult massResult = this.wxService.getWxMpMassMessageService()
                .massOpenIdsMessageSend(massMessage);
        assertNotNull(massResult);
        assertNotNull(massResult.getMsgId());
    }

    @Test
    public void testTextMassOpenIdMessageSend() throws WxErrorException {
        // 发送群发消息
        WxMpMassPreviewMessage massMessage = new WxMpMassPreviewMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("测试单发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:<img src=\"http://mmbiz.qpic.cn/mmbiz_jpg/ibFtctoL7Z6Tia4gQjzANBGCx6rxfdoEuQXbbZske5HxQia2qcick19ajicEj2M2SribIW6Nk67lsx4LLYHWMs8TS7YA/0\"></img>");
        massMessage.setToWxUserOpenid("odnet5jB84uLoCcaPjv-eYtx0pHA");

        WxMpMassSendResult massResult = this.wxService.getWxMpMassMessageService()
                .massMessagePreview(massMessage);
        System.out.println(massResult.toString());
        assertNotNull(massResult);
        assertNotNull(massResult.getMsgId());
    }
}
