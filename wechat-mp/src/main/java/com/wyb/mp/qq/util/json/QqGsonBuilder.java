package com.wyb.mp.qq.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyb.mp.qq.util.json.adapter.QqAuth2AccessTokenAdapter;
import com.wyb.mp.qq.bean.result.QqOAuth2AccessToken;

/**
 * @author Kunzite
 */
public class QqGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(QqOAuth2AccessToken.class, new QqAuth2AccessTokenAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
