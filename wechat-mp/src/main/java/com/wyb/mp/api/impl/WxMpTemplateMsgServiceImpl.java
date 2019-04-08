package com.wyb.mp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wyb.common.exception.WxError;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.api.WxMpTemplateMsgService;
import com.wyb.mp.bean.template.WxMpTemplate;
import com.wyb.mp.bean.template.WxMpTemplateMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信模板消息推送
 *
 * @author Kunzite
 */
public class WxMpTemplateMsgServiceImpl implements WxMpTemplateMsgService {

    public static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/template";
    private static final JsonParser JSON_PARSER = new JsonParser();

    private WxMpService wxMpService;

    public WxMpTemplateMsgServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public String addTemplate(String shortTemplateId) throws WxErrorException {
        String url = API_URL_PREFIX + "/api_add_template";
        Map<String, String> map = new HashMap<>();
        map.put("template_id_short", shortTemplateId);
        String responseContent = wxMpService.post(url, map);
        final JsonObject result = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (result.get("errcode").getAsInt() == 0) {
            return result.get("template_id").getAsString();
        }
        throw new WxErrorException(WxError.fromJson(responseContent));
    }

    @Override
    public List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException {
        String url = API_URL_PREFIX + "/get_all_private_template";
        return WxMpTemplate.fromJson(this.wxMpService.get(url, null));
    }

    @Override
    public String sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
        String responseContent = this.wxMpService.post(url, templateMessage.toJson());
        final JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode").getAsInt() == 0) {
            return jsonObject.get("msgid").getAsString();
        }
        throw new WxErrorException(WxError.fromJson(responseContent));
    }

}
