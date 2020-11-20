package com.wyb.mp.api.impl;

import static com.wyb.mp.enums.WxMpApiUrl.UserTag.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wyb.common.WxType;
import com.wyb.common.exception.WxError;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.api.WxMpUserTagService;
import com.wyb.mp.bean.tag.WxTagListUser;
import com.wyb.mp.bean.tag.WxUserTag;
import com.wyb.mp.util.json.WxMpGsonBuilder;

@RequiredArgsConstructor
public class WxMpUserTagServiceImpl implements WxMpUserTagService {
    private final WxMpService wxMpService;

    @Override
    public WxUserTag tagCreate(String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_CREATE.getUrl(wxMpService.getWxMpConfigStorage()),
                json.toString());
        return WxUserTag.fromJson(responseContent);
    }

    @Override
    public List<WxUserTag> tagGet() throws WxErrorException {
        String responseContent = this.wxMpService.get(TAGS_GET.getUrl(wxMpService.getWxMpConfigStorage()), null);
        return WxUserTag.listFromJson(responseContent);
    }

    @Override
    public Boolean tagUpdate(Long id, String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_UPDATE.getUrl(wxMpService.getWxMpConfigStorage()),
                json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public Boolean tagDelete(Long id) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_DELETE.getUrl(wxMpService.getWxMpConfigStorage()),
                json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public WxTagListUser tagListUser(Long tagId, String nextOpenid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        json.addProperty("next_openid", StringUtils.trimToEmpty(nextOpenid));

        String responseContent = this.wxMpService.post(TAG_GET.getUrl(wxMpService.getWxMpConfigStorage()),
                json.toString());
        return WxTagListUser.fromJson(responseContent);
    }

    @Override
    public boolean batchTagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService
                .post(TAGS_MEMBERS_BATCHTAGGING.getUrl(wxMpService.getWxMpConfigStorage()), json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public boolean batchUntagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService
                .post(TAGS_MEMBERS_BATCHUNTAGGING.getUrl(wxMpService.getWxMpConfigStorage()), json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public List<Long> userTagList(String openid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);

        String responseContent = this.wxMpService.post(TAGS_GETIDLIST.getUrl(wxMpService.getWxMpConfigStorage()),
                json.toString());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent).getAsJsonObject().get("tagid_list"),
                new TypeToken<List<Long>>() {
                }.getType());
    }
}
