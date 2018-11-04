package com.ggboy.sms.mapper;

import com.ggboy.common.query.Query;
import com.ggboy.sms.domain.DO.SmsSendDetailDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface SmsSendDetailMapper {
    String table = "sms_send_detail";

    @SelectProvider(type = Provider.class, method = "count")
    int count(Query<SmsSendDetailDO> query);

    @Insert("insert into " + table + " (sms_id, template_alias, receiver, content, create_time)" +
            "values (#{smsId,jdbcType=VARCHAR}, #{templateAlias,jdbcType=VARCHAR}, #{receiver,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, NOW())")
    int insert(SmsSendDetailDO record);
}

