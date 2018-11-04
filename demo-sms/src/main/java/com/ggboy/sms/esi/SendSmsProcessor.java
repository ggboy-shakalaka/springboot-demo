package com.ggboy.sms.esi;

import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.InternalException;
import com.ggboy.sms.common.enums.SmsChannel;

import java.util.Map;

public class SendSmsProcessor {
    private Map<SmsChannel, BaseSendSmsProcessor> processorMap;

    public SendSmsProcessor(Map<SmsChannel, BaseSendSmsProcessor> processorMap) {
        this.processorMap = processorMap;
    }

    public String sendSms(Map<String, String> param, SmsChannel channel) {
        BaseSendSmsProcessor processor = processorMap.get(channel);
        if (processor == null)
            throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "channel not defined");
        try {
            return processor.sendSms(param);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }
}