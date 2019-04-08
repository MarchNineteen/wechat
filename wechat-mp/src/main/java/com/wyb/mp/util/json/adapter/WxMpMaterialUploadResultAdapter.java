package com.wyb.mp.util.json.adapter;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.wyb.common.util.json.GsonHelper;
import com.wyb.mp.bean.material.WxMpMaterialUploadResult;

/**
 * @author codepiano
 */
public class WxMpMaterialUploadResultAdapter implements JsonDeserializer<WxMpMaterialUploadResult> {

    @Override
    public WxMpMaterialUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        WxMpMaterialUploadResult uploadResult = new WxMpMaterialUploadResult();
        JsonObject uploadResultJsonObject = json.getAsJsonObject();

        if (uploadResultJsonObject.get("url") != null && !uploadResultJsonObject.get("url").isJsonNull()) {
            uploadResult.setUrl(GsonHelper.getAsString(uploadResultJsonObject.get("url")));
        }
        if (uploadResultJsonObject.get("media_id") != null && !uploadResultJsonObject.get("media_id").isJsonNull()) {
            uploadResult.setMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("media_id")));
        }
        return uploadResult;
    }

}
