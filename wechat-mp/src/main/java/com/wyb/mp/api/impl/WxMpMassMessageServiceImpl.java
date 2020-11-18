package com.wyb.mp.api.impl;

import java.io.File;

import com.wyb.mp.bean.message.WxMpMassPreviewMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyb.common.exception.WxError;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpMassMessageService;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.material.WxMediaImgUploadResult;
import com.wyb.mp.bean.message.WxMpMassNews;
import com.wyb.mp.bean.message.WxMpMassOpenIdsMessage;
import com.wyb.mp.bean.result.WxMpMassSendResult;
import com.wyb.mp.bean.result.WxMpMassUploadResult;

/**
 * <pre>
 * 群发消息服务类
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMpMassMessageServiceImpl implements WxMpMassMessageService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private WxMpService wxMpService;

    public WxMpMassMessageServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMediaImgUploadResult mediaImgUpload(File file) throws WxErrorException {
        String responseContent = wxMpService.postFile(IMG_UPLOAD_URL, file);
        WxError error = WxError.fromJson(responseContent);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
        return WxMediaImgUploadResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
        String responseContent = this.wxMpService.post(MEDIA_UPLOAD_NEWS_URL, news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    // @Override
    // public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
    // String responseContent = this.wxMpService.post(MEDIA_UPLOAD_VIDEO_URL, video.toJson());
    // return WxMpMassUploadResult.fromJson(responseContent);
    // }
    //
    // @Override
    // public WxMpMassSendResult massGroupMessageSend(WxMpMassTagMessage message) throws WxErrorException {
    // String responseContent = this.wxMpService.post(WxMpMassMessageService.MESSAGE_MASS_SENDALL_URL,
    // message.toJson());
    // return WxMpMassSendResult.fromJson(responseContent);
    // }

    @Override
    public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_MASS_SEND_URL, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massMessagePreview(WxMpMassPreviewMessage wxMpMassPreviewMessage)
            throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_MASS_PREVIEW_URL, wxMpMassPreviewMessage.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    // @Override
    // public void delete(Long msgId, Integer articleIndex) throws WxErrorException {
    // JsonObject jsonObject = new JsonObject();
    // jsonObject.addProperty("msg_id", msgId);
    // jsonObject.addProperty("article_idx", articleIndex);
    // this.wxMpService.post(MESSAGE_MASS_DELETE_URL, jsonObject.toString());
    // }

}
