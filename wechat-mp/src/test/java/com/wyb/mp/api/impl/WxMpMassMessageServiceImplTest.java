package com.wyb.mp.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.wyb.common.exception.WxError;
import com.wyb.common.exception.WxErrorException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.message.WxMediaImgUploadResult;

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
//        File tmpFile = null;
//        try {
//            tmpFile = FileUtils.cr(inputStream, UUID.randomUUID().toString(), fileType);
//            return this.mediaUpload(mediaType, tmpFile);
//        } catch (IOException e) {
//            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg(e.getMessage()).build(), e);
//        } finally {
//            if (tmpFile != null) {
//                tmpFile.delete();
//            }
        WxMediaImgUploadResult result = this.wxService.getWxMpMassMessageService().mediaImgUpload(file);
        Assert.assertNotNull(result);
        System.err.println(result.getUrl());
    }
}
