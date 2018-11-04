package com.ggboy.sms.service;

import com.ggboy.sms.domain.info.SmsSendDetailInfo;

public interface SmsSendService {
    boolean checkSendIsFrequently(String templateAlias, String receiver, int time, int frequency);

    int createSmsSendDetail(SmsSendDetailInfo smsSendDetailInfo);
}