package com.ggboy.sms.api;

import com.alibaba.fastjson.JSON;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.BusinessException;
import com.ggboy.common.exception.InternalException;
import com.ggboy.common.redis.RedisWrapper;
import com.ggboy.common.utils.JsonUtil;
import com.ggboy.common.utils.StringUtil;
import com.ggboy.common.utils.RandomUtil;
import com.ggboy.sms.common.constant.SmsErrorCodeConstant;
import com.ggboy.sms.common.constant.SmsPropertiesConstant;
import com.ggboy.sms.domain.info.SmsSendDetailInfo;
import com.ggboy.sms.domain.info.SmsTemplateInfo;
import com.ggboy.sms.esi.SendSmsProcessor;
import com.ggboy.sms.service.SmsSendService;
import com.ggboy.sms.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SmsSendApi {

    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private SmsTemplateService smsTemplateService;
    @Autowired
    private SendSmsProcessor sendSmsProcessor;
    @Autowired
    private RedisWrapper redisWrapper;

    public void sendConfirmationCode(String templateAlias, String receiver) {
        // TODO lock
        try {
            String codeKey = getCodeKey(templateAlias, receiver);
            String verifyTimesKey = getVerifyTimesKey(codeKey);

            String code = RandomUtil.produceConfirmationCode(SmsPropertiesConstant.getConfirmationCodeLength());

            Map<String, String> params = new HashMap<>(2);
            params.put("code", code);

            sendSms(templateAlias, receiver, params);

            redisWrapper.set(codeKey, code, SmsPropertiesConstant.getVerifyExpTime());
            redisWrapper.set(verifyTimesKey, "0", SmsPropertiesConstant.getVerifyExpTime());
            // TODO
            throw new BusinessException("OK", code, "send is frequently");
        } finally {
            // TODO unlock
        }
    }

    public boolean verifyConfirmationCode(String templateAlias, String receiver, String code, boolean clean) {
        String codeKey = getCodeKey(templateAlias, receiver);
        String verifyTimesKey = getVerifyTimesKey(codeKey);
        String verifyCode = redisWrapper.get(codeKey);

        if (StringUtil.isEmpty(verifyCode))
            return false;

        if (!verifyCode.equals(code)) {
            if (Integer.valueOf(redisWrapper.get(verifyTimesKey)) + 1 >= SmsPropertiesConstant.getVerifyMaxTime())
                cleanKey(codeKey);
            return false;
        }

        if (clean)
            cleanKey(codeKey);
        return true;
    }

    private void sendSms(String templateAlias, String receiver, Map<String, String> contentMap) {
        SmsTemplateInfo smsTemplateInfo = smsTemplateService.queryForSend(templateAlias);

        if (smsTemplateInfo == null)
            throw new InternalException(ErrorCodeConstant.PARAMETER_ERROR, "模板不存在", "alias is not exist");

        if (smsSendService.checkSendIsFrequently(templateAlias, receiver, smsTemplateInfo.getIntervalTime(), smsTemplateInfo.getIntervalFrequency()))
            throw new BusinessException(SmsErrorCodeConstant.SEND_FREQUENTLY, "频繁发送", "send is frequently");

        Map<String, String> params = JsonUtil.toMap(smsTemplateInfo.getExt1());
        params.put("receiver", receiver);
        params.putAll(contentMap);

//        String bizId = sendSmsProcessor.sendSms(params, SmsChannel.ali);
        String bizId = UUID.randomUUID().toString().replaceAll("-", "");

        if (StringUtil.isEmpty(bizId))
            throw new InternalException(SmsErrorCodeConstant.SEND_FREQUENTLY, "发送失败", "ali return null");

        SmsSendDetailInfo info = new SmsSendDetailInfo();
        info.setSmsId(bizId);
        info.setTemplateAlias(smsTemplateInfo.getTemplateAlias());
        info.setReceiver(receiver);
        info.setContent(JSON.toJSONString(contentMap));
        smsSendService.createSmsSendDetail(info);
    }

    private String getCodeKey(String templateAlias, String receiver) {
        return StringUtil.toString(templateAlias, "-", receiver);
    }

    private String getVerifyTimesKey(String codeKey) {
        return StringUtil.toString(codeKey, "-verify-times");
    }

    private void cleanKey(String codeKey) {
        redisWrapper.remove(codeKey);
        redisWrapper.remove(getVerifyTimesKey(codeKey));
    }
}