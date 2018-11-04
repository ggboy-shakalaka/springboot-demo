package com.ggboy.common.constant;

import com.ggboy.common.exception.InternalException;

public class PropertiesConstant {
    private static boolean init = false;
    private static Integer DEFAULT_SMS_PAGE_SIZE;
    private static String SEQUENCE_DEFAULT_NAME;

    public static void init(Integer pageSize, String sequenceName) {
        if (init)
            throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "配置文件已被初始化");
        init = true;
        DEFAULT_SMS_PAGE_SIZE = pageSize;
        SEQUENCE_DEFAULT_NAME = sequenceName;
    }

    public static Integer getDefaultSmsPageSize() {
        return DEFAULT_SMS_PAGE_SIZE;
    }

    public static String getSequenceDefaultName() {
        return SEQUENCE_DEFAULT_NAME;
    }
}
