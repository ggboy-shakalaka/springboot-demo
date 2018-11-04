package com.ggboy.config;

import com.ggboy.sms.common.enums.SmsChannel;
import com.ggboy.sms.esi.BaseSendSmsProcessor;
import com.ggboy.sms.esi.SendSmsProcessor;
import com.ggboy.sms.esi.ali.AliSendSmsProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:config/sms.properties")
public class SmsConfig {
    @Value("${sms.aliyun.product}")
    private String product;
    @Value("${sms.aliyun.domain}")
    private String domain;
    @Value("${sms.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public SendSmsProcessor sendSmsProcessor() throws Exception {
        Map<SmsChannel, BaseSendSmsProcessor> map = new HashMap<>(1);
        map.put(SmsChannel.ali, new AliSendSmsProcessor(product, domain, accessKeyId, accessKeySecret));
        return new SendSmsProcessor(map);
    }
}
