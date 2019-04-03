package com.wyb.demo.enums;

/**
 *
 * @author Kunzite
 */
public enum WeixinAuthEnum {

    AUTHTYPE_WEIXINPAY("WEIXINPAY", 1001, "支付授权"),

    /**
     * 登陆授权
     */
    AUTHTYPE_WEIXINLOGIN("WEIXINLOGIN", 1002, "登陆授权");

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 数值型错误码
     */
    private final int codeNumber;

    /**
     * 枚举信息
     */
    private final String description;

    /**
     * 构造函数
     *
     * @param code        枚举值
     * @param codeNumber  数值型错误码
     * @param description 枚举信息
     */
    WeixinAuthEnum(String code, int codeNumber, String description) {
        this.code = code;
        this.codeNumber = codeNumber;
        this.description = description;
    }

    /**
     * 根据code获取枚举
     *
     * @param code
     * @return
     */
    public static WeixinAuthEnum getByCode(String code) {
        for (WeixinAuthEnum item : values()) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

}
