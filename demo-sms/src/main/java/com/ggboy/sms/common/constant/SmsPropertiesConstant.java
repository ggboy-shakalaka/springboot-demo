package com.ggboy.sms.common.constant;

import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.InternalException;

public class SmsPropertiesConstant {
    private static boolean init = false;
    private static Integer CONFIRMATION_CODE_LENGTH;
    private static Integer VERIFY_MAX_TIME;
    private static Integer VERIFY_EXP_TIME;

    public static void init(Integer codeLength, Integer maxTime, Integer expTime) {
        if (init)
            throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "配置文件已被初始化");
        init = true;
        CONFIRMATION_CODE_LENGTH = codeLength;
        VERIFY_MAX_TIME = maxTime;
        VERIFY_EXP_TIME = expTime;
    }

    public static Integer getConfirmationCodeLength() {
        return CONFIRMATION_CODE_LENGTH;
    }

    public static Integer getVerifyMaxTime() {
        return VERIFY_MAX_TIME;
    }

    public static Integer getVerifyExpTime() {
        return VERIFY_EXP_TIME;
    }
}
