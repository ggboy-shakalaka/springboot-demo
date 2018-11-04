package com.ggboy.sms.service.impl;

import com.ggboy.sms.common.convert.SmsConvert;
import com.ggboy.sms.common.enums.SmsTamplateStatus;
import com.ggboy.sms.domain.DO.SmsTemplateDO;
import com.ggboy.sms.domain.info.SmsTemplateInfo;
import com.ggboy.sms.mapper.SmsTemplateMapper;
import com.ggboy.sms.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private final static String[] QUERY_FOR_SEND = "template_alias,interval_time,interval_frequency,ext1".split(",");

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    public SmsTemplateInfo queryForSend(String templateAlias) {
        SmsTemplateDO smsTemplateDO = new SmsTemplateDO();
        smsTemplateDO.setTemplateAlias(templateAlias);
        smsTemplateDO.setStatus(SmsTamplateStatus.enable.name());
        smsTemplateDO = smsTemplateMapper.selectOne(QUERY_FOR_SEND, smsTemplateDO);
        return SmsConvert.convertToSmsTemplateInfo(smsTemplateDO);
    }
}