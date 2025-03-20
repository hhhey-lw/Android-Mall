package com.wei.diploma_project.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.*;
import com.wei.diploma_project.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

/**
 * Date: 2023/5/8
 * description:
 */
@Component
public class MessageUtil {
    private final String accessKeyId = "???";
    private final String accessKeySecret = "???";
    private final Random random = new Random();

    @Autowired
    private RedisService redisService;

    public com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public void sendMessage(String phoneNumber) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = createClient(accessKeyId, accessKeySecret);
        HashMap<String, String> map = new HashMap<>();
        String code = getRandomCode();
        map.put("code", code);
        String param = JSONObject.toJSONString(map);
        SendSmsRequest smsRequest = new SendSmsRequest()
                .setSignName("weilong")
                .setTemplateCode("SMS_460720795")
                .setPhoneNumbers("?")
                .setTemplateParam(param);
        SendSmsResponse smsResponse = client.sendSms(smsRequest);
        System.out.println(smsResponse.getBody().getMessage());
        cacheCode(phoneNumber, code);
    }

    private String getRandomCode() {
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    private boolean cacheCode(String phoneNumber, String code) {
        int duration = 5 * 60;
        return redisService.cacheValue(phoneNumber, code, duration);
    }
}
