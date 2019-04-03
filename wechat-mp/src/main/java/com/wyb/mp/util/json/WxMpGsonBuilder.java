package com.wyb.mp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyb.mp.bean.result.WxMpOAuth2AccessToken;
import com.wyb.mp.bean.result.WxMpUser;
import com.wyb.mp.util.json.adapter.WxMpOAuth2AccessTokenGsonAdapter;
import com.wyb.mp.util.json.adapter.WxMpUserGsonAdapter;

public class WxMpGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
