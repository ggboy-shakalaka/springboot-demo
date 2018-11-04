package com.ggboy.sms.common.validator;

import com.ggboy.common.utils.Assert;
import com.ggboy.sms.domain.info.SmsSendDetailInfo;

public class SmsValidator {
    public final static void verifyCheckSendIsFrequentlyParam(String templateAlias, String receiver) {
        Assert.isNotNull(templateAlias, "templateAlias is empty");
        Assert.isNotNull(receiver, "receiver is empty");
    }

    public final static void verifyInsertSmsSendDetail(SmsSendDetailInfo info) {
        Assert.isNotNull(info, "info is null");
        Assert.isNotNull(info.getSmsId(), "smsId is null");
        Assert.isNotNull(info.getTemplateAlias(), "templateAlias is null");
        Assert.isNotNull(info.getReceiver(), "receiver is null");

        Assert.maxLength(info.getSmsId(), 32, "smsId is too long");
        Assert.maxLength(info.getTemplateAlias(), 32, "templateAlias is too long");
        Assert.maxLength(info.getReceiver(), 32, "receiver is too long");
        Assert.maxLength(info.getContent(), 255, "content is too long");
    }
}
