package com.wyb.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.wyb.demo.bean.entity.Button.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Kunzite
 * @date 2016/9/29
 * 创建菜单的Util
 */
public class MenuUtil {
    /**
     * 封装菜单数据
     */
    public static Menu getMenu() {
        //首先创建二级菜单
        CommonButton cb_1 = new CommonButton();
        cb_1.setKey("V1001_TODAY_MUSIC");
        cb_1.setName("今日歌曲");
        cb_1.setType("click");


        CommonButton cb_2 = new CommonButton();
        cb_2.setKey("V1001_GOOD");
        cb_2.setName("赞一下我们");
        cb_2.setType("click");

        //创建第一个一级菜单
        ComplexButton cx_1 = new ComplexButton();
        cx_1.setName("click");
        cx_1.setSub_button(new Button[]{cb_1, cb_2});


        //继续创建二级菜单
        ViewButton cb_3 = new ViewButton();
        cb_3.setUrl("http://v.qq.com");
        cb_3.setName("搜索");
        cb_3.setType("view");


//        ViewButton cb_4 = new ViewButton();
//        cb_4.setName("百度");
//        cb_4.setType("view");
        //需要使用网页授权获取微信用户的信息
//        cb_4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=要访问的网页 &response_type=code&scope=snsapi_base&state=xxx");
//        cb_4.setUrl("https://www.baidu.com");

        ViewButton cb_5 = new ViewButton();
        cb_5.setName("测试授权登录");
        cb_5.setType("view");
        //需要使用网页授权获取微信用户的信息
        cb_5.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx555e719fb5428862&redirect_uri=http://wybcs.wezoz.com/getAccessToken&response_type=code&scope=snsapi_userinfo&state=1001#wechat_redirect");
//        cb_4.setUrl("http://wybcs.wezoz.com/code");

        //创建第二个一级菜单
        ComplexButton cx_2 = new ComplexButton();
        cx_2.setName("view");
        cx_2.setSub_button(new Button[]{cb_3, cb_5});

        //封装菜单数据
        Menu menu = new Menu();
        menu.setButton(new ComplexButton[]{cx_1, cx_2});

        return menu;
    }

    /**
     * 创建自定义菜单(每天限制1000次)
     */
    public static int createMenu(Menu menu) {
        String jsonMenu = JSONObject.toJSONString(menu);

        int status = 0;

        System.out.println("菜单：" + jsonMenu);
        String path = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + WeixinAuthUtil.getAccessToken().getAccesstoken();
        try {
            URL url = new URL(path);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(jsonMenu.getBytes("UTF-8"));
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message = new String(bt, "UTF-8");
            JSONObject jsonMsg = JSONObject.parseObject(message);
            status = Integer.parseInt(jsonMsg.getString("errcode"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }


}
