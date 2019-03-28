package com.wyb.accout.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyb.accout.utils.HttpUtil;
import com.wyb.accout.utils.MenuUtil;
import com.wyb.accout.utils.WechatMessageUtil;
import com.wyb.accout.utils.WeixinAuthUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Kunzite
 */
@Controller
public class MenuController {

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
