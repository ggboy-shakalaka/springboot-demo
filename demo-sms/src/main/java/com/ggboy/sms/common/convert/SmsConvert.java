package com.ggboy.sms.common.convert;

import com.ggboy.sms.domain.DO.SmsSendDetailDO;
import com.ggboy.sms.domain.DO.SmsTemplateDO;
import com.ggboy.sms.domain.info.SmsSendDetailInfo;
import com.ggboy.sms.domain.info.SmsTemplateInfo;

public class SmsConvert {
    public final static SmsTemplateInfo convertToSmsTemplateInfo(SmsTemplateDO smsTemplateDO) {
        if (smsTemplateDO == null)
            return null;

        SmsTemplateInfo info = new SmsTemplateInfo();
        info.setTemplateAlias(smsTemplateDO.getTemplateAlias());
        info.setTemplateCategory(smsTemplateDO.getTemplateCategory());
        info.setTemplateName(smsTemplateDO.getTemplateName());
        info.setIntervalTime(smsTemplateDO.getIntervalTime());
        info.setIntervalFrequency(smsTemplateDO.getIntervalFrequency());
        info.setStatus(smsTemplateDO.getStatus());
        info.setCreateTime(smsTemplateDO.getCreateTime());
        info.setModifyTime(smsTemplateDO.getModifyTime());
        info.setMessage(smsTemplateDO.getMessage());
        info.setExt1(smsTemplateDO.getExt1());

        return info;
    }

    public final static SmsSendDetailDO convertToCheckSendIsFrequentlyDO(String templateAlias, String receiver) {
        SmsSendDetailDO smsSendDetailDO = new SmsSendDetailDO();
        smsSendDetailDO.setTemplateAlias(templateAlias);
        smsSendDetailDO.setReceiver(receiver);
        return smsSendDetailDO;
    }

    public final static SmsSendDetailDO convertToSmsSendDetailDO(SmsSendDetailInfo info) {
        if (info == null)
            return null;

        SmsSendDetailDO smsSendDetailDO = new SmsSendDetailDO();
        smsSendDetailDO.setSmsId(info.getSmsId());
        smsSendDetailDO.setTemplateAlias(info.getTemplateAlias());
        smsSendDetailDO.setReceiver(info.getReceiver());
        smsSendDetailDO.setContent(info.getContent());
        smsSendDetailDO.setCreateTime(info.getCreateTime());

        return smsSendDetailDO;
    }
}
