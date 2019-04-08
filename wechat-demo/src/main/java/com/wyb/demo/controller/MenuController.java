package com.wyb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wyb.common.util.http.HttpClientUtil;
import com.wyb.demo.utils.MenuUtil;
import com.wyb.demo.utils.WeixinAuthUtil;

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
        // 调用UTI执行创建菜单的动作
        int status = MenuUtil.createMenu(MenuUtil.getMenu());
        if (status == 0) {
            System.out.println("菜单创建成功！");
        }
        else {
            System.out.println("菜单创建失败！");
        }
    }

    /**
     * 查询菜单
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    public String searchMenu() {
        String path = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="
                + WeixinAuthUtil.getAccessToken().getAccesstoken();
        JSONObject jsonObject = HttpClientUtil.doGetJson(path);
        return jsonObject.toJSONString();
    }

    /**
     * 删除菜单
     */
    // @RequestMapping(value = "/delete")
    // public void deleteMenu(){
    // String path="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+
    // WeixinAuthUtil.getAccessToken().getAccesstoken();
    // JSONObject jsonObject = HttpUtil.requestJSON(path, null);
    // String jsonObject1 = JSONObject.toJSONString(jsonObject);
    // }

    public void createOrder() {

    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public void deleteMenu() {
        String path = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
                + WeixinAuthUtil.getAccessToken().getAccesstoken();
        JSONObject jsonObject = HttpClientUtil.doGetJson(path);
        jsonObject.toJSONString();
    }

}
