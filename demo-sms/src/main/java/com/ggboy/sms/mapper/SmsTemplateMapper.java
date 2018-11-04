package com.ggboy.sms.mapper;

import com.ggboy.sms.domain.DO.SmsTemplateDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface SmsTemplateMapper {
    @SelectProvider(type = Provider.class, method = "select")
    SmsTemplateDO selectOne(@Param("fields") String[] fields, @Param("smsTemplateDO") SmsTemplateDO smsTemplateDO);
}