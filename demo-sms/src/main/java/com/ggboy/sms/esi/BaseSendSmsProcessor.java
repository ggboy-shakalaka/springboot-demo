package com.ggboy.sms.esi;

import java.util.Map;

public interface BaseSendSmsProcessor {
    String sendSms(Map<String, String> params) throws Exception;
}
