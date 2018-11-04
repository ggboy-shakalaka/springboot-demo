package com.ggboy.sms.common.enums;

import com.ggboy.common.utils.StringUtil;

public enum SmsTamplateStatus {
    enable,
    disable,
    ;

    public static SmsTamplateStatus getByCode(String code) {
        if (StringUtil.isEmpty(code))
            return null;

        for (SmsTamplateStatus item : SmsTamplateStatus.values()) {
            if (item.name().equals(code))
                return item;
        }

        return null;
    }
}
