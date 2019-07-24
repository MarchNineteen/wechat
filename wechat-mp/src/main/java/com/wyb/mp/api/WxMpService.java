package com.wyb.mp.api;

import com.wyb.common.bean.WxJsapiSignature;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.bean.result.WxMpOAuth2AccessToken;
import com.wyb.mp.bean.result.WxMpUser;
import com.wyb.mp.enums.TicketType;

import java.io.File;
import java.util.Map;

/**
 * @author Kunzite
 * 微信公众号API的service & 请求地址
 */
public interface WxMpService {
    /**
     * 获取access_token.
     */
    String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 获得各种类型的ticket.
     */
    String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=";
    /**
     * 用code换取oauth2的access token.
     */
    String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 刷新oauth2的access token.
     */
    String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    /**
     * 用oauth2获取用户信息.
     */
    String OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";
    /**
     * 验证oauth2的access token是否有效.
     */
    String OAUTH2_VALIDATE_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
    /**
     * 第三方使用网站应用授权登录的url.
     */
    String QRCONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * oauth2授权的url连接. (获取code)
     */
    String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&connect_redirect=1#wechat_redirect";

    /**
     * 获取微信服务器IP地址.
     */
    String GET_CALLBACK_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";

    /**
     * <pre>
     * 验证消息的确来自微信服务器.
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN
     * </pre>
     */
    boolean checkSignature(String timestamp, String nonce, String signature);

    /**
     * 获取access_token, 不强制刷新access_token.
     */
    String getAccessToken() throws WxErrorException;

    /**
     * <pre>
     * 获取access_token，本方法线程安全.
     * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
     *
     * 另：本service的所有方法都会在access_token过期时调用此方法
     *
     * 程序员在非必要情况下尽量不要主动调用此方法
     *
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN
     * </pre>
     *
     * @param forceRefresh 强制刷新
     */
    String getAccessToken(boolean forceRefresh) throws WxErrorException;

    /**
     * 获得ticket,不强制刷新ticket.
     *
     * @see #getTicket(TicketType, boolean)
     */
    String getTicket(TicketType type) throws WxErrorException;

    /**
     * <pre>
     * 获得ticket.
     * 获得时会检查 Token是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     * </pre>
     *
     * @param forceRefresh 强制刷新
     */
    String getTicket(TicketType type, boolean forceRefresh) throws WxErrorException;

    /**
     * 获得jsapi_ticket,不强制刷新jsapi_ticket.
     *
     * @see #getJsapiTicket(boolean)
     */
    String getJsapiTicket() throws WxErrorException;

    /**
     * <pre>
     * 获得jsapi_ticket.
     * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     *
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     * </pre>
     *
     * @param forceRefresh 强制刷新
     */
    String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

    /**
     * <pre>
     * 创建调用jsapi时所需要的签名.
     *
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     * </pre>
     */
    WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;

    /**
     * <pre>
     * 构造第三方使用网站应用授权登录的url.
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站应用微信登录开发指南</a>
     * URL格式为：https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scope       应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
     * @param state       非必填，用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     * @return url
     */
    String buildQrConnectUrl(String redirectURI, String scope, String state);

    /**
     * <pre>
     * 构造oauth2授权的url连接. 即获取code
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @return url
     */
    String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state);

    /**
     * <pre>
     * 用code换取oauth2的access token.
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     */
    WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException;

//    /**
//     * <pre>
//     * 刷新oauth2的access token.
//     * </pre>
//     */
//    WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException;
//
//    /**
//     * <pre>
//     * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以.
//     * </pre>
//     *
//     * @param lang zh_CN, zh_TW, en
//     */
//    WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException;
//
//    /**
//     * <pre>
//     * 验证oauth2的access token是否有效.
//     * </pre>
//     */
//    boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken);

    /**
     * <pre>
     * 获取微信服务器IP地址
     * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
     * </pre>
     */
    String[] getCallbackIP() throws WxErrorException;

    /**
     * <pre>
     * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以.
     * </pre>
     */
    WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken token) throws WxErrorException;


    /**
     * url附加token
     */
    public String get(String url, Map<String, String> params) throws WxErrorException;

    /**
     * url附加token
     */
    public String post(String url, Map<String, String> params) throws WxErrorException;

    /**
     * url附加token
     * jsonString
     */
    public String post(String url, String jsonString) throws WxErrorException;

    /**
     * url附加token
     * 上传文件
     */
    public String postFile(String url, File file) throws WxErrorException;


    /**
     * 获取微信配置
     */
    public WxMpConfigStorage getWxMpConfigStorage();

    /**
     * 注入 {@link WxMpConfigStorage} 的实现.
     */
    void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider);

    public WxMpTemplateMsgService getWxMpTemplateMsgService();

    public WxMpMassMessageService getWxMpMassMessageService();

}
