package com.wyb.mp.bean.menu;

import java.io.Serializable;

import com.wyb.common.util.json.WxGsonBuilder;
import com.wyb.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import com.google.gson.annotations.SerializedName;

@Data
public class WxMpGetSelfMenuInfoResult implements Serializable {
  private static final long serialVersionUID = -5612495636936835166L;

  @SerializedName("selfmenu_info")
  private WxMpSelfMenuInfo selfMenuInfo;

  @SerializedName("is_menu_open")
  private Integer isMenuOpen;

  public static WxMpGetSelfMenuInfoResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMpGetSelfMenuInfoResult.class);
  }

  @Override
  public String toString() {
    return WxMpGsonBuilder.create().toJson(this);
  }

}
