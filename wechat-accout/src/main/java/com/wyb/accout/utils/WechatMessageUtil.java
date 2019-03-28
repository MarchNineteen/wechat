package com.wyb.accout.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wyb.accout.bean.entity.FormatXml;
import com.wyb.accout.bean.entity.ReceiveXmlEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author Kunzite
 * @date 2016/10/10
 */
public class WechatMessageUtil {
    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     *
     * @param xml 接收到的微信数据
     * @return 最终的解析结果（xml格式数据）
     */
    public static String processWechatMag(String xml) {
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = getMsgEntity(xml);

        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "";
//        if("text".endsWith(xmlEntity.getMsgType())){
//            result = getTulingResult(xmlEntity.getContent());
//        }
//
//        /** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
//         *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
//         * */
        result = FormatXml.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), "你也好");

        return result;
    }

    /**
     * 解析微信xml消息
     *
     * @param strXml
     * @return
     */
    public static ReceiveXmlEntity getMsgEntity(String strXml) {
        ReceiveXmlEntity msg = null;
        try {
            if (strXml.length() <= 0 || strXml == null)
                return null;

            // 将字符串转化为XML文档对象
            Document document = DocumentHelper.parseText(strXml);
            // 获得文档的根节点
            Element root = document.getRootElement();
            // 遍历根节点下所有子节点
            Iterator<?> iter = root.elementIterator();

            // 遍历所有结点
            msg = new ReceiveXmlEntity();
            //利用反射机制，调用set方法
            //获取该实体的元类型
            Class<?> c = Class.forName("com.wyb.accout.bean.entity.ReceiveXmlEntity");
            msg = (ReceiveXmlEntity) c.newInstance();//创建这个实体的对象

            while (iter.hasNext()) {
                Element ele = (Element) iter.next();
                //获取set方法中的参数字段（实体类的属性）
                Field field = c.getDeclaredField(ele.getName());
                //获取set方法，field.getType())获取它的参数数据类型
                Method method = c.getDeclaredMethod("set" + ele.getName(), field.getType());
                //调用set方法
                method.invoke(msg, ele.getText());
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("xml 格式异常: " + strXml);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     *
     * @param content
     * @return
     */
    public static String getTulingResult(String content) {
        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
        String apiUrl = "http://www.tuling123.com/openapi/api?key=11111111&info=";
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //将参数转为url编码

        /** 发送httpget请求 */
        HttpGet request = new HttpGet(param);
        String result = "";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 请求失败处理 */
        if (null == result) {
            return "对不起，你说的话真是太高深了……";
        }

        try {
            JSONObject json = JSONObject.parseObject(result);
            //以code=100000为例，参考图灵机器人api文档
            if (100000 == json.getInteger("code")) {
                result = json.getString("text");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
