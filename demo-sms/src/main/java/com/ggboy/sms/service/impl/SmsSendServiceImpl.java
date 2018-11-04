package com.ggboy.sms.service.impl;

import com.ggboy.common.exception.AssertException;
import com.ggboy.common.exception.InternalException;
import com.ggboy.common.query.Query;
import com.ggboy.sms.common.convert.SmsConvert;
import com.ggboy.sms.common.validator.SmsValidator;
import com.ggboy.sms.domain.DO.SmsSendDetailDO;
import com.ggboy.sms.domain.info.SmsSendDetailInfo;
import com.ggboy.sms.mapper.SmsSendDetailMapper;
import com.ggboy.sms.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private SmsSendDetailMapper smsSendDetailMapper;

    @Override
    public boolean checkSendIsFrequently(String templateAlias, String receiver, int time, int frequency) {
        try {
            if (time < 0)
                return false;
            SmsValidator.verifyCheckSendIsFrequentlyParam(templateAlias, receiver);

            SmsSendDetailDO smsSendDetailDO = SmsConvert.convertToCheckSendIsFrequentlyDO(templateAlias, receiver);
            Query<SmsSendDetailDO> query = new Query<>(smsSendDetailDO);
            query.setStartCreateTime(new Date(System.currentTimeMillis() - time * 1000));

            int count = smsSendDetailMapper.count(query);
            return count >= frequency;
        } catch (AssertException e) {
            throw new InternalException(e.getCode(),e.getMessage());
        }
    }

    @Override
    public int createSmsSendDetail(SmsSendDetailInfo smsSendDetailInfo) {
        try {
            SmsValidator.verifyInsertSmsSendDetail(smsSendDetailInfo);
            SmsSendDetailDO smsSendDetailDO = SmsConvert.convertToSmsSendDetailDO(smsSendDetailInfo);
            return smsSendDetailMapper.insert(smsSendDetailDO);
        } catch (AssertException e) {
            throw new InternalException(e.getCode(),e.getMessage());
        }
    }
}
