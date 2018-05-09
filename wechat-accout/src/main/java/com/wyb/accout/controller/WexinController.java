package com.wyb.accout.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyb.accout.enums.WeixinAuthEnum;
import com.wyb.accout.model.weixin.config.WeixinConfig;
import com.wyb.accout.model.weixin.entity.WeixinUserInfoEntity;
import com.wyb.accout.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        String weiXinAuthUrl = WeixinConfig.WEIXIN_CODE.replace("APPID",WeixinConfig.WEIXIN_APPID).replace("STATE", WeixinAuthEnum.AUTHTYPE_WEIXINPAY.getCode());
        response.sendRedirect(weiXinAuthUrl);
    }

    /**
     * code换取access_token
     */
    @RequestMapping(value = "getAccessToken")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String status = request.getParameter("state");
        if (StringUtils.isNotBlank(code)) {
            WeixinUserInfoEntity weixinTokenEntity = WeixinAuthUtil.getWeiXinUserInfoByCode(code);

            return;
        } else {
            //跳转页面
            response.sendRedirect("");
            return;
        }
    }

    /**
     * 创建菜单
     */
    @RequestMapping("/createMenu")
    public void createMenu() {
        //调用UTI执行创建菜单的动作
        int status = MenuUtil.createMenu(MenuUtil.getMenu());
        if (status == 0) {
            System.out.println("菜单创建成功！");
        } else {
            System.out.println("菜单创建失败！");
        }
    }

    /**
     * 查询菜单
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    public String searchMenu() {
        String path = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" +WeixinAuthUtil.getAccessToken().getAccesstoken();
        JSONObject jsonObject = HttpUtil.requestJSON(path, null);
        String jsonObject1 = JSONObject.toJSONString(jsonObject);
        return jsonObject1;
    }
    /**
     *删除菜单
     */
//    @RequestMapping(value = "/delete")
//    public void deleteMenu(){
//        String path="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+ WeixinAuthUtil.getAccessToken().getAccesstoken();
//        JSONObject jsonObject = HttpUtil.requestJSON(path, null);
//        String jsonObject1 = JSONObject.toJSONString(jsonObject);
//    }

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

    public void createOrder() {

    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public void deleteMenu() {
        String path = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + WeixinAuthUtil.getAccessToken().getAccesstoken();
        JSONObject jsonObject = HttpUtil.requestJSON(path, null);
        String jsonObject1 = JSONObject.toJSONString(jsonObject);
    }
}
