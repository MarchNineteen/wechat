package com.wyb.mp.qq.util.json.adapter;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.wyb.common.util.json.GsonHelper;
import com.wyb.mp.qq.bean.result.QqUserInfo;

/**
 * @author wangyb
 * @version $$ Revision: 1.0 $$, $$ Date: 2019/4/11 10:37 $$
 */
public class QqUserInfoGsonAdapter implements JsonDeserializer<QqUserInfo> {

    @Override
    public QqUserInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject o = json.getAsJsonObject();
        QqUserInfo info = new QqUserInfo();
        info.setRet(GsonHelper.getString(o, "ret"));
        info.setMsg(GsonHelper.getString(o, "msg"));
        info.setNickname(GsonHelper.getString(o, "nickname"));
        info.setFigureUrl(GsonHelper.getString(o, "figureurl"));
        info.setFigureUrl_1(GsonHelper.getString(o, "figureurl_1"));
        info.setFigureUrl_2(GsonHelper.getString(o, "figureurl_2"));
        info.setFigureUrlQq_1(GsonHelper.getString(o, "figureurl_qq_1"));
        info.setFigureUrlQq_2(GsonHelper.getString(o, "figureurl_qq_2"));
        info.setGender(GsonHelper.getString(o, "gender"));
        return info;
    }
}
