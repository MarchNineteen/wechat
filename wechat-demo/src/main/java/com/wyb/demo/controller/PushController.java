package com.wyb.demo.controller;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.function.Supplier;

import javax.annotation.Resource;

import com.wyb.common.util.http.HttpClientUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.wyb.common.api.WxConsts;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.message.WxMpMassPreviewMessage;
import com.wyb.mp.bean.result.WxMpMassSendResult;

/**
 * @author Marcher丶
 */
@RestController
public class PushController {

    private static String PUSH_URL = "https://sc.ftqq.com/SCU126345Tc4982a62552749201c028da63ceda18f5fae2f827b82b.send";
    @Resource
    private WxMpService wxMpService;

    @Scheduled(cron = "0 0 0-23 29 11 ?")
    public void pushBirthDayMessage() throws WxErrorException {
        WxMpMassPreviewMessage massMessage = new WxMpMassPreviewMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent(contentSupplier.get());
        massMessage.setToWxUserOpenid("odnet5jB84uLoCcaPjv-eYtx0pHA");

        WxMpMassSendResult massResult = this.wxMpService.getWxMpMassMessageService()
                .massMessagePreview(massMessage);
        System.out.println(massResult.toString());
    }

    @Scheduled(cron = "0 0 0-23 19 11 ?")
    public void test() throws WxErrorException {
        WxMpMassPreviewMessage massMessage = new WxMpMassPreviewMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent(contentSupplier.get());
        massMessage.setToWxUserOpenid("odnet5jB84uLoCcaPjv-eYtx0pHA");

        WxMpMassSendResult massResult = this.wxMpService.getWxMpMassMessageService()
                .massMessagePreview(massMessage);
        HashMap map = new HashMap();
        map.put("text", massResult.toString());
        map.put("desp", massMessage.getContent());
        HttpClientUtil.doPost(PUSH_URL, map);
    }

    static Supplier<String> contentSupplier = () -> {
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();
        switch (hour) {
        case 0:
            return "今天你出生啦！1岁的小宝宝，世间从此又多了一位小仙女，爸爸妈妈今天一定很开心！我在你255天之前先来到这个世界，刚出生的2个婴儿25年之后才会相遇。祝你平安长大，生日快乐！";
        case 1:
            return "今天你1周岁啦！小仙女的周岁肯定有很多亲戚朋友，热热闹闹的。在大家的欢声笑语中，祝你平安健康，生日快乐！";
        case 2:
            return "今天你2周岁啦！2岁的小仙女应该蹒跚学步，踉跄前行了，你走路摇摇摆摆的样子一定很可爱。祝你越长越快，生日快乐！";
        case 3:
            return "今天你3周岁啦！";
        case 4:
            return "今天你4周岁啦！";
        case 5:
            return "今天你5周岁啦！";
        case 6:
            return "今天你6周岁啦！";
        case 7:
            return "今天你7周岁啦！";
        case 8:
            return "今天你8周岁啦！";
        case 9:
            return "今天你9周岁啦！";
        case 10:
            return "今天你10周岁啦！";
        case 11:
            return "今天你11周岁啦！";
        case 12:
            return "今天你12周岁啦！";
        case 13:
            return "今天你13周岁啦！";
        case 14:
            return "今天你14周岁啦！";
        case 15:
            return "今天你15周岁啦！";
        case 16:
            return "今天你16周岁啦！";
        case 17:
            return "今天你17周岁啦！";
        case 18:
            return "今天你18周岁啦！";
        case 19:
            return "今天你19周岁啦！";
        case 20:
            return "今天你20周岁啦！";
        case 21:
            return "今天你21周岁啦！";
        case 22:
            return "今天你22周岁啦！";
        case 23:
            return "今天你23周岁啦！";
        default:
            return "啦啦啦啦啦啦啦！";
        }
    };

    public static void main(String[] args) throws WxErrorException {
        PushController controller = new PushController();
        System.out.println(contentSupplier.get());
    }
}
