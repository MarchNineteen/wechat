package com.wyb.accout.model.weixin.entity;

import java.util.Date;

/**
 * Created by Kunzite on 2016/10/10.
 * 封装最终的xml格式结果
 */
public class FormatXml {
    /**
     * 封装文字类的返回消息
     *
     * @param to
     * @param from
     * @param content
     * @return
     */
    public static String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }
}
