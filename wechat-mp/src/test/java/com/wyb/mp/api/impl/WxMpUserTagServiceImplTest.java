package com.wyb.mp.api.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.tag.WxTagListUser;
import com.wyb.mp.bean.tag.WxUserTag;

/**
 * Created by Binary Wang on 2016/9/2.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMpUserTagServiceImplTest {

    protected WxMpService wxService;

    @Before
    public void initService() {
        wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(
                new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204"));
    }

    private Long tagId = 2L;

    @Test
    public void testTagCreate() throws Exception {
        String tagName = "测试标签" + System.currentTimeMillis();
        WxUserTag res = this.wxService.getWxMpUserTagService().tagCreate(tagName);
        System.out.println(res);
        this.tagId = res.getId();
        Assert.assertEquals(tagName, res.getName());
    }

    @Test
    public void testTagGet() throws Exception {
        List<WxUserTag> res = this.wxService.getWxMpUserTagService().tagGet();
        System.out.println(res);
        Assert.assertNotNull(res);
    }

//    @Test(dependsOnMethods = { "testTagCreate" })
    public void testTagUpdate() throws Exception {
        String tagName = "修改标签" + System.currentTimeMillis();
        Boolean res = this.wxService.getWxMpUserTagService().tagUpdate(this.tagId, tagName);
        System.out.println(res);
        Assert.assertTrue(res);
    }

//    @Test(dependsOnMethods = { "testTagCreate" })
    public void testTagDelete() throws Exception {
        Boolean res = this.wxService.getWxMpUserTagService().tagDelete(this.tagId);
        System.out.println(res);
        Assert.assertTrue(res);
    }

    @Test
    public void testTagListUser() throws Exception {
        WxTagListUser res = this.wxService.getWxMpUserTagService().tagListUser(this.tagId, null);
        System.out.println(res);
        Assert.assertNotNull(res);
    }

    @Test
    public void testBatchTagging() throws Exception {
//        String[] openids = new String[] { ((TestConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid() };
        String[] openids = new String[] { "odnet5jB84uLoCcaPjv-eYtx0pHA" };

        boolean res = this.wxService.getWxMpUserTagService().batchTagging(this.tagId, openids);
        System.out.println(res);
        Assert.assertTrue(res);
    }
//
//    @Test
//    public void testBatchUntagging() throws Exception {
//        String[] openids = new String[] { ((TestConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid() };
//        boolean res = this.wxService.getWxMpUserTagService().batchUntagging(this.tagId, openids);
//        System.out.println(res);
//        Assert.assertTrue(res);
//    }
//
//    @Test
//    public void testUserTagList() throws Exception {
//        List<Long> res = this.wxService.getWxMpUserTagService()
//                .userTagList(((TestConfigStorage) this.wxService.getWxMpConfigStorage()).getOpenid());
//        System.out.println(res);
//        Assert.assertNotNull(res);
//    }
}
