package com.wyb.demo.controller;

import com.wyb.common.bean.WxJsapiSignature;
import com.wyb.demo.bean.config.WeixinConfig;
import com.wyb.demo.enums.WeixinAuthEnum;
import com.wyb.demo.utils.CommonUtils;
import com.wyb.common.exception.WxErrorException;
import com.wyb.demo.utils.URLUtil;
import com.wyb.demo.utils.WechatMessageUtil;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.result.WxMpOAuth2AccessToken;
import com.wyb.mp.bean.result.WxMpUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kunzite on 2016/9/13.
 */
@Controller
@Slf4j
public class WxBaseController {

    @Resource
    private WxMpService wxMpService;

    /**-------------------------页面授权 s -----------------------------*/
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
        String sortString = CommonUtils.sort(wxMpService.getWxMpConfigStorage().getToken(), timestamp, nonce);
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
    /**-------------------------页面授权 e -----------------------------*/

    /**
     *  PC网页登录获取code
     */
    @RequestMapping(value = "/web/code")
    public void getWebCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String weiXinAuthUrl = wxMpService.buildQrConnectUrl("http://wybcs.wezoz.com/getAccessToken", "snsapi_login", null);
        response.sendRedirect(weiXinAuthUrl);
    }

    /**
     * H5网页授权获取code
     */
    @RequestMapping(value = "/h5/code")
    public void getH5Code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeUrl = wxMpService.oauth2buildAuthorizationUrl("http://m.dev.zerobook.com:8080/getAccessToken", "snsapi_userinfo", null);
        log.info("codeUrl {}", codeUrl);
        response.sendRedirect(codeUrl);
    }

    /**
     * code换取access_token
     */
    @RequestMapping(value = "getAccessToken")
    public String auth(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, WxErrorException {
        String code = request.getParameter("code");
        String status = request.getParameter("state");


        if (StringUtils.isNotBlank(code)) {
//            WeixinAccessTokenEntity weixinTokenEntity = WeixinAuthUtil.getOauthAccessToken(code);
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            log.info("openId {}", accessToken.getOpenId());
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken);
            model.addAttribute("nickName", wxMpUser.getNickname());
            model.addAttribute("openId", wxMpUser.getOpenId());
            model.addAttribute("unionId", accessToken.getUnionId());
            model.addAttribute("accessToken", accessToken.getAccessToken());
            return "/test/success";
        } else {
            //跳转页面
//            response.sendRedirect("");
            return "/test/success";

        }
    }

    @RequestMapping(value = "share")
    public String share(HttpServletRequest request, Model model) throws WxErrorException {
        Map parameters = new HashMap();
        parameters.putAll(request.getParameterMap());
        // submitUrl = URLUtil.addQueryString(getRequest().getRequestURL() + "", parameters);
        String submitUrl = URLUtil.addQueryString("http://wybcs.wezoz.com" + request.getServletPath(), parameters);
        if (StringUtils.isBlank(submitUrl)) {
            // submitUrl = getRequest().getRequestURL() + "";
            submitUrl = request.getRequestURI() + request.getServletPath();
        }
        WxJsapiSignature signature = wxMpService.createJsapiSignature(submitUrl);
        model.addAttribute("signature", signature);
        return "/test/share";
    }

    /**
     * 接收消息
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //接收xml文件
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";

        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String xml = sb.toString(); //即为接收到微信端发送过来的xml数据

        String result = "";
        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
        String echostr = request.getParameter("echostr");
        if (echostr != null && echostr.length() > 1) {
            result = echostr;
        } else {
            //正常的微信处理流程
            result = WechatMessageUtil.processWechatMag(xml);
        }

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
