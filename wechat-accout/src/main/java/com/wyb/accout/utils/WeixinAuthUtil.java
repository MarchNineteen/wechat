package com.wyb.accout.utils;

import com.alibaba.fastjson.JSONObject;
import com.wyb.accout.model.weixin.config.WeixinConfig;
import com.wyb.accout.model.weixin.entity.WeixinAccessTokenEntity;
import com.wyb.accout.model.weixin.entity.WeixinUserInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinAuthUtil {

    private static Logger logger = LoggerFactory.getLogger(WeixinAuthUtil.class);
    /**
     * 获取用户access_token的url地址
     */
    private static String WEIXIN_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /**
     * 获取用户详细信息，的url地址
     */
    private static String WEIXIN_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * code换取access_token
     *
     * @param code
     * @return
     */
    public static WeixinAccessTokenEntity getOauthAccessToken(String code) {
        // 根据用户授权后返回的code拼接获取该用户access_token的地址
        String _reqUrl = WEIXIN_ACCESSTOKEN_URL;
        _reqUrl = _reqUrl.replace("APPID", WeixinConfig.WEIXIN_APPID);
        _reqUrl = _reqUrl.replace("SECRET", WeixinConfig.WEIXIN_APPSECRET);
        _reqUrl = _reqUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpUtil.requestJSON(_reqUrl, null);
        String errorCode = jsonObject.getString("errcode");
        String errorMsg = jsonObject.getString("errmsg");

        if (StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(errorMsg)) {
//            throw new BizException(BizResultCodeEnum.ILLEGAL_ARGUMENT, "code get openid fail:" + JSONObject.toJSONString(jsonObject));
        }
        WeixinAccessTokenEntity ate = new WeixinAccessTokenEntity();
        ate.setAccesstoken(jsonObject.getString("access_token"));
        ate.setExpiresin(jsonObject.getIntValue("expires_in"));
        ate.setRefreshToken(jsonObject.getString("refresh_token"));
        ate.setOpenId(jsonObject.getString("openid"));
        ate.setScope(jsonObject.getString("scope"));
        ate.setErrcode(jsonObject.getString("errcode"));
        ate.setErrmsg(jsonObject.getString("errmsg"));
        logger.error("get auth token =========================================");
        logger.error("get auth token ：" + JSONObject.toJSONString(jsonObject));
        logger.error("get auth token =========================================");
        return ate;
    }

    /**
     * 根据获得的accessToken获取用户详细信息
     *
     * @param accessToken
     * @param openId
     * @return
     * @throws java.io.IOException
     */
    public static JSONObject getWeiXinUserInfo(String accessToken, String openId) {
        String _reqUserInfoUrl = WEIXIN_USERINFO_URL;
        _reqUserInfoUrl = _reqUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        JSONObject weixinUserInfo = HttpUtil.requestJSON(_reqUserInfoUrl, null);
        return weixinUserInfo;
    }


    /**
     * 获取用户信息
     *
     * @param code
     * @return
     */
    public static WeixinUserInfoEntity getWeiXinUserInfoByCode(String code) {
        WeixinUserInfoEntity infoVo = new WeixinUserInfoEntity();
        WeixinAccessTokenEntity tokenEntity = WeixinAuthUtil.getOauthAccessToken(code);
        logger.info("code get access_token info ==========================================");
        logger.info("code get access_token info:" + JSONObject.toJSONString(tokenEntity));
        logger.info("code get access_token info ==========================================");
        String accToken = tokenEntity.getAccesstoken();
        String openId = tokenEntity.getOpenId();
        if (tokenEntity.isSuccess()) {
            JSONObject infoJson = WeixinAuthUtil.getWeiXinUserInfo(accToken, openId);
            infoVo.setOpenid(infoJson.getString("openid"));
            infoVo.setUnionid(infoJson.getString("unionid"));
            infoVo.setNickname(infoJson.getString("nickname"));
            infoVo.setSex(infoJson.getString("sex"));
            infoVo.setProvince(infoJson.getString("province"));
            infoVo.setCity(infoJson.getString("city"));
            infoVo.setCountry(infoJson.getString("country"));
            infoVo.setHeadimgurl(infoJson.getString("headimgurl"));
            infoVo.setPrivilege(infoJson.getString("privilege"));
        }
        if (StringUtils.isNotBlank(tokenEntity.getErrcode())) {
            infoVo.setErrcode(tokenEntity.getErrcode());
        }
        if (StringUtils.isNotBlank(tokenEntity.getErrmsg())) {
            infoVo.setErrmsg(tokenEntity.getErrmsg());
        }
        return infoVo;
    }
//
//
//    public static void main(String[] args) {
//        String code = "031tQVFc0jKvbH14Z4Ec03EYFc0tQVF-";
//        String appId = "wxa5cb2516251c6942";
//        System.out.println(JSONObject.toJSONString(getWeiXinUserInfoByCode("031MTTNg2gJyvN0Z0lNg2GBTNg2MTTN5")));
//
//
//    }

    /**
     * 获取access_token
     */
    public static WeixinAccessTokenEntity getAccessToken() {
        String url = WeixinConfig.ACCESS_TOKEN;
//        url.replace("APPID",WeixinConfig.WEIXIN_APPID).replace("APPSECRET",WeixinConfig.WEIXIN_APPSECRET);
        url = url.replace("APPID", WeixinConfig.WEIXIN_APPID);
        url = url.replace("APPSECRET", WeixinConfig.WEIXIN_APPSECRET);
        JSONObject jsonObject = HttpUtil.requestJSON(url, null);
        String errcode = jsonObject.getString("errcode");
        String errmsg = jsonObject.getString("errmsg");
        if (StringUtils.isNotBlank(errcode) || StringUtils.isNotBlank(errmsg)) {
//            throw new BizException(BizResultCodeEnum.ILLEGAL_ARGUMENT, "code get openid fail:" + JSONObject.toJSONString(jsonObject));
        }
        WeixinAccessTokenEntity ate = new WeixinAccessTokenEntity();
        ate.setAccesstoken(jsonObject.getString("access_token"));
        ate.setExpiresin(jsonObject.getIntValue("expires_in"));
        logger.error("get auth token =========================================");
        logger.error("get auth token ：" + JSONObject.toJSONString(jsonObject));
        logger.error("get auth token =========================================");
        return ate;
    }
}
