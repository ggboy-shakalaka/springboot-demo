package com.ggboy.sms.service;

import com.ggboy.sms.domain.info.SmsTemplateInfo;

public interface SmsTemplateService {
    SmsTemplateInfo queryForSend(String templateAlias);
}
