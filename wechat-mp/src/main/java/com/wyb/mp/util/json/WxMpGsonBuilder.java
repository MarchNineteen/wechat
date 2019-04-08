package com.wyb.mp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyb.mp.bean.material.WxMediaImgUploadResult;
import com.wyb.mp.bean.material.WxMpMaterialNews;
import com.wyb.mp.bean.material.WxMpMaterialUploadResult;
import com.wyb.mp.bean.message.WxMpMassOpenIdsMessage;
import com.wyb.mp.bean.result.WxMpMassSendResult;
import com.wyb.mp.bean.result.WxMpMassUploadResult;
import com.wyb.mp.bean.result.WxMpOAuth2AccessToken;
import com.wyb.mp.bean.result.WxMpUser;
import com.wyb.mp.util.json.adapter.*;

public class WxMpGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMpMassOpenIdsMessage.class, new WxMpMassOpenIdsMessageGsonAdapter());

        INSTANCE.registerTypeAdapter(WxMediaImgUploadResult.class, new WxMediaImgUploadResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMpMaterialUploadResult.class, new WxMpMaterialUploadResultAdapter());
        INSTANCE.registerTypeAdapter(WxMpMaterialNews.WxMpMaterialNewsArticle.class, new WxMpMaterialNewsArticleGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMpMassUploadResult.class, new WxMpMassUploadResultAdapter());
        INSTANCE.registerTypeAdapter(WxMpMassSendResult.class, new WxMpMassSendResultAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
