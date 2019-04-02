package com.wyb.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyb.common.exception.WxError;

/**
 * @author Kunzite
 */
public class WxGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
