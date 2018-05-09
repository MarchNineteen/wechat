package com.wyb.accout.model.weixin.config;

/**
 * Created by Kunzite on 2016/9/14.
 */
public class WeixinConfig {
    /**
     * 签名加密方式
     */
    public final static String SIGN_TYPE = "MD5";

    /**
     * appid
     */
    public static final String WEIXIN_APPID = "wxc8ec1841f53febcb"; //

    /**
     * SECRET
     */
    public static final String WEIXIN_APPSECRET = "a5afcffe8de3f08002dc6775d9ea674a";//

    /**
     * 商户号
     */
    public static final String WEIXIN_MCHID = "1334581301";//

    /**
     * app_key 商户支付密码
     */
    public static final String WEIXIN_PAY_APPKEY = "Baiyufan123456789012345678901234";

    /**
     * 微信支付之后的异步回掉地址回调
     */
    public static final String WEIXIN_PAY_NOTIFYURL = "http://www.mogo-tech.com/mogu-activity-web/activity/weixin/async/notify.post";

    /**
     * 微信支付 统一预订单接口地址
     */
    public static final String WEIXIN_PAY_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 授权之后的回调地址
     */
    public static final String WEIXIN_AUTH_REDIRECT_URL = "http://wybtxy.tunnel.qydev.com/getAccessToken";

    /**
     * access Token
     */
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //微信获得请求code地址
    public static String WEIXIN_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=" + WEIXIN_AUTH_REDIRECT_URL + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     * 获取用户access_token的url地址
     */
    public static String WEIXIN_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

}
