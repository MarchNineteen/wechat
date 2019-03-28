package com.wyb.accout.controller;

import com.wyb.accout.enums.WeixinAuthEnum;
import com.wyb.accout.model.weixin.config.WeixinConfig;
import com.wyb.accout.model.weixin.entity.WeixinAccessTokenEntity;
import com.wyb.accout.model.weixin.entity.WeixinUserInfoEntity;
import com.wyb.accout.utils.*;
import com.wyb.open.api.WxMpConfigStorage;
import com.wyb.open.api.WxMpService;
import com.wyb.open.api.impl.WxMpInMemoryConfigStorage;
import com.wyb.open.api.impl.WxMpServiceImpl;
import com.wyb.open.bean.result.WxMpOAuth2AccessToken;
import com.wyb.open.common.exception.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Kunzite on 2016/9/13.
 */
@Controller
public class WexinController {

    private final String token = "asfasdjasbdgjayfguajsvdjasbdjaygdas";

    /**-------------------------页面授权------------------------------------*/
    /**
     * token 校验
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "token")
    public void doGetToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);
        //排序
        String sortString = CommonUtils.sort(token, timestamp, nonce);
        //加密
        String mytoken = CommonUtils.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            System.out.println("签名校验通过。");
            response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            System.out.println("签名校验失败。");
        }
    }


    /**
     * 微信授权获取code
     */
    @RequestMapping(value = "/code")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String weiXinAuthUrl = WeixinConfig.WEIXIN_CODE.replace("APPID", WeixinConfig.WEIXIN_APPID).replace("STATE", WeixinAuthEnum.AUTHTYPE_WEIXINPAY.getCode());
        response.sendRedirect(weiXinAuthUrl);
    }

    /**
     * code换取access_token
     */
    @RequestMapping(value = "getAccessToken")
    public String auth(HttpServletRequest request, HttpServletResponse response) throws IOException, WxErrorException {
        String code = request.getParameter("code");
        String status = request.getParameter("state");


        if (StringUtils.isNotBlank(code)) {
//            WeixinAccessTokenEntity weixinTokenEntity = WeixinAuthUtil.getOauthAccessToken(code);

            WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204");
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
            WxMpOAuth2AccessToken accessToekn = wxMpService.oauth2getAccessToken(code);

            return "/test/success";
        } else {
            //跳转页面
//            response.sendRedirect("");
            return "/test/success";

        }
    }

}
