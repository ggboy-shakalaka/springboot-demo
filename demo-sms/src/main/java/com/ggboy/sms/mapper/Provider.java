package com.ggboy.sms.mapper;

import com.ggboy.common.constant.SymbolConstant;
import com.ggboy.common.query.Query;
import com.ggboy.common.utils.ArrayUtil;
import com.ggboy.sms.domain.DO.SmsSendDetailDO;
import com.ggboy.sms.domain.DO.SmsTemplateDO;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class Provider {
    private final String sms_send_detail_table = "sms_send_detail";

    public String count(Query<SmsSendDetailDO> query) {
        SQL sql = new SQL().SELECT("count(0)").FROM(sms_send_detail_table);
        if (query == null)
            return sql.toString();
        if (query.getStartCreateTime() != null)
            sql.WHERE("create_time >= #{startCreateTime, jdbcType=TIMESTAMP}");
        if (query.getQueryInfo() == null)
            return sql.toString();
        SmsSendDetailDO info = query.getQueryInfo();
        if (info.getTemplateAlias() != null)
            sql.WHERE("template_alias = #{queryInfo.templateAlias, jdbcType=VARCHAR}");
        if (info.getReceiver() != null)
            sql.WHERE("receiver = #{queryInfo.receiver, jdbcType=VARCHAR}");
        return sql.toString();
    }

    private static final String sms_template_table = "sms_template";

    public String select(final Map<String, Object> params) {
        return new SQL() {{
            SELECT(ArrayUtil.toString((Object[]) params.get("fields"), SymbolConstant.COMMA));
            FROM(sms_template_table);
            SmsTemplateDO smsTemplateDO = (SmsTemplateDO) params.get("smsTemplateDO");
            if (smsTemplateDO != null) {
                if (smsTemplateDO.getTemplateAlias() != null)
                    WHERE("template_alias >= #{smsTemplateDO.templateAlias, jdbcType=VARCHAR}");
                if (smsTemplateDO.getStatus() != null)
                    WHERE("status = #{smsTemplateDO.status, jdbcType=VARCHAR}");
            }
        }}.toString();
    }
}