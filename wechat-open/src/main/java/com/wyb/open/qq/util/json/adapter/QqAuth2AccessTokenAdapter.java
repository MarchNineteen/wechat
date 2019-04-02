package com.wyb.open.qq.util.json.adapter;

import com.google.gson.*;
import com.wyb.common.util.json.GsonHelper;
import com.wyb.open.qq.bean.result.QqOAuth2AccessToken;

import java.lang.reflect.Type;

/**
 * @author wangyb
 */
public class QqAuth2AccessTokenAdapter implements JsonDeserializer<QqOAuth2AccessToken> {

  @Override
  public QqOAuth2AccessToken deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
    JsonParseException {
    QqOAuth2AccessToken accessToken = new QqOAuth2AccessToken();
    JsonObject accessTokenJsonObject = json.getAsJsonObject();

    if (accessTokenJsonObject.get("access_token") != null && !accessTokenJsonObject.get("access_token").isJsonNull()) {
      accessToken.setAccessToken(GsonHelper.getAsString(accessTokenJsonObject.get("access_token")));
    }
    if (accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
      accessToken.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
    }
    if (accessTokenJsonObject.get("refresh_token") != null && !accessTokenJsonObject.get("refresh_token").isJsonNull()) {
      accessToken.setRefreshToken(GsonHelper.getAsString(accessTokenJsonObject.get("refresh_token")));
    }
    return accessToken;
  }

}
