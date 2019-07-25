package com.wyb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyb.mp.qq.api.QqApiService;
import com.wyb.mp.qq.bean.result.QqOAuth2AccessToken;
import com.wyb.mp.qq.bean.result.QqUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "qq")
public class QqController {

    @Resource
    private QqApiService qqApiService;

    /**
     * 网页登录获取code
     */
    @RequestMapping(value = "/web/code")
    public void getWebCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String weiXinAuthUrl = qqApiService.qqAuth2buildAuthorizationUrl("http://wybcs.wezoz.com/qq/getAccessToken", "11", "get_user_info", null);
        response.sendRedirect(weiXinAuthUrl);
    }


    /**
     * code换取access_token
     */
    @RequestMapping(value = "/getAccessToken")
    public String auth(HttpServletRequest request, Model model) throws IOException {
        String code = request.getParameter("code");
        String status = request.getParameter("state");
        if (StringUtils.isNotBlank(code)) {
//          // 回调地址
            String redirectUri = "http://wybcs.wezoz.com/qq/getAccessToken";
            QqOAuth2AccessToken token = qqApiService.qqAuth2getAccessToken("authorization_code", code, redirectUri);
            if (null == token) ;
            QqUserInfo qqUserInfo = qqApiService.getUserInfo(token);
            if (!"0".equals(qqUserInfo.getRet())) {
                System.out.println(qqUserInfo.getMsg());
            }
            else {
                System.out.println(qqUserInfo.toString());
            }
            model.addAttribute("nickName", qqUserInfo.getNickname());
            model.addAttribute("openId", token.getOpenId());
            model.addAttribute("accessToken", token.getAccessToken());
            return "/test/success";
        } else {
            //跳转页面
//            response.sendRedirect("");
            return "/test/success";

        }
    }

    @RequestMapping(value = "/redirect")
    public String redirect(HttpServletRequest request, Model model) throws IOException {
        return "test/qqLogin";
    }

    /**
     * qq js sdk 登录
     */
    @RequestMapping(value = "/h5Login")
    public String h5Login(HttpServletRequest request, Model model) throws IOException {
        return "";
    }

}
