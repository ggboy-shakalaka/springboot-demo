package com.ggboy.config;

import com.ggboy.common.constant.PropertiesConstant;
import com.ggboy.sms.common.constant.SmsPropertiesConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:config/static.properties")
public class PropertiesConfig {

    @Value("${sequence.default.name}")
    private String sequence_default_name;
    @Value("${default.pageSize}")
    private Integer default_page_size;

    @PostConstruct
    public void setProperties() {
        PropertiesConstant.init(default_page_size, sequence_default_name);
    }

    @Value("${sms.verify.codeLength}")
    private Integer codeLength;
    @Value("${sms.verify.maxTime}")
    private Integer verifyMaxTime;
    @Value("${sms.verify.expTime}")
    private Integer verifyExpTime;
    @PostConstruct
    public void setSmsProperties() {
        SmsPropertiesConstant.init(codeLength, verifyMaxTime, verifyExpTime);
    }
}