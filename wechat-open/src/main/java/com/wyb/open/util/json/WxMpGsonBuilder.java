package com.wyb.open.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyb.open.bean.result.WxMpOAuth2AccessToken;
import com.wyb.open.util.json.adapter.WxMpOAuth2AccessTokenAdapter;

public class WxMpGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
