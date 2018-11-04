package com.ggboy.sms.esi.ali;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ggboy.common.exception.InternalException;
import com.ggboy.sms.esi.BaseSendSmsProcessor;

import java.util.Map;

public class AliSendSmsProcessor implements BaseSendSmsProcessor {

    private final IAcsClient acsClient;

    public AliSendSmsProcessor(String product, String domain, String accessKeyId, String accessKeySecret) throws Exception {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        acsClient = new DefaultAcsClient(profile);
    }

    public String sendSms(Map<String, String> params) throws Exception {
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(params.remove("receiver"));
        request.setSignName(params.remove("signName"));
        request.setTemplateCode(params.remove("templateCode"));
        request.setTemplateParam(params == null ? null : JSON.toJSONString(params));
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        if ("OK".equals(sendSmsResponse.getCode()))
            return sendSmsResponse.getBizId();

        throw new InternalException(sendSmsResponse.getCode(), sendSmsResponse.getMessage(), sendSmsResponse.getRequestId());
    }
}
