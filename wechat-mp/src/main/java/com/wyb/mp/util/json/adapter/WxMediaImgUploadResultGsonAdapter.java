package com.wyb.mp.util.json.adapter;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.wyb.common.util.json.GsonHelper;
import com.wyb.mp.bean.message.WxMediaImgUploadResult;

/**
 * @author Kunzite
 */
public class WxMediaImgUploadResultGsonAdapter implements JsonDeserializer<WxMediaImgUploadResult> {
    @Override
    public WxMediaImgUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        WxMediaImgUploadResult wxMediaImgUploadResult = new WxMediaImgUploadResult();
        JsonObject jsonObject = json.getAsJsonObject();
        if (null != jsonObject.get("url") && !jsonObject.get("url").isJsonNull()) {
            wxMediaImgUploadResult.setUrl(GsonHelper.getAsString(jsonObject.get("url")));
        }
        return wxMediaImgUploadResult;
    }
}
