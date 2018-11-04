package com.ggboy.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface SequenceMapper {
    String columns = "current_value as value,increment as inc";
    String table = "sys_sequence";

    @Select("select " + columns + " from " + table + " where sequence_name = #{sequenceName,jdbcType=VARCHAR} for update")
    Map<String, Object> lockSequenceName(@Param("sequenceName") String sequenceName);

    @Update("update " + table + " SET current_value = current_value + increment WHERE sequence_name = #{sequenceName,jdbcType=VARCHAR}")
    int update(@Param("sequenceName") String sequenceName);
}