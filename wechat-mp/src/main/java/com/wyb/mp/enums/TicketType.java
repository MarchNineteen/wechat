package com.wyb.mp.enums;

import lombok.Getter;

/**
 * <pre>
 * ticket类型枚举
 * </pre>
 */
@Getter
public enum TicketType {
  /**
   * jsapi
   */
  JSAPI("jsapi"),
  /**
   * sdk
   */
  SDK("2"),
  /**
   * 微信卡券
   */
  WX_CARD("wx_card");
  /**
   * type代码
   */
  private String code;

  TicketType(String code) {
    this.code = code;
  }
}
